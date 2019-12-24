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
@Table(name = "hr_mst_route")
@NamedQueries({
    @NamedQuery(name = "HrMstRoute.findAll", query = "SELECT h FROM HrMstRoute h"),
    @NamedQuery(name = "HrMstRoute.findHrMstRouteByCompCode", query = "SELECT h FROM HrMstRoute h WHERE h.hrMstRoutePK.compCode = :compCode"),
    @NamedQuery(name = "HrMstRoute.findByRouteCode", query = "SELECT h FROM HrMstRoute h WHERE h.hrMstRoutePK.routeCode = :routeCode"),
    @NamedQuery(name = "HrMstRoute.findByRouteStart", query = "SELECT h FROM HrMstRoute h WHERE h.routeStart = :routeStart"),
    @NamedQuery(name = "HrMstRoute.findByRouteEnd", query = "SELECT h FROM HrMstRoute h WHERE h.routeEnd = :routeEnd"),
    @NamedQuery(name = "HrMstRoute.findByRouteVia", query = "SELECT h FROM HrMstRoute h WHERE h.routeVia = :routeVia"),
    @NamedQuery(name = "HrMstRoute.findByDefaultComp", query = "SELECT h FROM HrMstRoute h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrMstRoute.findByStatFlag", query = "SELECT h FROM HrMstRoute h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrMstRoute.findByStatUpFlag", query = "SELECT h FROM HrMstRoute h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrMstRoute.findByModDate", query = "SELECT h FROM HrMstRoute h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrMstRoute.findByAuthBy", query = "SELECT h FROM HrMstRoute h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrMstRoute.findByEnteredBy", query = "SELECT h FROM HrMstRoute h WHERE h.enteredBy = :enteredBy")})
public class HrMstRoute extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrMstRoutePK hrMstRoutePK;
    @Basic(optional = false)
    @Column(name = "ROUTE_START")
    private String routeStart;
    @Basic(optional = false)
    @Column(name = "ROUTE_END")
    private String routeEnd;
    @Column(name = "ROUTE_VIA")
    private String routeVia;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hrMstRoute")
    private Collection<HrPersonnelTransport> hrPersonnelTransportCollection;

    public HrMstRoute() {
    }

    public HrMstRoute(HrMstRoutePK hrMstRoutePK) {
        this.hrMstRoutePK = hrMstRoutePK;
    }

    public HrMstRoute(HrMstRoutePK hrMstRoutePK, String routeStart, String routeEnd) {
        this.hrMstRoutePK = hrMstRoutePK;
        this.routeStart = routeStart;
        this.routeEnd = routeEnd;
    }

    public HrMstRoute(int compCode, String routeCode) {
        this.hrMstRoutePK = new HrMstRoutePK(compCode, routeCode);
    }

    public HrMstRoutePK getHrMstRoutePK() {
        return hrMstRoutePK;
    }

    public void setHrMstRoutePK(HrMstRoutePK hrMstRoutePK) {
        this.hrMstRoutePK = hrMstRoutePK;
    }

    public String getRouteStart() {
        return routeStart;
    }

    public void setRouteStart(String routeStart) {
        this.routeStart = routeStart;
    }

    public String getRouteEnd() {
        return routeEnd;
    }

    public void setRouteEnd(String routeEnd) {
        this.routeEnd = routeEnd;
    }

    public String getRouteVia() {
        return routeVia;
    }

    public void setRouteVia(String routeVia) {
        this.routeVia = routeVia;
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
        hash += (hrMstRoutePK != null ? hrMstRoutePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstRoute)) {
            return false;
        }
        HrMstRoute other = (HrMstRoute) object;
        if ((this.hrMstRoutePK == null && other.hrMstRoutePK != null) || (this.hrMstRoutePK != null && !this.hrMstRoutePK.equals(other.hrMstRoutePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.hr.HrMstRoute[hrMstRoutePK=" + hrMstRoutePK + "]";
    }
}
