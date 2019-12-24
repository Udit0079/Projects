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
@Table(name = "cbs_buyer_seller_limit_details_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findAll", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByCustomerId", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByDraweeCode", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.draweeCode = :draweeCode"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByBillType", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.billType = :billType"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByStaus", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.staus = :staus"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByImpCurrencyCodeCCY", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.impCurrencyCodeCCY = :impCurrencyCodeCCY"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByImportLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.importLimit = :importLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByUtilisedImportLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.utilisedImportLimit = :utilisedImportLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByBuyLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.buyLimit = :buyLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByUtilisedBuyLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.utilisedBuyLimit = :utilisedBuyLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByExpCurrencyCodeCCY", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.expCurrencyCodeCCY = :expCurrencyCodeCCY"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByExportLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.exportLimit = :exportLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByUtilisedExportLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.utilisedExportLimit = :utilisedExportLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findBySellLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.sellLimit = :sellLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByUtilisedSellLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.utilisedSellLimit = :utilisedSellLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByLastChangeUserID", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByLastChangeTime", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByRecordCreaterID", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetailsHis.findByTxnid", query = "SELECT c FROM CBSBuyerSellerLimitDetailsHis c WHERE c.txnid = :txnid")})
public class CBSBuyerSellerLimitDetailsHis extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Size(max = 10)
    @Column(name = "DraweeCode")
    private String draweeCode;
    @Size(max = 1)
    @Column(name = "BillType")
    private String billType;
    @Size(max = 1)
    @Column(name = "Staus")
    private String staus;
    @Size(max = 3)
    @Column(name = "ImpCurrencyCode_CCY")
    private String impCurrencyCodeCCY;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ImportLimit")
    private Double importLimit;
    @Column(name = "UtilisedImportLimit")
    private Double utilisedImportLimit;
    @Column(name = "BuyLimit")
    private Double buyLimit;
    @Column(name = "UtilisedBuyLimit")
    private Double utilisedBuyLimit;
    @Size(max = 3)
    @Column(name = "ExpCurrencyCode_CCY")
    private String expCurrencyCodeCCY;
    @Column(name = "ExportLimit")
    private Double exportLimit;
    @Column(name = "UtilisedExportLimit")
    private Double utilisedExportLimit;
    @Column(name = "SellLimit")
    private Double sellLimit;
    @Column(name = "UtilisedSellLimit")
    private Double utilisedSellLimit;
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

    public CBSBuyerSellerLimitDetailsHis() {
    }

    public CBSBuyerSellerLimitDetailsHis(Long txnid) {
        this.txnid = txnid;
    }

    public CBSBuyerSellerLimitDetailsHis(Long txnid, String customerId) {
        this.txnid = txnid;
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDraweeCode() {
        return draweeCode;
    }

    public void setDraweeCode(String draweeCode) {
        this.draweeCode = draweeCode;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public String getImpCurrencyCodeCCY() {
        return impCurrencyCodeCCY;
    }

    public void setImpCurrencyCodeCCY(String impCurrencyCodeCCY) {
        this.impCurrencyCodeCCY = impCurrencyCodeCCY;
    }

    public Double getImportLimit() {
        return importLimit;
    }

    public void setImportLimit(Double importLimit) {
        this.importLimit = importLimit;
    }

    public Double getUtilisedImportLimit() {
        return utilisedImportLimit;
    }

    public void setUtilisedImportLimit(Double utilisedImportLimit) {
        this.utilisedImportLimit = utilisedImportLimit;
    }

    public Double getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(Double buyLimit) {
        this.buyLimit = buyLimit;
    }

    public Double getUtilisedBuyLimit() {
        return utilisedBuyLimit;
    }

    public void setUtilisedBuyLimit(Double utilisedBuyLimit) {
        this.utilisedBuyLimit = utilisedBuyLimit;
    }

    public String getExpCurrencyCodeCCY() {
        return expCurrencyCodeCCY;
    }

    public void setExpCurrencyCodeCCY(String expCurrencyCodeCCY) {
        this.expCurrencyCodeCCY = expCurrencyCodeCCY;
    }

    public Double getExportLimit() {
        return exportLimit;
    }

    public void setExportLimit(Double exportLimit) {
        this.exportLimit = exportLimit;
    }

    public Double getUtilisedExportLimit() {
        return utilisedExportLimit;
    }

    public void setUtilisedExportLimit(Double utilisedExportLimit) {
        this.utilisedExportLimit = utilisedExportLimit;
    }

    public Double getSellLimit() {
        return sellLimit;
    }

    public void setSellLimit(Double sellLimit) {
        this.sellLimit = sellLimit;
    }

    public Double getUtilisedSellLimit() {
        return utilisedSellLimit;
    }

    public void setUtilisedSellLimit(Double utilisedSellLimit) {
        this.utilisedSellLimit = utilisedSellLimit;
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
        if (!(object instanceof CBSBuyerSellerLimitDetailsHis)) {
            return false;
        }
        CBSBuyerSellerLimitDetailsHis other = (CBSBuyerSellerLimitDetailsHis) object;
        if ((this.txnid == null && other.txnid != null) || (this.txnid != null && !this.txnid.equals(other.txnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CBSBuyerSellerLimitDetailsHis[ txnid=" + txnid + " ]";
    }
    
}
