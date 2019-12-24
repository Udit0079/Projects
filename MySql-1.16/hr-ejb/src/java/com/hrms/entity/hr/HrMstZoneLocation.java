/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
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
@Table(name = "hr_mst_zone_location")
@NamedQueries({@NamedQuery(name = "HrMstZoneLocation.findAll", query = "SELECT h FROM HrMstZoneLocation h"), @NamedQuery(name = "HrMstZoneLocation.findByCompCode", query = "SELECT h FROM HrMstZoneLocation h WHERE h.hrMstZoneLocationPK.compCode = :compCode"), @NamedQuery(name = "HrMstZoneLocation.findByZoneCode", query = "SELECT h FROM HrMstZoneLocation h WHERE h.hrMstZoneLocationPK.zoneCode = :zoneCode"), @NamedQuery(name = "HrMstZoneLocation.findByLocationCode", query = "SELECT h FROM HrMstZoneLocation h WHERE h.hrMstZoneLocationPK.locationCode = :locationCode"), @NamedQuery(name = "HrMstZoneLocation.findByStatFlag", query = "SELECT h FROM HrMstZoneLocation h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrMstZoneLocation.findByStatUpFlag", query = "SELECT h FROM HrMstZoneLocation h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrMstZoneLocation.findByModDate", query = "SELECT h FROM HrMstZoneLocation h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrMstZoneLocation.findByDefaultComp", query = "SELECT h FROM HrMstZoneLocation h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrMstZoneLocation.findByAuthBy", query = "SELECT h FROM HrMstZoneLocation h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrMstZoneLocation.findByEnteredBy", query = "SELECT h FROM HrMstZoneLocation h WHERE h.enteredBy = :enteredBy")})
public class HrMstZoneLocation extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrMstZoneLocationPK hrMstZoneLocationPK;
    @Basic(optional = false)
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Basic(optional = false)
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Basic(optional = false)
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Basic(optional = false)
    @Column(name = "DEFAULT_COMP")
    private int defaultComp;
    @Basic(optional = false)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "ZONE_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "LOCATION_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct1;

    public HrMstZoneLocation() {
    }

    public HrMstZoneLocation(HrMstZoneLocationPK hrMstZoneLocationPK) {
        this.hrMstZoneLocationPK = hrMstZoneLocationPK;
    }

    public HrMstZoneLocation(HrMstZoneLocationPK hrMstZoneLocationPK, String statFlag, String statUpFlag, Date modDate, int defaultComp, String authBy, String enteredBy) {
        this.hrMstZoneLocationPK = hrMstZoneLocationPK;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.defaultComp = defaultComp;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrMstZoneLocation(int compCode, String zoneCode, String locationCode) {
        this.hrMstZoneLocationPK = new HrMstZoneLocationPK(compCode, zoneCode, locationCode);
    }

    public HrMstZoneLocationPK getHrMstZoneLocationPK() {
        return hrMstZoneLocationPK;
    }

    public void setHrMstZoneLocationPK(HrMstZoneLocationPK hrMstZoneLocationPK) {
        this.hrMstZoneLocationPK = hrMstZoneLocationPK;
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

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
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

    public HrMstStruct getHrMstStruct() {
        return hrMstStruct;
    }

    public void setHrMstStruct(HrMstStruct hrMstStruct) {
        this.hrMstStruct = hrMstStruct;
    }

    public HrMstStruct getHrMstStruct1() {
        return hrMstStruct1;
    }

    public void setHrMstStruct1(HrMstStruct hrMstStruct1) {
        this.hrMstStruct1 = hrMstStruct1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrMstZoneLocationPK != null ? hrMstZoneLocationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstZoneLocation)) {
            return false;
        }
        HrMstZoneLocation other = (HrMstZoneLocation) object;
        if ((this.hrMstZoneLocationPK == null && other.hrMstZoneLocationPK != null) || (this.hrMstZoneLocationPK != null && !this.hrMstZoneLocationPK.equals(other.hrMstZoneLocationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrMstZoneLocation[hrMstZoneLocationPK=" + hrMstZoneLocationPK + "]";
    }

}
