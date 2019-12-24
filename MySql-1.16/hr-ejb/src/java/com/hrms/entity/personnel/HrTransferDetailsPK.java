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
public class HrTransferDetailsPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "AR_NO")
    private String arNo;

    public HrTransferDetailsPK() {
    }

    public HrTransferDetailsPK(int compCode, String arNo) {
        this.compCode = compCode;
        this.arNo = arNo;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getArNo() {
        return arNo;
    }

    public void setArNo(String arNo) {
        this.arNo = arNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (arNo != null ? arNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTransferDetailsPK)) {
            return false;
        }
        HrTransferDetailsPK other = (HrTransferDetailsPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.arNo == null && other.arNo != null) || (this.arNo != null && !this.arNo.equals(other.arNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrTransferDetailsPK[compCode=" + compCode + ", arNo=" + arNo + "]";
    }

}
