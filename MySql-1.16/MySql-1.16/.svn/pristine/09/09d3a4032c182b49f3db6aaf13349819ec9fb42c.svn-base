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
public class HrDataReferencePK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "ADVT_CODE")
    private String advtCode;
    @Basic(optional = false)
    @Column(name = "JOB_CODE")
    private String jobCode;
    @Basic(optional = false)
    @Column(name = "CAND_SRNO")
    private String candSrno;
    @Basic(optional = false)
    @Column(name = "REFER_NAME")
    private String referName;

    public HrDataReferencePK() {
    }

    public HrDataReferencePK(int compCode, String advtCode, String jobCode, String candSrno, String referName) {
        this.compCode = compCode;
        this.advtCode = advtCode;
        this.jobCode = jobCode;
        this.candSrno = candSrno;
        this.referName = referName;
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

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getCandSrno() {
        return candSrno;
    }

    public void setCandSrno(String candSrno) {
        this.candSrno = candSrno;
    }

    public String getReferName() {
        return referName;
    }

    public void setReferName(String referName) {
        this.referName = referName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (advtCode != null ? advtCode.hashCode() : 0);
        hash += (jobCode != null ? jobCode.hashCode() : 0);
        hash += (candSrno != null ? candSrno.hashCode() : 0);
        hash += (referName != null ? referName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDataReferencePK)) {
            return false;
        }
        HrDataReferencePK other = (HrDataReferencePK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.advtCode == null && other.advtCode != null) || (this.advtCode != null && !this.advtCode.equals(other.advtCode))) {
            return false;
        }
        if ((this.jobCode == null && other.jobCode != null) || (this.jobCode != null && !this.jobCode.equals(other.jobCode))) {
            return false;
        }
        if ((this.candSrno == null && other.candSrno != null) || (this.candSrno != null && !this.candSrno.equals(other.candSrno))) {
            return false;
        }
        if ((this.referName == null && other.referName != null) || (this.referName != null && !this.referName.equals(other.referName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrDataReferencePK[compCode=" + compCode + ", advtCode=" + advtCode + ", jobCode=" + jobCode + ", candSrno=" + candSrno + ", referName=" + referName + "]";
    }

}
