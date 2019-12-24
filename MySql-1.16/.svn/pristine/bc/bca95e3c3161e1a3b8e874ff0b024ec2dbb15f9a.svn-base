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

@Entity
@Table(name = "cbs_cust_misinfo_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsCustMisinfoHis.findAll", query = "SELECT c FROM CbsCustMisinfoHis c"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByCustomerId", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByType", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.type = :type"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByGroupID", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.groupID = :groupID"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByStatusCode", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.statusCode = :statusCode"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByStatusAsOn", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.statusAsOn = :statusAsOn"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByOccupationCode", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.occupationCode = :occupationCode"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByConstitutionCode", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.constitutionCode = :constitutionCode"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByCasteCode", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.casteCode = :casteCode"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByCommunityCode", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.communityCode = :communityCode"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByHealthCode", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.healthCode = :healthCode"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByBusinessSegment", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.businessSegment = :businessSegment"),
    @NamedQuery(name = "CbsCustMisinfoHis.findBySalesTurnover", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.salesTurnover = :salesTurnover"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByLastChangeUserID", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByLastChangeTime", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByRecordCreaterID", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByTxnid", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.txnid = :txnid"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByIncorporationDate", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.incorporationDate = :incorporationDate"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByIncorporationPlace", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.incorporationPlace = :incorporationPlace"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByCommencementDate", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.commencementDate = :commencementDate"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByMisJuriResidence", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.misJuriResidence = :misJuriResidence"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByMisTin", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.misTin = :misTin"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByMisCity", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.misCity = :misCity"),
    @NamedQuery(name = "CbsCustMisinfoHis.findByMisBirthCountry", query = "SELECT c FROM CbsCustMisinfoHis c WHERE c.misBirthCountry = :misBirthCountry")})
public class CbsCustMisinfoHis extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TXNID")
    private Long txnid;
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

    public CbsCustMisinfoHis() {
    }

    public CbsCustMisinfoHis(Long txnid) {
        this.txnid = txnid;
    }

    public CbsCustMisinfoHis(Long txnid, String customerId) {
        this.txnid = txnid;
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

    public Long getTxnid() {
        return txnid;
    }

    public void setTxnid(Long txnid) {
        this.txnid = txnid;
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
        hash += (txnid != null ? txnid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsCustMisinfoHis)) {
            return false;
        }
        CbsCustMisinfoHis other = (CbsCustMisinfoHis) object;
        if ((this.txnid == null && other.txnid != null) || (this.txnid != null && !this.txnid.equals(other.txnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsCustMisinfoHis[ txnid=" + txnid + " ]";
    }
}
