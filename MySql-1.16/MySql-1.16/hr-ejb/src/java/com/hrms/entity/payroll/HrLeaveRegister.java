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
@Table(name = "hr_leave_register")
@NamedQueries({@NamedQuery(name = "HrLeaveRegister.findAll", query = "SELECT h FROM HrLeaveRegister h"),
@NamedQuery(name = "HrLeaveRegister.findByCompCode", query = "SELECT h FROM HrLeaveRegister h WHERE h.hrLeaveRegisterPK.compCode = :compCode"),
@NamedQuery(name = "HrLeaveRegister.findByEmpCode", query = "SELECT h FROM HrLeaveRegister h WHERE h.hrLeaveRegisterPK.empCode = :empCode"),
@NamedQuery(name = "HrLeaveRegister.findByLeavRegCode", query = "SELECT h FROM HrLeaveRegister h WHERE h.hrLeaveRegisterPK.leavRegCode = :leavRegCode"),
@NamedQuery(name = "HrLeaveRegister.findByFromDate", query = "SELECT h FROM HrLeaveRegister h WHERE h.fromDate = :fromDate"),
@NamedQuery(name = "HrLeaveRegister.findByToDate", query = "SELECT h FROM HrLeaveRegister h WHERE h.toDate = :toDate"),
@NamedQuery(name = "HrLeaveRegister.findByLeaveCode", query = "SELECT h FROM HrLeaveRegister h WHERE h.leaveCode = :leaveCode"),
@NamedQuery(name = "HrLeaveRegister.findByDaysTaken", query = "SELECT h FROM HrLeaveRegister h WHERE h.daysTaken = :daysTaken"),
@NamedQuery(name = "HrLeaveRegister.findByReasLeave", query = "SELECT h FROM HrLeaveRegister h WHERE h.reasLeave = :reasLeave"),
@NamedQuery(name = "HrLeaveRegister.findByPaid", query = "SELECT h FROM HrLeaveRegister h WHERE h.paid = :paid"),
@NamedQuery(name = "HrLeaveRegister.findByRemarks", query = "SELECT h FROM HrLeaveRegister h WHERE h.remarks = :remarks"),
@NamedQuery(name = "HrLeaveRegister.findByDepartRecom", query = "SELECT h FROM HrLeaveRegister h WHERE h.departRecom = :departRecom"),
@NamedQuery(name = "HrLeaveRegister.findBySanctAuth", query = "SELECT h FROM HrLeaveRegister h WHERE h.sanctAuth = :sanctAuth"),
@NamedQuery(name = "HrLeaveRegister.findByDefaultComp", query = "SELECT h FROM HrLeaveRegister h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrLeaveRegister.findByStatFlag", query = "SELECT h FROM HrLeaveRegister h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrLeaveRegister.findByStatUpFlag", query = "SELECT h FROM HrLeaveRegister h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrLeaveRegister.findByModDate", query = "SELECT h FROM HrLeaveRegister h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrLeaveRegister.findByAuthBy", query = "SELECT h FROM HrLeaveRegister h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrLeaveRegister.findByEnteredBy", query = "SELECT h FROM HrLeaveRegister h WHERE h.enteredBy = :enteredBy"),
@NamedQuery(name = "HrLeaveRegister.findTotalLeaveDays", query = "SELECT h FROM HrLeaveRegister h WHERE  h.hrLeaveRegisterPK.compCode = :compCode AND " +
    " h.leaveCode = :leaveCode AND h.hrLeaveRegisterPK.empCode = :empCode AND h.fromDate between :fromDate AND :toDate AND" +
    " h.fromDate between :fromDate AND :toDate"),
@NamedQuery(name = "HrLeaveRegister.findLeaveRegisterEntityByCompCode", query = "SELECT max(substring(h.hrLeaveRegisterPK.leavRegCode,4)) FROM HrLeaveRegister h WHERE h.hrLeaveRegisterPK.compCode = :compCode"),
@NamedQuery(name = "HrLeaveRegister.findByCompCodeAndEmpCode",query = "SELECT h FROM HrLeaveRegister h WHERE h.hrLeaveRegisterPK.compCode = :compCode AND h.hrLeaveRegisterPK.empCode = :empCode and h.hrLeaveRegisterPK.leavRegCode != :leaveRegCode "),
@NamedQuery(name = "HrLeaveRegister.findByEmpCodeDepartRecom", query = "SELECT h FROM HrLeaveRegister h WHERE h.hrLeaveRegisterPK.compCode = :compCode and h.hrLeaveRegisterPK.empCode = :empCode and h.departRecom = :departRecom and :date between h.fromDate and h.toDate")
})
public class HrLeaveRegister extends BaseEntity implements  Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrLeaveRegisterPK hrLeaveRegisterPK;
    @Basic(optional = false)
    @Column(name = "FROM_DATE")
    private String fromDate;
    @Basic(optional = false)
    @Column(name = "TO_DATE")
    private String toDate;
    @Basic(optional = false)
    @Column(name = "LEAVE_CODE")
    private String leaveCode;
    @Basic(optional = false)
    @Column(name = "DAYS_TAKEN")
    private int daysTaken;
    @Column(name = "REAS_LEAVE")
    private String reasLeave;
    @Column(name = "PAID")
    private Character paid;
    @Column(name = "REMARKS")
    private String remarks;
    @Basic(optional = false)
    @Column(name = "DEPART_RECOM")
    private char departRecom;
    @Basic(optional = false)
    @Column(name = "SANCT_AUTH")
    private String sanctAuth;
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

    public HrLeaveRegister() {
    }

    public HrLeaveRegister(HrLeaveRegisterPK hrLeaveRegisterPK) {
        this.hrLeaveRegisterPK = hrLeaveRegisterPK;
    }

    public HrLeaveRegister(HrLeaveRegisterPK hrLeaveRegisterPK, String fromDate, String toDate, String leaveCode, int daysTaken, char departRecom, String sanctAuth, String statFlag, String statUpFlag, Date modDate, String authBy, String enteredBy) {
        this.hrLeaveRegisterPK = hrLeaveRegisterPK;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.leaveCode = leaveCode;
        this.daysTaken = daysTaken;
        this.departRecom = departRecom;
        this.sanctAuth = sanctAuth;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrLeaveRegister(int compCode, long empCode, String leavRegCode) {
        this.hrLeaveRegisterPK = new HrLeaveRegisterPK(compCode, empCode, leavRegCode);
    }

    public HrLeaveRegisterPK getHrLeaveRegisterPK() {
        return hrLeaveRegisterPK;
    }

    public void setHrLeaveRegisterPK(HrLeaveRegisterPK hrLeaveRegisterPK) {
        this.hrLeaveRegisterPK = hrLeaveRegisterPK;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public int getDaysTaken() {
        return daysTaken;
    }

    public void setDaysTaken(int daysTaken) {
        this.daysTaken = daysTaken;
    }

    public String getReasLeave() {
        return reasLeave;
    }

    public void setReasLeave(String reasLeave) {
        this.reasLeave = reasLeave;
    }

    public Character getPaid() {
        return paid;
    }

    public void setPaid(Character paid) {
        this.paid = paid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public char getDepartRecom() {
        return departRecom;
    }

    public void setDepartRecom(char departRecom) {
        this.departRecom = departRecom;
    }

    public String getSanctAuth() {
        return sanctAuth;
    }

    public void setSanctAuth(String sanctAuth) {
        this.sanctAuth = sanctAuth;
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
        hash += (hrLeaveRegisterPK != null ? hrLeaveRegisterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLeaveRegister)) {
            return false;
        }
        HrLeaveRegister other = (HrLeaveRegister) object;
        if ((this.hrLeaveRegisterPK == null && other.hrLeaveRegisterPK != null) || (this.hrLeaveRegisterPK != null && !this.hrLeaveRegisterPK.equals(other.hrLeaveRegisterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrLeaveRegister[hrLeaveRegisterPK=" + hrLeaveRegisterPK + "]";
    }

}
