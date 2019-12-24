/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_deposit_overdue_interest_parameters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeDepositOverdueInterestParameters.findAll", query = "SELECT c FROM CbsSchemeDepositOverdueInterestParameters c"),
    @NamedQuery(name = "CbsSchemeDepositOverdueInterestParameters.findBySchemeCode", query = "SELECT c FROM CbsSchemeDepositOverdueInterestParameters c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeDepositOverdueInterestParameters.findBySchemeType", query = "SELECT c FROM CbsSchemeDepositOverdueInterestParameters c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeDepositOverdueInterestParameters.findByCurrencyCode", query = "SELECT c FROM CbsSchemeDepositOverdueInterestParameters c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeDepositOverdueInterestParameters.findByOverdueGlSubHeadCode", query = "SELECT c FROM CbsSchemeDepositOverdueInterestParameters c WHERE c.overdueGlSubHeadCode = :overdueGlSubHeadCode"),
    @NamedQuery(name = "CbsSchemeDepositOverdueInterestParameters.findByOverdueInterestCode", query = "SELECT c FROM CbsSchemeDepositOverdueInterestParameters c WHERE c.overdueInterestCode = :overdueInterestCode"),
    @NamedQuery(name = "CbsSchemeDepositOverdueInterestParameters.findByOverdueInterestTblCodeType", query = "SELECT c FROM CbsSchemeDepositOverdueInterestParameters c WHERE c.overdueInterestTblCodeType = :overdueInterestTblCodeType"),
    @NamedQuery(name = "CbsSchemeDepositOverdueInterestParameters.findByOverdueInterestCalcMethod", query = "SELECT c FROM CbsSchemeDepositOverdueInterestParameters c WHERE c.overdueInterestCalcMethod = :overdueInterestCalcMethod")})
public class CbsSchemeDepositOverdueInterestParameters extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 3)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Size(max = 12)
    @Column(name = "OVERDUE_GL_SUB_HEAD_CODE")
    private String overdueGlSubHeadCode;
    @Size(max = 12)
    @Column(name = "OVERDUE_INTEREST_CODE")
    private String overdueInterestCode;
    @Size(max = 1)
    @Column(name = "OVERDUE_INTEREST_TBL_CODE_TYPE")
    private String overdueInterestTblCodeType;
    @Size(max = 1)
    @Column(name = "OVERDUE_INTEREST_CALC_METHOD")
    private String overdueInterestCalcMethod;

    public CbsSchemeDepositOverdueInterestParameters() {
    }

    public CbsSchemeDepositOverdueInterestParameters(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getOverdueGlSubHeadCode() {
        return overdueGlSubHeadCode;
    }

    public void setOverdueGlSubHeadCode(String overdueGlSubHeadCode) {
        this.overdueGlSubHeadCode = overdueGlSubHeadCode;
    }

    public String getOverdueInterestCode() {
        return overdueInterestCode;
    }

    public void setOverdueInterestCode(String overdueInterestCode) {
        this.overdueInterestCode = overdueInterestCode;
    }

    public String getOverdueInterestTblCodeType() {
        return overdueInterestTblCodeType;
    }

    public void setOverdueInterestTblCodeType(String overdueInterestTblCodeType) {
        this.overdueInterestTblCodeType = overdueInterestTblCodeType;
    }

    public String getOverdueInterestCalcMethod() {
        return overdueInterestCalcMethod;
    }

    public void setOverdueInterestCalcMethod(String overdueInterestCalcMethod) {
        this.overdueInterestCalcMethod = overdueInterestCalcMethod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeDepositOverdueInterestParameters)) {
            return false;
        }
        CbsSchemeDepositOverdueInterestParameters other = (CbsSchemeDepositOverdueInterestParameters) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeDepositOverdueInterestParameters[ schemeCode=" + schemeCode + " ]";
    }
    
}
