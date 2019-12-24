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
@Table(name = "cbs_cust_additional_address_details_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findAll", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByCustomerId", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findBySno", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.sno = :sno"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByAddressType", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.addressType = :addressType"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByLocalAddressLine1", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.localAddressLine1 = :localAddressLine1"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByLocalAddressLine2", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.localAddressLine2 = :localAddressLine2"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByLocalAddressLine3", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.localAddressLine3 = :localAddressLine3"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByLocalAddressCityVillage", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.localAddressCityVillage = :localAddressCityVillage"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByLocalAddressDistrict", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.localAddressDistrict = :localAddressDistrict"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByLocalAddressPINCode", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.localAddressPINCode = :localAddressPINCode"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByLocalAddressState", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.localAddressState = :localAddressState"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByLocalAddressCountry", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.localAddressCountry = :localAddressCountry"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByProofofAdd", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.proofofAdd = :proofofAdd"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByResiTelSTDCode", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.resiTelSTDCode = :resiTelSTDCode"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByResiTelNo", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.resiTelNo = :resiTelNo"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByOfficeTelSTDCode", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.officeTelSTDCode = :officeTelSTDCode"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByOfficeTelNo", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.officeTelNo = :officeTelNo"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByMobISDCode", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.mobISDCode = :mobISDCode"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByMobileNo", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.mobileNo = :mobileNo"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByFaxSTDCode", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.faxSTDCode = :faxSTDCode"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByFaxNo", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.faxNo = :faxNo"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByEmailID", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.emailID = :emailID"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByDateofDeclaration", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.dateofDeclaration = :dateofDeclaration"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByPlaceofDeclaration", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.placeofDeclaration = :placeofDeclaration"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByEnterDate", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.enterDate = :enterDate"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByEnterTime", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.enterTime = :enterTime"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByEnterBy", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.enterBy = :enterBy"),
    @NamedQuery(name = "CbsCustAdditionalAddressDetailsHis.findByTxnid", query = "SELECT c FROM CbsCustAdditionalAddressDetailsHis c WHERE c.txnid = :txnid")})
