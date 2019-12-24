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
public class HrPersonnelHobbyPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "HOBBY_NAME")
    private String hobbyName;

    public HrPersonnelHobbyPK() {
    }

    public HrPersonnelHobbyPK(int compCode, long empCode, String hobbyName) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.hobbyName = hobbyName;
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

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (hobbyName != null ? hobbyName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelHobbyPK)) {
            return false;
        }
        HrPersonnelHobbyPK other = (HrPersonnelHobbyPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.hobbyName == null && other.hobbyName != null) || (this.hobbyName != null && !this.hobbyName.equals(other.hobbyName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPersonnelHobbyPK[compCode=" + compCode + ", empCode=" + empCode + ", hobbyName=" + hobbyName + "]";
    }

}
