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
 * @author Administrator
 */
@Embeddable
public class HrMstRoutePK extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "ROUTE_CODE")
    private String routeCode;

    public HrMstRoutePK() {
    }

    public HrMstRoutePK(int compCode, String routeCode) {
        this.compCode = compCode;
        this.routeCode = routeCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (routeCode != null ? routeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstRoutePK)) {
            return false;
        }
        HrMstRoutePK other = (HrMstRoutePK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.routeCode == null && other.routeCode != null) || (this.routeCode != null && !this.routeCode.equals(other.routeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.hr.HrMstRoutePK[compCode=" + compCode + ", routeCode=" + routeCode + "]";
    }
}
