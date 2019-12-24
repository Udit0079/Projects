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
 * @author root
 */
@Embeddable
public class HrSlabMasterPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SLAB_CODE")
    private String slabCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "BASE_COMPONENT")
    private String baseComponent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DEPEND_COMPONENT")
    private String dependComponent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PURPOSE_CODE")
    private String purposeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NATURE")
    private String nature;

    public HrSlabMasterPK() {
    }

    public HrSlabMasterPK(String slabCode, int compCode, String baseComponent, String dependComponent, String purposeCode, String nature) {
        this.slabCode = slabCode;
        this.compCode = compCode;
        this.baseComponent = baseComponent;
        this.dependComponent = dependComponent;
        this.purposeCode = purposeCode;
        this.nature = nature;
    }

    public String getSlabCode() {
        return slabCode;
    }

    public void setSlabCode(String slabCode) {
        this.slabCode = slabCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getBaseComponent() {
        return baseComponent;
    }

    public void setBaseComponent(String baseComponent) {
        this.baseComponent = baseComponent;
    }

    public String getDependComponent() {
        return dependComponent;
    }

    public void setDependComponent(String dependComponent) {
        this.dependComponent = dependComponent;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (slabCode != null ? slabCode.hashCode() : 0);
        hash += (int) compCode;
        hash += (baseComponent != null ? baseComponent.hashCode() : 0);
        hash += (dependComponent != null ? dependComponent.hashCode() : 0);
        hash += (purposeCode != null ? purposeCode.hashCode() : 0);
        hash += (nature != null ? nature.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSlabMasterPK)) {
            return false;
        }
        HrSlabMasterPK other = (HrSlabMasterPK) object;
        if ((this.slabCode == null && other.slabCode != null) || (this.slabCode != null && !this.slabCode.equals(other.slabCode))) {
            return false;
        }
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.baseComponent == null && other.baseComponent != null) || (this.baseComponent != null && !this.baseComponent.equals(other.baseComponent))) {
            return false;
        }
        if ((this.dependComponent == null && other.dependComponent != null) || (this.dependComponent != null && !this.dependComponent.equals(other.dependComponent))) {
            return false;
        }
        if ((this.purposeCode == null && other.purposeCode != null) || (this.purposeCode != null && !this.purposeCode.equals(other.purposeCode))) {
            return false;
        }
        if ((this.nature == null && other.nature != null) || (this.nature != null && !this.nature.equals(other.nature))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.HrSlabMasterPK[ slabCode=" + slabCode + ", compCode=" + compCode + ", baseComponent=" + baseComponent + ", dependComponent=" + dependComponent + ", purposeCode=" + purposeCode + ", nature=" + nature + " ]";
    }
    
}
