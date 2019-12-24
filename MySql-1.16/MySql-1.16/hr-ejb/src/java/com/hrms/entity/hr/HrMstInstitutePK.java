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
public class HrMstInstitutePK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "INST_CODE")
    private String instCode;

    public HrMstInstitutePK() {
    }

    public HrMstInstitutePK(int compCode, String instCode) {
        this.compCode = compCode;
        this.instCode = instCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (instCode != null ? instCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstInstitutePK)) {
            return false;
        }
        HrMstInstitutePK other = (HrMstInstitutePK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.instCode == null && other.instCode != null) || (this.instCode != null && !this.instCode.equals(other.instCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrMstInstitutePK[compCode=" + compCode + ", instCode=" + instCode + "]";
    }

}
