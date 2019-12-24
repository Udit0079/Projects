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
public class HrApprisalPerformanceDtPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "PERF_CODE")
    private String perfCode;
    @Basic(optional = false)
    @Column(name = "RATING_CODE")
    private String ratingCode;
    @Basic(optional = false)
    @Column(name = "APPRAISAL_DT")
    private String appraisalDt;

    public HrApprisalPerformanceDtPK() {
    }

    public HrApprisalPerformanceDtPK(int compCode, long empCode, String perfCode, String ratingCode, String appraisalDt) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.perfCode = perfCode;
        this.ratingCode = ratingCode;
        this.appraisalDt = appraisalDt;
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

    public String getPerfCode() {
        return perfCode;
    }

    public void setPerfCode(String perfCode) {
        this.perfCode = perfCode;
    }

    public String getRatingCode() {
        return ratingCode;
    }

    public void setRatingCode(String ratingCode) {
        this.ratingCode = ratingCode;
    }

    public String getAppraisalDt() {
        return appraisalDt;
    }

    public void setAppraisalDt(String appraisalDt) {
        this.appraisalDt = appraisalDt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (perfCode != null ? perfCode.hashCode() : 0);
        hash += (ratingCode != null ? ratingCode.hashCode() : 0);
        hash += (appraisalDt != null ? appraisalDt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrApprisalPerformanceDtPK)) {
            return false;
        }
        HrApprisalPerformanceDtPK other = (HrApprisalPerformanceDtPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.perfCode == null && other.perfCode != null) || (this.perfCode != null && !this.perfCode.equals(other.perfCode))) {
            return false;
        }
        if ((this.ratingCode == null && other.ratingCode != null) || (this.ratingCode != null && !this.ratingCode.equals(other.ratingCode))) {
            return false;
        }
        if ((this.appraisalDt == null && other.appraisalDt != null) || (this.appraisalDt != null && !this.appraisalDt.equals(other.appraisalDt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrApprisalPerformanceDtPK[compCode=" + compCode + ", empCode=" + empCode + ", perfCode=" + perfCode + ", ratingCode=" + ratingCode + ", appraisalDt=" + appraisalDt + "]";
    }

}
