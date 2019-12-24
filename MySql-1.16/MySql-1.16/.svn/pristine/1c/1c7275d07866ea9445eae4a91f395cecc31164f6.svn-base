/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.payroll;

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
@Table(name = "hr_attendance_maintenance")
@NamedQueries({@NamedQuery(name = "HrAttendanceMaintenance.findAll", query = "SELECT h FROM HrAttendanceMaintenance h"),
@NamedQuery(name = "HrAttendanceMaintenance.findByCompCode", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.hrAttendanceMaintenancePK.compCode = :compCode"),
@NamedQuery(name = "HrAttendanceMaintenance.findByEmpCode", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.hrAttendanceMaintenancePK.empCode = :empCode"),
@NamedQuery(name = "HrAttendanceMaintenance.findByAttenDate", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.hrAttendanceMaintenancePK.attenDate = :attenDate"),
@NamedQuery(name = "HrAttendanceMaintenance.findByTimeIn", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.timeIn = :timeIn"),
@NamedQuery(name = "HrAttendanceMaintenance.findByTimeOut", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.timeOut = :timeOut"),
@NamedQuery(name = "HrAttendanceMaintenance.findByAttenStatusFixed", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.attenStatusFixed = :attenStatusFixed"),
@NamedQuery(name = "HrAttendanceMaintenance.findByDefaultComp", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrAttendanceMaintenance.findByStatFlag", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrAttendanceMaintenance.findByStatUpFlag", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrAttendanceMaintenance.findByModDate", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrAttendanceMaintenance.findByAuthBy", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrAttendanceMaintenance.findByEnteredBy", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.enteredBy = :enteredBy"),
@NamedQuery(name = "HrAttendanceMaintenance.findByComCodeEmCodeAndAttenDate", query = "SELECT h FROM HrAttendanceMaintenance h WHERE h.hrAttendanceMaintenancePK.compCode = :compCode and h.hrAttendanceMaintenancePK.empCode = :empCode and h.hrAttendanceMaintenancePK.attenDate = :attenDate")
})
public class HrAttendanceMaintenance extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrAttendanceMaintenancePK hrAttendanceMaintenancePK;
    @Column(name = "TIME_IN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeIn;
    @Column(name = "TIME_OUT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOut;
    @Basic(optional = false)
    @Column(name = "ATTEN_STATUS_FIXED")
    private char attenStatusFixed;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
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
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrAttendanceMaintenance() {
    }

    public HrAttendanceMaintenance(HrAttendanceMaintenancePK hrAttendanceMaintenancePK) {
        this.hrAttendanceMaintenancePK = hrAttendanceMaintenancePK;
    }

    public HrAttendanceMaintenance(HrAttendanceMaintenancePK hrAttendanceMaintenancePK, char attenStatusFixed, String statFlag, String statUpFlag, Date modDate, String authBy, String enteredBy) {
        this.hrAttendanceMaintenancePK = hrAttendanceMaintenancePK;
        this.attenStatusFixed = attenStatusFixed;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrAttendanceMaintenance(int compCode, long empCode, Date attenDate) {
        this.hrAttendanceMaintenancePK = new HrAttendanceMaintenancePK(compCode, empCode, attenDate);
    }

    public HrAttendanceMaintenancePK getHrAttendanceMaintenancePK() {
        return hrAttendanceMaintenancePK;
    }

    public void setHrAttendanceMaintenancePK(HrAttendanceMaintenancePK hrAttendanceMaintenancePK) {
        this.hrAttendanceMaintenancePK = hrAttendanceMaintenancePK;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public char getAttenStatusFixed() {
        return attenStatusFixed;
    }

    public void setAttenStatusFixed(char attenStatusFixed) {
        this.attenStatusFixed = attenStatusFixed;
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
        hash += (hrAttendanceMaintenancePK != null ? hrAttendanceMaintenancePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAttendanceMaintenance)) {
            return false;
        }
        HrAttendanceMaintenance other = (HrAttendanceMaintenance) object;
        if ((this.hrAttendanceMaintenancePK == null && other.hrAttendanceMaintenancePK != null) || (this.hrAttendanceMaintenancePK != null && !this.hrAttendanceMaintenancePK.equals(other.hrAttendanceMaintenancePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrAttendanceMaintenance[hrAttendanceMaintenancePK=" + hrAttendanceMaintenancePK + "]";
    }

}
