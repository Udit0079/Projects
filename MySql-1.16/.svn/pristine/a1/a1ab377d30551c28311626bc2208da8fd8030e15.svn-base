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
public class CbsSchemeTodExceptionDetailsCurrencyWisePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TOD_SRL_NO")
    private String todSrlNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    public CbsSchemeTodExceptionDetailsCurrencyWisePK() {
    }

    public CbsSchemeTodExceptionDetailsCurrencyWisePK(String schemeCode, String todSrlNo, String currencyCode) {
        this.schemeCode = schemeCode;
        this.todSrlNo = todSrlNo;
        this.currencyCode = currencyCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getTodSrlNo() {
        return todSrlNo;
    }

    public void setTodSrlNo(String todSrlNo) {
        this.todSrlNo = todSrlNo;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (todSrlNo != null ? todSrlNo.hashCode() : 0);
        hash += (currencyCode != null ? currencyCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeTodExceptionDetailsCurrencyWisePK)) {
            return false;
        }
        CbsSchemeTodExceptionDetailsCurrencyWisePK other = (CbsSchemeTodExceptionDetailsCurrencyWisePK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.todSrlNo == null && other.todSrlNo != null) || (this.todSrlNo != null && !this.todSrlNo.equals(other.todSrlNo))) {
            return false;
        }
        if ((this.currencyCode == null && other.currencyCode != null) || (this.currencyCode != null && !this.currencyCode.equals(other.currencyCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeTodExceptionDetailsCurrencyWisePK[ schemeCode=" + schemeCode + ", todSrlNo=" + todSrlNo + ", currencyCode=" + currencyCode + " ]";
    }
    
}
