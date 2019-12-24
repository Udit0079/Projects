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
 * @author root
 */
@Embeddable
public class CbsRelatedPersonsDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Person_SrNo")
    private int personSrNo;

    public CbsRelatedPersonsDetailsPK() {
    }

    public CbsRelatedPersonsDetailsPK(String customerId, int personSrNo) {
        this.customerId = customerId;
        this.personSrNo = personSrNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getPersonSrNo() {
        return personSrNo;
    }

    public void setPersonSrNo(int personSrNo) {
        this.personSrNo = personSrNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        hash += (int) personSrNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsRelatedPersonsDetailsPK)) {
            return false;
        }
        CbsRelatedPersonsDetailsPK other = (CbsRelatedPersonsDetailsPK) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        if (this.personSrNo != other.personSrNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsRelatedPersonsDetailsPK[ customerId=" + customerId + ", personSrNo=" + personSrNo + " ]";
    }
    
}
