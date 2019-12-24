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
public class CbsSchemeValidInstrumentsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "INSTRUMENT_CODE")
    private String instrumentCode;

    public CbsSchemeValidInstrumentsPK() {
    }

    public CbsSchemeValidInstrumentsPK(String schemeCode, String instrumentCode) {
        this.schemeCode = schemeCode;
        this.instrumentCode = instrumentCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getInstrumentCode() {
        return instrumentCode;
    }

    public void setInstrumentCode(String instrumentCode) {
        this.instrumentCode = instrumentCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (instrumentCode != null ? instrumentCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeValidInstrumentsPK)) {
            return false;
        }
        CbsSchemeValidInstrumentsPK other = (CbsSchemeValidInstrumentsPK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.instrumentCode == null && other.instrumentCode != null) || (this.instrumentCode != null && !this.instrumentCode.equals(other.instrumentCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeValidInstrumentsPK[ schemeCode=" + schemeCode + ", instrumentCode=" + instrumentCode + " ]";
    }
    
}
