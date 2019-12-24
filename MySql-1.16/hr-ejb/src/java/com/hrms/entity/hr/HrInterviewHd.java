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
@Table(name = "hr_interview_hd")
@NamedQueries({@NamedQuery(name = "HrInterviewHd.findAll", query = "SELECT h FROM HrInterviewHd h"),
    @NamedQuery(name = "HrInterviewHd.findByCompCode", query = "SELECT h FROM HrInterviewHd h WHERE h.hrInterviewHdPK.compCode = :compCode"), 
    @NamedQuery(name = "HrInterviewHd.findByIntCode", query = "SELECT h FROM HrInterviewHd h WHERE h.hrInterviewHdPK.intCode = :intCode"), 
    @NamedQuery(name = "HrInterviewHd.findByDesgCode", query = "SELECT h FROM HrInterviewHd h WHERE h.desgCode = :desgCode"),
    @NamedQuery(name = "HrInterviewHd.findByIntDate", query = "SELECT h FROM HrInterviewHd h WHERE h.intDate = :intDate"), 
    @NamedQuery(name = "HrInterviewHd.findByIntTime", query = "SELECT h FROM HrInterviewHd h WHERE h.intTime = :intTime"),
    @NamedQuery(name = "HrInterviewHd.findByTimePerCand", query = "SELECT h FROM HrInterviewHd h WHERE h.timePerCand = :timePerCand"), 
    @NamedQuery(name = "HrInterviewHd.findByTimeBreak", query = "SELECT h FROM HrInterviewHd h WHERE h.timeBreak = :timeBreak"), 
    @NamedQuery(name = "HrInterviewHd.findByFareCatg", query = "SELECT h FROM HrInterviewHd h WHERE h.fareCatg = :fareCatg"), 
    @NamedQuery(name = "HrInterviewHd.findByIntVenue", query = "SELECT h FROM HrInterviewHd h WHERE h.intVenue = :intVenue"), 
    @NamedQuery(name = "HrInterviewHd.findByEmpCode", query = "SELECT h FROM HrInterviewHd h WHERE h.empCode = :empCode"), 
    @NamedQuery(name = "HrInterviewHd.findByEmpCode2", query = "SELECT h FROM HrInterviewHd h WHERE h.empCode2 = :empCode2"), 
    @NamedQuery(name = "HrInterviewHd.findByEmp1Desg", query = "SELECT h FROM HrInterviewHd h WHERE h.emp1Desg = :emp1Desg"), 
    @NamedQuery(name = "HrInterviewHd.findByEmp2Desg", query = "SELECT h FROM HrInterviewHd h WHERE h.emp2Desg = :emp2Desg"), 
    @NamedQuery(name = "HrInterviewHd.findByRecType", query = "SELECT h FROM HrInterviewHd h WHERE h.recType = :recType"), 
    @NamedQuery(name = "HrInterviewHd.findByDefaultCompcode", query = "SELECT h FROM HrInterviewHd h WHERE h.defaultCompcode = :defaultCompcode"), 
    @NamedQuery(name = "HrInterviewHd.findByStatFlag", query = "SELECT h FROM HrInterviewHd h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrInterviewHd.findByStatUpFlag", query = "SELECT h FROM HrInterviewHd h WHERE h.statUpFlag = :statUpFlag"), 
    @NamedQuery(name = "HrInterviewHd.findByModDate", query = "SELECT h FROM HrInterviewHd h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrInterviewHd.findByAuthBy", query = "SELECT h FROM HrInterviewHd h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrInterviewHd.findMaxIntCodeByCompCode", query = "SELECT max(substring(h.hrInterviewHdPK.intCode,4,10)) FROM HrInterviewHd h WHERE h.hrInterviewHdPK.compCode = :compCode"), 
    @NamedQuery(name = "HrInterviewHd.findByEnteredBy", query = "SELECT h FROM HrInterviewHd h WHERE h.enteredBy = :enteredBy")})
