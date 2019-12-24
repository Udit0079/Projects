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
public class HrExitInterviewDtPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "REASON_CODE")
    private String reasonCode;

    public HrExitInterviewDtPK() {
    }

    public HrExitInterviewDtPK(int compCode, long empCode, String reasonCode) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.reasonCode = reasonCode;
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

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (reasonCode != null ? reasonCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrExitInterviewDtPK)) {
            return false;
        }
        HrExitInterviewDtPK other = (HrExitInterviewDtPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.reasonCode == null && other.reasonCode != null) || (this.reasonCode != null && !this.reasonCode.equals(other.reasonCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.personnel.HrExitInterviewDtPK[compCode=" + compCode + ", empCode=" + empCode + ", reasonCode=" + reasonCode + "]";
    }

}
