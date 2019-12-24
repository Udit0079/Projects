/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
import com.hrms.entity.personnel.HrPersonnelTransport;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "hr_mst_bus")
@NamedQueries({
    @NamedQuery(name = "HrMstBus.findAll", query = "SELECT h FROM HrMstBus h"),
    @NamedQuery(name = "HrMstBus.findHrMstBusByCompCode", query = "SELECT h FROM HrMstBus h WHERE h.hrMstBusPK.compCode = :compCode"),
    @NamedQuery(name = "HrMstBus.findByBusNo", query = "SELECT h FROM HrMstBus h WHERE h.hrMstBusPK.busNo = :busNo"),
    @NamedQuery(name = "HrMstBus.findByBusStartTm", query = "SELECT h FROM HrMstBus h WHERE h.busStartTm = :busStartTm"),
    @NamedQuery(name = "HrMstBus.findByBusEndTm", query = "SELECT h FROM HrMstBus h WHERE h.busEndTm = :busEndTm"),
    @NamedQuery(name = "HrMstBus.findByBusDriver", query = "SELECT h FROM HrMstBus h WHERE h.busDriver = :busDriver"),
    @NamedQuery(name = "HrMstBus.findByRemarks", query = "SELECT h FROM HrMstBus h WHERE h.remarks = :remarks"),
    @NamedQuery(name = "HrMstBus.findByDefaultComp", query = "SELECT h FROM HrMstBus h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrMstBus.findByStatFlag", query = "SELECT h FROM HrMstBus h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrMstBus.findByStatUpFlag", query = "SELECT h FROM HrMstBus h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrMstBus.findByModDate", query = "SELECT h FROM HrMstBus h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrMstBus.findByAuthBy", query = "SELECT h FROM HrMstBus h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrMstBus.findByEnteredBy", query = "SELECT h FROM HrMstBus h WHERE h.enteredBy = :enteredBy")})
public class HrMstBus extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrMstBusPK hrMstBusPK;
    @Basic(optional = false)
    @Column(name = "BUS_START_TM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date busStartTm;
    @Basic(optional = false)
    @Column(name = "BUS_END_TM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date busEndTm;
    @Column(name = "BUS_DRIVER")
    private String busDriver;
    @Column(name = "REMARKS")
    private String remarks;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hrMstBus")
    private Collection<HrPersonnelTransport> hrPersonnelTransportCollection;

    public HrMstBus() {
    }

    public HrMstBus(HrMstBusPK hrMstBusPK) {
        this.hrMstBusPK = hrMstBusPK;
    }

    public HrMstBus(HrMstBusPK hrMstBusPK, Date busStartTm, Date busEndTm) {
        this.hrMstBusPK = hrMstBusPK;
        this.busStartTm = busStartTm;
        this.busEndTm = busEndTm;
    }

    public HrMstBus(int compCode, String busNo) {
        this.hrMstBusPK = new HrMstBusPK(compCode, busNo);
    }

    public HrMstBusPK getHrMstBusPK() {
        return hrMstBusPK;
    }

    public void setHrMstBusPK(HrMstBusPK hrMstBusPK) {
        this.hrMstBusPK = hrMstBusPK;
    }

    public Date getBusStartTm() {
        return busStartTm;
    }

    public void setBusStartTm(Date busStartTm) {
        this.busStartTm = busStartTm;
    }

    public Date getBusEndTm() {
        return busEndTm;
    }

    public void setBusEndTm(Date busEndTm) {
        this.busEndTm = busEndTm;
    }

    public String getBusDriver() {
        return busDriver;
    }

    public void setBusDriver(String busDriver) {
        this.busDriver = busDriver;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Collection<HrPersonnelTransport> getHrPersonnelTransportCollection() {
        return hrPersonnelTransportCollection;
    }

    public void setHrPersonnelTransportCollection(Collection<HrPersonnelTransport> hrPersonnelTransportCollection) {
        this.hrPersonnelTransportCollection = hrPersonnelTransportCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrMstBusPK != null ? hrMstBusPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstBus)) {
            return false;
        }
        HrMstBus other = (HrMstBus) object;
        if ((this.hrMstBusPK == null && other.hrMstBusPK != null) || (this.hrMstBusPK != null && !this.hrMstBusPK.equals(other.hrMstBusPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.hr.HrMstBus[hrMstBusPK=" + hrMstBusPK + "]";
    }
}
