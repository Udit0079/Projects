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
public class HrDataQualPK extends BaseEntity implements Serializable {
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
    @Column(name = "QUAL_CODE")
    private String qualCode;

    public HrDataQualPK() {
    }

    public HrDataQualPK(int compCode, String advtCode, String jobCode, String candSrno, String qualCode) {
        this.compCode = compCode;
        this.advtCode = advtCode;
        this.jobCode = jobCode;
        this.candSrno = candSrno;
        this.qualCode = qualCode;
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
        hash += (advtCode != null ? advtCode.hashCode() : 0);
        hash += (jobCode != null ? jobCode.hashCode() : 0);
        hash += (candSrno != null ? candSrno.hashCode() : 0);
        hash += (qualCode != null ? qualCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDataQualPK)) {
            return false;
        }
        HrDataQualPK other = (HrDataQualPK) object;
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
        if ((this.qualCode == null && other.qualCode != null) || (this.qualCode != null && !this.qualCode.equals(other.qualCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrDataQualPK[compCode=" + compCode + ", advtCode=" + advtCode + ", jobCode=" + jobCode + ", candSrno=" + candSrno + ", qualCode=" + qualCode + "]";
    }

}
