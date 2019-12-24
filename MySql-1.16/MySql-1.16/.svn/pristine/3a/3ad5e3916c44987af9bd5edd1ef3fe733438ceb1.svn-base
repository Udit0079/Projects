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
public class HrDirectRecPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "ARNO")
    private String arno;

    public HrDirectRecPK() {
    }

    public HrDirectRecPK(int compCode, String arno) {
        this.compCode = compCode;
        this.arno = arno;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getArno() {
        return arno;
    }

    public void setArno(String arno) {
        this.arno = arno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (arno != null ? arno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDirectRecPK)) {
            return false;
        }
        HrDirectRecPK other = (HrDirectRecPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.arno == null && other.arno != null) || (this.arno != null && !this.arno.equals(other.arno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrDirectRecPK[compCode=" + compCode + ", arno=" + arno + "]";
    }

}
