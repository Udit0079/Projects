/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ankit Verma
 */
@Embeddable
public class HrTaxSlabMasterPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TAX_SLAB_CODE")
    private String taxSlabCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TAX_FOR")
    private String taxFor;

    public HrTaxSlabMasterPK() {
    }

    public HrTaxSlabMasterPK(int compCode, String taxSlabCode, String taxFor) {
        this.compCode = compCode;
        this.taxSlabCode = taxSlabCode;
        this.taxFor = taxFor;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getTaxSlabCode() {
        return taxSlabCode;
    }

    public void setTaxSlabCode(String taxSlabCode) {
        this.taxSlabCode = taxSlabCode;
    }

    public String getTaxFor() {
        return taxFor;
    }

    public void setTaxFor(String taxFor) {
        this.taxFor = taxFor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (taxSlabCode != null ? taxSlabCode.hashCode() : 0);
        hash += (taxFor != null ? taxFor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTaxSlabMasterPK)) {
            return false;
        }
        HrTaxSlabMasterPK other = (HrTaxSlabMasterPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.taxSlabCode == null && other.taxSlabCode != null) || (this.taxSlabCode != null && !this.taxSlabCode.equals(other.taxSlabCode))) {
            return false;
        }
        if ((this.taxFor == null && other.taxFor != null) || (this.taxFor != null && !this.taxFor.equals(other.taxFor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.HrTaxSlabMasterPK[ compCode=" + compCode + ", taxSlabCode=" + taxSlabCode + ", taxFor=" + taxFor + " ]";
    }
    
}
