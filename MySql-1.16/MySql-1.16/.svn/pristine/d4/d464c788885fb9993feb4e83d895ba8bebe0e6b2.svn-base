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
public class HrTrainingPlanPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "TRNG_CODE")
    private String trngCode;
    @Basic(optional = false)
    @Column(name = "PROG_CODE")
    private String progCode;
    @Basic(optional = false)
    @Column(name = "DATE_FROM")
    private String dateFrom;
    @Basic(optional = false)
    @Column(name = "DATE_TO")
    private String dateTo;

    public HrTrainingPlanPK() {
    }

    public HrTrainingPlanPK(int compCode, long empCode, String trngCode, String progCode, String dateFrom, String dateTo) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.trngCode = trngCode;
        this.progCode = progCode;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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

    public String getTrngCode() {
        return trngCode;
    }

    public void setTrngCode(String trngCode) {
        this.trngCode = trngCode;
    }

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (trngCode != null ? trngCode.hashCode() : 0);
        hash += (progCode != null ? progCode.hashCode() : 0);
        hash += (dateFrom != null ? dateFrom.hashCode() : 0);
        hash += (dateTo != null ? dateTo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTrainingPlanPK)) {
            return false;
        }
        HrTrainingPlanPK other = (HrTrainingPlanPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.trngCode == null && other.trngCode != null) || (this.trngCode != null && !this.trngCode.equals(other.trngCode))) {
            return false;
        }
        if ((this.progCode == null && other.progCode != null) || (this.progCode != null && !this.progCode.equals(other.progCode))) {
            return false;
        }
        if ((this.dateFrom == null && other.dateFrom != null) || (this.dateFrom != null && !this.dateFrom.equals(other.dateFrom))) {
            return false;
        }
        if ((this.dateTo == null && other.dateTo != null) || (this.dateTo != null && !this.dateTo.equals(other.dateTo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrTrainingPlanPK[compCode=" + compCode + ", empCode=" + empCode + ", trngCode=" + trngCode + ", progCode=" + progCode + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
    }

}
