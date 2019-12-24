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
 * @author ROHIT KRISHNA
 */
@Entity
@Table(name = "cbs_cust_minorinfo_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CBSCustMinorInfoHis.findAll", query = "SELECT c FROM CBSCustMinorInfoHis c"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByCustomerId", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByGuardianCode", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.guardianCode = :guardianCode"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByGuardianTitle", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.guardianTitle = :guardianTitle"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByGuardianName", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.guardianName = :guardianName"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByLocalAddress1", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.localAddress1 = :localAddress1"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByLocalAddress2", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.localAddress2 = :localAddress2"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByVillage", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.village = :village"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByBlock", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.block = :block"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByCityCode", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.cityCode = :cityCode"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByStateCode", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.stateCode = :stateCode"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByPostalCode", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.postalCode = :postalCode"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByCountryCode", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.countryCode = :countryCode"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByPhoneNumber", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByMobileNumber", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.mobileNumber = :mobileNumber"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByDateOfBirth", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByMajorityDate", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.majorityDate = :majorityDate"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByEmailID", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.emailID = :emailID"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByLastChangeUserID", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByLastChangeTime", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByRecordCreaterID", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CBSCustMinorInfoHis.findByTxnid", query = "SELECT c FROM CBSCustMinorInfoHis c WHERE c.txnid = :txnid")})
public class CBSCustMinorInfoHis extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Size(max = 5)
    @Column(name = "GuardianCode")
    private String guardianCode;
    @Size(max = 50)
    @Column(name = "GuardianTitle")
    private String guardianTitle;
    @Size(max = 90)
    @Column(name = "GuardianName")
    private String guardianName;
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
    @Size(max = 5)
    @Column(name = "CityCode")
    private String cityCode;
    @Size(max = 5)
    @Column(name = "StateCode")
    private String stateCode;
    @Size(max = 6)
    @Column(name = "PostalCode")
    private String postalCode;
    @Size(max = 3)
    @Column(name = "CountryCode")
    private String countryCode;
    @Size(max = 15)
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    @Size(max = 15)
    @Column(name = "MobileNumber")
    private String mobileNumber;
    @Column(name = "DateOfBirth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    @Column(name = "MajorityDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date majorityDate;
    @Size(max = 40)
    @Column(name = "EmailID")
    private String emailID;
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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "TXNID")
    private Long txnid;

    public CBSCustMinorInfoHis() {
    }

    public CBSCustMinorInfoHis(Long txnid) {
        this.txnid = txnid;
    }

    public CBSCustMinorInfoHis(Long txnid, String customerId) {
        this.txnid = txnid;
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getGuardianCode() {
        return guardianCode;
    }

    public void setGuardianCode(String guardianCode) {
        this.guardianCode = guardianCode;
    }

    public String getGuardianTitle() {
        return guardianTitle;
    }

    public void setGuardianTitle(String guardianTitle) {
        this.guardianTitle = guardianTitle;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getMajorityDate() {
        return majorityDate;
    }

    public void setMajorityDate(Date majorityDate) {
        this.majorityDate = majorityDate;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnid != null ? txnid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBSCustMinorInfoHis)) {
            return false;
        }
        CBSCustMinorInfoHis other = (CBSCustMinorInfoHis) object;
        if ((this.txnid == null && other.txnid != null) || (this.txnid != null && !this.txnid.equals(other.txnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CBSCustMinorInfoHis[ txnid=" + txnid + " ]";
    }
    
}
