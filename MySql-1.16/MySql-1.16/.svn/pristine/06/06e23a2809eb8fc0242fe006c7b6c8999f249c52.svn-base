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
@Table(name = "cbs_kyc_assets_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsKycAssetsHis.findAll", query = "SELECT c FROM CbsKycAssetsHis c"),
    @NamedQuery(name = "CbsKycAssetsHis.findByCustomerId", query = "SELECT c FROM CbsKycAssetsHis c WHERE c.cbsKycAssetsHisPK.customerId = :customerId"),
    @NamedQuery(name = "CbsKycAssetsHis.findByTxnId", query = "SELECT c FROM CbsKycAssetsHis c WHERE c.cbsKycAssetsHisPK.txnId = :txnId"),
    @NamedQuery(name = "CbsKycAssetsHis.findByAssetstype", query = "SELECT c FROM CbsKycAssetsHis c WHERE c.assetstype = :assetstype"),
    @NamedQuery(name = "CbsKycAssetsHis.findByAssets", query = "SELECT c FROM CbsKycAssetsHis c WHERE c.assets = :assets"),
    @NamedQuery(name = "CbsKycAssetsHis.findByAssetsvalue", query = "SELECT c FROM CbsKycAssetsHis c WHERE c.assetsvalue = :assetsvalue"),
    @NamedQuery(name = "CbsKycAssetsHis.findByLastChangeUserID", query = "SELECT c FROM CbsKycAssetsHis c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CbsKycAssetsHis.findByLastChangeTime", query = "SELECT c FROM CbsKycAssetsHis c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CbsKycAssetsHis.findByRecordCreaterID", query = "SELECT c FROM CbsKycAssetsHis c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CbsKycAssetsHis.findByCreationTime", query = "SELECT c FROM CbsKycAssetsHis c WHERE c.creationTime = :creationTime")})
public class CbsKycAssetsHis extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsKycAssetsHisPK cbsKycAssetsHisPK;
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

    public CbsKycAssetsHis() {
    }

    public CbsKycAssetsHis(CbsKycAssetsHisPK cbsKycAssetsHisPK) {
        this.cbsKycAssetsHisPK = cbsKycAssetsHisPK;
    }

    public CbsKycAssetsHis(String customerId, int txnId) {
        this.cbsKycAssetsHisPK = new CbsKycAssetsHisPK(customerId, txnId);
    }

    public CbsKycAssetsHisPK getCbsKycAssetsHisPK() {
        return cbsKycAssetsHisPK;
    }

    public void setCbsKycAssetsHisPK(CbsKycAssetsHisPK cbsKycAssetsHisPK) {
        this.cbsKycAssetsHisPK = cbsKycAssetsHisPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsKycAssetsHisPK != null ? cbsKycAssetsHisPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsKycAssetsHis)) {
            return false;
        }
        CbsKycAssetsHis other = (CbsKycAssetsHis) object;
        if ((this.cbsKycAssetsHisPK == null && other.cbsKycAssetsHisPK != null) || (this.cbsKycAssetsHisPK != null && !this.cbsKycAssetsHisPK.equals(other.cbsKycAssetsHisPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsKycAssetsHis[ cbsKycAssetsHisPK=" + cbsKycAssetsHisPK + " ]";
    }
    
}
