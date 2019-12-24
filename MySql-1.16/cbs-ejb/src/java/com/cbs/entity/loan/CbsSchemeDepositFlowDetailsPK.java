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
public class CbsSchemeDepositFlowDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "FLOW_CODE")
    private String flowCode;

    public CbsSchemeDepositFlowDetailsPK() {
    }

    public CbsSchemeDepositFlowDetailsPK(String schemeCode, String schemeType, String flowCode) {
        this.schemeCode = schemeCode;
        this.schemeType = schemeType;
        this.flowCode = flowCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (schemeType != null ? schemeType.hashCode() : 0);
        hash += (flowCode != null ? flowCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeDepositFlowDetailsPK)) {
            return false;
        }
        CbsSchemeDepositFlowDetailsPK other = (CbsSchemeDepositFlowDetailsPK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.schemeType == null && other.schemeType != null) || (this.schemeType != null && !this.schemeType.equals(other.schemeType))) {
            return false;
        }
        if ((this.flowCode == null && other.flowCode != null) || (this.flowCode != null && !this.flowCode.equals(other.flowCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeDepositFlowDetailsPK[ schemeCode=" + schemeCode + ", schemeType=" + schemeType + ", flowCode=" + flowCode + " ]";
    }
    
}
