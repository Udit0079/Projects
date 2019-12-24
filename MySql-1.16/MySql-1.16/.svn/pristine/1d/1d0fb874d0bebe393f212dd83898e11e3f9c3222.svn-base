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
 * @author root
 */
@Embeddable
public class HrTrainingExecutionPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "TRNGEXEC_CODE")
    private String trngexecCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;

    public HrTrainingExecutionPK() {
    }

    public HrTrainingExecutionPK(int compCode, String trngexecCode, long empCode) {
        this.compCode = compCode;
        this.trngexecCode = trngexecCode;
        this.empCode = empCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getTrngexecCode() {
        return trngexecCode;
    }

    public void setTrngexecCode(String trngexecCode) {
        this.trngexecCode = trngexecCode;
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
        hash += (trngexecCode != null ? trngexecCode.hashCode() : 0);
        hash += (int) empCode;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTrainingExecutionPK)) {
            return false;
        }
        HrTrainingExecutionPK other = (HrTrainingExecutionPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.trngexecCode == null && other.trngexecCode != null) || (this.trngexecCode != null && !this.trngexecCode.equals(other.trngexecCode))) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrTrainingExecutionPK[compCode=" + compCode + ", trngexecCode=" + trngexecCode + ", empCode=" + empCode + "]";
    }

}
