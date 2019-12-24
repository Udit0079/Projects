/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author admin
 */
@Embeddable
public class HrExitInterviewHdPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;

    public HrExitInterviewHdPK() {
    }

    public HrExitInterviewHdPK(int compCode, long empCode) {
        this.compCode = compCode;
        this.empCode = empCode;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrExitInterviewHdPK)) {
            return false;
        }
        HrExitInterviewHdPK other = (HrExitInterviewHdPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.personnel.HrExitInterviewHdPK[compCode=" + compCode + ", empCode=" + empCode + "]";
    }

}
