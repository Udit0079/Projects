/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class CbsSchemeLedgerFolioDetailsCurrencyWisePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_AMOUNT")
    private BigDecimal startAmount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_AMOUNT")
    private BigDecimal endAmount;

    public CbsSchemeLedgerFolioDetailsCurrencyWisePK() {
    }

    public CbsSchemeLedgerFolioDetailsCurrencyWisePK(String schemeCode, BigDecimal startAmount, BigDecimal endAmount) {
        this.schemeCode = schemeCode;
        this.startAmount = startAmount;
        this.endAmount = endAmount;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public BigDecimal getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(BigDecimal startAmount) {
        this.startAmount = startAmount;
    }

    public BigDecimal getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(BigDecimal endAmount) {
        this.endAmount = endAmount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (startAmount != null ? startAmount.hashCode() : 0);
        hash += (endAmount != null ? endAmount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeLedgerFolioDetailsCurrencyWisePK)) {
            return false;
        }
        CbsSchemeLedgerFolioDetailsCurrencyWisePK other = (CbsSchemeLedgerFolioDetailsCurrencyWisePK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.startAmount == null && other.startAmount != null) || (this.startAmount != null && !this.startAmount.equals(other.startAmount))) {
            return false;
        }
        if ((this.endAmount == null && other.endAmount != null) || (this.endAmount != null && !this.endAmount.equals(other.endAmount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeLedgerFolioDetailsCurrencyWisePK[ schemeCode=" + schemeCode + ", startAmount=" + startAmount + ", endAmount=" + endAmount + " ]";
    }
    
}
