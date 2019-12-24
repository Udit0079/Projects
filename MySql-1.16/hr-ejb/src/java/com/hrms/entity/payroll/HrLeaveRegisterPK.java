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
public class HrLeaveRegisterPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "LEAV_REG_CODE")
    private String leavRegCode;

    public HrLeaveRegisterPK() {
    }

    public HrLeaveRegisterPK(int compCode, long empCode, String leavRegCode) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.leavRegCode = leavRegCode;
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

    public String getLeavRegCode() {
        return leavRegCode;
    }

    public void setLeavRegCode(String leavRegCode) {
        this.leavRegCode = leavRegCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (leavRegCode != null ? leavRegCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLeaveRegisterPK)) {
            return false;
        }
        HrLeaveRegisterPK other = (HrLeaveRegisterPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.leavRegCode == null && other.leavRegCode != null) || (this.leavRegCode != null && !this.leavRegCode.equals(other.leavRegCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrLeaveRegisterPK[compCode=" + compCode + ", empCode=" + empCode + ", leavRegCode=" + leavRegCode + "]";
    }

}