public class HrInterviewHd extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrInterviewHdPK hrInterviewHdPK;
    @Basic(optional = false)
    @Column(name = "DESG_CODE")
    private String desgCode;
    @Basic(optional = false)
    @Column(name = "INT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date intDate;
    @Basic(optional = false)
    @Column(name = "INT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date intTime;
    @Column(name = "TIME_PER_CAND")
    private Integer timePerCand;
    @Column(name = "TIME_BREAK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeBreak;
    @Column(name = "FARE_CATG")
    private String fareCatg;
    @Basic(optional = false)
    @Column(name = "INT_VENUE")
    private String intVenue;
    @Column(name = "EMP_CODE")
    private String empCode;
    @Column(name = "EMP_CODE2")
    private String empCode2;
    @Column(name = "EMP1_DESG")
    private String emp1Desg;
    @Column(name = "EMP2_DESG")
    private String emp2Desg;
    @Column(name = "REC_TYPE")
    private String recType;
    @Column(name = "DEFAULT_COMPCODE")
    private Integer defaultCompcode;
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

    public HrInterviewHd() {
    }

    public HrInterviewHd(HrInterviewHdPK hrInterviewHdPK) {
        this.hrInterviewHdPK = hrInterviewHdPK;
    }

    public HrInterviewHd(HrInterviewHdPK hrInterviewHdPK, String desgCode, Date intDate, Date intTime, String intVenue) {
        this.hrInterviewHdPK = hrInterviewHdPK;
        this.desgCode = desgCode;
        this.intDate = intDate;
        this.intTime = intTime;
        this.intVenue = intVenue;
    }

    public HrInterviewHd(int compCode, String intCode) {
        this.hrInterviewHdPK = new HrInterviewHdPK(compCode, intCode);
    }

    public HrInterviewHdPK getHrInterviewHdPK() {
        return hrInterviewHdPK;
    }

    public void setHrInterviewHdPK(HrInterviewHdPK hrInterviewHdPK) {
        this.hrInterviewHdPK = hrInterviewHdPK;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public Date getIntDate() {
        return intDate;
    }

    public void setIntDate(Date intDate) {
        this.intDate = intDate;
    }

    public Date getIntTime() {
        return intTime;
    }

    public void setIntTime(Date intTime) {
        this.intTime = intTime;
    }

    public Integer getTimePerCand() {
        return timePerCand;
    }

    public void setTimePerCand(Integer timePerCand) {
        this.timePerCand = timePerCand;
    }

    public Date getTimeBreak() {
        return timeBreak;
    }

    public void setTimeBreak(Date timeBreak) {
        this.timeBreak = timeBreak;
    }

    public String getFareCatg() {
        return fareCatg;
    }

    public void setFareCatg(String fareCatg) {
        this.fareCatg = fareCatg;
    }

    public String getIntVenue() {
        return intVenue;
    }

    public void setIntVenue(String intVenue) {
        this.intVenue = intVenue;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpCode2() {
        return empCode2;
    }

    public void setEmpCode2(String empCode2) {
        this.empCode2 = empCode2;
    }

    public String getEmp1Desg() {
        return emp1Desg;
    }

    public void setEmp1Desg(String emp1Desg) {
        this.emp1Desg = emp1Desg;
    }

    public String getEmp2Desg() {
        return emp2Desg;
    }

    public void setEmp2Desg(String emp2Desg) {
        this.emp2Desg = emp2Desg;
    }

    public String getRecType() {
        return recType;
    }

    public void setRecType(String recType) {
        this.recType = recType;
    }

    public Integer getDefaultCompcode() {
        return defaultCompcode;
    }

    public void setDefaultCompcode(Integer defaultCompcode) {
        this.defaultCompcode = defaultCompcode;
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
        hash += (hrInterviewHdPK != null ? hrInterviewHdPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrInterviewHd)) {
            return false;
        }
        HrInterviewHd other = (HrInterviewHd) object;
        if ((this.hrInterviewHdPK == null && other.hrInterviewHdPK != null) || (this.hrInterviewHdPK != null && !this.hrInterviewHdPK.equals(other.hrInterviewHdPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hrPayroll.HrInterviewHd[hrInterviewHdPK=" + hrInterviewHdPK + "]";
    }

}
