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
public class HrPersonnelTransportPK extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "COMP_CODE", nullable = false)
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE", nullable = false)
    private long empCode;
    @Basic(optional = false)
    @Column(name = "ROUTE_CODE", nullable = false, length = 10)
    private String routeCode;
    @Basic(optional = false)
    @Column(name = "BUS_NO", nullable = false, length = 10)
    private String busNo;

    public HrPersonnelTransportPK() {
    }

    public HrPersonnelTransportPK(int compCode, long empCode, String routeCode, String busNo) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.routeCode = routeCode;
        this.busNo = busNo;
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

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (routeCode != null ? routeCode.hashCode() : 0);
        hash += (busNo != null ? busNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelTransportPK)) {
            return false;
        }
        HrPersonnelTransportPK other = (HrPersonnelTransportPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.routeCode == null && other.routeCode != null) || (this.routeCode != null && !this.routeCode.equals(other.routeCode))) {
            return false;
        }
        if ((this.busNo == null && other.busNo != null) || (this.busNo != null && !this.busNo.equals(other.busNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.personnel.HrPersonnelTransportPK[compCode=" + compCode + ", empCode=" + empCode + ", routeCode=" + routeCode + ", busNo=" + busNo + "]";
    }
}
