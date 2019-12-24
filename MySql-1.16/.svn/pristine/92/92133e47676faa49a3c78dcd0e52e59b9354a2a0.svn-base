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
public class CBSCustCurrencyInfoPK  implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CustomerId")
    private String customerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CurrencyCodeType")
    private String currencyCodeType;

    public CBSCustCurrencyInfoPK() {
    }

    public CBSCustCurrencyInfoPK(String customerId, String currencyCodeType) {
        this.customerId = customerId;
        this.currencyCodeType = currencyCodeType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCurrencyCodeType() {
        return currencyCodeType;
    }

    public void setCurrencyCodeType(String currencyCodeType) {
        this.currencyCodeType = currencyCodeType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        hash += (currencyCodeType != null ? currencyCodeType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBSCustCurrencyInfoPK)) {
            return false;
        }
        CBSCustCurrencyInfoPK other = (CBSCustCurrencyInfoPK) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        if ((this.currencyCodeType == null && other.currencyCodeType != null) || (this.currencyCodeType != null && !this.currencyCodeType.equals(other.currencyCodeType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CBSCustCurrencyInfoPK[ customerId=" + customerId + ", currencyCodeType=" + currencyCodeType + " ]";
    }
    
}
