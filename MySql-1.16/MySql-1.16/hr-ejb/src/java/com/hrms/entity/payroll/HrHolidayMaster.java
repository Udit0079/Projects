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
@Table(name = "hr_holiday_master")
@NamedQueries({@NamedQuery(name = "HrHolidayMaster.findAll", query = "SELECT h FROM HrHolidayMaster h"),
@NamedQuery(name = "HrHolidayMaster.findByCompCode", query = "SELECT h FROM HrHolidayMaster h WHERE h.hrHolidayMasterPK.compCode = :compCode"),
@NamedQuery(name = "HrHolidayMaster.findByHolidayCode", query = "SELECT h FROM HrHolidayMaster h WHERE h.hrHolidayMasterPK.holidayCode = :holidayCode"),
@NamedQuery(name = "HrHolidayMaster.findByHolidayDateFrom", query = "SELECT h FROM HrHolidayMaster h WHERE h.holidayDateFrom = :holidayDateFrom"),
@NamedQuery(name = "HrHolidayMaster.findByHolidayDateTo", query = "SELECT h FROM HrHolidayMaster h WHERE h.holidayDateTo = :holidayDateTo"),
@NamedQuery(name = "HrHolidayMaster.findByHolidayDate", query = "SELECT h FROM HrHolidayMaster h WHERE h.holidayDate = :holidayDate"),
@NamedQuery(name = "HrHolidayMaster.findByHolidayDesc", query = "SELECT h FROM HrHolidayMaster h WHERE h.holidayDesc = :holidayDesc"),
@NamedQuery(name = "HrHolidayMaster.findByWeekDays", query = "SELECT h FROM HrHolidayMaster h WHERE h.weekDays = :weekDays"),
@NamedQuery(name = "HrHolidayMaster.findByDefaultComp", query = "SELECT h FROM HrHolidayMaster h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrHolidayMaster.findByStatFlag", query = "SELECT h FROM HrHolidayMaster h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrHolidayMaster.findByStatUpFlag", query = "SELECT h FROM HrHolidayMaster h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrHolidayMaster.findByModDate", query = "SELECT h FROM HrHolidayMaster h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrHolidayMaster.findByAuthBy", query = "SELECT h FROM HrHolidayMaster h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrHolidayMaster.findByEnteredBy", query = "SELECT h FROM HrHolidayMaster h WHERE h.enteredBy = :enteredBy"),
@NamedQuery(name = "HrHolidayMaster.findByCompCodeAndHolidayDate", query = "SELECT h FROM HrHolidayMaster h WHERE h.hrHolidayMasterPK.compCode = :compCode and h.holidayDate = :holidayDate"),
@NamedQuery(name = "HrHolidayMaster.findByCompCodeAndFrDtAndToDt", query = "SELECT h FROM HrHolidayMaster h WHERE h.hrHolidayMasterPK.compCode = :compCode "
        + "and h.hrPayrollCalendar.hrPayrollCalendarPK.dateFrom = :dateFrom and h.hrPayrollCalendar.hrPayrollCalendarPK.dateTo = :dateTo"),
@NamedQuery(name = "HrHolidayMaster.findMaxHoliDayCode", query = "SELECT max(h.hrHolidayMasterPK.holidayCode) FROM HrHolidayMaster h WHERE h.hrHolidayMasterPK.compCode = :compCode"),
@NamedQuery(name = "HrHolidayMaster.findByPrimaryKey", query = "SELECT h FROM HrHolidayMaster h WHERE h.hrHolidayMasterPK = :hrHolidayMasterPK")
})
public class HrHolidayMaster extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrHolidayMasterPK hrHolidayMasterPK;
    @Basic(optional = false)
    @Column(name = "HOLIDAY_DATE_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date holidayDateFrom;
    @Basic(optional = false)
    @Column(name = "HOLIDAY_DATE_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date holidayDateTo;
    @Basic(optional = false)
    @Column(name = "HOLIDAY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date holidayDate;
    @Basic(optional = false)
    @Column(name = "HOLIDAY_DESC")
    private String holidayDesc;
    @Basic(optional = false)
    @Column(name = "WEEK_DAYS")
    private String weekDays;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "CALENDAR_DATE_FROM", referencedColumnName = "DATE_FROM"), @JoinColumn(name = "CALENDAR_DATE_TO", referencedColumnName = "DATE_TO")})
    @ManyToOne(optional = false)
    private HrPayrollCalendar hrPayrollCalendar;

    public HrHolidayMaster() {
    }

    public HrHolidayMaster(HrHolidayMasterPK hrHolidayMasterPK) {
        this.hrHolidayMasterPK = hrHolidayMasterPK;
    }

    public HrHolidayMaster(HrHolidayMasterPK hrHolidayMasterPK, Date holidayDateFrom, Date holidayDateTo, Date holidayDate, String holidayDesc, String weekDays, Date modDate, String authBy, String enteredBy) {
        this.hrHolidayMasterPK = hrHolidayMasterPK;
        this.holidayDateFrom = holidayDateFrom;
        this.holidayDateTo = holidayDateTo;
        this.holidayDate = holidayDate;
        this.holidayDesc = holidayDesc;
        this.weekDays = weekDays;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrHolidayMaster(int compCode, long holidayCode) {
        this.hrHolidayMasterPK = new HrHolidayMasterPK(compCode, holidayCode);
    }

    public HrHolidayMasterPK getHrHolidayMasterPK() {
        return hrHolidayMasterPK;
    }

    public void setHrHolidayMasterPK(HrHolidayMasterPK hrHolidayMasterPK) {
        this.hrHolidayMasterPK = hrHolidayMasterPK;
    }

    public Date getHolidayDateFrom() {
        return holidayDateFrom;
    }

    public void setHolidayDateFrom(Date holidayDateFrom) {
        this.holidayDateFrom = holidayDateFrom;
    }

    public Date getHolidayDateTo() {
        return holidayDateTo;
    }

    public void setHolidayDateTo(Date holidayDateTo) {
        this.holidayDateTo = holidayDateTo;
    }

    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHolidayDesc() {
        return holidayDesc;
    }

    public void setHolidayDesc(String holidayDesc) {
        this.holidayDesc = holidayDesc;
    }

    public String getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String weekDays) {
        this.weekDays = weekDays;
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

    public HrPayrollCalendar getHrPayrollCalendar() {
        return hrPayrollCalendar;
    }

    public void setHrPayrollCalendar(HrPayrollCalendar hrPayrollCalendar) {
        this.hrPayrollCalendar = hrPayrollCalendar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrHolidayMasterPK != null ? hrHolidayMasterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrHolidayMaster)) {
            return false;
        }
        HrHolidayMaster other = (HrHolidayMaster) object;
        if ((this.hrHolidayMasterPK == null && other.hrHolidayMasterPK != null) || (this.hrHolidayMasterPK != null && !this.hrHolidayMasterPK.equals(other.hrHolidayMasterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrHolidayMaster[hrHolidayMasterPK=" + hrHolidayMasterPK + "]";
    }

}
