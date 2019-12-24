/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrMstBus;
import com.hrms.entity.hr.HrMstRoute;
import com.hrms.entity.hr.HrPersonnelDetails;
import java.io.Serializable;
import java.util.Date;
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
@Table(name = "hr_personnel_transport")
@NamedQueries({@NamedQuery(name = "HrPersonnelTransport.findAll", query = "SELECT h FROM HrPersonnelTransport h"), @NamedQuery(name = "HrPersonnelTransport.findByCompCode", query = "SELECT h FROM HrPersonnelTransport h WHERE h.hrPersonnelTransportPK.compCode = :compCode"), @NamedQuery(name = "HrPersonnelTransport.findByEmpCode", query = "SELECT h FROM HrPersonnelTransport h WHERE h.hrPersonnelTransportPK.empCode = :empCode"), @NamedQuery(name = "HrPersonnelTransport.findByRouteCode", query = "SELECT h FROM HrPersonnelTransport h WHERE h.hrPersonnelTransportPK.routeCode = :routeCode"), @NamedQuery(name = "HrPersonnelTransport.findByBusNo", query = "SELECT h FROM HrPersonnelTransport h WHERE h.hrPersonnelTransportPK.busNo = :busNo"), @NamedQuery(name = "HrPersonnelTransport.findByPickPnt", query = "SELECT h FROM HrPersonnelTransport h WHERE h.pickPnt = :pickPnt"), @NamedQuery(name = "HrPersonnelTransport.findByPickTm", query = "SELECT h FROM HrPersonnelTransport h WHERE h.pickTm = :pickTm"), @NamedQuery(name = "HrPersonnelTransport.findByDefaultComp", query = "SELECT h FROM HrPersonnelTransport h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrPersonnelTransport.findByStatFlag", query = "SELECT h FROM HrPersonnelTransport h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrPersonnelTransport.findByStatUpFlag", query = "SELECT h FROM HrPersonnelTransport h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrPersonnelTransport.findByModDate", query = "SELECT h FROM HrPersonnelTransport h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrPersonnelTransport.findByAuthBy", query = "SELECT h FROM HrPersonnelTransport h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrPersonnelTransport.findByEnteredBy", query = "SELECT h FROM HrPersonnelTransport h WHERE h.enteredBy = :enteredBy")})
public class HrPersonnelTransport extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrPersonnelTransportPK hrPersonnelTransportPK;
    @Column(name = "PICK_PNT", length = 25)
    private String pickPnt;
    @Column(name = "PICK_TM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pickTm;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Column(name = "STAT_FLAG", length = 2)
    private String statFlag;
    @Column(name = "STAT_UP_FLAG", length = 2)
    private String statUpFlag;
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Column(name = "AUTH_BY", length = 100)
    private String authBy;
    @Column(name = "ENTERED_BY", length = 100)
    private String enteredBy;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "BUS_NO", referencedColumnName = "BUS_NO", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstBus hrMstBus;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "ROUTE_CODE", referencedColumnName = "ROUTE_CODE", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstRoute hrMstRoute;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrPersonnelTransport() {
    }

    public HrPersonnelTransport(HrPersonnelTransportPK hrPersonnelTransportPK) {
        this.hrPersonnelTransportPK = hrPersonnelTransportPK;
    }

    public HrPersonnelTransport(int compCode, long empCode, String routeCode, String busNo) {
        this.hrPersonnelTransportPK = new HrPersonnelTransportPK(compCode, empCode, routeCode, busNo);
    }

    public HrPersonnelTransportPK getHrPersonnelTransportPK() {
        return hrPersonnelTransportPK;
    }

    public void setHrPersonnelTransportPK(HrPersonnelTransportPK hrPersonnelTransportPK) {
        this.hrPersonnelTransportPK = hrPersonnelTransportPK;
    }

    public String getPickPnt() {
        return pickPnt;
    }

    public void setPickPnt(String pickPnt) {
        this.pickPnt = pickPnt;
    }

    public Date getPickTm() {
        return pickTm;
    }

    public void setPickTm(Date pickTm) {
        this.pickTm = pickTm;
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

    public HrMstBus getHrMstBus() {
        return hrMstBus;
    }

    public void setHrMstBus(HrMstBus hrMstBus) {
        this.hrMstBus = hrMstBus;
    }

    public HrMstRoute getHrMstRoute() {
        return hrMstRoute;
    }

    public void setHrMstRoute(HrMstRoute hrMstRoute) {
        this.hrMstRoute = hrMstRoute;
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
        hash += (hrPersonnelTransportPK != null ? hrPersonnelTransportPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelTransport)) {
            return false;
        }
        HrPersonnelTransport other = (HrPersonnelTransport) object;
        if ((this.hrPersonnelTransportPK == null && other.hrPersonnelTransportPK != null) || (this.hrPersonnelTransportPK != null && !this.hrPersonnelTransportPK.equals(other.hrPersonnelTransportPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.personnel.HrPersonnelTransport[hrPersonnelTransportPK=" + hrPersonnelTransportPK + "]";
    }
}
