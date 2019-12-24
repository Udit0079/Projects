/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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
@Table(name = "hr_data_reference")
@NamedQueries({@NamedQuery(name = "HrDataReference.findAll", query = "SELECT h FROM HrDataReference h"),
@NamedQuery(name = "HrDataReference.findByCompCode", query = "SELECT h FROM HrDataReference h WHERE h.hrDataReferencePK.compCode = :compCode"),
@NamedQuery(name = "HrDataReference.findByAdvtCode", query = "SELECT h FROM HrDataReference h WHERE h.hrDataReferencePK.advtCode = :advtCode"),
@NamedQuery(name = "HrDataReference.findByJobCode", query = "SELECT h FROM HrDataReference h WHERE h.hrDataReferencePK.jobCode = :jobCode"),
@NamedQuery(name = "HrDataReference.findByCandSrno", query = "SELECT h FROM HrDataReference h WHERE h.hrDataReferencePK.candSrno = :candSrno"),
@NamedQuery(name = "HrDataReference.findByReferName", query = "SELECT h FROM HrDataReference h WHERE h.hrDataReferencePK.referName = :referName"),
@NamedQuery(name = "HrDataReference.findByReferAdd", query = "SELECT h FROM HrDataReference h WHERE h.referAdd = :referAdd"),
@NamedQuery(name = "HrDataReference.findByReferPin", query = "SELECT h FROM HrDataReference h WHERE h.referPin = :referPin"),
@NamedQuery(name = "HrDataReference.findByReferState", query = "SELECT h FROM HrDataReference h WHERE h.referState = :referState"),
@NamedQuery(name = "HrDataReference.findByReferCity", query = "SELECT h FROM HrDataReference h WHERE h.referCity = :referCity"),
@NamedQuery(name = "HrDataReference.findByReferPhone", query = "SELECT h FROM HrDataReference h WHERE h.referPhone = :referPhone"),
@NamedQuery(name = "HrDataReference.findByReferOcc", query = "SELECT h FROM HrDataReference h WHERE h.referOcc = :referOcc"),
@NamedQuery(name = "HrDataReference.findByDefaultCompCode", query = "SELECT h FROM HrDataReference h WHERE h.defaultCompCode = :defaultCompCode"),
@NamedQuery(name = "HrDataReference.findByStatFlag", query = "SELECT h FROM HrDataReference h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrDataReference.findByStatUpFlag", query = "SELECT h FROM HrDataReference h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrDataReference.findByModDate", query = "SELECT h FROM HrDataReference h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrDataReference.findByAuthBy", query = "SELECT h FROM HrDataReference h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrDataReference.findByEnteredBy", query = "SELECT h FROM HrDataReference h WHERE h.enteredBy = :enteredBy")})
public class HrDataReference extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrDataReferencePK hrDataReferencePK;
    @Column(name = "REFER_ADD")
    private String referAdd;
    @Column(name = "REFER_PIN")
    private String referPin;
    @Column(name = "REFER_STATE")
    private String referState;
    @Column(name = "REFER_CITY")
    private String referCity;
    @Column(name = "REFER_PHONE")
    private String referPhone;
    @Column(name = "REFER_OCC")
    private String referOcc;
    @Column(name = "DEFAULT_COMP_CODE")
    private Integer defaultCompCode;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "CAND_SRNO", referencedColumnName = "CAND_ID", insertable = false, updatable = false), @JoinColumn(name = "ADVT_CODE", referencedColumnName = "ADVT_CODE", insertable = false, updatable = false), @JoinColumn(name = "JOB_CODE", referencedColumnName = "JOB_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrDatabank hrDatabank;

    public HrDataReference() {
    }

    public HrDataReference(HrDataReferencePK hrDataReferencePK) {
        this.hrDataReferencePK = hrDataReferencePK;
    }

    public HrDataReference(int compCode, String advtCode, String jobCode, String candSrno, String referName) {
        this.hrDataReferencePK = new HrDataReferencePK(compCode, advtCode, jobCode, candSrno, referName);
    }

    public HrDataReferencePK getHrDataReferencePK() {
        return hrDataReferencePK;
    }

    public void setHrDataReferencePK(HrDataReferencePK hrDataReferencePK) {
        this.hrDataReferencePK = hrDataReferencePK;
    }

    public String getReferAdd() {
        return referAdd;
    }

    public void setReferAdd(String referAdd) {
        this.referAdd = referAdd;
    }

    public String getReferPin() {
        return referPin;
    }

    public void setReferPin(String referPin) {
        this.referPin = referPin;
    }

    public String getReferState() {
        return referState;
    }

    public void setReferState(String referState) {
        this.referState = referState;
    }

    public String getReferCity() {
        return referCity;
    }

    public void setReferCity(String referCity) {
        this.referCity = referCity;
    }

    public String getReferPhone() {
        return referPhone;
    }

    public void setReferPhone(String referPhone) {
        this.referPhone = referPhone;
    }

    public String getReferOcc() {
        return referOcc;
    }

    public void setReferOcc(String referOcc) {
        this.referOcc = referOcc;
    }

    public Integer getDefaultCompCode() {
        return defaultCompCode;
    }

    public void setDefaultCompCode(Integer defaultCompCode) {
        this.defaultCompCode = defaultCompCode;
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

    public HrDatabank getHrDatabank() {
        return hrDatabank;
    }

    public void setHrDatabank(HrDatabank hrDatabank) {
        this.hrDatabank = hrDatabank;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrDataReferencePK != null ? hrDataReferencePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDataReference)) {
            return false;
        }
        HrDataReference other = (HrDataReference) object;
        if ((this.hrDataReferencePK == null && other.hrDataReferencePK != null) || (this.hrDataReferencePK != null && !this.hrDataReferencePK.equals(other.hrDataReferencePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrDataReference[hrDataReferencePK=" + hrDataReferencePK + "]";
    }

}
