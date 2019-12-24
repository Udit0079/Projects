/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.payroll;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "hr_payroll_calendar")
@NamedQueries({
    @NamedQuery(name = "HrPayrollCalendar.findAll", query = "SELECT h FROM HrPayrollCalendar h"),
    @NamedQuery(name = "HrPayrollCalendar.findByCompCode", query = "SELECT h FROM HrPayrollCalendar h WHERE h.hrPayrollCalendarPK.compCode = :compCode"),
    @NamedQuery(name = "HrPayrollCalendar.findByDateFrom", query = "SELECT h FROM HrPayrollCalendar h WHERE h.hrPayrollCalendarPK.dateFrom = :dateFrom"),
    @NamedQuery(name = "HrPayrollCalendar.findByDateTo", query = "SELECT h FROM HrPayrollCalendar h WHERE h.hrPayrollCalendarPK.dateTo = :dateTo"),
    @NamedQuery(name = "HrPayrollCalendar.findByStatusFlag", query = "SELECT h FROM HrPayrollCalendar h WHERE h.statusFlag = :statusFlag"),
    @NamedQuery(name = "HrPayrollCalendar.findByDefaultComp", query = "SELECT h FROM HrPayrollCalendar h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrPayrollCalendar.findByStatFlag", query = "SELECT h FROM HrPayrollCalendar h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrPayrollCalendar.findByStatUpFlag", query = "SELECT h FROM HrPayrollCalendar h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrPayrollCalendar.findByModDate", query = "SELECT h FROM HrPayrollCalendar h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrPayrollCalendar.findByAuthBy", query = "SELECT h FROM HrPayrollCalendar h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrPayrollCalendar.findByEnteredBy", query = "SELECT h FROM HrPayrollCalendar h WHERE h.enteredBy = :enteredBy"),
    @NamedQuery(name = "HrPayrollCalendar.findPayrollCalendarListByCompCode", query = "select h FROM HrPayrollCalendar h WHERE h.hrPayrollCalendarPK.compCode = :compCode"),
    @NamedQuery(name = "HrPayrollCalendar.findPayrollCalendarByStatusFlag", query = "SELECT h FROM HrPayrollCalendar h WHERE h.hrPayrollCalendarPK.compCode = :compCode and h.statusFlag = :statusFlag"),
    @NamedQuery(name = "HrPayrollCalendar.HrPayrollCalendar.findExistingPayrollCalendar", query = "SELECT h FROM HrPayrollCalendar h WHERE h.hrPayrollCalendarPK.compCode = :compCode and h.hrPayrollCalendarPK.dateFrom = :dateFrom and h.hrPayrollCalendarPK.dateTo = :dateTo and h.statusFlag = :statusFlag"),
    @NamedQuery(name = "HrPayrollCalendar.findOpenPayrollCalendar",query = "SELECT h FROM HrPayrollCalendar h WHERE h.hrPayrollCalendarPK.compCode = :compCode and h.statusFlag = :statusFlag"),
    @NamedQuery(name = "HrPayrollCalendar.findPayrollCalendarByStatusFlag1", query = "SELECT h.hrPayrollCalendarPK.dateFrom,h.hrPayrollCalendarPK.dateTo FROM HrPayrollCalendar h WHERE h.hrPayrollCalendarPK.compCode = :compCode and h.statusFlag = :statusFlag")

})
public class HrPayrollCalendar extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrPayrollCalendarPK hrPayrollCalendarPK;
    @Basic(optional = false)
    @Column(name = "STATUS_FLAG")
    private String statusFlag;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Basic(optional = false)
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Basic(optional = false)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    

    public HrPayrollCalendar() {
    }

    public HrPayrollCalendar(HrPayrollCalendarPK hrPayrollCalendarPK) {
        this.hrPayrollCalendarPK = hrPayrollCalendarPK;
    }

    public HrPayrollCalendar(HrPayrollCalendarPK hrPayrollCalendarPK, String statusFlag, Date modDate, String authBy, String enteredBy) {
        this.hrPayrollCalendarPK = hrPayrollCalendarPK;
        this.statusFlag = statusFlag;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrPayrollCalendar(int compCode, Date dateFrom, Date dateTo) {
        this.hrPayrollCalendarPK = new HrPayrollCalendarPK(compCode, dateFrom, dateTo);
    }

    public HrPayrollCalendarPK getHrPayrollCalendarPK() {
        return hrPayrollCalendarPK;
    }

    public void setHrPayrollCalendarPK(HrPayrollCalendarPK hrPayrollCalendarPK) {
        this.hrPayrollCalendarPK = hrPayrollCalendarPK;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
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
        hash += (hrPayrollCalendarPK != null ? hrPayrollCalendarPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPayrollCalendar)) {
            return false;
        }
        HrPayrollCalendar other = (HrPayrollCalendar) object;
        if ((this.hrPayrollCalendarPK == null && other.hrPayrollCalendarPK != null) || (this.hrPayrollCalendarPK != null && !this.hrPayrollCalendarPK.equals(other.hrPayrollCalendarPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPayrollCalendar[hrPayrollCalendarPK=" + hrPayrollCalendarPK + "]";
    }

}
