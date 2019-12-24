/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "cbs_scheme_transaction_report_code_currency_wise")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeTransactionReportCodeCurrencyWise.findAll", query = "SELECT c FROM CbsSchemeTransactionReportCodeCurrencyWise c"),
    @NamedQuery(name = "CbsSchemeTransactionReportCodeCurrencyWise.findBySchemeCode", query = "SELECT c FROM CbsSchemeTransactionReportCodeCurrencyWise c WHERE c.cbsSchemeTransactionReportCodeCurrencyWisePK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeTransactionReportCodeCurrencyWise.findByTransactionReportCode", query = "SELECT c FROM CbsSchemeTransactionReportCodeCurrencyWise c WHERE c.cbsSchemeTransactionReportCodeCurrencyWisePK.transactionReportCode = :transactionReportCode"),
    @NamedQuery(name = "CbsSchemeTransactionReportCodeCurrencyWise.findByCurrencyCode", query = "SELECT c FROM CbsSchemeTransactionReportCodeCurrencyWise c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeTransactionReportCodeCurrencyWise.findBySchemeType", query = "SELECT c FROM CbsSchemeTransactionReportCodeCurrencyWise c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeTransactionReportCodeCurrencyWise.findByDrAmtLimit", query = "SELECT c FROM CbsSchemeTransactionReportCodeCurrencyWise c WHERE c.drAmtLimit = :drAmtLimit"),
    @NamedQuery(name = "CbsSchemeTransactionReportCodeCurrencyWise.findByCrAmtLimit", query = "SELECT c FROM CbsSchemeTransactionReportCodeCurrencyWise c WHERE c.crAmtLimit = :crAmtLimit"),
    @NamedQuery(name = "CbsSchemeTransactionReportCodeCurrencyWise.findByDelFlag", query = "SELECT c FROM CbsSchemeTransactionReportCodeCurrencyWise c WHERE c.delFlag = :delFlag")})
public class CbsSchemeTransactionReportCodeCurrencyWise extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeTransactionReportCodeCurrencyWisePK cbsSchemeTransactionReportCodeCurrencyWisePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DR_AMT_LIMIT")
    private BigDecimal drAmtLimit;
    @Column(name = "CR_AMT_LIMIT")
    private BigDecimal crAmtLimit;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsSchemeTransactionReportCodeCurrencyWise() {
    }

    public CbsSchemeTransactionReportCodeCurrencyWise(CbsSchemeTransactionReportCodeCurrencyWisePK cbsSchemeTransactionReportCodeCurrencyWisePK) {
        this.cbsSchemeTransactionReportCodeCurrencyWisePK = cbsSchemeTransactionReportCodeCurrencyWisePK;
    }

    public CbsSchemeTransactionReportCodeCurrencyWise(CbsSchemeTransactionReportCodeCurrencyWisePK cbsSchemeTransactionReportCodeCurrencyWisePK, String currencyCode, String schemeType) {
        this.cbsSchemeTransactionReportCodeCurrencyWisePK = cbsSchemeTransactionReportCodeCurrencyWisePK;
        this.currencyCode = currencyCode;
        this.schemeType = schemeType;
    }

    public CbsSchemeTransactionReportCodeCurrencyWise(String schemeCode, String transactionReportCode) {
        this.cbsSchemeTransactionReportCodeCurrencyWisePK = new CbsSchemeTransactionReportCodeCurrencyWisePK(schemeCode, transactionReportCode);
    }

    public CbsSchemeTransactionReportCodeCurrencyWisePK getCbsSchemeTransactionReportCodeCurrencyWisePK() {
        return cbsSchemeTransactionReportCodeCurrencyWisePK;
    }

    public void setCbsSchemeTransactionReportCodeCurrencyWisePK(CbsSchemeTransactionReportCodeCurrencyWisePK cbsSchemeTransactionReportCodeCurrencyWisePK) {
        this.cbsSchemeTransactionReportCodeCurrencyWisePK = cbsSchemeTransactionReportCodeCurrencyWisePK;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public BigDecimal getDrAmtLimit() {
        return drAmtLimit;
    }

    public void setDrAmtLimit(BigDecimal drAmtLimit) {
        this.drAmtLimit = drAmtLimit;
    }

    public BigDecimal getCrAmtLimit() {
        return crAmtLimit;
    }

    public void setCrAmtLimit(BigDecimal crAmtLimit) {
        this.crAmtLimit = crAmtLimit;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsSchemeTransactionReportCodeCurrencyWisePK != null ? cbsSchemeTransactionReportCodeCurrencyWisePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeTransactionReportCodeCurrencyWise)) {
            return false;
        }
        CbsSchemeTransactionReportCodeCurrencyWise other = (CbsSchemeTransactionReportCodeCurrencyWise) object;
        if ((this.cbsSchemeTransactionReportCodeCurrencyWisePK == null && other.cbsSchemeTransactionReportCodeCurrencyWisePK != null) || (this.cbsSchemeTransactionReportCodeCurrencyWisePK != null && !this.cbsSchemeTransactionReportCodeCurrencyWisePK.equals(other.cbsSchemeTransactionReportCodeCurrencyWisePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeTransactionReportCodeCurrencyWise[ cbsSchemeTransactionReportCodeCurrencyWisePK=" + cbsSchemeTransactionReportCodeCurrencyWisePK + " ]";
    }
    
}