public class CbsCustAdditionalAddressDetailsHis extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Size(max = 2)
    @Column(name = "Sno")
    private String sno;
    @Size(max = 2)
    @Column(name = "AddressType")
    private String addressType;
    @Size(max = 55)
    @Column(name = "LocalAddressLine1")
    private String localAddressLine1;
    @Size(max = 55)
    @Column(name = "LocalAddressLine2")
    private String localAddressLine2;
    @Size(max = 55)
    @Column(name = "LocalAddressLine3")
    private String localAddressLine3;
    @Size(max = 50)
    @Column(name = "LocalAddressCityVillage")
    private String localAddressCityVillage;
    @Size(max = 50)
    @Column(name = "LocalAddressDistrict")
    private String localAddressDistrict;
    @Size(max = 10)
    @Column(name = "LocalAddressPINCode")
    private String localAddressPINCode;
    @Size(max = 2)
    @Column(name = "LocalAddressState")
    private String localAddressState;
    @Size(max = 3)
    @Column(name = "LocalAddressCountry")
    private String localAddressCountry;
    @Size(max = 3)
    @Column(name = "ProofofAdd")
    private String proofofAdd;
    @Size(max = 4)
    @Column(name = "ResiTelSTDCode")
    private String resiTelSTDCode;
    @Size(max = 10)
    @Column(name = "ResiTelNo")
    private String resiTelNo;
    @Size(max = 4)
    @Column(name = "OfficeTelSTDCode")
    private String officeTelSTDCode;
    @Size(max = 10)
    @Column(name = "OfficeTelNo")
    private String officeTelNo;
    @Size(max = 3)
    @Column(name = "MobISDCode")
    private String mobISDCode;
    @Size(max = 10)
    @Column(name = "MobileNo")
    private String mobileNo;
    @Size(max = 4)
    @Column(name = "FaxSTDCode")
    private String faxSTDCode;
    @Size(max = 10)
    @Column(name = "FaxNo")
    private String faxNo;
    @Size(max = 100)
    @Column(name = "EmailID")
    private String emailID;
    @Size(max = 8)
    @Column(name = "DateofDeclaration")
    private String dateofDeclaration;
    @Size(max = 50)
    @Column(name = "PlaceofDeclaration")
    private String placeofDeclaration;
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

    public CbsCustAdditionalAddressDetailsHis() {
    }

    public CbsCustAdditionalAddressDetailsHis(Long txnid) {
        this.txnid = txnid;
    }

    public CbsCustAdditionalAddressDetailsHis(Long txnid, String customerId) {
        this.txnid = txnid;
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getLocalAddressLine1() {
        return localAddressLine1;
    }

    public void setLocalAddressLine1(String localAddressLine1) {
        this.localAddressLine1 = localAddressLine1;
    }

    public String getLocalAddressLine2() {
        return localAddressLine2;
    }

    public void setLocalAddressLine2(String localAddressLine2) {
        this.localAddressLine2 = localAddressLine2;
    }

    public String getLocalAddressLine3() {
        return localAddressLine3;
    }

    public void setLocalAddressLine3(String localAddressLine3) {
        this.localAddressLine3 = localAddressLine3;
    }

    public String getLocalAddressCityVillage() {
        return localAddressCityVillage;
    }

    public void setLocalAddressCityVillage(String localAddressCityVillage) {
        this.localAddressCityVillage = localAddressCityVillage;
    }

    public String getLocalAddressDistrict() {
        return localAddressDistrict;
    }

    public void setLocalAddressDistrict(String localAddressDistrict) {
        this.localAddressDistrict = localAddressDistrict;
    }

    public String getLocalAddressPINCode() {
        return localAddressPINCode;
    }

    public void setLocalAddressPINCode(String localAddressPINCode) {
        this.localAddressPINCode = localAddressPINCode;
    }

    public String getLocalAddressState() {
        return localAddressState;
    }

    public void setLocalAddressState(String localAddressState) {
        this.localAddressState = localAddressState;
    }

    public String getLocalAddressCountry() {
        return localAddressCountry;
    }

    public void setLocalAddressCountry(String localAddressCountry) {
        this.localAddressCountry = localAddressCountry;
    }

    public String getProofofAdd() {
        return proofofAdd;
    }

    public void setProofofAdd(String proofofAdd) {
        this.proofofAdd = proofofAdd;
    }

    public String getResiTelSTDCode() {
        return resiTelSTDCode;
    }

    public void setResiTelSTDCode(String resiTelSTDCode) {
        this.resiTelSTDCode = resiTelSTDCode;
    }

    public String getResiTelNo() {
        return resiTelNo;
    }

    public void setResiTelNo(String resiTelNo) {
        this.resiTelNo = resiTelNo;
    }

    public String getOfficeTelSTDCode() {
        return officeTelSTDCode;
    }

    public void setOfficeTelSTDCode(String officeTelSTDCode) {
        this.officeTelSTDCode = officeTelSTDCode;
    }

    public String getOfficeTelNo() {
        return officeTelNo;
    }

    public void setOfficeTelNo(String officeTelNo) {
        this.officeTelNo = officeTelNo;
    }

    public String getMobISDCode() {
        return mobISDCode;
    }

    public void setMobISDCode(String mobISDCode) {
        this.mobISDCode = mobISDCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFaxSTDCode() {
        return faxSTDCode;
    }

    public void setFaxSTDCode(String faxSTDCode) {
        this.faxSTDCode = faxSTDCode;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getDateofDeclaration() {
        return dateofDeclaration;
    }

    public void setDateofDeclaration(String dateofDeclaration) {
        this.dateofDeclaration = dateofDeclaration;
    }

    public String getPlaceofDeclaration() {
        return placeofDeclaration;
    }

    public void setPlaceofDeclaration(String placeofDeclaration) {
        this.placeofDeclaration = placeofDeclaration;
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
        if (!(object instanceof CbsCustAdditionalAddressDetailsHis)) {
            return false;
        }
        CbsCustAdditionalAddressDetailsHis other = (CbsCustAdditionalAddressDetailsHis) object;
        if ((this.txnid == null && other.txnid != null) || (this.txnid != null && !this.txnid.equals(other.txnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsCustAdditionalAddressDetailsHis[ txnid=" + txnid + " ]";
    }
}
