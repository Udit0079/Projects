/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ankit Verma
 */
@Entity
@Table(name = "hr_tax_investment_category")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTaxInvestmentCategory.findAll", query = "SELECT h FROM HrTaxInvestmentCategory h"),
    @NamedQuery(name = "HrTaxInvestmentCategory.findByCompCode", query = "SELECT h FROM HrTaxInvestmentCategory h WHERE h.hrTaxInvestmentCategoryPK.compCode = :compCode"),
    @NamedQuery(name = "HrTaxInvestmentCategory.findByCategoryCode", query = "SELECT h FROM HrTaxInvestmentCategory h WHERE h.hrTaxInvestmentCategoryPK.categoryCode = :categoryCode"),
    @NamedQuery(name = "HrTaxInvestmentCategory.findByEmpCode", query = "SELECT h FROM HrTaxInvestmentCategory h WHERE h.hrTaxInvestmentCategoryPK.empCode = :empCode"),
    @NamedQuery(name = "HrTaxInvestmentCategory.findByCategoryAmt", query = "SELECT h FROM HrTaxInvestmentCategory h WHERE h.categoryAmt = :categoryAmt"),
    @NamedQuery(name = "HrTaxInvestmentCategory.findByCategoryMaxLimit", query = "SELECT h FROM HrTaxInvestmentCategory h WHERE h.categoryMaxLimit = :categoryMaxLimit"),
    @NamedQuery(name = "HrTaxInvestmentCategory.findByEnteredBy", query = "SELECT h FROM HrTaxInvestmentCategory h WHERE h.enteredBy = :enteredBy"),
    @NamedQuery(name = "HrTaxInvestmentCategory.findByAuthBy", query = "SELECT h FROM HrTaxInvestmentCategory h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrTaxInvestmentCategory.findByDefaultComp", query = "SELECT h FROM HrTaxInvestmentCategory h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrTaxInvestmentCategory.findByModDate", query = "SELECT h FROM HrTaxInvestmentCategory h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrTaxInvestmentCategory.findByCompCodeAndEmpCode", query = "SELECT h FROM HrTaxInvestmentCategory h WHERE h.hrTaxInvestmentCategoryPK.compCode = :compCode "
        + "and h.hrTaxInvestmentCategoryPK.empCode = :empCode"),
    @NamedQuery(name = "HrTaxInvestmentCategory.findMaxCategoryCode", query="SELECT max(h.hrTaxInvestmentCategoryPK.categoryCode) FROM HrTaxInvestmentCategory h WHERE h.hrTaxInvestmentCategoryPK.compCode = :compCode")})
public class HrTaxInvestmentCategory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrTaxInvestmentCategoryPK hrTaxInvestmentCategoryPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CATEGORY_AMT")
    private double categoryAmt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CATEGORY_MAX_LIMIT")
    private Double categoryMaxLimit;
    @Size(max = 255)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @Size(max = 255)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;

    public HrTaxInvestmentCategory() {
    }

    public HrTaxInvestmentCategory(HrTaxInvestmentCategoryPK hrTaxInvestmentCategoryPK) {
        this.hrTaxInvestmentCategoryPK = hrTaxInvestmentCategoryPK;
    }

    public HrTaxInvestmentCategory(HrTaxInvestmentCategoryPK hrTaxInvestmentCategoryPK, double categoryAmt) {
        this.hrTaxInvestmentCategoryPK = hrTaxInvestmentCategoryPK;
        this.categoryAmt = categoryAmt;
    }

    public HrTaxInvestmentCategory(int compCode, String categoryCode, BigInteger empCode) {
        this.hrTaxInvestmentCategoryPK = new HrTaxInvestmentCategoryPK(compCode, categoryCode, empCode);
    }

    public HrTaxInvestmentCategoryPK getHrTaxInvestmentCategoryPK() {
        return hrTaxInvestmentCategoryPK;
    }

    public void setHrTaxInvestmentCategoryPK(HrTaxInvestmentCategoryPK hrTaxInvestmentCategoryPK) {
        this.hrTaxInvestmentCategoryPK = hrTaxInvestmentCategoryPK;
    }

    public double getCategoryAmt() {
        return categoryAmt;
    }

    public void setCategoryAmt(double categoryAmt) {
        this.categoryAmt = categoryAmt;
    }

    public Double getCategoryMaxLimit() {
        return categoryMaxLimit;
    }

    public void setCategoryMaxLimit(Double categoryMaxLimit) {
        this.categoryMaxLimit = categoryMaxLimit;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrTaxInvestmentCategoryPK != null ? hrTaxInvestmentCategoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTaxInvestmentCategory)) {
            return false;
        }
        HrTaxInvestmentCategory other = (HrTaxInvestmentCategory) object;
        if ((this.hrTaxInvestmentCategoryPK == null && other.hrTaxInvestmentCategoryPK != null) || (this.hrTaxInvestmentCategoryPK != null && !this.hrTaxInvestmentCategoryPK.equals(other.hrTaxInvestmentCategoryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.HrTaxInvestmentCategory[ hrTaxInvestmentCategoryPK=" + hrTaxInvestmentCategoryPK + " ]";
    }
    
}
