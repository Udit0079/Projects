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
public class HrDatabankPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "CAND_ID")
    private String candId;
    @Basic(optional = false)
    @Column(name = "ADVT_CODE")
    private String advtCode;
    @Basic(optional = false)
    @Column(name = "JOB_CODE")
    private String jobCode;

    public HrDatabankPK() {
    }

    public HrDatabankPK(int compCode, String candId, String advtCode, String jobCode) {
        this.compCode = compCode;
        this.candId = candId;
        this.advtCode = advtCode;
        this.jobCode = jobCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getCandId() {
        return candId;
    }

    public void setCandId(String candId) {
        this.candId = candId;
    }

    public String getAdvtCode() {
        return advtCode;
    }

    public void setAdvtCode(String advtCode) {
        this.advtCode = advtCode;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (candId != null ? candId.hashCode() : 0);
        hash += (advtCode != null ? advtCode.hashCode() : 0);
        hash += (jobCode != null ? jobCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDatabankPK)) {
            return false;
        }
        HrDatabankPK other = (HrDatabankPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.candId == null && other.candId != null) || (this.candId != null && !this.candId.equals(other.candId))) {
            return false;
        }
        if ((this.advtCode == null && other.advtCode != null) || (this.advtCode != null && !this.advtCode.equals(other.advtCode))) {
            return false;
        }
        if ((this.jobCode == null && other.jobCode != null) || (this.jobCode != null && !this.jobCode.equals(other.jobCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hrPayroll.HrDatabankPK[compCode=" + compCode + ", candId=" + candId + ", advtCode=" + advtCode + ", jobCode=" + jobCode + "]";
    }

}
