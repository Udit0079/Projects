/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
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
@Table(name = "hr_tax_slab_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTaxSlabMaster.findAll", query = "SELECT h FROM HrTaxSlabMaster h"),
    @NamedQuery(name = "HrTaxSlabMaster.findByCompCode", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.hrTaxSlabMasterPK.compCode = :compCode"),
    @NamedQuery(name = "HrTaxSlabMaster.findByTaxSlabCode", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.hrTaxSlabMasterPK.taxSlabCode = :taxSlabCode"),
    @NamedQuery(name = "HrTaxSlabMaster.findByTaxFor", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.hrTaxSlabMasterPK.taxFor = :taxFor"),
    @NamedQuery(name = "HrTaxSlabMaster.findByRangeFrom", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.rangeFrom = :rangeFrom"),
    @NamedQuery(name = "HrTaxSlabMaster.findByRangeTo", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.rangeTo = :rangeTo"),
    @NamedQuery(name = "HrTaxSlabMaster.findByApplicableTax", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.applicableTax = :applicableTax"),
    @NamedQuery(name = "HrTaxSlabMaster.findByStatFlag", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrTaxSlabMaster.findByStatUpFlag", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrTaxSlabMaster.findByModDate", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrTaxSlabMaster.findByEnteredBy", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.enteredBy = :enteredBy"),
    @NamedQuery(name = "HrTaxSlabMaster.findByAuthBy", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrTaxSlabMaster.findMaxSlabCodeByCompCode", query = "SELECT max(h.hrTaxSlabMasterPK.taxSlabCode) FROM HrTaxSlabMaster h WHERE h.hrTaxSlabMasterPK.compCode = :compCode"),
    @NamedQuery(name = "HrTaxSlabMaster.findByCompCodeAndTaxFor", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.hrTaxSlabMasterPK.compCode = :compCode and h.hrTaxSlabMasterPK.taxFor = :taxFor"),
    @NamedQuery(name = "HrTaxSlabMaster.findByDefaultComp", query = "SELECT h FROM HrTaxSlabMaster h WHERE h.defaultComp = :defaultComp")})
public class HrTaxSlabMaster extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrTaxSlabMasterPK hrTaxSlabMasterPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RANGE_FROM")
    private double rangeFrom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RANGE_TO")
    private double rangeTo;
    @NotNull
    @Column(name = "APPLICABLE_TAX")
    private double applicableTax;
    @Size(max = 255)
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Size(max = 255)
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Size(max = 255)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @Size(max = 255)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;

    public HrTaxSlabMaster() {
    }

    public HrTaxSlabMaster(HrTaxSlabMasterPK hrTaxSlabMasterPK) {
        this.hrTaxSlabMasterPK = hrTaxSlabMasterPK;
    }

    public HrTaxSlabMaster(HrTaxSlabMasterPK hrTaxSlabMasterPK, double rangeFrom, double rangeTo) {
        this.hrTaxSlabMasterPK = hrTaxSlabMasterPK;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    public HrTaxSlabMaster(int compCode, String taxSlabCode, String taxFor) {
        this.hrTaxSlabMasterPK = new HrTaxSlabMasterPK(compCode, taxSlabCode, taxFor);
    }

    public HrTaxSlabMasterPK getHrTaxSlabMasterPK() {
        return hrTaxSlabMasterPK;
    }

    public void setHrTaxSlabMasterPK(HrTaxSlabMasterPK hrTaxSlabMasterPK) {
        this.hrTaxSlabMasterPK = hrTaxSlabMasterPK;
    }

    public double getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(double rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public double getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(double rangeTo) {
        this.rangeTo = rangeTo;
    }

    public double getApplicableTax() {
        return applicableTax;
    }

    public void setApplicableTax(double applicableTax) {
        this.applicableTax = applicableTax;
    }

 

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrTaxSlabMasterPK != null ? hrTaxSlabMasterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTaxSlabMaster)) {
            return false;
        }
        HrTaxSlabMaster other = (HrTaxSlabMaster) object;
        if ((this.hrTaxSlabMasterPK == null && other.hrTaxSlabMasterPK != null) || (this.hrTaxSlabMasterPK != null && !this.hrTaxSlabMasterPK.equals(other.hrTaxSlabMasterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.HrTaxSlabMaster[ hrTaxSlabMasterPK=" + hrTaxSlabMasterPK + " ]";
    }
    
}
