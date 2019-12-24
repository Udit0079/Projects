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
@Table(name = "cbs_scheme_tod_exception_details_currency_wise")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeTodExceptionDetailsCurrencyWise.findAll", query = "SELECT c FROM CbsSchemeTodExceptionDetailsCurrencyWise c"),
    @NamedQuery(name = "CbsSchemeTodExceptionDetailsCurrencyWise.findBySchemeCode", query = "SELECT c FROM CbsSchemeTodExceptionDetailsCurrencyWise c WHERE c.cbsSchemeTodExceptionDetailsCurrencyWisePK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeTodExceptionDetailsCurrencyWise.findByTodSrlNo", query = "SELECT c FROM CbsSchemeTodExceptionDetailsCurrencyWise c WHERE c.cbsSchemeTodExceptionDetailsCurrencyWisePK.todSrlNo = :todSrlNo"),
    @NamedQuery(name = "CbsSchemeTodExceptionDetailsCurrencyWise.findByCurrencyCode", query = "SELECT c FROM CbsSchemeTodExceptionDetailsCurrencyWise c WHERE c.cbsSchemeTodExceptionDetailsCurrencyWisePK.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeTodExceptionDetailsCurrencyWise.findBySchemeType", query = "SELECT c FROM CbsSchemeTodExceptionDetailsCurrencyWise c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeTodExceptionDetailsCurrencyWise.findByBeginAmount", query = "SELECT c FROM CbsSchemeTodExceptionDetailsCurrencyWise c WHERE c.beginAmount = :beginAmount"),
    @NamedQuery(name = "CbsSchemeTodExceptionDetailsCurrencyWise.findByEndAmount", query = "SELECT c FROM CbsSchemeTodExceptionDetailsCurrencyWise c WHERE c.endAmount = :endAmount"),
    @NamedQuery(name = "CbsSchemeTodExceptionDetailsCurrencyWise.findByTodException", query = "SELECT c FROM CbsSchemeTodExceptionDetailsCurrencyWise c WHERE c.todException = :todException"),
    @NamedQuery(name = "CbsSchemeTodExceptionDetailsCurrencyWise.findByDelFlag", query = "SELECT c FROM CbsSchemeTodExceptionDetailsCurrencyWise c WHERE c.delFlag = :delFlag")})
public class CbsSchemeTodExceptionDetailsCurrencyWise extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeTodExceptionDetailsCurrencyWisePK cbsSchemeTodExceptionDetailsCurrencyWisePK;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BEGIN_AMOUNT")
    private BigDecimal beginAmount;
    @Column(name = "END_AMOUNT")
    private BigDecimal endAmount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TOD_EXCEPTION")
    private String todException;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsSchemeTodExceptionDetailsCurrencyWise() {
    }

    public CbsSchemeTodExceptionDetailsCurrencyWise(CbsSchemeTodExceptionDetailsCurrencyWisePK cbsSchemeTodExceptionDetailsCurrencyWisePK) {
        this.cbsSchemeTodExceptionDetailsCurrencyWisePK = cbsSchemeTodExceptionDetailsCurrencyWisePK;
    }

    public CbsSchemeTodExceptionDetailsCurrencyWise(CbsSchemeTodExceptionDetailsCurrencyWisePK cbsSchemeTodExceptionDetailsCurrencyWisePK, String todException) {
        this.cbsSchemeTodExceptionDetailsCurrencyWisePK = cbsSchemeTodExceptionDetailsCurrencyWisePK;
        this.todException = todException;
    }

    public CbsSchemeTodExceptionDetailsCurrencyWise(String schemeCode, String todSrlNo, String currencyCode) {
        this.cbsSchemeTodExceptionDetailsCurrencyWisePK = new CbsSchemeTodExceptionDetailsCurrencyWisePK(schemeCode, todSrlNo, currencyCode);
    }

    public CbsSchemeTodExceptionDetailsCurrencyWisePK getCbsSchemeTodExceptionDetailsCurrencyWisePK() {
        return cbsSchemeTodExceptionDetailsCurrencyWisePK;
    }

    public void setCbsSchemeTodExceptionDetailsCurrencyWisePK(CbsSchemeTodExceptionDetailsCurrencyWisePK cbsSchemeTodExceptionDetailsCurrencyWisePK) {
        this.cbsSchemeTodExceptionDetailsCurrencyWisePK = cbsSchemeTodExceptionDetailsCurrencyWisePK;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public BigDecimal getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(BigDecimal beginAmount) {
        this.beginAmount = beginAmount;
    }

    public BigDecimal getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(BigDecimal endAmount) {
        this.endAmount = endAmount;
    }

    public String getTodException() {
        return todException;
    }

    public void setTodException(String todException) {
        this.todException = todException;
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
        hash += (cbsSchemeTodExceptionDetailsCurrencyWisePK != null ? cbsSchemeTodExceptionDetailsCurrencyWisePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeTodExceptionDetailsCurrencyWise)) {
            return false;
        }
        CbsSchemeTodExceptionDetailsCurrencyWise other = (CbsSchemeTodExceptionDetailsCurrencyWise) object;
        if ((this.cbsSchemeTodExceptionDetailsCurrencyWisePK == null && other.cbsSchemeTodExceptionDetailsCurrencyWisePK != null) || (this.cbsSchemeTodExceptionDetailsCurrencyWisePK != null && !this.cbsSchemeTodExceptionDetailsCurrencyWisePK.equals(other.cbsSchemeTodExceptionDetailsCurrencyWisePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeTodExceptionDetailsCurrencyWise[ cbsSchemeTodExceptionDetailsCurrencyWisePK=" + cbsSchemeTodExceptionDetailsCurrencyWisePK + " ]";
    }
    
}
