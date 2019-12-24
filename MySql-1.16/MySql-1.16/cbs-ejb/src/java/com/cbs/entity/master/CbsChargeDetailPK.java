/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Embeddable
public class CbsChargeDetailPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CHARGE_NAME")
    private String chargeName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "AC_TYPE")
    private String acType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "FROM_RANGE")
    private BigDecimal fromRange;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TO_RANGE")
    private BigDecimal toRange;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EFF_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effDate;

    public CbsChargeDetailPK() {
    }

    public CbsChargeDetailPK(String chargeName, String acType, BigDecimal fromRange, BigDecimal toRange, Date effDate) {
        this.chargeName = chargeName;
        this.acType = acType;
        this.fromRange = fromRange;
        this.toRange = toRange;
        this.effDate = effDate;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public BigDecimal getFromRange() {
        return fromRange;
    }

    public void setFromRange(BigDecimal fromRange) {
        this.fromRange = fromRange;
    }

    public BigDecimal getToRange() {
        return toRange;
    }

    public void setToRange(BigDecimal toRange) {
        this.toRange = toRange;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chargeName != null ? chargeName.hashCode() : 0);
        hash += (acType != null ? acType.hashCode() : 0);
        hash += (fromRange != null ? fromRange.hashCode() : 0);
        hash += (toRange != null ? toRange.hashCode() : 0);
        hash += (effDate != null ? effDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsChargeDetailPK)) {
            return false;
        }
        CbsChargeDetailPK other = (CbsChargeDetailPK) object;
        if ((this.chargeName == null && other.chargeName != null) || (this.chargeName != null && !this.chargeName.equals(other.chargeName))) {
            return false;
        }
        if ((this.acType == null && other.acType != null) || (this.acType != null && !this.acType.equals(other.acType))) {
            return false;
        }
        if ((this.fromRange == null && other.fromRange != null) || (this.fromRange != null && !this.fromRange.equals(other.fromRange))) {
            return false;
        }
        if ((this.toRange == null && other.toRange != null) || (this.toRange != null && !this.toRange.equals(other.toRange))) {
            return false;
        }
        if ((this.effDate == null && other.effDate != null) || (this.effDate != null && !this.effDate.equals(other.effDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.master.CbsChargeDetailPK[ chargeName=" + chargeName + ", acType=" + acType + ", fromRange=" + fromRange + ", toRange=" + toRange + ", effDate=" + effDate + " ]";
    }
    
}
