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
public class HrPersonnelQualificationPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "QUAL_CODE")
    private String qualCode;

    public HrPersonnelQualificationPK() {
    }

    public HrPersonnelQualificationPK(int compCode, long empCode, String qualCode) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.qualCode = qualCode;
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

    public String getQualCode() {
        return qualCode;
    }

    public void setQualCode(String qualCode) {
        this.qualCode = qualCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (qualCode != null ? qualCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelQualificationPK)) {
            return false;
        }
        HrPersonnelQualificationPK other = (HrPersonnelQualificationPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.qualCode == null && other.qualCode != null) || (this.qualCode != null && !this.qualCode.equals(other.qualCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPersonnelQualificationPK[compCode=" + compCode + ", empCode=" + empCode + ", qualCode=" + qualCode + "]";
    }

}
