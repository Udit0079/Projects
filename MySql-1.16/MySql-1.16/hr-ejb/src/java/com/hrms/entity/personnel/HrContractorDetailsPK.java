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
public class HrContractorDetailsPK extends  BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "CONT_CODE")
    private String contCode;

    public HrContractorDetailsPK() {
    }

    public HrContractorDetailsPK(int compCode, String contCode) {
        this.compCode = compCode;
        this.contCode = contCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getContCode() {
        return contCode;
    }

    public void setContCode(String contCode) {
        this.contCode = contCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (contCode != null ? contCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrContractorDetailsPK)) {
            return false;
        }
        HrContractorDetailsPK other = (HrContractorDetailsPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.contCode == null && other.contCode != null) || (this.contCode != null && !this.contCode.equals(other.contCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrContractorDetailsPK[compCode=" + compCode + ", contCode=" + contCode + "]";
    }

}
