/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrPersonnelDetails;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@Table(name = "hr_personnel_reference")
@NamedQueries({@NamedQuery(name = "HrPersonnelReference.findAll", query = "SELECT h FROM HrPersonnelReference h"), @NamedQuery(name = "HrPersonnelReference.findByCompCode", query = "SELECT h FROM HrPersonnelReference h WHERE h.hrPersonnelReferencePK.compCode = :compCode"), @NamedQuery(name = "HrPersonnelReference.findByEmpCode", query = "SELECT h FROM HrPersonnelReference h WHERE h.hrPersonnelReferencePK.empCode = :empCode"), @NamedQuery(name = "HrPersonnelReference.findByRefCode", query = "SELECT h FROM HrPersonnelReference h WHERE h.hrPersonnelReferencePK.refCode = :refCode"), @NamedQuery(name = "HrPersonnelReference.findByRefName", query = "SELECT h FROM HrPersonnelReference h WHERE h.refName = :refName"), @NamedQuery(name = "HrPersonnelReference.findByRefAdd", query = "SELECT h FROM HrPersonnelReference h WHERE h.refAdd = :refAdd"), @NamedQuery(name = "HrPersonnelReference.findByRefPin", query = "SELECT h FROM HrPersonnelReference h WHERE h.refPin = :refPin"), @NamedQuery(name = "HrPersonnelReference.findByRefCity", query = "SELECT h FROM HrPersonnelReference h WHERE h.refCity = :refCity"), @NamedQuery(name = "HrPersonnelReference.findByRefState", query = "SELECT h FROM HrPersonnelReference h WHERE h.refState = :refState"), @NamedQuery(name = "HrPersonnelReference.findByRefPhone", query = "SELECT h FROM HrPersonnelReference h WHERE h.refPhone = :refPhone"), @NamedQuery(name = "HrPersonnelReference.findByRefOcc", query = "SELECT h FROM HrPersonnelReference h WHERE h.refOcc = :refOcc"), @NamedQuery(name = "HrPersonnelReference.findByDefaultComp", query = "SELECT h FROM HrPersonnelReference h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrPersonnelReference.findByStatFlag", query = "SELECT h FROM HrPersonnelReference h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrPersonnelReference.findByStatUpFlag", query = "SELECT h FROM HrPersonnelReference h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrPersonnelReference.findByModDate", query = "SELECT h FROM HrPersonnelReference h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrPersonnelReference.findByAuthBy", query = "SELECT h FROM HrPersonnelReference h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrPersonnelReference.findByEnteredBy", query = "SELECT h FROM HrPersonnelReference h WHERE h.enteredBy = :enteredBy")})
public class HrPersonnelReference extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrPersonnelReferencePK hrPersonnelReferencePK;
    @Basic(optional = false)
    @Column(name = "REF_NAME")
    private String refName;
    @Column(name = "REF_ADD")
    private String refAdd;
    @Column(name = "REF_PIN")
    private String refPin;
    @Column(name = "REF_CITY")
    private String refCity;
    @Column(name = "REF_STATE")
    private String refState;
    @Column(name = "REF_PHONE")
    private String refPhone;
    @Column(name = "REF_OCC")
    private String refOcc;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Column(name = "AUTH_BY")
    private String authBy;
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrPersonnelReference() {
    }

    public HrPersonnelReference(HrPersonnelReferencePK hrPersonnelReferencePK) {
        this.hrPersonnelReferencePK = hrPersonnelReferencePK;
    }

    public HrPersonnelReference(HrPersonnelReferencePK hrPersonnelReferencePK, String refName) {
        this.hrPersonnelReferencePK = hrPersonnelReferencePK;
        this.refName = refName;
    }

    public HrPersonnelReference(int compCode, long empCode, long refCode) {
        this.hrPersonnelReferencePK = new HrPersonnelReferencePK(compCode, empCode, refCode);
    }

    public HrPersonnelReferencePK getHrPersonnelReferencePK() {
        return hrPersonnelReferencePK;
    }

    public void setHrPersonnelReferencePK(HrPersonnelReferencePK hrPersonnelReferencePK) {
        this.hrPersonnelReferencePK = hrPersonnelReferencePK;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefAdd() {
        return refAdd;
    }

    public void setRefAdd(String refAdd) {
        this.refAdd = refAdd;
    }

    public String getRefPin() {
        return refPin;
    }

    public void setRefPin(String refPin) {
        this.refPin = refPin;
    }

    public String getRefCity() {
        return refCity;
    }

    public void setRefCity(String refCity) {
        this.refCity = refCity;
    }

    public String getRefState() {
        return refState;
    }

    public void setRefState(String refState) {
        this.refState = refState;
    }

    public String getRefPhone() {
        return refPhone;
    }

    public void setRefPhone(String refPhone) {
        this.refPhone = refPhone;
    }

    public String getRefOcc() {
        return refOcc;
    }

    public void setRefOcc(String refOcc) {
        this.refOcc = refOcc;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public HrPersonnelDetails getHrPersonnelDetails() {
        return hrPersonnelDetails;
    }

    public void setHrPersonnelDetails(HrPersonnelDetails hrPersonnelDetails) {
        this.hrPersonnelDetails = hrPersonnelDetails;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrPersonnelReferencePK != null ? hrPersonnelReferencePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelReference)) {
            return false;
        }
        HrPersonnelReference other = (HrPersonnelReference) object;
        if ((this.hrPersonnelReferencePK == null && other.hrPersonnelReferencePK != null) || (this.hrPersonnelReferencePK != null && !this.hrPersonnelReferencePK.equals(other.hrPersonnelReferencePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPersonnelReference[hrPersonnelReferencePK=" + hrPersonnelReferencePK + "]";
    }

}
