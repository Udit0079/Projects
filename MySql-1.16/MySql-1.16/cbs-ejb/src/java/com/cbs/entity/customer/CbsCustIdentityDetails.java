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
@Table(name = "cbs_cust_identity_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsCustIdentityDetails.findAll", query = "SELECT c FROM CbsCustIdentityDetails c"),
    @NamedQuery(name = "CbsCustIdentityDetails.findByCustomerId", query = "SELECT c FROM CbsCustIdentityDetails c WHERE c.cbsCustIdentityDetailsPK.customerId = :customerId"),
    @NamedQuery(name = "CbsCustIdentityDetails.findByIdentificationType", query = "SELECT c FROM CbsCustIdentityDetails c WHERE c.cbsCustIdentityDetailsPK.identificationType = :identificationType"),
    @NamedQuery(name = "CbsCustIdentityDetails.findByIdentityNo", query = "SELECT c FROM CbsCustIdentityDetails c WHERE c.identityNo = :identityNo"),
    @NamedQuery(name = "CbsCustIdentityDetails.findByIdExpiryDate", query = "SELECT c FROM CbsCustIdentityDetails c WHERE c.idExpiryDate = :idExpiryDate"),
    @NamedQuery(name = "CbsCustIdentityDetails.findByOtherIdentificationType", query = "SELECT c FROM CbsCustIdentityDetails c WHERE c.otherIdentificationType = :otherIdentificationType"),
    @NamedQuery(name = "CbsCustIdentityDetails.findByTinIssuingCountry", query = "SELECT c FROM CbsCustIdentityDetails c WHERE c.tinIssuingCountry = :tinIssuingCountry"),
    @NamedQuery(name = "CbsCustIdentityDetails.findByEnterDate", query = "SELECT c FROM CbsCustIdentityDetails c WHERE c.enterDate = :enterDate"),
    @NamedQuery(name = "CbsCustIdentityDetails.findByEnterTime", query = "SELECT c FROM CbsCustIdentityDetails c WHERE c.enterTime = :enterTime"),
    @NamedQuery(name = "CbsCustIdentityDetails.findByEnterBy", query = "SELECT c FROM CbsCustIdentityDetails c WHERE c.enterBy = :enterBy")})
public class CbsCustIdentityDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsCustIdentityDetailsPK cbsCustIdentityDetailsPK;
    @Size(max = 25)
    @Column(name = "IdentityNo")
    private String identityNo;
    @Size(max = 8)
    @Column(name = "IdExpiryDate")
    private String idExpiryDate;
    @Size(max = 60)
    @Column(name = "OtherIdentificationType")
    private String otherIdentificationType;
    @Size(max = 6)
    @Column(name = "TinIssuingCountry")
    private String tinIssuingCountry;
    @Column(name = "EnterDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enterDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EnterTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enterTime;
    @Size(max = 15)
    @Column(name = "EnterBy")
    private String enterBy;

    public CbsCustIdentityDetails() {
    }

    public CbsCustIdentityDetails(CbsCustIdentityDetailsPK cbsCustIdentityDetailsPK) {
        this.cbsCustIdentityDetailsPK = cbsCustIdentityDetailsPK;
    }

    public CbsCustIdentityDetails(CbsCustIdentityDetailsPK cbsCustIdentityDetailsPK, Date enterTime) {
        this.cbsCustIdentityDetailsPK = cbsCustIdentityDetailsPK;
        this.enterTime = enterTime;
    }

    public CbsCustIdentityDetails(String customerId, String identificationType) {
        this.cbsCustIdentityDetailsPK = new CbsCustIdentityDetailsPK(customerId, identificationType);
    }

    public CbsCustIdentityDetailsPK getCbsCustIdentityDetailsPK() {
        return cbsCustIdentityDetailsPK;
    }

    public void setCbsCustIdentityDetailsPK(CbsCustIdentityDetailsPK cbsCustIdentityDetailsPK) {
        this.cbsCustIdentityDetailsPK = cbsCustIdentityDetailsPK;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getIdExpiryDate() {
        return idExpiryDate;
    }

    public void setIdExpiryDate(String idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    public String getOtherIdentificationType() {
        return otherIdentificationType;
    }

    public void setOtherIdentificationType(String otherIdentificationType) {
        this.otherIdentificationType = otherIdentificationType;
    }

    public String getTinIssuingCountry() {
        return tinIssuingCountry;
    }

    public void setTinIssuingCountry(String tinIssuingCountry) {
        this.tinIssuingCountry = tinIssuingCountry;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsCustIdentityDetailsPK != null ? cbsCustIdentityDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsCustIdentityDetails)) {
            return false;
        }
        CbsCustIdentityDetails other = (CbsCustIdentityDetails) object;
        if ((this.cbsCustIdentityDetailsPK == null && other.cbsCustIdentityDetailsPK != null) || (this.cbsCustIdentityDetailsPK != null && !this.cbsCustIdentityDetailsPK.equals(other.cbsCustIdentityDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsCustIdentityDetails[ cbsCustIdentityDetailsPK=" + cbsCustIdentityDetailsPK + " ]";
    }
}
