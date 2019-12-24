/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.customer;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "cbs_related_persons_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsRelatedPersonsDetails.findAll", query = "SELECT c FROM CbsRelatedPersonsDetails c"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByCustomerId", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.cbsRelatedPersonsDetailsPK.customerId = :customerId"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonSrNo", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.cbsRelatedPersonsDetailsPK.personSrNo = :personSrNo"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonCustomerId", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personCustomerId = :personCustomerId"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonFirstName", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personFirstName = :personFirstName"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonMiddleName", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personMiddleName = :personMiddleName"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonLastName", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personLastName = :personLastName"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByRelationshipCode", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.relationshipCode = :relationshipCode"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonPAN", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personPAN = :personPAN"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonUID", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personUID = :personUID"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonVoterId", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personVoterId = :personVoterId"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonNrega", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personNrega = :personNrega"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonDL", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personDL = :personDL"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonDLExpiry", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personDLExpiry = :personDLExpiry"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonPassportNo", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personPassportNo = :personPassportNo"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonPassportExpiry", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personPassportExpiry = :personPassportExpiry"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonDIN", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personDIN = :personDIN"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonpoliticalexposed", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personpoliticalexposed = :personpoliticalexposed"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonAdd1", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personAdd1 = :personAdd1"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonAdd2", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personAdd2 = :personAdd2"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonCity", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personCity = :personCity"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonState", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personState = :personState"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonCountry", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personCountry = :personCountry"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByPersonPIN", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.personPIN = :personPIN"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByDelFlag", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.delFlag = :delFlag"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByDescriptipon", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.descriptipon = :descriptipon"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByLastChangeUserID", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByLastChangeTime", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByRecordCreaterID", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByCreationTime", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "CbsRelatedPersonsDetails.findByTsCnt", query = "SELECT c FROM CbsRelatedPersonsDetails c WHERE c.tsCnt = :tsCnt")})
public class CbsRelatedPersonsDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsRelatedPersonsDetailsPK cbsRelatedPersonsDetailsPK;
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

    public CbsRelatedPersonsDetails() {
    }

    public CbsRelatedPersonsDetails(CbsRelatedPersonsDetailsPK cbsRelatedPersonsDetailsPK) {
        this.cbsRelatedPersonsDetailsPK = cbsRelatedPersonsDetailsPK;
    }

    public CbsRelatedPersonsDetails(String customerId, int personSrNo) {
        this.cbsRelatedPersonsDetailsPK = new CbsRelatedPersonsDetailsPK(customerId, personSrNo);
    }

    public CbsRelatedPersonsDetailsPK getCbsRelatedPersonsDetailsPK() {
        return cbsRelatedPersonsDetailsPK;
    }

    public void setCbsRelatedPersonsDetailsPK(CbsRelatedPersonsDetailsPK cbsRelatedPersonsDetailsPK) {
        this.cbsRelatedPersonsDetailsPK = cbsRelatedPersonsDetailsPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsRelatedPersonsDetailsPK != null ? cbsRelatedPersonsDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsRelatedPersonsDetails)) {
            return false;
        }
        CbsRelatedPersonsDetails other = (CbsRelatedPersonsDetails) object;
        if ((this.cbsRelatedPersonsDetailsPK == null && other.cbsRelatedPersonsDetailsPK != null) || (this.cbsRelatedPersonsDetailsPK != null && !this.cbsRelatedPersonsDetailsPK.equals(other.cbsRelatedPersonsDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsRelatedPersonsDetails[ cbsRelatedPersonsDetailsPK=" + cbsRelatedPersonsDetailsPK + " ]";
    }
    
}
