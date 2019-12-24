/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

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
public class CbsSchemeDelinquencyDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "DELINQ_CYCLE")
    private String delinqCycle;

    public CbsSchemeDelinquencyDetailsPK() {
    }

    public CbsSchemeDelinquencyDetailsPK(String schemeCode, String delinqCycle) {
        this.schemeCode = schemeCode;
        this.delinqCycle = delinqCycle;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getDelinqCycle() {
        return delinqCycle;
    }

    public void setDelinqCycle(String delinqCycle) {
        this.delinqCycle = delinqCycle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (delinqCycle != null ? delinqCycle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeDelinquencyDetailsPK)) {
            return false;
        }
        CbsSchemeDelinquencyDetailsPK other = (CbsSchemeDelinquencyDetailsPK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.delinqCycle == null && other.delinqCycle != null) || (this.delinqCycle != null && !this.delinqCycle.equals(other.delinqCycle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.loan.CbsSchemeDelinquencyDetailsPK[ schemeCode=" + schemeCode + ", delinqCycle=" + delinqCycle + " ]";
    }
    
}
