/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.master;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author root
 */
@Entity
@Table(name = "cbs_charge_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsChargeDetail.findAll", query = "SELECT c FROM CbsChargeDetail c"),
    @NamedQuery(name = "CbsChargeDetail.findByChargeType", query = "SELECT c FROM CbsChargeDetail c WHERE c.chargeType = :chargeType"),
    @NamedQuery(name = "CbsChargeDetail.findByChargeName", query = "SELECT c FROM CbsChargeDetail c WHERE c.cbsChargeDetailPK.chargeName = :chargeName"),
    @NamedQuery(name = "CbsChargeDetail.findByAcType", query = "SELECT c FROM CbsChargeDetail c WHERE c.cbsChargeDetailPK.acType = :acType"),
    @NamedQuery(name = "CbsChargeDetail.findByFromRange", query = "SELECT c FROM CbsChargeDetail c WHERE c.cbsChargeDetailPK.fromRange = :fromRange"),
    @NamedQuery(name = "CbsChargeDetail.findByToRange", query = "SELECT c FROM CbsChargeDetail c WHERE c.cbsChargeDetailPK.toRange = :toRange"),
    @NamedQuery(name = "CbsChargeDetail.findByFixPercFlag", query = "SELECT c FROM CbsChargeDetail c WHERE c.fixPercFlag = :fixPercFlag"),
    @NamedQuery(name = "CbsChargeDetail.findByAmt", query = "SELECT c FROM CbsChargeDetail c WHERE c.amt = :amt"),
    @NamedQuery(name = "CbsChargeDetail.findByEffDate", query = "SELECT c FROM CbsChargeDetail c WHERE c.cbsChargeDetailPK.effDate = :effDate"),
    @NamedQuery(name = "CbsChargeDetail.findByCrGlHead", query = "SELECT c FROM CbsChargeDetail c WHERE c.crGlHead = :crGlHead"),
    @NamedQuery(name = "CbsChargeDetail.findByEnterBy", query = "SELECT c FROM CbsChargeDetail c WHERE c.enterBy = :enterBy"),
    @NamedQuery(name = "CbsChargeDetail.findByCreationDate", query = "SELECT c FROM CbsChargeDetail c WHERE c.creationDate = :creationDate"),
    @NamedQuery(name = "CbsChargeDetail.findByUpdatedBy", query = "SELECT c FROM CbsChargeDetail c WHERE c.updatedBy = :updatedBy"),
    @NamedQuery(name = "CbsChargeDetail.findByUpdateDate", query = "SELECT c FROM CbsChargeDetail c WHERE c.updateDate = :updateDate"),
    @NamedQuery(name = "CbsChargeDetail.findBySmsFrequency", query = "SELECT c FROM CbsChargeDetail c WHERE c.smsFrequency = :smsFrequency")})
public class CbsChargeDetail extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsChargeDetailPK cbsChargeDetailPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CHARGE_TYPE")
    private String chargeType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "FIX_PERC_FLAG")
    private String fixPercFlag;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMT")
    private double amt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CR_GL_HEAD")
    private String crGlHead;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Size(max = 20)
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    @Column(name = "UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Size(max = 1)
    @Column(name = "SMS_FREQUENCY")
    private String smsFrequency;

    public CbsChargeDetail() {
    }

    public CbsChargeDetail(CbsChargeDetailPK cbsChargeDetailPK) {
        this.cbsChargeDetailPK = cbsChargeDetailPK;
    }

    public CbsChargeDetail(CbsChargeDetailPK cbsChargeDetailPK, String chargeType, String fixPercFlag, double amt, String crGlHead, String enterBy, Date creationDate) {
        this.cbsChargeDetailPK = cbsChargeDetailPK;
        this.chargeType = chargeType;
        this.fixPercFlag = fixPercFlag;
        this.amt = amt;
        this.crGlHead = crGlHead;
        this.enterBy = enterBy;
        this.creationDate = creationDate;
    }

    public CbsChargeDetail(String chargeName, String acType, BigDecimal fromRange, BigDecimal toRange, Date effDate) {
        this.cbsChargeDetailPK = new CbsChargeDetailPK(chargeName, acType, fromRange, toRange, effDate);
    }

    public CbsChargeDetailPK getCbsChargeDetailPK() {
        return cbsChargeDetailPK;
    }

    public void setCbsChargeDetailPK(CbsChargeDetailPK cbsChargeDetailPK) {
        this.cbsChargeDetailPK = cbsChargeDetailPK;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getFixPercFlag() {
        return fixPercFlag;
    }

    public void setFixPercFlag(String fixPercFlag) {
        this.fixPercFlag = fixPercFlag;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getCrGlHead() {
        return crGlHead;
    }

    public void setCrGlHead(String crGlHead) {
        this.crGlHead = crGlHead;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
     public String getSmsFrequency() {
        return smsFrequency;
    }

    public void setSmsFrequency(String smsFrequency) {
        this.smsFrequency = smsFrequency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsChargeDetailPK != null ? cbsChargeDetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsChargeDetail)) {
            return false;
        }
        CbsChargeDetail other = (CbsChargeDetail) object;
        if ((this.cbsChargeDetailPK == null && other.cbsChargeDetailPK != null) || (this.cbsChargeDetailPK != null && !this.cbsChargeDetailPK.equals(other.cbsChargeDetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.master.CbsChargeDetail[ cbsChargeDetailPK=" + cbsChargeDetailPK + " ]";
    }
    
}
