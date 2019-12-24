/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.customer;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ROHIT KRISHNA
 */
@Entity
@Table(name = "cbs_cust_currencyinfo_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findAll", query = "SELECT c FROM CBSCustCurrencyInfoHis c"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByCustomerId", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByCurrencyCodeType", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.currencyCodeType = :currencyCodeType"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByWithHoldingTaxLevel", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.withHoldingTaxLevel = :withHoldingTaxLevel"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByWithHoldingTax", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.withHoldingTax = :withHoldingTax"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByWithHoldingTaxLimit", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.withHoldingTaxLimit = :withHoldingTaxLimit"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByTDSOperativeAccountID", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.tDSOperativeAccountID = :tDSOperativeAccountID"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByCustinterestRateCredit", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.custinterestRateCredit = :custinterestRateCredit"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByCustInterestRateDebit", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.custInterestRateDebit = :custInterestRateDebit"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByPreferentialInterestTillDate", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.preferentialInterestTillDate = :preferentialInterestTillDate"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByLastChangeUserID", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByLastChangeTime", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByRecordCreaterID", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CBSCustCurrencyInfoHis.findByTxnid", query = "SELECT c FROM CBSCustCurrencyInfoHis c WHERE c.txnid = :txnid")})
public class CBSCustCurrencyInfoHis extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CustomerId")
    private String customerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CurrencyCodeType")
    private String currencyCodeType;
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
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "TXNID")
    private Long txnid;

    public CBSCustCurrencyInfoHis() {
    }

    public CBSCustCurrencyInfoHis(Long txnid) {
        this.txnid = txnid;
    }

    public CBSCustCurrencyInfoHis(Long txnid, String customerId, String currencyCodeType) {
        this.txnid = txnid;
        this.customerId = customerId;
        this.currencyCodeType = currencyCodeType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCurrencyCodeType() {
        return currencyCodeType;
    }

    public void setCurrencyCodeType(String currencyCodeType) {
        this.currencyCodeType = currencyCodeType;
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

    public Long getTxnid() {
        return txnid;
    }

    public void setTxnid(Long txnid) {
        this.txnid = txnid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnid != null ? txnid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBSCustCurrencyInfoHis)) {
            return false;
        }
        CBSCustCurrencyInfoHis other = (CBSCustCurrencyInfoHis) object;
        if ((this.txnid == null && other.txnid != null) || (this.txnid != null && !this.txnid.equals(other.txnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CBSCustCurrencyInfoHis[ txnid=" + txnid + " ]";
    }
    
}
