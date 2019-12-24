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
public class CbsSchemeTransactionReportCodeCurrencyWisePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "TRANSACTION_REPORT_CODE")
    private String transactionReportCode;

    public CbsSchemeTransactionReportCodeCurrencyWisePK() {
    }

    public CbsSchemeTransactionReportCodeCurrencyWisePK(String schemeCode, String transactionReportCode) {
        this.schemeCode = schemeCode;
        this.transactionReportCode = transactionReportCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getTransactionReportCode() {
        return transactionReportCode;
    }

    public void setTransactionReportCode(String transactionReportCode) {
        this.transactionReportCode = transactionReportCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (transactionReportCode != null ? transactionReportCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeTransactionReportCodeCurrencyWisePK)) {
            return false;
        }
        CbsSchemeTransactionReportCodeCurrencyWisePK other = (CbsSchemeTransactionReportCodeCurrencyWisePK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.transactionReportCode == null && other.transactionReportCode != null) || (this.transactionReportCode != null && !this.transactionReportCode.equals(other.transactionReportCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeTransactionReportCodeCurrencyWisePK[ schemeCode=" + schemeCode + ", transactionReportCode=" + transactionReportCode + " ]";
    }
    
}
