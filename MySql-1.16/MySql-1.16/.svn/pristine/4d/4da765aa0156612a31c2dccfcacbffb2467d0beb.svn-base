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
public class HrMstStructPK extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "STRUCT_CODE")
    private String structCode;

    public HrMstStructPK() {
    }

    public HrMstStructPK(int compCode, String structCode) {
        this.compCode = compCode;
        this.structCode = structCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getStructCode() {
        return structCode;
    }

    public void setStructCode(String structCode) {
        this.structCode = structCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (structCode != null ? structCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstStructPK)) {
            return false;
        }
        HrMstStructPK other = (HrMstStructPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.structCode == null && other.structCode != null) || (this.structCode != null && !this.structCode.equals(other.structCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.hr.HrMstStructPK[compCode=" + compCode + ", structCode=" + structCode + "]";
    }
}
