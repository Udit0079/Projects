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
public class HrConsultantPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "CONS_CODE")
    private String consCode;

    public HrConsultantPK() {
    }

    public HrConsultantPK(int compCode, String consCode) {
        this.compCode = compCode;
        this.consCode = consCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getConsCode() {
        return consCode;
    }

    public void setConsCode(String consCode) {
        this.consCode = consCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (consCode != null ? consCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrConsultantPK)) {
            return false;
        }
        HrConsultantPK other = (HrConsultantPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.consCode == null && other.consCode != null) || (this.consCode != null && !this.consCode.equals(other.consCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.HrConsultantPK[compCode=" + compCode + ", consCode=" + consCode + "]";
    }

}
