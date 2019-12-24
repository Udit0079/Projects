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
@Table(name = "hr_advert_det_dt")
@NamedQueries({@NamedQuery(name = "HrAdvertDetDt.findAll", query = "SELECT h FROM HrAdvertDetDt h"), @NamedQuery(name = "HrAdvertDetDt.findByCompCode", query = "SELECT h FROM HrAdvertDetDt h WHERE h.hrAdvertDetDtPK.compCode = :compCode"), @NamedQuery(name = "HrAdvertDetDt.findByAdvtCode", query = "SELECT h FROM HrAdvertDetDt h WHERE h.hrAdvertDetDtPK.advtCode = :advtCode"), @NamedQuery(name = "HrAdvertDetDt.findByMediaType", query = "SELECT h FROM HrAdvertDetDt h WHERE h.hrAdvertDetDtPK.mediaType = :mediaType"), @NamedQuery(name = "HrAdvertDetDt.findByJobCode", query = "SELECT h FROM HrAdvertDetDt h WHERE h.hrAdvertDetDtPK.jobCode = :jobCode"), @NamedQuery(name = "HrAdvertDetDt.findByCity", query = "SELECT h FROM HrAdvertDetDt h WHERE h.hrAdvertDetDtPK.city = :city"), @NamedQuery(name = "HrAdvertDetDt.findByMediaName", query = "SELECT h FROM HrAdvertDetDt h WHERE h.hrAdvertDetDtPK.mediaName = :mediaName"), @NamedQuery(name = "HrAdvertDetDt.findByResponse", query = "SELECT h FROM HrAdvertDetDt h WHERE h.response = :response"), @NamedQuery(name = "HrAdvertDetDt.findByPageNo", query = "SELECT h FROM HrAdvertDetDt h WHERE h.pageNo = :pageNo"), @NamedQuery(name = "HrAdvertDetDt.findByColumnNo", query = "SELECT h FROM HrAdvertDetDt h WHERE h.columnNo = :columnNo"), @NamedQuery(name = "HrAdvertDetDt.findByLanguageIn", query = "SELECT h FROM HrAdvertDetDt h WHERE h.languageIn = :languageIn"), @NamedQuery(name = "HrAdvertDetDt.findByCost", query = "SELECT h FROM HrAdvertDetDt h WHERE h.cost = :cost"), @NamedQuery(name = "HrAdvertDetDt.findByTimeIn", query = "SELECT h FROM HrAdvertDetDt h WHERE h.timeIn = :timeIn"), @NamedQuery(name = "HrAdvertDetDt.findByDuration", query = "SELECT h FROM HrAdvertDetDt h WHERE h.duration = :duration"), @NamedQuery(name = "HrAdvertDetDt.findByDefaultComp", query = "SELECT h FROM HrAdvertDetDt h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrAdvertDetDt.findByStatFlag", query = "SELECT h FROM HrAdvertDetDt h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrAdvertDetDt.findByStatUpFlag", query = "SELECT h FROM HrAdvertDetDt h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrAdvertDetDt.findByModDate", query = "SELECT h FROM HrAdvertDetDt h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrAdvertDetDt.findByAuthBy", query = "SELECT h FROM HrAdvertDetDt h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrAdvertDetDt.findByEnteredBy", query = "SELECT h FROM HrAdvertDetDt h WHERE h.enteredBy = :enteredBy")})
public class HrAdvertDetDt extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrAdvertDetDtPK hrAdvertDetDtPK;
    @Column(name = "RESPONSE")
    private Integer response;
    @Column(name = "PAGE_NO")
    private Integer pageNo;
    @Column(name = "COLUMN_NO")
    private Integer columnNo;
    @Column(name = "LANGUAGE_IN")
    private String languageIn;
    @Column(name = "COST")
    private Double cost;
    @Column(name = "TIME_IN")
    private Integer timeIn;
    @Column(name = "DURATION")
    private Integer duration;
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

    public HrAdvertDetDt() {
    }

    public HrAdvertDetDt(HrAdvertDetDtPK hrAdvertDetDtPK) {
        this.hrAdvertDetDtPK = hrAdvertDetDtPK;
    }

    public HrAdvertDetDt(int compCode, String advtCode, char mediaType, String jobCode, String city, String mediaName) {
        this.hrAdvertDetDtPK = new HrAdvertDetDtPK(compCode, advtCode, mediaType, jobCode, city, mediaName);
    }

    public HrAdvertDetDtPK getHrAdvertDetDtPK() {
        return hrAdvertDetDtPK;
    }

    public void setHrAdvertDetDtPK(HrAdvertDetDtPK hrAdvertDetDtPK) {
        this.hrAdvertDetDtPK = hrAdvertDetDtPK;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(Integer columnNo) {
        this.columnNo = columnNo;
    }

    public String getLanguageIn() {
        return languageIn;
    }

    public void setLanguageIn(String languageIn) {
        this.languageIn = languageIn;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Integer timeIn) {
        this.timeIn = timeIn;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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
        hash += (hrAdvertDetDtPK != null ? hrAdvertDetDtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAdvertDetDt)) {
            return false;
        }
        HrAdvertDetDt other = (HrAdvertDetDt) object;
        if ((this.hrAdvertDetDtPK == null && other.hrAdvertDetDtPK != null) || (this.hrAdvertDetDtPK != null && !this.hrAdvertDetDtPK.equals(other.hrAdvertDetDtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrAdvertDetDt[hrAdvertDetDtPK=" + hrAdvertDetDtPK + "]";
    }

}
