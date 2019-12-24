/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.customer;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ROHIT KRISHNA
 */
@Entity
@Table(name = "cbs_cust_currencyinfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CBSCustCurrencyInfo.findAll", query = "SELECT c FROM CBSCustCurrencyInfo c"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByCustomerId", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.cBSCustCurrencyInfoPK.customerId = :customerId"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByCurrencyCodeType", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.cBSCustCurrencyInfoPK.currencyCodeType = :currencyCodeType"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByWithHoldingTaxLevel", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.withHoldingTaxLevel = :withHoldingTaxLevel"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByWithHoldingTax", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.withHoldingTax = :withHoldingTax"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByWithHoldingTaxLimit", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.withHoldingTaxLimit = :withHoldingTaxLimit"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByTDSOperativeAccountID", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.tDSOperativeAccountID = :tDSOperativeAccountID"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByCustinterestRateCredit", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.custinterestRateCredit = :custinterestRateCredit"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByCustInterestRateDebit", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.custInterestRateDebit = :custInterestRateDebit"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByPreferentialInterestTillDate", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.preferentialInterestTillDate = :preferentialInterestTillDate"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByLastChangeUserID", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByLastChangeTime", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByRecordCreaterID", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByCreationTime", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "CBSCustCurrencyInfo.findByTsCnt", query = "SELECT c FROM CBSCustCurrencyInfo c WHERE c.tsCnt = :tsCnt")})
public class CBSCustCurrencyInfo extends BaseEntity implements Serializable  {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CBSCustCurrencyInfoPK cBSCustCurrencyInfoPK;
    @Size(max = 10)
    @Column(name = "WithHoldingTaxLevel")
    private String withHoldingTaxLevel;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "WithHoldingTax")
    private Double withHoldingTax;
    @Column(name = "WithHoldingTaxLimit")
    private Double withHoldingTaxLimit;
    @Size(max = 12)
    @Column(name = "TDSOperativeAccountID")
    private String tDSOperativeAccountID;
    @Column(name = "CustinterestRateCredit")
    private Double custinterestRateCredit;
    @Column(name = "CustInterestRateDebit")
    private Double custInterestRateDebit;
    @Column(name = "PreferentialInterestTillDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preferentialInterestTillDate;
    @Size(max = 15)
    @Column(name = "LastChangeUserID")
    private String lastChangeUserID;
    @Column(name = "LastChangeTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChangeTime;
    @Size(max = 15)
    @Column(name = "RecordCreaterID")
    private String recordCreaterID;
    @Column(name = "CreationTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    @Size(max = 5)
    @Column(name = "TsCnt")
    private String tsCnt;

    public CBSCustCurrencyInfo() {
    }

    public CBSCustCurrencyInfo(CBSCustCurrencyInfoPK cBSCustCurrencyInfoPK) {
        this.cBSCustCurrencyInfoPK = cBSCustCurrencyInfoPK;
    }

    public CBSCustCurrencyInfo(String customerId, String currencyCodeType) {
        this.cBSCustCurrencyInfoPK = new CBSCustCurrencyInfoPK(customerId, currencyCodeType);
    }

    public CBSCustCurrencyInfoPK getCBSCustCurrencyInfoPK() {
        return cBSCustCurrencyInfoPK;
    }

    public void setCBSCustCurrencyInfoPK(CBSCustCurrencyInfoPK cBSCustCurrencyInfoPK) {
        this.cBSCustCurrencyInfoPK = cBSCustCurrencyInfoPK;
    }

    public String getWithHoldingTaxLevel() {
        return withHoldingTaxLevel;
    }

    public void setWithHoldingTaxLevel(String withHoldingTaxLevel) {
        this.withHoldingTaxLevel = withHoldingTaxLevel;
    }

    public Double getWithHoldingTax() {
        return withHoldingTax;
    }

    public void setWithHoldingTax(Double withHoldingTax) {
        this.withHoldingTax = withHoldingTax;
    }

    public Double getWithHoldingTaxLimit() {
        return withHoldingTaxLimit;
    }

    public void setWithHoldingTaxLimit(Double withHoldingTaxLimit) {
        this.withHoldingTaxLimit = withHoldingTaxLimit;
    }

    public String getTDSOperativeAccountID() {
        return tDSOperativeAccountID;
    }

    public void setTDSOperativeAccountID(String tDSOperativeAccountID) {
        this.tDSOperativeAccountID = tDSOperativeAccountID;
    }

    public Double getCustinterestRateCredit() {
        return custinterestRateCredit;
    }

    public void setCustinterestRateCredit(Double custinterestRateCredit) {
        this.custinterestRateCredit = custinterestRateCredit;
    }

    public Double getCustInterestRateDebit() {
        return custInterestRateDebit;
    }

    public void setCustInterestRateDebit(Double custInterestRateDebit) {
        this.custInterestRateDebit = custInterestRateDebit;
    }

    public Date getPreferentialInterestTillDate() {
        return preferentialInterestTillDate;
    }

    public void setPreferentialInterestTillDate(Date preferentialInterestTillDate) {
        this.preferentialInterestTillDate = preferentialInterestTillDate;
    }

    public String getLastChangeUserID() {
        return lastChangeUserID;
    }

    public void setLastChangeUserID(String lastChangeUserID) {
        this.lastChangeUserID = lastChangeUserID;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getTsCnt() {
        return tsCnt;
    }

    public void setTsCnt(String tsCnt) {
        this.tsCnt = tsCnt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cBSCustCurrencyInfoPK != null ? cBSCustCurrencyInfoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBSCustCurrencyInfo)) {
            return false;
        }
        CBSCustCurrencyInfo other = (CBSCustCurrencyInfo) object;
        if ((this.cBSCustCurrencyInfoPK == null && other.cBSCustCurrencyInfoPK != null) || (this.cBSCustCurrencyInfoPK != null && !this.cBSCustCurrencyInfoPK.equals(other.cBSCustCurrencyInfoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CBSCustCurrencyInfo[ cBSCustCurrencyInfoPK=" + cBSCustCurrencyInfoPK + " ]";
    }
    
}
