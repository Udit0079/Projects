/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.master;

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
public class CbsGlSubHeadSchemeDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "GL_SUB_HEAD_CODE")
    private String glSubHeadCode;

    public CbsGlSubHeadSchemeDetailsPK() {
    }

    public CbsGlSubHeadSchemeDetailsPK(String schemeCode, String glSubHeadCode) {
        this.schemeCode = schemeCode;
        this.glSubHeadCode = glSubHeadCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getGlSubHeadCode() {
        return glSubHeadCode;
    }

    public void setGlSubHeadCode(String glSubHeadCode) {
        this.glSubHeadCode = glSubHeadCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (glSubHeadCode != null ? glSubHeadCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsGlSubHeadSchemeDetailsPK)) {
            return false;
        }
        CbsGlSubHeadSchemeDetailsPK other = (CbsGlSubHeadSchemeDetailsPK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.glSubHeadCode == null && other.glSubHeadCode != null) || (this.glSubHeadCode != null && !this.glSubHeadCode.equals(other.glSubHeadCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsGlSubHeadSchemeDetailsPK[ schemeCode=" + schemeCode + ", glSubHeadCode=" + glSubHeadCode + " ]";
    }
    
}
