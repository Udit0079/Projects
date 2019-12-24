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
public class HrMstBusPK extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "BUS_NO")
    private String busNo;

    public HrMstBusPK() {
    }

    public HrMstBusPK(int compCode, String busNo) {
        this.compCode = compCode;
        this.busNo = busNo;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
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
        hash += (busNo != null ? busNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstBusPK)) {
            return false;
        }
        HrMstBusPK other = (HrMstBusPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.busNo == null && other.busNo != null) || (this.busNo != null && !this.busNo.equals(other.busNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.hr.HrMstBusPK[compCode=" + compCode + ", busNo=" + busNo + "]";
    }
}
