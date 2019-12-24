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
public class CbsSchemeFeeOrChargesDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "CHARGE_TYPE")
    private String chargeType;

    public CbsSchemeFeeOrChargesDetailsPK() {
    }

    public CbsSchemeFeeOrChargesDetailsPK(String schemeCode, String chargeType) {
        this.schemeCode = schemeCode;
        this.chargeType = chargeType;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (chargeType != null ? chargeType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeFeeOrChargesDetailsPK)) {
            return false;
        }
        CbsSchemeFeeOrChargesDetailsPK other = (CbsSchemeFeeOrChargesDetailsPK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.chargeType == null && other.chargeType != null) || (this.chargeType != null && !this.chargeType.equals(other.chargeType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeFeeOrChargesDetailsPK[ schemeCode=" + schemeCode + ", chargeType=" + chargeType + " ]";
    }
    
}
