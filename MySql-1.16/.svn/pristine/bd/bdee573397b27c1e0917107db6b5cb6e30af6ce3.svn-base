/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.payroll;

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
public class HrHolidayMasterPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "HOLIDAY_CODE")
    private long holidayCode;

    public HrHolidayMasterPK() {
    }

    public HrHolidayMasterPK(int compCode, long holidayCode) {
        this.compCode = compCode;
        this.holidayCode = holidayCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public long getHolidayCode() {
        return holidayCode;
    }

    public void setHolidayCode(long holidayCode) {
        this.holidayCode = holidayCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) holidayCode;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrHolidayMasterPK)) {
            return false;
        }
        HrHolidayMasterPK other = (HrHolidayMasterPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.holidayCode != other.holidayCode) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrHolidayMasterPK[compCode=" + compCode + ", holidayCode=" + holidayCode + "]";
    }

}
