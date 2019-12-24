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
public class HrOrgChartPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "DESG_CODE")
    private String desgCode;

    public HrOrgChartPK() {
    }

    public HrOrgChartPK(int compCode, String desgCode) {
        this.compCode = compCode;
        this.desgCode = desgCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (desgCode != null ? desgCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrOrgChartPK)) {
            return false;
        }
        HrOrgChartPK other = (HrOrgChartPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.desgCode == null && other.desgCode != null) || (this.desgCode != null && !this.desgCode.equals(other.desgCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrOrgChartPK[compCode=" + compCode + ", desgCode=" + desgCode + "]";
    }

}
