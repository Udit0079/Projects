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

@Entity
@Table(name = "cbs_cust_misinfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsCustMisinfo.findAll", query = "SELECT c FROM CbsCustMisinfo c"),
    @NamedQuery(name = "CbsCustMisinfo.findByCustomerId", query = "SELECT c FROM CbsCustMisinfo c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CbsCustMisinfo.findByType", query = "SELECT c FROM CbsCustMisinfo c WHERE c.type = :type"),
    @NamedQuery(name = "CbsCustMisinfo.findByGroupID", query = "SELECT c FROM CbsCustMisinfo c WHERE c.groupID = :groupID"),
    @NamedQuery(name = "CbsCustMisinfo.findByStatusCode", query = "SELECT c FROM CbsCustMisinfo c WHERE c.statusCode = :statusCode"),
    @NamedQuery(name = "CbsCustMisinfo.findByStatusAsOn", query = "SELECT c FROM CbsCustMisinfo c WHERE c.statusAsOn = :statusAsOn"),
    @NamedQuery(name = "CbsCustMisinfo.findByOccupationCode", query = "SELECT c FROM CbsCustMisinfo c WHERE c.occupationCode = :occupationCode"),
    @NamedQuery(name = "CbsCustMisinfo.findByConstitutionCode", query = "SELECT c FROM CbsCustMisinfo c WHERE c.constitutionCode = :constitutionCode"),
    @NamedQuery(name = "CbsCustMisinfo.findByCasteCode", query = "SELECT c FROM CbsCustMisinfo c WHERE c.casteCode = :casteCode"),
    @NamedQuery(name = "CbsCustMisinfo.findByCommunityCode", query = "SELECT c FROM CbsCustMisinfo c WHERE c.communityCode = :communityCode"),
    @NamedQuery(name = "CbsCustMisinfo.findByHealthCode", query = "SELECT c FROM CbsCustMisinfo c WHERE c.healthCode = :healthCode"),
    @NamedQuery(name = "CbsCustMisinfo.findByBusinessSegment", query = "SELECT c FROM CbsCustMisinfo c WHERE c.businessSegment = :businessSegment"),
    @NamedQuery(name = "CbsCustMisinfo.findBySalesTurnover", query = "SELECT c FROM CbsCustMisinfo c WHERE c.salesTurnover = :salesTurnover"),
    @NamedQuery(name = "CbsCustMisinfo.findByLastChangeUserID", query = "SELECT c FROM CbsCustMisinfo c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CbsCustMisinfo.findByLastChangeTime", query = "SELECT c FROM CbsCustMisinfo c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CbsCustMisinfo.findByRecordCreaterID", query = "SELECT c FROM CbsCustMisinfo c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CbsCustMisinfo.findByCreationTime", query = "SELECT c FROM CbsCustMisinfo c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "CbsCustMisinfo.findByTsCnt", query = "SELECT c FROM CbsCustMisinfo c WHERE c.tsCnt = :tsCnt"),
    @NamedQuery(name = "CbsCustMisinfo.findByIncorporationDate", query = "SELECT c FROM CbsCustMisinfo c WHERE c.incorporationDate = :incorporationDate"),
    @NamedQuery(name = "CbsCustMisinfo.findByIncorporationPlace", query = "SELECT c FROM CbsCustMisinfo c WHERE c.incorporationPlace = :incorporationPlace"),
    @NamedQuery(name = "CbsCustMisinfo.findByCommencementDate", query = "SELECT c FROM CbsCustMisinfo c WHERE c.commencementDate = :commencementDate"),
    @NamedQuery(name = "CbsCustMisinfo.findByMisJuriResidence", query = "SELECT c FROM CbsCustMisinfo c WHERE c.misJuriResidence = :misJuriResidence"),
    @NamedQuery(name = "CbsCustMisinfo.findByMisTin", query = "SELECT c FROM CbsCustMisinfo c WHERE c.misTin = :misTin"),
    @NamedQuery(name = "CbsCustMisinfo.findByMisCity", query = "SELECT c FROM CbsCustMisinfo c WHERE c.misCity = :misCity"),
    @NamedQuery(name = "CbsCustMisinfo.findByMisBirthCountry", query = "SELECT c FROM CbsCustMisinfo c WHERE c.misBirthCountry = :misBirthCountry")})
