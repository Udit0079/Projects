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
@Table(name = "hr_advert_hd")
@NamedQueries({@NamedQuery(name = "HrAdvertHd.findAll", query = "SELECT h FROM HrAdvertHd h"),
@NamedQuery(name = "HrAdvertHd.findByCompCode", query = "SELECT h FROM HrAdvertHd h WHERE h.hrAdvertHdPK.compCode = :compCode"),
@NamedQuery(name = "HrAdvertHd.findByAdvtCode", query = "SELECT h FROM HrAdvertHd h WHERE h.hrAdvertHdPK.advtCode = :advtCode"),
@NamedQuery(name = "HrAdvertHd.findByMediaType", query = "SELECT h FROM HrAdvertHd h WHERE h.mediaType = :mediaType"),
@NamedQuery(name = "HrAdvertHd.findByAdvtDate", query = "SELECT h FROM HrAdvertHd h WHERE h.hrAdvertHdPK.advtDate = :advtDate"),
@NamedQuery(name = "HrAdvertHd.findByJobCode", query = "SELECT h FROM HrAdvertHd h WHERE h.hrAdvertHdPK.jobCode = :jobCode"),
@NamedQuery(name = "HrAdvertHd.findByDefaultComp", query = "SELECT h FROM HrAdvertHd h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrAdvertHd.findByStatFlag", query = "SELECT h FROM HrAdvertHd h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrAdvertHd.findByStatUpFlag", query = "SELECT h FROM HrAdvertHd h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrAdvertHd.findByModDate", query = "SELECT h FROM HrAdvertHd h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrAdvertHd.findByAuthBy", query = "SELECT h FROM HrAdvertHd h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrAdvertHd.findAdvtCodeLike", query = "select h from HrAdvertHd h where h.hrAdvertHdPK.compCode = :compCode and h.hrAdvertHdPK.advtCode like:advtCode"),
@NamedQuery(name = "HrAdvertHd.findByCompCodeAndAdvertCode", query = "select h from HrAdvertHd h where h.hrAdvertHdPK.compCode=:compCode and  h.hrAdvertHdPK.advtCode=:advtCode"),
@NamedQuery(name = "HrAdvertHd.findMaxAdvtCodeByCompCode", query = "SELECT max(substring(h.hrAdvertHdPK.advtCode,4,10)) FROM HrAdvertHd h WHERE h.hrAdvertHdPK.compCode = :compCode"),
@NamedQuery(name = "HrAdvertHd.findByEnteredBy", query = "SELECT h FROM HrAdvertHd h WHERE h.enteredBy = :enteredBy")})
public class HrAdvertHd extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrAdvertHdPK hrAdvertHdPK;
    @Column(name = "MEDIA_TYPE")
    private Character mediaType;
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

    public HrAdvertHd() {
    }

    public HrAdvertHd(HrAdvertHdPK hrAdvertHdPK) {
        this.hrAdvertHdPK = hrAdvertHdPK;
    }

    public HrAdvertHd(int compCode, String advtCode, String advtDate, String jobCode) {
        this.hrAdvertHdPK = new HrAdvertHdPK(compCode, advtCode, advtDate, jobCode);
    }

    public HrAdvertHdPK getHrAdvertHdPK() {
        return hrAdvertHdPK;
    }

    public void setHrAdvertHdPK(HrAdvertHdPK hrAdvertHdPK) {
        this.hrAdvertHdPK = hrAdvertHdPK;
    }

    public Character getMediaType() {
        return mediaType;
    }

    public void setMediaType(Character mediaType) {
        this.mediaType = mediaType;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrAdvertHdPK != null ? hrAdvertHdPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAdvertHd)) {
            return false;
        }
        HrAdvertHd other = (HrAdvertHd) object;
        if ((this.hrAdvertHdPK == null && other.hrAdvertHdPK != null) || (this.hrAdvertHdPK != null && !this.hrAdvertHdPK.equals(other.hrAdvertHdPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrAdvertHd[hrAdvertHdPK=" + hrAdvertHdPK + "]";
    }

}
