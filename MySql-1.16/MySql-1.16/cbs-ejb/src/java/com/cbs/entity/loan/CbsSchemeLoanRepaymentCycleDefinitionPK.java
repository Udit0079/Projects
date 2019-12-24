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
public class CbsSchemeLoanRepaymentCycleDefinitionPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ACCOUNT_OPEN_FROM_DATE")
    private String accountOpenFromDate;

    public CbsSchemeLoanRepaymentCycleDefinitionPK() {
    }

    public CbsSchemeLoanRepaymentCycleDefinitionPK(String schemeCode, String accountOpenFromDate) {
        this.schemeCode = schemeCode;
        this.accountOpenFromDate = accountOpenFromDate;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getAccountOpenFromDate() {
        return accountOpenFromDate;
    }

    public void setAccountOpenFromDate(String accountOpenFromDate) {
        this.accountOpenFromDate = accountOpenFromDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        hash += (accountOpenFromDate != null ? accountOpenFromDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeLoanRepaymentCycleDefinitionPK)) {
            return false;
        }
        CbsSchemeLoanRepaymentCycleDefinitionPK other = (CbsSchemeLoanRepaymentCycleDefinitionPK) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        if ((this.accountOpenFromDate == null && other.accountOpenFromDate != null) || (this.accountOpenFromDate != null && !this.accountOpenFromDate.equals(other.accountOpenFromDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeLoanRepaymentCycleDefinitionPK[ schemeCode=" + schemeCode + ", accountOpenFromDate=" + accountOpenFromDate + " ]";
    }
    
}