public class CbsCustMisinfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Size(max = 40)
    @Column(name = "Type")
    private String type;
    @Size(max = 10)
    @Column(name = "GroupID")
    private String groupID;
    @Size(max = 10)
    @Column(name = "StatusCode")
    private String statusCode;
    @Column(name = "StatusAsOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusAsOn;
    @Size(max = 5)
    @Column(name = "OccupationCode")
    private String occupationCode;
    @Size(max = 5)
    @Column(name = "ConstitutionCode")
    private String constitutionCode;
    @Size(max = 5)
    @Column(name = "CasteCode")
    private String casteCode;
    @Size(max = 5)
    @Column(name = "CommunityCode")
    private String communityCode;
    @Size(max = 5)
    @Column(name = "HealthCode")
    private String healthCode;
    @Size(max = 15)
    @Column(name = "BusinessSegment")
    private String businessSegment;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SalesTurnover")
    private Double salesTurnover;
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
    @Size(max = 10)
    @Column(name = "Incorporation_Date")
    private String incorporationDate;
    @Size(max = 6)
    @Column(name = "Incorporation_Place")
    private String incorporationPlace;
    @Size(max = 10)
    @Column(name = "Commencement_Date")
    private String commencementDate;
    @Size(max = 6)
    @Column(name = "Mis_Juri_Residence")
    private String misJuriResidence;
    @Size(max = 16)
    @Column(name = "Mis_Tin")
    private String misTin;
    @Size(max = 25)
    @Column(name = "Mis_City")
    private String misCity;
    @Size(max = 6)
    @Column(name = "Mis_Birth_Country")
    private String misBirthCountry;
    @Size(max = 6)
    @Column(name = "ResidenceCountryTaxLaw")
    private String residenceCountryTaxLaw;
    @Size(max = 6)
    @Column(name = "CountryOfIncorp")
    private String countryOfIncorp;
    @Size(max = 50)
    @Column(name = "StateOfIncorp")
    private String stateOfIncorp;

    public CbsCustMisinfo() {
    }

    public CbsCustMisinfo(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Date getStatusAsOn() {
        return statusAsOn;
    }

    public void setStatusAsOn(Date statusAsOn) {
        this.statusAsOn = statusAsOn;
    }

    public String getOccupationCode() {
        return occupationCode;
    }

    public void setOccupationCode(String occupationCode) {
        this.occupationCode = occupationCode;
    }

    public String getConstitutionCode() {
        return constitutionCode;
    }

    public void setConstitutionCode(String constitutionCode) {
        this.constitutionCode = constitutionCode;
    }

    public String getCasteCode() {
        return casteCode;
    }

    public void setCasteCode(String casteCode) {
        this.casteCode = casteCode;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getHealthCode() {
        return healthCode;
    }

    public void setHealthCode(String healthCode) {
        this.healthCode = healthCode;
    }

    public String getBusinessSegment() {
        return businessSegment;
    }

    public void setBusinessSegment(String businessSegment) {
        this.businessSegment = businessSegment;
    }

    public Double getSalesTurnover() {
        return salesTurnover;
    }

    public void setSalesTurnover(Double salesTurnover) {
        this.salesTurnover = salesTurnover;
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

    public String getIncorporationDate() {
        return incorporationDate;
    }

    public void setIncorporationDate(String incorporationDate) {
        this.incorporationDate = incorporationDate;
    }

    public String getIncorporationPlace() {
        return incorporationPlace;
    }

    public void setIncorporationPlace(String incorporationPlace) {
        this.incorporationPlace = incorporationPlace;
    }

    public String getCommencementDate() {
        return commencementDate;
    }

    public void setCommencementDate(String commencementDate) {
        this.commencementDate = commencementDate;
    }

    public String getMisJuriResidence() {
        return misJuriResidence;
    }

    public void setMisJuriResidence(String misJuriResidence) {
        this.misJuriResidence = misJuriResidence;
    }

    public String getMisTin() {
        return misTin;
    }

    public void setMisTin(String misTin) {
        this.misTin = misTin;
    }

    public String getMisCity() {
        return misCity;
    }

    public void setMisCity(String misCity) {
        this.misCity = misCity;
    }

    public String getMisBirthCountry() {
        return misBirthCountry;
    }

    public void setMisBirthCountry(String misBirthCountry) {
        this.misBirthCountry = misBirthCountry;
    }

    public String getResidenceCountryTaxLaw() {
        return residenceCountryTaxLaw;
    }

    public void setResidenceCountryTaxLaw(String residenceCountryTaxLaw) {
        this.residenceCountryTaxLaw = residenceCountryTaxLaw;
    }

    public String getCountryOfIncorp() {
        return countryOfIncorp;
    }

    public void setCountryOfIncorp(String countryOfIncorp) {
        this.countryOfIncorp = countryOfIncorp;
    }

    public String getStateOfIncorp() {
        return stateOfIncorp;
    }

    public void setStateOfIncorp(String stateOfIncorp) {
        this.stateOfIncorp = stateOfIncorp;
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
        if (!(object instanceof CbsCustMisinfo)) {
            return false;
        }
        CbsCustMisinfo other = (CbsCustMisinfo) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsCustMisinfo[ customerId=" + customerId + " ]";
    }
}
