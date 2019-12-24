/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Administrator
 */
@Embeddable
public class HrMstDeptSubdeptPK extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "DEPT_CODE")
    private String deptCode;
    @Basic(optional = false)
    @Column(name = "SUB_DEPT_CODE")
    private String subDeptCode;

    public HrMstDeptSubdeptPK() {
    }

    public HrMstDeptSubdeptPK(int compCode, String deptCode, String subDeptCode) {
        this.compCode = compCode;
        this.deptCode = deptCode;
        this.subDeptCode = subDeptCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getSubDeptCode() {
        return subDeptCode;
    }

    public void setSubDeptCode(String subDeptCode) {
        this.subDeptCode = subDeptCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (deptCode != null ? deptCode.hashCode() : 0);
        hash += (subDeptCode != null ? subDeptCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstDeptSubdeptPK)) {
            return false;
        }
        HrMstDeptSubdeptPK other = (HrMstDeptSubdeptPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.deptCode == null && other.deptCode != null) || (this.deptCode != null && !this.deptCode.equals(other.deptCode))) {
            return false;
        }
        if ((this.subDeptCode == null && other.subDeptCode != null) || (this.subDeptCode != null && !this.subDeptCode.equals(other.subDeptCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.hr.HrMstDeptSubdeptPK[compCode=" + compCode + ", deptCode=" + deptCode + ", subDeptCode=" + subDeptCode + "]";
    }
}
