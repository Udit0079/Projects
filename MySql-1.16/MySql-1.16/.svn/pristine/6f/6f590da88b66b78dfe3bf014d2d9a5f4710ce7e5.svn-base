/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.customer;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ROHIT KRISHNA
 */
@Embeddable
public class CustomeridPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "CustId")
    private long custId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "Acno")
    private String acno;

    public CustomeridPK() {
    }

    public CustomeridPK(long custId, String acno) {
        this.custId = custId;
        this.acno = acno;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) custId;
        hash += (acno != null ? acno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomeridPK)) {
            return false;
        }
        CustomeridPK other = (CustomeridPK) object;
        if (this.custId != other.custId) {
            return false;
        }
        if ((this.acno == null && other.acno != null) || (this.acno != null && !this.acno.equals(other.acno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CustomeridPK[ custId=" + custId + ", acno=" + acno + " ]";
    }
    
}
