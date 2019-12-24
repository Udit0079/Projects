/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author root
 */
@Embeddable
public class HrSalaryStructurePK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "PURPOSE_CODE")
    private String purposeCode;
    @Basic(optional = false)
    @Column(name = "NATURE")
    private String nature;
    @Basic(optional = false)
    @Column(name = "AL_DESC")
    private String alDesc;
//    @Basic(optional = false)
//    @Column(name = "DATE_FROM")
//    private String dateFrom;
//    @Basic(optional = false)
//    @Column(name = "DATE_TO")
//    private String dateTo;

    public HrSalaryStructurePK() {
    }

//    public HrSalaryStructurePK(int compCode, String purposeCode, String nature, String alDesc, String dateFrom, String dateTo) {
    public HrSalaryStructurePK(int compCode, String purposeCode, String nature, String alDesc) {
        this.compCode = compCode;
        this.purposeCode = purposeCode;
        this.nature = nature;
        this.alDesc = alDesc;
//        this.dateFrom = dateFrom;
//        this.dateTo = dateTo;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
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

    public String getAlDesc() {
        return alDesc;
    }

    public void setAlDesc(String alDesc) {
        this.alDesc = alDesc;
    }

//    public String getDateFrom() {
//        return dateFrom;
//    }
//
//    public void setDateFrom(String dateFrom) {
//        this.dateFrom = dateFrom;
//    }
//
//    public String getDateTo() {
//        return dateTo;
//    }
//
//    public void setDateTo(String dateTo) {
//        this.dateTo = dateTo;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (purposeCode != null ? purposeCode.hashCode() : 0);
        hash += (nature != null ? nature.hashCode() : 0);
        hash += (alDesc != null ? alDesc.hashCode() : 0);
//        hash += (dateFrom != null ? dateFrom.hashCode() : 0);
//        hash += (dateTo != null ? dateTo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSalaryStructurePK)) {
            return false;
        }
        HrSalaryStructurePK other = (HrSalaryStructurePK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.purposeCode == null && other.purposeCode != null) || (this.purposeCode != null && !this.purposeCode.equals(other.purposeCode))) {
            return false;
        }
        if ((this.nature == null && other.nature != null) || (this.nature != null && !this.nature.equals(other.nature))) {
            return false;
        }
        if ((this.alDesc == null && other.alDesc != null) || (this.alDesc != null && !this.alDesc.equals(other.alDesc))) {
            return false;
        }
//        if ((this.dateFrom == null && other.dateFrom != null) || (this.dateFrom != null && !this.dateFrom.equals(other.dateFrom))) {
//            return false;
//        }
//        if ((this.dateTo == null && other.dateTo != null) || (this.dateTo != null && !this.dateTo.equals(other.dateTo))) {
//            return false;
//        }
        return true;
    }

    @Override
    public String toString() {
//        return "entity.hr.HrSalaryStructurePK[compCode=" + compCode + ", purposeCode=" + purposeCode + ", nature=" + nature + ", alDesc=" + alDesc + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
        return "entity.hr.HrSalaryStructurePK[compCode=" + compCode + ", purposeCode=" + purposeCode + ", nature=" + nature + ", alDesc=" + alDesc + "]";
    }

}
