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
public class HrInterviewHdPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "INT_CODE")
    private String intCode;

    public HrInterviewHdPK() {
    }

    public HrInterviewHdPK(int compCode, String intCode) {
        this.compCode = compCode;
        this.intCode = intCode;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (intCode != null ? intCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrInterviewHdPK)) {
            return false;
        }
        HrInterviewHdPK other = (HrInterviewHdPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.intCode == null && other.intCode != null) || (this.intCode != null && !this.intCode.equals(other.intCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hrPayroll.HrInterviewHdPK[compCode=" + compCode + ", intCode=" + intCode + "]";
    }

}
