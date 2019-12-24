/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.payroll;


import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrMstStruct;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "hr_leave_master")
@NamedQueries({
    @NamedQuery(name = "HrLeaveMaster.findAll", query = "SELECT h FROM HrLeaveMaster h"),
    @NamedQuery(name = "HrLeaveMaster.findByCompCode", query = "SELECT h FROM HrLeaveMaster h WHERE h.hrLeaveMasterPK.compCode = :compCode"),
    @NamedQuery(name = "HrLeaveMaster.findByLeaveCode", query = "SELECT h FROM HrLeaveMaster h WHERE h.hrLeaveMasterPK.leaveCode = :leaveCode"),
    @NamedQuery(name = "HrLeaveMaster.findByNumDays", query = "SELECT h FROM HrLeaveMaster h WHERE h.numDays = :numDays"),
    @NamedQuery(name = "HrLeaveMaster.findByDateFrom", query = "SELECT h FROM HrLeaveMaster h WHERE h.hrLeaveMasterPK.dateFrom = :dateFrom"),
    @NamedQuery(name = "HrLeaveMaster.findByDateTo", query = "SELECT h FROM HrLeaveMaster h WHERE h.hrLeaveMasterPK.dateTo = :dateTo"),
    @NamedQuery(name = "HrLeaveMaster.findByApplicableDate", query = "SELECT h FROM HrLeaveMaster h WHERE h.applicableDate = :applicableDate"),
    @NamedQuery(name = "HrLeaveMaster.findByDescription", query = "SELECT h FROM HrLeaveMaster h WHERE h.description = :description"),
    @NamedQuery(name = "HrLeaveMaster.findByEncash", query = "SELECT h FROM HrLeaveMaster h WHERE h.encash = :encash"),
    @NamedQuery(name = "HrLeaveMaster.findByDefaultComp", query = "SELECT h FROM HrLeaveMaster h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrLeaveMaster.findByStatFlag", query = "SELECT h FROM HrLeaveMaster h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrLeaveMaster.findByStatUpFlag", query = "SELECT h FROM HrLeaveMaster h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrLeaveMaster.findByModDate", query = "SELECT h FROM HrLeaveMaster h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrLeaveMaster.findByAuthBy", query = "SELECT h FROM HrLeaveMaster h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrLeaveMaster.findByEnteredBy", query = "SELECT h FROM HrLeaveMaster h WHERE h.enteredBy = :enteredBy"),
    @NamedQuery(name = "HrLeaveMaster.findByDateFromAndDateTo", query = "SELECT h FROM HrLeaveMaster h WHERE h.hrLeaveMasterPK.compCode = :compCode AND h.hrLeaveMasterPK.dateFrom = :dateFrom AND h.hrLeaveMasterPK.dateTo = :dateTo"),
    @NamedQuery(name = "HrLeaveMaster.findNumOfLeaveDaysByLeaveCode", query = "SELECT h FROM HrLeaveMaster h where h.hrLeaveMasterPK.compCode = :compCode AND" +
    " h.hrLeaveMasterPK.leaveCode = :leaveCode AND h.hrLeaveMasterPK.dateFrom = :dateFrom AND h.hrLeaveMasterPK.dateTo = :dateTo")

})
public class HrLeaveMaster extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrLeaveMasterPK hrLeaveMasterPK;

    @Basic(optional = false)
    @Column(name = "NUM_DAYS")
    private int numDays;

    @Basic(optional = false)
    @Column(name = "APPLICABLE_DATE")
    private String applicableDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @Basic(optional = false)
    @Column(name = "ENCASH")
    private char encash;

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
    @Column(name = "LEAVE_NATURE")
    private String leaveNature;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "LEAVE_NATURE",insertable = false, updatable = false, referencedColumnName = "STRUCT_CODE", nullable=false),
        @JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false)
    })
    private HrMstStruct hrMstStruct;

    public HrLeaveMaster() {
    }

    public HrLeaveMaster(HrLeaveMasterPK hrLeaveMasterPK) {
        this.hrLeaveMasterPK = hrLeaveMasterPK;
    }

    public HrLeaveMaster(HrLeaveMasterPK hrLeaveMasterPK, int numDays, String applicableDate, char encash, String statFlag, String statUpFlag, Date modDate, String authBy, String enteredBy) {
        this.hrLeaveMasterPK = hrLeaveMasterPK;
        this.numDays = numDays;
        this.applicableDate = applicableDate;
        this.encash = encash;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrLeaveMaster(int compCode, String leaveCode, String dateFrom, String dateTo) {
        this.hrLeaveMasterPK = new HrLeaveMasterPK(compCode, leaveCode, dateFrom, dateTo);
    }

    public String getLeaveNature() {
        return leaveNature;
    }

    public void setLeaveNature(String leaveNature) {
        this.leaveNature = leaveNature;
    }

    public HrLeaveMasterPK getHrLeaveMasterPK() {
        return hrLeaveMasterPK;
    }

    public void setHrLeaveMasterPK(HrLeaveMasterPK hrLeaveMasterPK) {
        this.hrLeaveMasterPK = hrLeaveMasterPK;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char getEncash() {
        return encash;
    }

    public void setEncash(char encash) {
        this.encash = encash;
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

    public HrMstStruct getHrMstStruct() {
        return hrMstStruct;
    }

    public void setHrMstStruct(HrMstStruct hrMstStruct) {
        this.hrMstStruct = hrMstStruct;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrLeaveMasterPK != null ? hrLeaveMasterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLeaveMaster)) {
            return false;
        }
        HrLeaveMaster other = (HrLeaveMaster) object;
        if ((this.hrLeaveMasterPK == null && other.hrLeaveMasterPK != null) || (this.hrLeaveMasterPK != null && !this.hrLeaveMasterPK.equals(other.hrLeaveMasterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrLeaveMaster[hrLeaveMasterPK=" + hrLeaveMasterPK + "]";
    }

}
