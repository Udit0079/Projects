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
@Table(name = "hr_attendance_details")
@NamedQueries({
    @NamedQuery(name = "HrAttendanceDetails.findAll", query = "SELECT h FROM HrAttendanceDetails h"),
    @NamedQuery(name = "HrAttendanceDetails.findByCompCode", query = "SELECT h FROM HrAttendanceDetails h WHERE h.hrAttendanceDetailsPK.compCode = :compCode"),
    @NamedQuery(name = "HrAttendanceDetails.findByEmpCode", query = "SELECT h FROM HrAttendanceDetails h WHERE h.hrAttendanceDetailsPK.empCode = :empCode"),
    @NamedQuery(name = "HrAttendanceDetails.findByAttenMonth", query = "SELECT h FROM HrAttendanceDetails h WHERE h.hrAttendanceDetailsPK.attenMonth = :attenMonth"),
    @NamedQuery(name = "HrAttendanceDetails.findByAttenYear", query = "SELECT h FROM HrAttendanceDetails h WHERE h.hrAttendanceDetailsPK.attenYear = :attenYear"),
    @NamedQuery(name = "HrAttendanceDetails.findByProcessDateFrom", query = "SELECT h FROM HrAttendanceDetails h WHERE h.processDateFrom = :processDateFrom"),
    @NamedQuery(name = "HrAttendanceDetails.findByProcessDateTo", query = "SELECT h FROM HrAttendanceDetails h WHERE h.processDateTo = :processDateTo"),
    @NamedQuery(name = "HrAttendanceDetails.findByWorkingDays", query = "SELECT h FROM HrAttendanceDetails h WHERE h.workingDays = :workingDays"),
    @NamedQuery(name = "HrAttendanceDetails.findByPresentDays", query = "SELECT h FROM HrAttendanceDetails h WHERE h.presentDays = :presentDays"),
    @NamedQuery(name = "HrAttendanceDetails.findByAbsentDays", query = "SELECT h FROM HrAttendanceDetails h WHERE h.absentDays = :absentDays"),
    @NamedQuery(name = "HrAttendanceDetails.findByOverTimePeriod", query = "SELECT h FROM HrAttendanceDetails h WHERE h.overTimePeriod = :overTimePeriod"),
    @NamedQuery(name = "HrAttendanceDetails.findByOverTimeUnit", query = "SELECT h FROM HrAttendanceDetails h WHERE h.overTimeUnit = :overTimeUnit"),
    @NamedQuery(name = "HrAttendanceDetails.findByPostFlag", query = "SELECT h FROM HrAttendanceDetails h WHERE h.postFlag = :postFlag"),
    @NamedQuery(name = "HrAttendanceDetails.findByDefaultComp", query = "SELECT h FROM HrAttendanceDetails h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrAttendanceDetails.findByStatFlag", query = "SELECT h FROM HrAttendanceDetails h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrAttendanceDetails.findByStatUpFlag", query = "SELECT h FROM HrAttendanceDetails h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrAttendanceDetails.findByPaidLeave", query = "SELECT h FROM HrAttendanceDetails h WHERE h.paidLeave = :paidLeave"),
    @NamedQuery(name = "HrAttendanceDetails.findByModDate", query = "SELECT h FROM HrAttendanceDetails h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrAttendanceDetails.findByAuthBy", query = "SELECT h FROM HrAttendanceDetails h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrAttendanceDetails.findByEnteredBy", query = "SELECT h FROM HrAttendanceDetails h WHERE h.enteredBy = :enteredBy"),
    @NamedQuery(name = "HrAttendanceDetails.findByDeductDays", query = "SELECT h FROM HrAttendanceDetails h WHERE h.deductDays = :deductDays"),
    @NamedQuery(name = "HrAttendanceDetails.findByCompCodeAndEmpCode", query = "Select h from HrAttendanceDetails h where h.hrAttendanceDetailsPK.compCode = :compCode and h.hrAttendanceDetailsPK.empCode = :empCode and h.hrAttendanceDetailsPK.attenMonth = :attenMonth and h.hrAttendanceDetailsPK.attenYear = :attenYear and h.postFlag = :postFlag"),
    @NamedQuery(name = "HrAttendanceDetails.findPostedAttendanceDetailsOfMonth", query = "Select h from HrAttendanceDetails h where h.hrAttendanceDetailsPK.compCode = :compCode and h.hrAttendanceDetailsPK.empCode = :empCode and h.hrAttendanceDetailsPK.attenMonth = :attenMonth and h.hrAttendanceDetailsPK.attenYear = :attenYear and h.postFlag = :postFlag"),
    @NamedQuery(name = "HrAttendanceDetails.findAttenDetailsWithPostFlag", query = "Select h from HrAttendanceDetails h where h.hrAttendanceDetailsPK.compCode = :compCode and h.hrAttendanceDetailsPK.empCode = :empCode and"
            + " h.processDateFrom >= :pageDateFrom and h.processDateFrom <= :pageDateTo and h.processDateTo >= :pageDateFrom and h.processDateTo <= :pageDateTo  and h.postFlag = :postFlag")
})
public class HrAttendanceDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrAttendanceDetailsPK hrAttendanceDetailsPK;
    @Basic(optional = false)
    @Column(name = "PROCESS_DATE_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDateFrom;
    @Basic(optional = false)
    @Column(name = "PROCESS_DATE_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDateTo;
    @Basic(optional = false)
    @Column(name = "WORKING_DAYS")
    private int workingDays;
    @Basic(optional = false)
    @Column(name = "PRESENT_DAYS")
    private int presentDays;
    @Basic(optional = false)
    @Column(name = "ABSENT_DAYS")
    private int absentDays;
    @Basic(optional = false)
    @Column(name = "OVER_TIME_PERIOD")
    private long overTimePeriod;
    @Basic(optional = false)
    @Column(name = "OVER_TIME_UNIT")
    private String overTimeUnit;
    @Basic(optional = false)
    @Column(name = "POST_FLAG")
    private char postFlag;
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
    @Basic(optional = false)
    @Column(name = "PAID_LEAVE")
    private int paidLeave;
    @Column(name = "DEDUCT_DAYS")
    private double deductDays;
    @JoinColumns({
        @JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false),
        @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrAttendanceDetails() {
    }

    public HrAttendanceDetails(HrAttendanceDetailsPK hrAttendanceDetailsPK) {
        this.hrAttendanceDetailsPK = hrAttendanceDetailsPK;
    }

    public HrAttendanceDetails(HrAttendanceDetailsPK hrAttendanceDetailsPK, Date processDateFrom, Date processDateTo,
        int workingDays, int presentDays, int absentDays, long overTimePeriod, String overTimeUnit,
        char postFlag, String statFlag, String statUpFlag, Date modDate, String authBy,
        String enteredBy, int paidLeave, int deductDays) {
        this.hrAttendanceDetailsPK = hrAttendanceDetailsPK;
        this.processDateFrom = processDateFrom;
        this.processDateTo = processDateTo;
        this.workingDays = workingDays;
        this.presentDays = presentDays;
        this.absentDays = absentDays;
        this.overTimePeriod = overTimePeriod;
        this.overTimeUnit = overTimeUnit;
        this.postFlag = postFlag;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
        this.paidLeave = paidLeave;
        this.deductDays = deductDays;
    }

    public HrAttendanceDetails(int compCode, long empCode, String attenMonth, int attenYear) {
        this.hrAttendanceDetailsPK = new HrAttendanceDetailsPK(compCode, empCode, attenMonth, attenYear);
    }

    public HrAttendanceDetailsPK getHrAttendanceDetailsPK() {
        return hrAttendanceDetailsPK;
    }

    public void setHrAttendanceDetailsPK(HrAttendanceDetailsPK hrAttendanceDetailsPK) {
        this.hrAttendanceDetailsPK = hrAttendanceDetailsPK;
    }

    public Date getProcessDateFrom() {
        return processDateFrom;
    }

    public void setProcessDateFrom(Date processDateFrom) {
        this.processDateFrom = processDateFrom;
    }

    public Date getProcessDateTo() {
        return processDateTo;
    }

    public void setProcessDateTo(Date processDateTo) {
        this.processDateTo = processDateTo;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }

    public int getPresentDays() {
        return presentDays;
    }

    public void setPresentDays(int presentDays) {
        this.presentDays = presentDays;
    }

    public int getAbsentDays() {
        return absentDays;
    }

    public void setAbsentDays(int absentDays) {
        this.absentDays = absentDays;
    }

    public long getOverTimePeriod() {
        return overTimePeriod;
    }

    public void setOverTimePeriod(long overTimePeriod) {
        this.overTimePeriod = overTimePeriod;
    }

    public String getOverTimeUnit() {
        return overTimeUnit;
    }

    public void setOverTimeUnit(String overTimeUnit) {
        this.overTimeUnit = overTimeUnit;
    }

    public char getPostFlag() {
        return postFlag;
    }

    public void setPostFlag(char postFlag) {
        this.postFlag = postFlag;
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

    public int getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(int paidLeave) {
        this.paidLeave = paidLeave;
    }

    public double getDeductDays() {
        return deductDays;
    }

    public void setDeductDays(double deductDays) {
        this.deductDays = deductDays;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrAttendanceDetailsPK != null ? hrAttendanceDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAttendanceDetails)) {
            return false;
        }
        HrAttendanceDetails other = (HrAttendanceDetails) object;
        if ((this.hrAttendanceDetailsPK == null && other.hrAttendanceDetailsPK != null) || (this.hrAttendanceDetailsPK != null && !this.hrAttendanceDetailsPK.equals(other.hrAttendanceDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrAttendanceDetails[hrAttendanceDetailsPK=" + hrAttendanceDetailsPK + "]";
    }
}
