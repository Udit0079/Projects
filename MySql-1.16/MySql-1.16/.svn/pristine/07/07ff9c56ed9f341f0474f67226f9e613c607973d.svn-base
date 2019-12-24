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
 * @author root
 */
@Entity
@Table(name = "cbs_cust_nreinfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsCustNreinfo.findAll", query = "SELECT c FROM CbsCustNreinfo c"),
    @NamedQuery(name = "CbsCustNreinfo.findByCustomerId", query = "SELECT c FROM CbsCustNreinfo c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CbsCustNreinfo.findByNationality", query = "SELECT c FROM CbsCustNreinfo c WHERE c.nationality = :nationality"),
    @NamedQuery(name = "CbsCustNreinfo.findByNonResidentDate", query = "SELECT c FROM CbsCustNreinfo c WHERE c.nonResidentDate = :nonResidentDate"),
    @NamedQuery(name = "CbsCustNreinfo.findByCountryCode", query = "SELECT c FROM CbsCustNreinfo c WHERE c.countryCode = :countryCode"),
    @NamedQuery(name = "CbsCustNreinfo.findByCountryType", query = "SELECT c FROM CbsCustNreinfo c WHERE c.countryType = :countryType"),
    @NamedQuery(name = "CbsCustNreinfo.findByLocalContPersonCode", query = "SELECT c FROM CbsCustNreinfo c WHERE c.localContPersonCode = :localContPersonCode"),
    @NamedQuery(name = "CbsCustNreinfo.findByLocalPersonTitle", query = "SELECT c FROM CbsCustNreinfo c WHERE c.localPersonTitle = :localPersonTitle"),
    @NamedQuery(name = "CbsCustNreinfo.findByLocalContPersonName", query = "SELECT c FROM CbsCustNreinfo c WHERE c.localContPersonName = :localContPersonName"),
    @NamedQuery(name = "CbsCustNreinfo.findByLocalAddress1", query = "SELECT c FROM CbsCustNreinfo c WHERE c.localAddress1 = :localAddress1"),
    @NamedQuery(name = "CbsCustNreinfo.findByLocalAddress2", query = "SELECT c FROM CbsCustNreinfo c WHERE c.localAddress2 = :localAddress2"),
    @NamedQuery(name = "CbsCustNreinfo.findByVillage", query = "SELECT c FROM CbsCustNreinfo c WHERE c.village = :village"),
    @NamedQuery(name = "CbsCustNreinfo.findByBlock", query = "SELECT c FROM CbsCustNreinfo c WHERE c.block = :block"),
    @NamedQuery(name = "CbsCustNreinfo.findByCityCode", query = "SELECT c FROM CbsCustNreinfo c WHERE c.cityCode = :cityCode"),
    @NamedQuery(name = "CbsCustNreinfo.findByStateCode", query = "SELECT c FROM CbsCustNreinfo c WHERE c.stateCode = :stateCode"),
    @NamedQuery(name = "CbsCustNreinfo.findByPostalCode", query = "SELECT c FROM CbsCustNreinfo c WHERE c.postalCode = :postalCode"),
    @NamedQuery(name = "CbsCustNreinfo.findByPhoneNumber", query = "SELECT c FROM CbsCustNreinfo c WHERE c.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "CbsCustNreinfo.findByMobileNumber", query = "SELECT c FROM CbsCustNreinfo c WHERE c.mobileNumber = :mobileNumber"),
    @NamedQuery(name = "CbsCustNreinfo.findByNreEmail", query = "SELECT c FROM CbsCustNreinfo c WHERE c.nreEmail = :nreEmail"),
    @NamedQuery(name = "CbsCustNreinfo.findByNonResidentEndDate", query = "SELECT c FROM CbsCustNreinfo c WHERE c.nonResidentEndDate = :nonResidentEndDate"),
    @NamedQuery(name = "CbsCustNreinfo.findByLastChangeUserID", query = "SELECT c FROM CbsCustNreinfo c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CbsCustNreinfo.findByLastChangeTime", query = "SELECT c FROM CbsCustNreinfo c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CbsCustNreinfo.findByRecordCreaterID", query = "SELECT c FROM CbsCustNreinfo c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CbsCustNreinfo.findByCreationTime", query = "SELECT c FROM CbsCustNreinfo c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "CbsCustNreinfo.findByTsCnt", query = "SELECT c FROM CbsCustNreinfo c WHERE c.tsCnt = :tsCnt")})
public class CbsCustNreinfo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Size(max = 3)
    @Column(name = "Nationality")
    private String nationality;
    @Column(name = "NonResidentDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nonResidentDate;
    @Size(max = 3)
    @Column(name = "CountryCode")
    private String countryCode;
    @Size(max = 3)
    @Column(name = "CountryType")
    private String countryType;
    @Size(max = 10)
    @Column(name = "LocalContPersonCode")
    private String localContPersonCode;
    @Size(max = 10)
    @Column(name = "LocalPersonTitle")
    private String localPersonTitle;
    @Size(max = 90)
    @Column(name = "LocalContPersonName")
    private String localContPersonName;
    @Size(max = 50)
    @Column(name = "LocalAddress1")
    private String localAddress1;
    @Size(max = 50)
    @Column(name = "LocalAddress2")
    private String localAddress2;
    @Size(max = 45)
    @Column(name = "Village")
    private String village;
    @Size(max = 45)
    @Column(name = "Block")
    private String block;
    @Size(max = 50)
    @Column(name = "CityCode")
    private String cityCode;
    @Size(max = 50)
    @Column(name = "StateCode")
    private String stateCode;
    @Size(max = 6)
    @Column(name = "PostalCode")
    private String postalCode;
    @Size(max = 15)
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    @Size(max = 15)
    @Column(name = "MobileNumber")
    private String mobileNumber;
    @Size(max = 40)
    @Column(name = "NreEmail")
    private String nreEmail;
    @Column(name = "NonResidentEndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nonResidentEndDate;
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

    public CbsCustNreinfo() {
    }

    public CbsCustNreinfo(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getNonResidentDate() {
        return nonResidentDate;
    }

    public void setNonResidentDate(Date nonResidentDate) {
        this.nonResidentDate = nonResidentDate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryType() {
        return countryType;
    }

    public void setCountryType(String countryType) {
        this.countryType = countryType;
    }

    public String getLocalContPersonCode() {
        return localContPersonCode;
    }

    public void setLocalContPersonCode(String localContPersonCode) {
        this.localContPersonCode = localContPersonCode;
    }

    public String getLocalPersonTitle() {
        return localPersonTitle;
    }

    public void setLocalPersonTitle(String localPersonTitle) {
        this.localPersonTitle = localPersonTitle;
    }

    public String getLocalContPersonName() {
        return localContPersonName;
    }

    public void setLocalContPersonName(String localContPersonName) {
        this.localContPersonName = localContPersonName;
    }

    public String getLocalAddress1() {
        return localAddress1;
    }

    public void setLocalAddress1(String localAddress1) {
        this.localAddress1 = localAddress1;
    }

    public String getLocalAddress2() {
        return localAddress2;
    }

    public void setLocalAddress2(String localAddress2) {
        this.localAddress2 = localAddress2;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNreEmail() {
        return nreEmail;
    }

    public void setNreEmail(String nreEmail) {
        this.nreEmail = nreEmail;
    }

    public Date getNonResidentEndDate() {
        return nonResidentEndDate;
    }

    public void setNonResidentEndDate(Date nonResidentEndDate) {
        this.nonResidentEndDate = nonResidentEndDate;
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
        if (!(object instanceof CbsCustNreinfo)) {
            return false;
        }
        CbsCustNreinfo other = (CbsCustNreinfo) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsCustNreinfo[ customerId=" + customerId + " ]";
    }
    
}
