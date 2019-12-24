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
 * @author root
 */
@Entity
@Table(name = "cbs_cust_kyc_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsCustKycDetails.findAll", query = "SELECT c FROM CbsCustKycDetails c"),
    @NamedQuery(name = "CbsCustKycDetails.findByCustomerId", query = "SELECT c FROM CbsCustKycDetails c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CbsCustKycDetails.findByRiskCategory", query = "SELECT c FROM CbsCustKycDetails c WHERE c.riskCategory = :riskCategory"),
    @NamedQuery(name = "CbsCustKycDetails.findByTypeOfDocSubmitted", query = "SELECT c FROM CbsCustKycDetails c WHERE c.typeOfDocSubmitted = :typeOfDocSubmitted"),
    @NamedQuery(name = "CbsCustKycDetails.findByBrnCode", query = "SELECT c FROM CbsCustKycDetails c WHERE c.brnCode = :brnCode"),
    @NamedQuery(name = "CbsCustKycDetails.findByErrorCode", query = "SELECT c FROM CbsCustKycDetails c WHERE c.errorCode = :errorCode"),
    @NamedQuery(name = "CbsCustKycDetails.findByCkycrUpdateFlag", query = "SELECT c FROM CbsCustKycDetails c WHERE c.ckycrUpdateFlag = :ckycrUpdateFlag"),
    @NamedQuery(name = "CbsCustKycDetails.findByKycCreatedBy", query = "SELECT c FROM CbsCustKycDetails c WHERE c.kycCreatedBy = :kycCreatedBy"),
    @NamedQuery(name = "CbsCustKycDetails.findByKycCreationDate", query = "SELECT c FROM CbsCustKycDetails c WHERE c.kycCreationDate = :kycCreationDate"),
    @NamedQuery(name = "CbsCustKycDetails.findByKycVerifiedBy", query = "SELECT c FROM CbsCustKycDetails c WHERE c.kycVerifiedBy = :kycVerifiedBy"),
    @NamedQuery(name = "CbsCustKycDetails.findByKycVerifiedDate", query = "SELECT c FROM CbsCustKycDetails c WHERE c.kycVerifiedDate = :kycVerifiedDate"),
    @NamedQuery(name = "CbsCustKycDetails.findByKycVerifiedUserName", query = "SELECT c FROM CbsCustKycDetails c WHERE c.kycVerifiedUserName = :kycVerifiedUserName"),
    @NamedQuery(name = "CbsCustKycDetails.findByTxnId", query = "SELECT c FROM CbsCustKycDetails c WHERE c.txnId = :txnId")})
public class CbsCustKycDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "RiskCategory")
    private String riskCategory;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "TypeOfDocSubmitted")
    private String typeOfDocSubmitted;
    @Size(max = 20)
    @Column(name = "BrnCode")
    private String brnCode;
    @Size(max = 250)
    @Column(name = "ErrorCode")
    private String errorCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CkycrUpdateFlag")
    private String ckycrUpdateFlag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "KycCreatedBy")
    private String kycCreatedBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "KycCreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kycCreationDate;
    @Size(max = 50)
    @Column(name = "KycVerifiedBy")
    private String kycVerifiedBy;
    @Column(name = "KycVerifiedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kycVerifiedDate;
    @Size(max = 150)
    @Column(name = "KycVerifiedUserName")
    private String kycVerifiedUserName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TxnId")
    private Integer txnId;

    public CbsCustKycDetails() {
    }

    public CbsCustKycDetails(Integer txnId) {
        this.txnId = txnId;
    }

    public CbsCustKycDetails(Integer txnId, String customerId, String riskCategory, String typeOfDocSubmitted, String ckycrUpdateFlag, String kycCreatedBy, Date kycCreationDate) {
        this.txnId = txnId;
        this.customerId = customerId;
        this.riskCategory = riskCategory;
        this.typeOfDocSubmitted = typeOfDocSubmitted;
        this.ckycrUpdateFlag = ckycrUpdateFlag;
        this.kycCreatedBy = kycCreatedBy;
        this.kycCreationDate = kycCreationDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }

    public String getTypeOfDocSubmitted() {
        return typeOfDocSubmitted;
    }

    public void setTypeOfDocSubmitted(String typeOfDocSubmitted) {
        this.typeOfDocSubmitted = typeOfDocSubmitted;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCkycrUpdateFlag() {
        return ckycrUpdateFlag;
    }

    public void setCkycrUpdateFlag(String ckycrUpdateFlag) {
        this.ckycrUpdateFlag = ckycrUpdateFlag;
    }

    public String getKycCreatedBy() {
        return kycCreatedBy;
    }

    public void setKycCreatedBy(String kycCreatedBy) {
        this.kycCreatedBy = kycCreatedBy;
    }

    public Date getKycCreationDate() {
        return kycCreationDate;
    }

    public void setKycCreationDate(Date kycCreationDate) {
        this.kycCreationDate = kycCreationDate;
    }

    public String getKycVerifiedBy() {
        return kycVerifiedBy;
    }

    public void setKycVerifiedBy(String kycVerifiedBy) {
        this.kycVerifiedBy = kycVerifiedBy;
    }

    public Date getKycVerifiedDate() {
        return kycVerifiedDate;
    }

    public void setKycVerifiedDate(Date kycVerifiedDate) {
        this.kycVerifiedDate = kycVerifiedDate;
    }

    public String getKycVerifiedUserName() {
        return kycVerifiedUserName;
    }

    public void setKycVerifiedUserName(String kycVerifiedUserName) {
        this.kycVerifiedUserName = kycVerifiedUserName;
    }

    public Integer getTxnId() {
        return txnId;
    }

    public void setTxnId(Integer txnId) {
        this.txnId = txnId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnId != null ? txnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsCustKycDetails)) {
            return false;
        }
        CbsCustKycDetails other = (CbsCustKycDetails) object;
        if ((this.txnId == null && other.txnId != null) || (this.txnId != null && !this.txnId.equals(other.txnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsCustKycDetails[ txnId=" + txnId + " ]";
    }
}
