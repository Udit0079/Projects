/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import java.io.Serializable;
import java.math.BigInteger;
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
public class HrTaxInvestmentCategoryPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CATEGORY_CODE")
    private String categoryCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMP_CODE")
    private BigInteger empCode;

    public HrTaxInvestmentCategoryPK() {
    }

    public HrTaxInvestmentCategoryPK(int compCode, String categoryCode, BigInteger empCode) {
        this.compCode = compCode;
        this.categoryCode = categoryCode;
        this.empCode = empCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public BigInteger getEmpCode() {
        return empCode;
    }

    public void setEmpCode(BigInteger empCode) {
        this.empCode = empCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (categoryCode != null ? categoryCode.hashCode() : 0);
        hash += (empCode != null ? empCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTaxInvestmentCategoryPK)) {
            return false;
        }
        HrTaxInvestmentCategoryPK other = (HrTaxInvestmentCategoryPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.categoryCode == null && other.categoryCode != null) || (this.categoryCode != null && !this.categoryCode.equals(other.categoryCode))) {
            return false;
        }
        if ((this.empCode == null && other.empCode != null) || (this.empCode != null && !this.empCode.equals(other.empCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.HrTaxInvestmentCategoryPK[ compCode=" + compCode + ", categoryCode=" + categoryCode + ", empCode=" + empCode + " ]";
    }
    
}
