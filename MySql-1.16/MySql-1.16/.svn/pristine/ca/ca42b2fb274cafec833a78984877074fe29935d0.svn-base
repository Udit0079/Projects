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
@Table(name = "cbs_buyer_seller_limit_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findAll", query = "SELECT c FROM CBSBuyerSellerLimitDetails c"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByCustomerId", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByDraweeCode", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.draweeCode = :draweeCode"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByBillType", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.billType = :billType"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByStaus", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.staus = :staus"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByImpCurrencyCodeCCY", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.impCurrencyCodeCCY = :impCurrencyCodeCCY"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByImportLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.importLimit = :importLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByUtilisedImportLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.utilisedImportLimit = :utilisedImportLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByBuyLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.buyLimit = :buyLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByUtilisedBuyLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.utilisedBuyLimit = :utilisedBuyLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByExpCurrencyCodeCCY", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.expCurrencyCodeCCY = :expCurrencyCodeCCY"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByExportLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.exportLimit = :exportLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByUtilisedExportLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.utilisedExportLimit = :utilisedExportLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findBySellLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.sellLimit = :sellLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByUtilisedSellLimit", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.utilisedSellLimit = :utilisedSellLimit"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByLastChangeUserID", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByLastChangeTime", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByRecordCreaterID", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByCreationTime", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "CBSBuyerSellerLimitDetails.findByTsCnt", query = "SELECT c FROM CBSBuyerSellerLimitDetails c WHERE c.tsCnt = :tsCnt")})
public class CBSBuyerSellerLimitDetails extends BaseEntity implements  Serializable {
    private static final long serialVersionUID = 1L;
    @Id
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
    @Column(name = "CreationTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    @Size(max = 5)
    @Column(name = "TsCnt")
    private String tsCnt;

    public CBSBuyerSellerLimitDetails() {
    }

    public CBSBuyerSellerLimitDetails(String customerId) {
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
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBSBuyerSellerLimitDetails)) {
            return false;
        }
        CBSBuyerSellerLimitDetails other = (CBSBuyerSellerLimitDetails) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CBSBuyerSellerLimitDetails[ customerId=" + customerId + " ]";
    }
    
}
