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
public class HrInterviewDtPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "INT_CODE")
    private String intCode;
    @Basic(optional = false)
    @Column(name = "ADVT_CODE")
    private String advtCode;
    @Basic(optional = false)
    @Column(name = "JOB_CODE")
    private String jobCode;
    @Basic(optional = false)
    @Column(name = "CAND_SRNO")
    private String candSrno;

    public HrInterviewDtPK() {
    }

    public HrInterviewDtPK(int compCode, String intCode, String advtCode, String jobCode, String candSrno) {
        this.compCode = compCode;
        this.intCode = intCode;
        this.advtCode = advtCode;
        this.jobCode = jobCode;
        this.candSrno = candSrno;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getIntCode() {
        return intCode;
    }

    public void setIntCode(String intCode) {
        this.intCode = intCode;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (intCode != null ? intCode.hashCode() : 0);
        hash += (advtCode != null ? advtCode.hashCode() : 0);
        hash += (jobCode != null ? jobCode.hashCode() : 0);
        hash += (candSrno != null ? candSrno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrInterviewDtPK)) {
            return false;
        }
        HrInterviewDtPK other = (HrInterviewDtPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.intCode == null && other.intCode != null) || (this.intCode != null && !this.intCode.equals(other.intCode))) {
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
        return true;
    }

    @Override
    public String toString() {
        return "entity.hrPayroll.HrInterviewDtPK[compCode=" + compCode + ", intCode=" + intCode + ", advtCode=" + advtCode + ", jobCode=" + jobCode + ", candSrno=" + candSrno + "]";
    }

}
