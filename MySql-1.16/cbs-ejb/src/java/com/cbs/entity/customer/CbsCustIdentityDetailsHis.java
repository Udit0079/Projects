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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_cust_identity_details_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsCustIdentityDetailsHis.findAll", query = "SELECT c FROM CbsCustIdentityDetailsHis c"),
    @NamedQuery(name = "CbsCustIdentityDetailsHis.findByCustomerId", query = "SELECT c FROM CbsCustIdentityDetailsHis c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CbsCustIdentityDetailsHis.findByIdentificationType", query = "SELECT c FROM CbsCustIdentityDetailsHis c WHERE c.identificationType = :identificationType"),
    @NamedQuery(name = "CbsCustIdentityDetailsHis.findByIdentityNo", query = "SELECT c FROM CbsCustIdentityDetailsHis c WHERE c.identityNo = :identityNo"),
    @NamedQuery(name = "CbsCustIdentityDetailsHis.findByIdExpiryDate", query = "SELECT c FROM CbsCustIdentityDetailsHis c WHERE c.idExpiryDate = :idExpiryDate"),
    @NamedQuery(name = "CbsCustIdentityDetailsHis.findByOtherIdentificationType", query = "SELECT c FROM CbsCustIdentityDetailsHis c WHERE c.otherIdentificationType = :otherIdentificationType"),
    @NamedQuery(name = "CbsCustIdentityDetailsHis.findByTinIssuingCountry", query = "SELECT c FROM CbsCustIdentityDetailsHis c WHERE c.tinIssuingCountry = :tinIssuingCountry"),
    @NamedQuery(name = "CbsCustIdentityDetailsHis.findByEnterDate", query = "SELECT c FROM CbsCustIdentityDetailsHis c WHERE c.enterDate = :enterDate"),
    @NamedQuery(name = "CbsCustIdentityDetailsHis.findByEnterTime", query = "SELECT c FROM CbsCustIdentityDetailsHis c WHERE c.enterTime = :enterTime"),
    @NamedQuery(name = "CbsCustIdentityDetailsHis.findByEnterBy", query = "SELECT c FROM CbsCustIdentityDetailsHis c WHERE c.enterBy = :enterBy"),
    @NamedQuery(name = "CbsCustIdentityDetailsHis.findByTxnid", query = "SELECT c FROM CbsCustIdentityDetailsHis c WHERE c.txnid = :txnid")})
public class CbsCustIdentityDetailsHis extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Size(max = 6)
    @Column(name = "IdentificationType")
    private String identificationType;
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
    @Column(name = "EnterTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enterTime;
    @Size(max = 15)
    @Column(name = "EnterBy")
    private String enterBy;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TXNID")
    private Long txnid;

    public CbsCustIdentityDetailsHis() {
    }

    public CbsCustIdentityDetailsHis(Long txnid) {
        this.txnid = txnid;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
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
        if (!(object instanceof CbsCustIdentityDetailsHis)) {
            return false;
        }
        CbsCustIdentityDetailsHis other = (CbsCustIdentityDetailsHis) object;
        if ((this.txnid == null && other.txnid != null) || (this.txnid != null && !this.txnid.equals(other.txnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsCustIdentityDetailsHis[ txnid=" + txnid + " ]";
    }
}
