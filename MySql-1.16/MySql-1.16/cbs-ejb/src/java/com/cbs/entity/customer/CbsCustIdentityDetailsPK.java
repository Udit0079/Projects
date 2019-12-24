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
public class CbsCustIdentityDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "IdentificationType")
    private String identificationType;

    public CbsCustIdentityDetailsPK() {
    }

    public CbsCustIdentityDetailsPK(String customerId, String identificationType) {
        this.customerId = customerId;
        this.identificationType = identificationType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        hash += (identificationType != null ? identificationType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsCustIdentityDetailsPK)) {
            return false;
        }
        CbsCustIdentityDetailsPK other = (CbsCustIdentityDetailsPK) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        if ((this.identificationType == null && other.identificationType != null) || (this.identificationType != null && !this.identificationType.equals(other.identificationType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsCustIdentityDetailsPK[ customerId=" + customerId + ", identificationType=" + identificationType + " ]";
    }
    
}
