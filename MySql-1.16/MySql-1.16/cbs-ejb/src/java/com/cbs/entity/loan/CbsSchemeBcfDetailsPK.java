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
public class CbsSchemeBcfDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "FIXED_CHARGE_EVENT_ID")
    private String fixedChargeEventId;

    public CbsSchemeBcfDetailsPK() {
    }

    public CbsSchemeBcfDetailsPK(String schemeCode, String fixedChargeEventId) {
        this.schemeCode = schemeCode;
        this.fixedChargeEventId = fixedChargeEventId;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getFixedChargeEventId() {
        return fixedChargeEventId;
    }

    public void setFixedChargeEventId(String fixedChargeEventId) {
        this.fixedChargeEventId = fixedChargeEventId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (fixedChargeEventId != null ? fixedChargeEventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeBcfDetailsPK)) {
            return false;
        }
        CbsSchemeBcfDetailsPK other = (CbsSchemeBcfDetailsPK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.fixedChargeEventId == null && other.fixedChargeEventId != null) || (this.fixedChargeEventId != null && !this.fixedChargeEventId.equals(other.fixedChargeEventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeBcfDetailsPK[ schemeCode=" + schemeCode + ", fixedChargeEventId=" + fixedChargeEventId + " ]";
    }
    
}
