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
public class HrManpowerPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "YEAR")
    private int year;
    @Basic(optional = false)
    @Column(name = "MONTH")
    private String month;
    @Basic(optional = false)
    @Column(name = "ZONE")
    private String zone;
    @Basic(optional = false)
    @Column(name = "DEPT_CODE")
    private String deptCode;

    public HrManpowerPK() {
    }

    public HrManpowerPK(int compCode, int year, String month, String zone, String deptCode) {
        this.compCode = compCode;
        this.year = year;
        this.month = month;
        this.zone = zone;
        this.deptCode = deptCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) year;
        hash += (month != null ? month.hashCode() : 0);
        hash += (zone != null ? zone.hashCode() : 0);
        hash += (deptCode != null ? deptCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrManpowerPK)) {
            return false;
        }
        HrManpowerPK other = (HrManpowerPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        if ((this.month == null && other.month != null) || (this.month != null && !this.month.equals(other.month))) {
            return false;
        }
        if ((this.zone == null && other.zone != null) || (this.zone != null && !this.zone.equals(other.zone))) {
            return false;
        }
        if ((this.deptCode == null && other.deptCode != null) || (this.deptCode != null && !this.deptCode.equals(other.deptCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrManpowerPK[compCode=" + compCode + ", year=" + year + ", month=" + month + ", zone=" + zone + ", deptCode=" + deptCode + "]";
    }

}
