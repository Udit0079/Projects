/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_ledger_folio_details_currency_wise")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeLedgerFolioDetailsCurrencyWise.findAll", query = "SELECT c FROM CbsSchemeLedgerFolioDetailsCurrencyWise c"),
    @NamedQuery(name = "CbsSchemeLedgerFolioDetailsCurrencyWise.findBySchemeCode", query = "SELECT c FROM CbsSchemeLedgerFolioDetailsCurrencyWise c WHERE c.cbsSchemeLedgerFolioDetailsCurrencyWisePK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeLedgerFolioDetailsCurrencyWise.findBySchemeType", query = "SELECT c FROM CbsSchemeLedgerFolioDetailsCurrencyWise c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeLedgerFolioDetailsCurrencyWise.findByCurrencyCode", query = "SELECT c FROM CbsSchemeLedgerFolioDetailsCurrencyWise c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeLedgerFolioDetailsCurrencyWise.findByStartAmount", query = "SELECT c FROM CbsSchemeLedgerFolioDetailsCurrencyWise c WHERE c.cbsSchemeLedgerFolioDetailsCurrencyWisePK.startAmount = :startAmount"),
    @NamedQuery(name = "CbsSchemeLedgerFolioDetailsCurrencyWise.findByEndAmount", query = "SELECT c FROM CbsSchemeLedgerFolioDetailsCurrencyWise c WHERE c.cbsSchemeLedgerFolioDetailsCurrencyWisePK.endAmount = :endAmount"),
    @NamedQuery(name = "CbsSchemeLedgerFolioDetailsCurrencyWise.findByFreeFolios", query = "SELECT c FROM CbsSchemeLedgerFolioDetailsCurrencyWise c WHERE c.freeFolios = :freeFolios"),
    @NamedQuery(name = "CbsSchemeLedgerFolioDetailsCurrencyWise.findByDelFlag", query = "SELECT c FROM CbsSchemeLedgerFolioDetailsCurrencyWise c WHERE c.delFlag = :delFlag")})
public class CbsSchemeLedgerFolioDetailsCurrencyWise extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeLedgerFolioDetailsCurrencyWisePK cbsSchemeLedgerFolioDetailsCurrencyWisePK;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 3)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Column(name = "FREE_FOLIOS")
    private Integer freeFolios;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsSchemeLedgerFolioDetailsCurrencyWise() {
    }

    public CbsSchemeLedgerFolioDetailsCurrencyWise(CbsSchemeLedgerFolioDetailsCurrencyWisePK cbsSchemeLedgerFolioDetailsCurrencyWisePK) {
        this.cbsSchemeLedgerFolioDetailsCurrencyWisePK = cbsSchemeLedgerFolioDetailsCurrencyWisePK;
    }

    public CbsSchemeLedgerFolioDetailsCurrencyWise(String schemeCode, BigDecimal startAmount, BigDecimal endAmount) {
        this.cbsSchemeLedgerFolioDetailsCurrencyWisePK = new CbsSchemeLedgerFolioDetailsCurrencyWisePK(schemeCode, startAmount, endAmount);
    }

    public CbsSchemeLedgerFolioDetailsCurrencyWisePK getCbsSchemeLedgerFolioDetailsCurrencyWisePK() {
        return cbsSchemeLedgerFolioDetailsCurrencyWisePK;
    }

    public void setCbsSchemeLedgerFolioDetailsCurrencyWisePK(CbsSchemeLedgerFolioDetailsCurrencyWisePK cbsSchemeLedgerFolioDetailsCurrencyWisePK) {
        this.cbsSchemeLedgerFolioDetailsCurrencyWisePK = cbsSchemeLedgerFolioDetailsCurrencyWisePK;
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

    public Integer getFreeFolios() {
        return freeFolios;
    }

    public void setFreeFolios(Integer freeFolios) {
        this.freeFolios = freeFolios;
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
        hash += (cbsSchemeLedgerFolioDetailsCurrencyWisePK != null ? cbsSchemeLedgerFolioDetailsCurrencyWisePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeLedgerFolioDetailsCurrencyWise)) {
            return false;
        }
        CbsSchemeLedgerFolioDetailsCurrencyWise other = (CbsSchemeLedgerFolioDetailsCurrencyWise) object;
        if ((this.cbsSchemeLedgerFolioDetailsCurrencyWisePK == null && other.cbsSchemeLedgerFolioDetailsCurrencyWisePK != null) || (this.cbsSchemeLedgerFolioDetailsCurrencyWisePK != null && !this.cbsSchemeLedgerFolioDetailsCurrencyWisePK.equals(other.cbsSchemeLedgerFolioDetailsCurrencyWisePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeLedgerFolioDetailsCurrencyWise[ cbsSchemeLedgerFolioDetailsCurrencyWisePK=" + cbsSchemeLedgerFolioDetailsCurrencyWisePK + " ]";
    }
    
}
