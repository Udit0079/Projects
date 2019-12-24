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
@Table(name = "hr_interview_dt")
@NamedQueries({@NamedQuery(name = "HrInterviewDt.findAll", query = "SELECT h FROM HrInterviewDt h"),
@NamedQuery(name = "HrInterviewDt.findByCompCode", query = "SELECT h FROM HrInterviewDt h WHERE h.hrInterviewDtPK.compCode = :compCode"),
@NamedQuery(name = "HrInterviewDt.findByIntCode", query = "SELECT h FROM HrInterviewDt h WHERE h.hrInterviewDtPK.intCode = :intCode"),
@NamedQuery(name = "HrInterviewDt.findByAdvtCode", query = "SELECT h FROM HrInterviewDt h WHERE h.hrInterviewDtPK.advtCode = :advtCode"),
@NamedQuery(name = "HrInterviewDt.findByJobCode", query = "SELECT h FROM HrInterviewDt h WHERE h.hrInterviewDtPK.jobCode = :jobCode"),
@NamedQuery(name = "HrInterviewDt.findByCandSrno", query = "SELECT h FROM HrInterviewDt h WHERE h.hrInterviewDtPK.candSrno = :candSrno"),
@NamedQuery(name = "HrInterviewDt.findByIntResult", query = "SELECT h FROM HrInterviewDt h WHERE h.intResult = :intResult"),
@NamedQuery(name = "HrInterviewDt.findByTimeIn", query = "SELECT h FROM HrInterviewDt h WHERE h.timeIn = :timeIn"),
@NamedQuery(name = "HrInterviewDt.findByCancel", query = "SELECT h FROM HrInterviewDt h WHERE h.cancel = :cancel"),
@NamedQuery(name = "HrInterviewDt.findByAdviceCancel", query = "SELECT h FROM HrInterviewDt h WHERE h.adviceCancel = :adviceCancel"),
@NamedQuery(name = "HrInterviewDt.findByExpectJoin", query = "SELECT h FROM HrInterviewDt h WHERE h.expectJoin = :expectJoin"),
@NamedQuery(name = "HrInterviewDt.findByExtension", query = "SELECT h FROM HrInterviewDt h WHERE h.extension = :extension"),
@NamedQuery(name = "HrInterviewDt.findByIntRemarks", query = "SELECT h FROM HrInterviewDt h WHERE h.intRemarks = :intRemarks"),
@NamedQuery(name = "HrInterviewDt.findByDefaultCompCode", query = "SELECT h FROM HrInterviewDt h WHERE h.defaultCompCode = :defaultCompCode"),
@NamedQuery(name = "HrInterviewDt.findByStatFlag", query = "SELECT h FROM HrInterviewDt h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrInterviewDt.findByStatUpFlag", query = "SELECT h FROM HrInterviewDt h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrInterviewDt.findByModDate", query = "SELECT h FROM HrInterviewDt h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrInterviewDt.findByAuthBy", query = "SELECT h FROM HrInterviewDt h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrInterviewDt.extensionSave", query = "UPDATE HrInterviewDt s SET s.adviceCancel=:adviceCancel,s.expectJoin=:expectJoin,s.extension=:extension,s.statFlag=:stateFlag,s.statUpFlag=:statupFlag,s.modDate=:modDt,s.authBy=:authBy,s.enteredBy=:enterBy  WHERE s.hrInterviewDtPK.compCode=:compCode and  s.hrInterviewDtPK.intCode = :intcode  and  s.hrInterviewDtPK.candSrno = :candsrno"),
@NamedQuery(name = "HrInterviewDt.extensionUpdate", query = "UPDATE HrInterviewDt s SET s.cancel=:cancel,s.adviceCancel=:adviceCancel,s.expectJoin=:expectJoin,s.extension=:extension,s.statFlag=:stateFlag,s.statUpFlag=:statupFlag,s.modDate=:modDt,s.authBy=:authBy,s.enteredBy=:enterBy  WHERE s.hrInterviewDtPK.compCode=:compCode and  s.hrInterviewDtPK.intCode = :intcode  and  s.hrInterviewDtPK.candSrno = :candsrno"),
@NamedQuery(name = "HrInterviewDt.findByEnteredBy", query = "SELECT h FROM HrInterviewDt h WHERE h.enteredBy = :enteredBy")})
public class HrInterviewDt extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrInterviewDtPK hrInterviewDtPK;
    @Basic(optional = false)
    @Column(name = "INT_RESULT")
    private char intResult;
    @Column(name = "TIME_IN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeIn;
    @Column(name = "CANCEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancel;
    @Column(name = "ADVICE_CANCEL")
    private Character adviceCancel;
    @Column(name = "EXPECT_JOIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expectJoin;
    @Column(name = "EXTENSION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date extension;
    @Column(name = "INT_REMARKS")
    private String intRemarks;
    @Column(name = "DEFAULT_COMP_CODE")
    private Integer defaultCompCode;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "CAND_SRNO", referencedColumnName = "CAND_ID", insertable = false, updatable = false), @JoinColumn(name = "ADVT_CODE", referencedColumnName = "ADVT_CODE", insertable = false, updatable = false), @JoinColumn(name = "JOB_CODE", referencedColumnName = "JOB_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrDatabank hrDatabank;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "INT_CODE", referencedColumnName = "INT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrInterviewHd hrInterviewHd;

    public HrInterviewDt() {
    }

    public HrInterviewDt(HrInterviewDtPK hrInterviewDtPK) {
        this.hrInterviewDtPK = hrInterviewDtPK;
    }

    public HrInterviewDt(HrInterviewDtPK hrInterviewDtPK, char intResult) {
        this.hrInterviewDtPK = hrInterviewDtPK;
        this.intResult = intResult;
    }

    public HrInterviewDt(int compCode, String intCode, String advtCode, String jobCode, String candSrno) {
        this.hrInterviewDtPK = new HrInterviewDtPK(compCode, intCode, advtCode, jobCode, candSrno);
    }

    public HrInterviewDtPK getHrInterviewDtPK() {
        return hrInterviewDtPK;
    }

    public void setHrInterviewDtPK(HrInterviewDtPK hrInterviewDtPK) {
        this.hrInterviewDtPK = hrInterviewDtPK;
    }

    public char getIntResult() {
        return intResult;
    }

    public void setIntResult(char intResult) {
        this.intResult = intResult;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getCancel() {
        return cancel;
    }

    public void setCancel(Date cancel) {
        this.cancel = cancel;
    }

    public Character getAdviceCancel() {
        return adviceCancel;
    }

    public void setAdviceCancel(Character adviceCancel) {
        this.adviceCancel = adviceCancel;
    }

    public Date getExpectJoin() {
        return expectJoin;
    }

    public void setExpectJoin(Date expectJoin) {
        this.expectJoin = expectJoin;
    }

    public Date getExtension() {
        return extension;
    }

    public void setExtension(Date extension) {
        this.extension = extension;
    }

    public String getIntRemarks() {
        return intRemarks;
    }

    public void setIntRemarks(String intRemarks) {
        this.intRemarks = intRemarks;
    }

    public Integer getDefaultCompCode() {
        return defaultCompCode;
    }

    public void setDefaultCompCode(Integer defaultCompCode) {
        this.defaultCompCode = defaultCompCode;
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

    public HrDatabank getHrDatabank() {
        return hrDatabank;
    }

    public void setHrDatabank(HrDatabank hrDatabank) {
        this.hrDatabank = hrDatabank;
    }

    public HrInterviewHd getHrInterviewHd() {
        return hrInterviewHd;
    }

    public void setHrInterviewHd(HrInterviewHd hrInterviewHd) {
        this.hrInterviewHd = hrInterviewHd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrInterviewDtPK != null ? hrInterviewDtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrInterviewDt)) {
            return false;
        }
        HrInterviewDt other = (HrInterviewDt) object;
        if ((this.hrInterviewDtPK == null && other.hrInterviewDtPK != null) || (this.hrInterviewDtPK != null && !this.hrInterviewDtPK.equals(other.hrInterviewDtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hrPayroll.HrInterviewDt[hrInterviewDtPK=" + hrInterviewDtPK + "]";
    }

}
