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
public class HrMembershipDetailPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "MEM_NO")
    private int memNo;

    public HrMembershipDetailPK() {
    }

    public HrMembershipDetailPK(int compCode, long empCode, int memNo) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.memNo = memNo;
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

    public int getMemNo() {
        return memNo;
    }

    public void setMemNo(int memNo) {
        this.memNo = memNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (int) memNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMembershipDetailPK)) {
            return false;
        }
        HrMembershipDetailPK other = (HrMembershipDetailPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if (this.memNo != other.memNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrMembershipDetailPK[compCode=" + compCode + ", empCode=" + empCode + ", memNo=" + memNo + "]";
    }

}
