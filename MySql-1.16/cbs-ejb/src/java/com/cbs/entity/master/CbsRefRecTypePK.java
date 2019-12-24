/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.master;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Embeddable
public class CbsRefRecTypePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "REF_REC_NO")
    private String refRecNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "REF_CODE")
    private String refCode;

    public CbsRefRecTypePK() {
    }

    public CbsRefRecTypePK(String refRecNo, String refCode) {
        this.refRecNo = refRecNo;
        this.refCode = refCode;
    }

    public String getRefRecNo() {
        return refRecNo;
    }

    public void setRefRecNo(String refRecNo) {
        this.refRecNo = refRecNo;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (refRecNo != null ? refRecNo.hashCode() : 0);
        hash += (refCode != null ? refCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsRefRecTypePK)) {
            return false;
        }
        CbsRefRecTypePK other = (CbsRefRecTypePK) object;
        if ((this.refRecNo == null && other.refRecNo != null) || (this.refRecNo != null && !this.refRecNo.equals(other.refRecNo))) {
            return false;
        }
        if ((this.refCode == null && other.refCode != null) || (this.refCode != null && !this.refCode.equals(other.refCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsRefRecTypePK[ refRecNo=" + refRecNo + ", refCode=" + refCode + " ]";
    }
    
}
