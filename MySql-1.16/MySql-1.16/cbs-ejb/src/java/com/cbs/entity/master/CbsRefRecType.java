/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.master;

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
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_ref_rec_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsRefRecType.findAll", query = "SELECT c FROM CbsRefRecType c"),
    @NamedQuery(name = "CbsRefRecType.findByRefRecNo", query = "SELECT c FROM CbsRefRecType c WHERE c.cbsRefRecTypePK.refRecNo = :refRecNo"),
    @NamedQuery(name = "CbsRefRecType.findByRefCode", query = "SELECT c FROM CbsRefRecType c WHERE c.cbsRefRecTypePK.refCode = :refCode"),
    @NamedQuery(name = "CbsRefRecType.findByRefDesc", query = "SELECT c FROM CbsRefRecType c WHERE c.refDesc = :refDesc"),
    @NamedQuery(name = "CbsRefRecType.findByCreatedByUserId", query = "SELECT c FROM CbsRefRecType c WHERE c.createdByUserId = :createdByUserId"),
    @NamedQuery(name = "CbsRefRecType.findByCreationDate", query = "SELECT c FROM CbsRefRecType c WHERE c.creationDate = :creationDate"),
    @NamedQuery(name = "CbsRefRecType.findByLastUpdatedByUserId", query = "SELECT c FROM CbsRefRecType c WHERE c.lastUpdatedByUserId = :lastUpdatedByUserId"),
    @NamedQuery(name = "CbsRefRecType.findByLastUpdatedDate", query = "SELECT c FROM CbsRefRecType c WHERE c.lastUpdatedDate = :lastUpdatedDate"),
    @NamedQuery(name = "CbsRefRecType.findByTsCnt", query = "SELECT c FROM CbsRefRecType c WHERE c.tsCnt = :tsCnt")})
public class CbsRefRecType extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsRefRecTypePK cbsRefRecTypePK;
    @Size(max = 60)
    @Column(name = "REF_DESC")
    private String refDesc;
    @Size(max = 15)
    @Column(name = "CREATED_BY_USER_ID")
    private String createdByUserId;
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Size(max = 15)
    @Column(name = "LAST_UPDATED_BY_USER_ID")
    private String lastUpdatedByUserId;
    @Column(name = "LAST_UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;
    @Column(name = "TS_CNT")
    private Integer tsCnt;

    public CbsRefRecType() {
    }

    public CbsRefRecType(CbsRefRecTypePK cbsRefRecTypePK) {
        this.cbsRefRecTypePK = cbsRefRecTypePK;
    }

    public CbsRefRecType(String refRecNo, String refCode) {
        this.cbsRefRecTypePK = new CbsRefRecTypePK(refRecNo, refCode);
    }

    public CbsRefRecTypePK getCbsRefRecTypePK() {
        return cbsRefRecTypePK;
    }

    public void setCbsRefRecTypePK(CbsRefRecTypePK cbsRefRecTypePK) {
        this.cbsRefRecTypePK = cbsRefRecTypePK;
    }

    public String getRefDesc() {
        return refDesc;
    }

    public void setRefDesc(String refDesc) {
        this.refDesc = refDesc;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedByUserId() {
        return lastUpdatedByUserId;
    }

    public void setLastUpdatedByUserId(String lastUpdatedByUserId) {
        this.lastUpdatedByUserId = lastUpdatedByUserId;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getTsCnt() {
        return tsCnt;
    }

    public void setTsCnt(Integer tsCnt) {
        this.tsCnt = tsCnt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsRefRecTypePK != null ? cbsRefRecTypePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsRefRecType)) {
            return false;
        }
        CbsRefRecType other = (CbsRefRecType) object;
        if ((this.cbsRefRecTypePK == null && other.cbsRefRecTypePK != null) || (this.cbsRefRecTypePK != null && !this.cbsRefRecTypePK.equals(other.cbsRefRecTypePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsRefRecType[ cbsRefRecTypePK=" + cbsRefRecTypePK + " ]";
    }
    
}
