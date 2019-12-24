/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Embeddable
public class NeftOwDetailsHisPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "UniqueCustomerRefNo")
    private String uniqueCustomerRefNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SNo")
    private long sNo;

    public NeftOwDetailsHisPK() {
    }

    public NeftOwDetailsHisPK(String uniqueCustomerRefNo, long sNo) {
        this.uniqueCustomerRefNo = uniqueCustomerRefNo;
        this.sNo = sNo;
    }

    public String getUniqueCustomerRefNo() {
        return uniqueCustomerRefNo;
    }

    public void setUniqueCustomerRefNo(String uniqueCustomerRefNo) {
        this.uniqueCustomerRefNo = uniqueCustomerRefNo;
    }

    public long getSNo() {
        return sNo;
    }

    public void setSNo(long sNo) {
        this.sNo = sNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uniqueCustomerRefNo != null ? uniqueCustomerRefNo.hashCode() : 0);
        hash += (int) sNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NeftOwDetailsHisPK)) {
            return false;
        }
        NeftOwDetailsHisPK other = (NeftOwDetailsHisPK) object;
        if ((this.uniqueCustomerRefNo == null && other.uniqueCustomerRefNo != null) || (this.uniqueCustomerRefNo != null && !this.uniqueCustomerRefNo.equals(other.uniqueCustomerRefNo))) {
            return false;
        }
        if (this.sNo != other.sNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.NeftOwDetailsHisPK[ uniqueCustomerRefNo=" + uniqueCustomerRefNo + ", sNo=" + sNo + " ]";
    }
    
}
