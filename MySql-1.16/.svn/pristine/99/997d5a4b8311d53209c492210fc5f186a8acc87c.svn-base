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
public class CbsSchemeAssetDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "DPD_COUNTER")
    private String dpdCounter;

    public CbsSchemeAssetDetailsPK() {
    }

    public CbsSchemeAssetDetailsPK(String schemeCode, String dpdCounter) {
        this.schemeCode = schemeCode;
        this.dpdCounter = dpdCounter;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getDpdCounter() {
        return dpdCounter;
    }

    public void setDpdCounter(String dpdCounter) {
        this.dpdCounter = dpdCounter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (dpdCounter != null ? dpdCounter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeAssetDetailsPK)) {
            return false;
        }
        CbsSchemeAssetDetailsPK other = (CbsSchemeAssetDetailsPK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.dpdCounter == null && other.dpdCounter != null) || (this.dpdCounter != null && !this.dpdCounter.equals(other.dpdCounter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeAssetDetailsPK[ schemeCode=" + schemeCode + ", dpdCounter=" + dpdCounter + " ]";
    }
    
}
