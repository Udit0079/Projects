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
@Table(name = "cbs_related_persons_details_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findAll", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByCustomerId", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonSrNo", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personSrNo = :personSrNo"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonCustomerId", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personCustomerId = :personCustomerId"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonFirstName", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personFirstName = :personFirstName"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonMiddleName", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personMiddleName = :personMiddleName"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonLastName", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personLastName = :personLastName"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByRelationshipCode", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.relationshipCode = :relationshipCode"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonPAN", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personPAN = :personPAN"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonUID", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personUID = :personUID"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonVoterId", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personVoterId = :personVoterId"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonNrega", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personNrega = :personNrega"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonDL", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personDL = :personDL"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonDLExpiry", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personDLExpiry = :personDLExpiry"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonPassportNo", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personPassportNo = :personPassportNo"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonPassportExpiry", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personPassportExpiry = :personPassportExpiry"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonDIN", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personDIN = :personDIN"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonpoliticalexposed", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personpoliticalexposed = :personpoliticalexposed"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonAdd1", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personAdd1 = :personAdd1"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonAdd2", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personAdd2 = :personAdd2"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonCity", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personCity = :personCity"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonState", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personState = :personState"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonCountry", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personCountry = :personCountry"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByPersonPIN", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.personPIN = :personPIN"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByDelFlag", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.delFlag = :delFlag"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByDescriptipon", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.descriptipon = :descriptipon"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByLastChangeUserID", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByLastChangeTime", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByRecordCreaterID", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByCreationTime", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByTsCnt", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.tsCnt = :tsCnt"),
    @NamedQuery(name = "CbsRelatedPersonsDetailsHis.findByTxnId", query = "SELECT c FROM CbsRelatedPersonsDetailsHis c WHERE c.txnId = :txnId")})
