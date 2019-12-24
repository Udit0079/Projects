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
public class HrEmpLoanHdPK extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "EMP_LOAN_NO")
    private long empLoanNo;
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "LOAN_TYPE")
    private String loanType;

    public HrEmpLoanHdPK() {
    }

    public HrEmpLoanHdPK(long empLoanNo, int compCode, long empCode, String loanType) {
        this.empLoanNo = empLoanNo;
        this.compCode = compCode;
        this.empCode = empCode;
        this.loanType = loanType;
    }

    public long getEmpLoanNo() {
        return empLoanNo;
    }

    public void setEmpLoanNo(long empLoanNo) {
        this.empLoanNo = empLoanNo;
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

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) empLoanNo;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (loanType != null ? loanType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpLoanHdPK)) {
            return false;
        }
        HrEmpLoanHdPK other = (HrEmpLoanHdPK) object;
        if (this.empLoanNo != other.empLoanNo) {
            return false;
        }
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.loanType == null && other.loanType != null) || (this.loanType != null && !this.loanType.equals(other.loanType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.personnel.HrEmpLoanHdPK[empLoanNo=" + empLoanNo + ", compCode=" + compCode + ", empCode=" + empCode + ", loanType=" + loanType + "]";
    }
}
