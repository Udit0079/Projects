/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

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
public class CbsSchemeDepositInterestDefinitionDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "INTEREST_METHOD")
    private String interestMethod;

    public CbsSchemeDepositInterestDefinitionDetailsPK() {
    }

    public CbsSchemeDepositInterestDefinitionDetailsPK(String schemeCode, String interestMethod) {
        this.schemeCode = schemeCode;
        this.interestMethod = interestMethod;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getInterestMethod() {
        return interestMethod;
    }

    public void setInterestMethod(String interestMethod) {
        this.interestMethod = interestMethod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (interestMethod != null ? interestMethod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeDepositInterestDefinitionDetailsPK)) {
            return false;
        }
        CbsSchemeDepositInterestDefinitionDetailsPK other = (CbsSchemeDepositInterestDefinitionDetailsPK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.interestMethod == null && other.interestMethod != null) || (this.interestMethod != null && !this.interestMethod.equals(other.interestMethod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeDepositInterestDefinitionDetailsPK[ schemeCode=" + schemeCode + ", interestMethod=" + interestMethod + " ]";
    }
    
}