public class CbsRelatedPersonsDetailsHis extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Person_SrNo")
    private int personSrNo;
    @Size(max = 10)
    @Column(name = "Person_CustomerId")
    private String personCustomerId;
    @Size(max = 70)
    @Column(name = "Person_First_Name")
    private String personFirstName;
    @Size(max = 70)
    @Column(name = "Person_Middle_Name")
    private String personMiddleName;
    @Size(max = 70)
    @Column(name = "Person_Last_Name")
    private String personLastName;
    @Size(max = 6)
    @Column(name = "Relationship_Code")
    private String relationshipCode;
    @Size(max = 10)
    @Column(name = "Person_PAN")
    private String personPAN;
    @Size(max = 16)
    @Column(name = "Person_UID")
    private String personUID;
    @Size(max = 10)
    @Column(name = "Person_Voter_Id")
    private String personVoterId;
    @Size(max = 25)
    @Column(name = "Person_Nrega")
    private String personNrega;
    @Size(max = 10)
    @Column(name = "Person_DL")
    private String personDL;
    @Size(max = 10)
    @Column(name = "Person_DL_Expiry")
    private String personDLExpiry;
    @Size(max = 32)
    @Column(name = "Person_Passport_No")
    private String personPassportNo;
    @Size(max = 10)
    @Column(name = "Person_Passport_Expiry")
    private String personPassportExpiry;
    @Size(max = 8)
    @Column(name = "Person_DIN")
    private String personDIN;
    @Size(max = 6)
    @Column(name = "Person_political_exposed")
    private String personpoliticalexposed;
    @Size(max = 250)
    @Column(name = "Person_Add1")
    private String personAdd1;
    @Size(max = 250)
    @Column(name = "Person_Add2")
    private String personAdd2;
    @Size(max = 6)
    @Column(name = "Person_City")
    private String personCity;
    @Size(max = 6)
    @Column(name = "Person_State")
    private String personState;
    @Size(max = 6)
    @Column(name = "Person_Country")
    private String personCountry;
    @Size(max = 6)
    @Column(name = "Person_PIN")
    private String personPIN;
    @Size(max = 1)
    @Column(name = "Del_Flag")
    private String delFlag;
    @Size(max = 100)
    @Column(name = "Descriptipon")
    private String descriptipon;
    @Size(max = 15)
    @Column(name = "Last_Change_UserID")
    private String lastChangeUserID;
    @Column(name = "Last_Change_Time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChangeTime;
    @Size(max = 15)
    @Column(name = "Record_CreaterID")
    private String recordCreaterID;
    @Column(name = "Creation_Time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    @Size(max = 5)
    @Column(name = "TsCnt")
    private String tsCnt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Txn_Id")
    private Long txnId;

    public CbsRelatedPersonsDetailsHis() {
    }

    public CbsRelatedPersonsDetailsHis(Long txnId) {
        this.txnId = txnId;
    }

    public CbsRelatedPersonsDetailsHis(Long txnId, String customerId, int personSrNo) {
        this.txnId = txnId;
        this.customerId = customerId;
        this.personSrNo = personSrNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getPersonSrNo() {
        return personSrNo;
    }

    public void setPersonSrNo(int personSrNo) {
        this.personSrNo = personSrNo;
    }

    public String getPersonCustomerId() {
        return personCustomerId;
    }

    public void setPersonCustomerId(String personCustomerId) {
        this.personCustomerId = personCustomerId;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getPersonMiddleName() {
        return personMiddleName;
    }

    public void setPersonMiddleName(String personMiddleName) {
        this.personMiddleName = personMiddleName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getRelationshipCode() {
        return relationshipCode;
    }

    public void setRelationshipCode(String relationshipCode) {
        this.relationshipCode = relationshipCode;
    }

    public String getPersonPAN() {
        return personPAN;
    }

    public void setPersonPAN(String personPAN) {
        this.personPAN = personPAN;
    }

    public String getPersonUID() {
        return personUID;
    }

    public void setPersonUID(String personUID) {
        this.personUID = personUID;
    }

    public String getPersonVoterId() {
        return personVoterId;
    }

    public void setPersonVoterId(String personVoterId) {
        this.personVoterId = personVoterId;
    }

    public String getPersonNrega() {
        return personNrega;
    }

    public void setPersonNrega(String personNrega) {
        this.personNrega = personNrega;
    }

    public String getPersonDL() {
        return personDL;
    }

    public void setPersonDL(String personDL) {
        this.personDL = personDL;
    }

    public String getPersonDLExpiry() {
        return personDLExpiry;
    }

    public void setPersonDLExpiry(String personDLExpiry) {
        this.personDLExpiry = personDLExpiry;
    }

    public String getPersonPassportNo() {
        return personPassportNo;
    }

    public void setPersonPassportNo(String personPassportNo) {
        this.personPassportNo = personPassportNo;
    }

    public String getPersonPassportExpiry() {
        return personPassportExpiry;
    }

    public void setPersonPassportExpiry(String personPassportExpiry) {
        this.personPassportExpiry = personPassportExpiry;
    }

    public String getPersonDIN() {
        return personDIN;
    }

    public void setPersonDIN(String personDIN) {
        this.personDIN = personDIN;
    }

    public String getPersonpoliticalexposed() {
        return personpoliticalexposed;
    }

    public void setPersonpoliticalexposed(String personpoliticalexposed) {
        this.personpoliticalexposed = personpoliticalexposed;
    }

    public String getPersonAdd1() {
        return personAdd1;
    }

    public void setPersonAdd1(String personAdd1) {
        this.personAdd1 = personAdd1;
    }

    public String getPersonAdd2() {
        return personAdd2;
    }

    public void setPersonAdd2(String personAdd2) {
        this.personAdd2 = personAdd2;
    }

    public String getPersonCity() {
        return personCity;
    }

    public void setPersonCity(String personCity) {
        this.personCity = personCity;
    }

    public String getPersonState() {
        return personState;
    }

    public void setPersonState(String personState) {
        this.personState = personState;
    }

    public String getPersonCountry() {
        return personCountry;
    }

    public void setPersonCountry(String personCountry) {
        this.personCountry = personCountry;
    }

    public String getPersonPIN() {
        return personPIN;
    }

    public void setPersonPIN(String personPIN) {
        this.personPIN = personPIN;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDescriptipon() {
        return descriptipon;
    }

    public void setDescriptipon(String descriptipon) {
        this.descriptipon = descriptipon;
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

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
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
        if (!(object instanceof CbsRelatedPersonsDetailsHis)) {
            return false;
        }
        CbsRelatedPersonsDetailsHis other = (CbsRelatedPersonsDetailsHis) object;
        if ((this.txnId == null && other.txnId != null) || (this.txnId != null && !this.txnId.equals(other.txnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsRelatedPersonsDetailsHis[ txnId=" + txnId + " ]";
    }
}
