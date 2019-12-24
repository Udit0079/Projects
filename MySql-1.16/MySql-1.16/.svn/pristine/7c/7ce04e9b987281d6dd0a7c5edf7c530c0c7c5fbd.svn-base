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
@Table(name = "cbs_kyc_assets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsKycAssets.findAll", query = "SELECT c FROM CbsKycAssets c"),
    @NamedQuery(name = "CbsKycAssets.findByCustomerId", query = "SELECT c FROM CbsKycAssets c WHERE c.cbsKycAssetsPK.customerId = :customerId"),
    @NamedQuery(name = "CbsKycAssets.findByTxnId", query = "SELECT c FROM CbsKycAssets c WHERE c.cbsKycAssetsPK.txnId = :txnId"),
    @NamedQuery(name = "CbsKycAssets.findByAssetstype", query = "SELECT c FROM CbsKycAssets c WHERE c.assetstype = :assetstype"),
    @NamedQuery(name = "CbsKycAssets.findByAssets", query = "SELECT c FROM CbsKycAssets c WHERE c.assets = :assets"),
    @NamedQuery(name = "CbsKycAssets.findByAssetsvalue", query = "SELECT c FROM CbsKycAssets c WHERE c.assetsvalue = :assetsvalue"),
    @NamedQuery(name = "CbsKycAssets.findByLastChangeUserID", query = "SELECT c FROM CbsKycAssets c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CbsKycAssets.findByLastChangeTime", query = "SELECT c FROM CbsKycAssets c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CbsKycAssets.findByRecordCreaterID", query = "SELECT c FROM CbsKycAssets c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CbsKycAssets.findByCreationTime", query = "SELECT c FROM CbsKycAssets c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "CbsKycAssets.getMaxTxnIdByCustomerId", query = "SELECT max(c.cbsKycAssetsPK.txnId) FROM CbsKycAssets c WHERE c.cbsKycAssetsPK.customerId = :customerId"),
    @NamedQuery(name = "CbsKycAssets.findByTsCnt", query = "SELECT c FROM CbsKycAssets c WHERE c.tsCnt = :tsCnt")})
public class CbsKycAssets extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsKycAssetsPK cbsKycAssetsPK;
    @Size(max = 255)
    @Column(name = "assetstype")
    private String assetstype;
    @Size(max = 255)
    @Column(name = "assets")
    private String assets;
    @Size(max = 255)
    @Column(name = "assetsvalue")
    private String assetsvalue;
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

    public CbsKycAssets() {
    }

    public CbsKycAssets(CbsKycAssetsPK cbsKycAssetsPK) {
        this.cbsKycAssetsPK = cbsKycAssetsPK;
    }

    public CbsKycAssets(String customerId, int txnId) {
        this.cbsKycAssetsPK = new CbsKycAssetsPK(customerId, txnId);
    }

    public CbsKycAssetsPK getCbsKycAssetsPK() {
        return cbsKycAssetsPK;
    }

    public void setCbsKycAssetsPK(CbsKycAssetsPK cbsKycAssetsPK) {
        this.cbsKycAssetsPK = cbsKycAssetsPK;
    }

    public String getAssetstype() {
        return assetstype;
    }

    public void setAssetstype(String assetstype) {
        this.assetstype = assetstype;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public String getAssetsvalue() {
        return assetsvalue;
    }

    public void setAssetsvalue(String assetsvalue) {
        this.assetsvalue = assetsvalue;
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
        hash += (cbsKycAssetsPK != null ? cbsKycAssetsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsKycAssets)) {
            return false;
        }
        CbsKycAssets other = (CbsKycAssets) object;
        if ((this.cbsKycAssetsPK == null && other.cbsKycAssetsPK != null) || (this.cbsKycAssetsPK != null && !this.cbsKycAssetsPK.equals(other.cbsKycAssetsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsKycAssets[ cbsKycAssetsPK=" + cbsKycAssetsPK + " ]";
    }
}
