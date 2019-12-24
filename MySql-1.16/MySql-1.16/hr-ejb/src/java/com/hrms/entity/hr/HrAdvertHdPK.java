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
public class HrAdvertHdPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "ADVT_CODE")
    private String advtCode;
    @Basic(optional = false)
    @Column(name = "ADVT_DATE")
    private String advtDate;
    @Basic(optional = false)
    @Column(name = "JOB_CODE")
    private String jobCode;

    public HrAdvertHdPK() {
    }

    public HrAdvertHdPK(int compCode, String advtCode, String advtDate, String jobCode) {
        this.compCode = compCode;
        this.advtCode = advtCode;
        this.advtDate = advtDate;
        this.jobCode = jobCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getAdvtCode() {
        return advtCode;
    }

    public void setAdvtCode(String advtCode) {
        this.advtCode = advtCode;
    }

    public String getAdvtDate() {
        return advtDate;
    }

    public void setAdvtDate(String advtDate) {
        this.advtDate = advtDate;
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
        hash += (advtCode != null ? advtCode.hashCode() : 0);
        hash += (advtDate != null ? advtDate.hashCode() : 0);
        hash += (jobCode != null ? jobCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAdvertHdPK)) {
            return false;
        }
        HrAdvertHdPK other = (HrAdvertHdPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.advtCode == null && other.advtCode != null) || (this.advtCode != null && !this.advtCode.equals(other.advtCode))) {
            return false;
        }
        if ((this.advtDate == null && other.advtDate != null) || (this.advtDate != null && !this.advtDate.equals(other.advtDate))) {
            return false;
        }
        if ((this.jobCode == null && other.jobCode != null) || (this.jobCode != null && !this.jobCode.equals(other.jobCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrAdvertHdPK[compCode=" + compCode + ", advtCode=" + advtCode + ", advtDate=" + advtDate + ", jobCode=" + jobCode + "]";
    }

}
