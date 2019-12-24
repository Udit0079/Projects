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
 * @author ROHIT KRISHNA
 */
@Entity
@Table(name = "cbs_kyc_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsKycDetails.findAll", query = "SELECT c FROM CbsKycDetails c"),
    @NamedQuery(name = "CbsKycDetails.findByCustomerId", query = "SELECT c FROM CbsKycDetails c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CbsKycDetails.findByEduDetails", query = "SELECT c FROM CbsKycDetails c WHERE c.eduDetails = :eduDetails"),
    @NamedQuery(name = "CbsKycDetails.findByDependents", query = "SELECT c FROM CbsKycDetails c WHERE c.dependents = :dependents"),
    @NamedQuery(name = "CbsKycDetails.findByNoChild", query = "SELECT c FROM CbsKycDetails c WHERE c.noChild = :noChild"),
    @NamedQuery(name = "CbsKycDetails.findByNoMales10", query = "SELECT c FROM CbsKycDetails c WHERE c.noMales10 = :noMales10"),
    @NamedQuery(name = "CbsKycDetails.findByNoMales20", query = "SELECT c FROM CbsKycDetails c WHERE c.noMales20 = :noMales20"),
    @NamedQuery(name = "CbsKycDetails.findByNoMales45", query = "SELECT c FROM CbsKycDetails c WHERE c.noMales45 = :noMales45"),
    @NamedQuery(name = "CbsKycDetails.findByNoMales60", query = "SELECT c FROM CbsKycDetails c WHERE c.noMales60 = :noMales60"),
    @NamedQuery(name = "CbsKycDetails.findByNoMalesAbove61", query = "SELECT c FROM CbsKycDetails c WHERE c.noMalesAbove61 = :noMalesAbove61"),
    @NamedQuery(name = "CbsKycDetails.findByNoFem10", query = "SELECT c FROM CbsKycDetails c WHERE c.noFem10 = :noFem10"),
    @NamedQuery(name = "CbsKycDetails.findByNoFem20", query = "SELECT c FROM CbsKycDetails c WHERE c.noFem20 = :noFem20"),
    @NamedQuery(name = "CbsKycDetails.findByNoFem45", query = "SELECT c FROM CbsKycDetails c WHERE c.noFem45 = :noFem45"),
    @NamedQuery(name = "CbsKycDetails.findByNoFem60", query = "SELECT c FROM CbsKycDetails c WHERE c.noFem60 = :noFem60"),
    @NamedQuery(name = "CbsKycDetails.findByNoFemAbove61", query = "SELECT c FROM CbsKycDetails c WHERE c.noFemAbove61 = :noFemAbove61"),
    @NamedQuery(name = "CbsKycDetails.findByAbRelName1", query = "SELECT c FROM CbsKycDetails c WHERE c.abRelName1 = :abRelName1"),
    @NamedQuery(name = "CbsKycDetails.findByAbRelName2", query = "SELECT c FROM CbsKycDetails c WHERE c.abRelName2 = :abRelName2"),
    @NamedQuery(name = "CbsKycDetails.findByAbRelName3", query = "SELECT c FROM CbsKycDetails c WHERE c.abRelName3 = :abRelName3"),
    @NamedQuery(name = "CbsKycDetails.findByAbRelAdd1", query = "SELECT c FROM CbsKycDetails c WHERE c.abRelAdd1 = :abRelAdd1"),
    @NamedQuery(name = "CbsKycDetails.findByAbRelAdd2", query = "SELECT c FROM CbsKycDetails c WHERE c.abRelAdd2 = :abRelAdd2"),
    @NamedQuery(name = "CbsKycDetails.findByAbRelAdd3", query = "SELECT c FROM CbsKycDetails c WHERE c.abRelAdd3 = :abRelAdd3"),
    @NamedQuery(name = "CbsKycDetails.findByAbVisited", query = "SELECT c FROM CbsKycDetails c WHERE c.abVisited = :abVisited"),
    @NamedQuery(name = "CbsKycDetails.findByBankRel", query = "SELECT c FROM CbsKycDetails c WHERE c.bankRel = :bankRel"),
    @NamedQuery(name = "CbsKycDetails.findByDirRel", query = "SELECT c FROM CbsKycDetails c WHERE c.dirRel = :dirRel"),
    @NamedQuery(name = "CbsKycDetails.findByCreditcard", query = "SELECT c FROM CbsKycDetails c WHERE c.creditcard = :creditcard"),
    @NamedQuery(name = "CbsKycDetails.findByMedinsurence", query = "SELECT c FROM CbsKycDetails c WHERE c.medinsurence = :medinsurence"),
    @NamedQuery(name = "CbsKycDetails.findByLastChangeUserID", query = "SELECT c FROM CbsKycDetails c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CbsKycDetails.findByLastChangeTime", query = "SELECT c FROM CbsKycDetails c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CbsKycDetails.findByRecordCreaterID", query = "SELECT c FROM CbsKycDetails c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CbsKycDetails.findByCreationTime", query = "SELECT c FROM CbsKycDetails c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "CbsKycDetails.findByTsCnt", query = "SELECT c FROM CbsKycDetails c WHERE c.tsCnt = :tsCnt")})
public class CbsKycDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Size(max = 50)
    @Column(name = "edu_details")
    private String eduDetails;
    @Size(max = 50)
    @Column(name = "dependents")
    private String dependents;
    @Column(name = "no_child")
    private Integer noChild;
    @Column(name = "no_males_10")
    private Integer noMales10;
    @Column(name = "no_males_20")
    private Integer noMales20;
    @Column(name = "no_males_45")
    private Integer noMales45;
    @Column(name = "no_males_60")
    private Integer noMales60;
    @Column(name = "no_males_above_61")
    private Integer noMalesAbove61;
    @Column(name = "no_fem_10")
    private Integer noFem10;
    @Column(name = "no_fem_20")
    private Integer noFem20;
    @Column(name = "no_fem_45")
    private Integer noFem45;
    @Column(name = "no_fem_60")
    private Integer noFem60;
    @Column(name = "no_fem_above_61")
    private Integer noFemAbove61;
    @Size(max = 100)
    @Column(name = "ab_rel_name1")
    private String abRelName1;
    @Size(max = 100)
    @Column(name = "ab_rel_name2")
    private String abRelName2;
    @Size(max = 100)
    @Column(name = "ab_rel_name3")
    private String abRelName3;
    @Size(max = 255)
    @Column(name = "ab_rel_add1")
    private String abRelAdd1;
    @Size(max = 255)
    @Column(name = "ab_rel_add2")
    private String abRelAdd2;
    @Size(max = 255)
    @Column(name = "ab_rel_add3")
    private String abRelAdd3;
    @Column(name = "ab_visited")
    private Integer abVisited;
    @Column(name = "bank_rel")
    private Character bankRel;
    @Column(name = "dir_rel")
    private Character dirRel;
    @Column(name = "creditcard")
    private Character creditcard;
    @Column(name = "medinsurence")
    private Character medinsurence;
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

    public CbsKycDetails() {
    }

    public CbsKycDetails(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEduDetails() {
        return eduDetails;
    }

    public void setEduDetails(String eduDetails) {
        this.eduDetails = eduDetails;
    }

    public String getDependents() {
        return dependents;
    }

    public void setDependents(String dependents) {
        this.dependents = dependents;
    }

    public Integer getNoChild() {
        return noChild;
    }

    public void setNoChild(Integer noChild) {
        this.noChild = noChild;
    }

    public Integer getNoMales10() {
        return noMales10;
    }

    public void setNoMales10(Integer noMales10) {
        this.noMales10 = noMales10;
    }

    public Integer getNoMales20() {
        return noMales20;
    }

    public void setNoMales20(Integer noMales20) {
        this.noMales20 = noMales20;
    }

    public Integer getNoMales45() {
        return noMales45;
    }

    public void setNoMales45(Integer noMales45) {
        this.noMales45 = noMales45;
    }

    public Integer getNoMales60() {
        return noMales60;
    }

    public void setNoMales60(Integer noMales60) {
        this.noMales60 = noMales60;
    }

    public Integer getNoMalesAbove61() {
        return noMalesAbove61;
    }

    public void setNoMalesAbove61(Integer noMalesAbove61) {
        this.noMalesAbove61 = noMalesAbove61;
    }

    public Integer getNoFem10() {
        return noFem10;
    }

    public void setNoFem10(Integer noFem10) {
        this.noFem10 = noFem10;
    }

    public Integer getNoFem20() {
        return noFem20;
    }

    public void setNoFem20(Integer noFem20) {
        this.noFem20 = noFem20;
    }

    public Integer getNoFem45() {
        return noFem45;
    }

    public void setNoFem45(Integer noFem45) {
        this.noFem45 = noFem45;
    }

    public Integer getNoFem60() {
        return noFem60;
    }

    public void setNoFem60(Integer noFem60) {
        this.noFem60 = noFem60;
    }

    public Integer getNoFemAbove61() {
        return noFemAbove61;
    }

    public void setNoFemAbove61(Integer noFemAbove61) {
        this.noFemAbove61 = noFemAbove61;
    }

    public String getAbRelName1() {
        return abRelName1;
    }

    public void setAbRelName1(String abRelName1) {
        this.abRelName1 = abRelName1;
    }

    public String getAbRelName2() {
        return abRelName2;
    }

    public void setAbRelName2(String abRelName2) {
        this.abRelName2 = abRelName2;
    }

    public String getAbRelName3() {
        return abRelName3;
    }

    public void setAbRelName3(String abRelName3) {
        this.abRelName3 = abRelName3;
    }

    public String getAbRelAdd1() {
        return abRelAdd1;
    }

    public void setAbRelAdd1(String abRelAdd1) {
        this.abRelAdd1 = abRelAdd1;
    }

    public String getAbRelAdd2() {
        return abRelAdd2;
    }

    public void setAbRelAdd2(String abRelAdd2) {
        this.abRelAdd2 = abRelAdd2;
    }

    public String getAbRelAdd3() {
        return abRelAdd3;
    }

    public void setAbRelAdd3(String abRelAdd3) {
        this.abRelAdd3 = abRelAdd3;
    }

    public Integer getAbVisited() {
        return abVisited;
    }

    public void setAbVisited(Integer abVisited) {
        this.abVisited = abVisited;
    }

    public Character getBankRel() {
        return bankRel;
    }

    public void setBankRel(Character bankRel) {
        this.bankRel = bankRel;
    }

    public Character getDirRel() {
        return dirRel;
    }

    public void setDirRel(Character dirRel) {
        this.dirRel = dirRel;
    }

    public Character getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(Character creditcard) {
        this.creditcard = creditcard;
    }

    public Character getMedinsurence() {
        return medinsurence;
    }

    public void setMedinsurence(Character medinsurence) {
        this.medinsurence = medinsurence;
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
        if (!(object instanceof CbsKycDetails)) {
            return false;
        }
        CbsKycDetails other = (CbsKycDetails) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsKycDetails[ customerId=" + customerId + " ]";
    }
    
}
