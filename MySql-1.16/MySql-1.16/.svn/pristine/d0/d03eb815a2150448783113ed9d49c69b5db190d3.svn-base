/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@Table(name = "hr_interview_dt_sal")
@NamedQueries({@NamedQuery(name = "HrInterviewDtSal.findAll", query = "SELECT h FROM HrInterviewDtSal h"), @NamedQuery(name = "HrInterviewDtSal.findByCompCode", query = "SELECT h FROM HrInterviewDtSal h WHERE h.hrInterviewDtSalPK.compCode = :compCode"), @NamedQuery(name = "HrInterviewDtSal.findByIntCode", query = "SELECT h FROM HrInterviewDtSal h WHERE h.hrInterviewDtSalPK.intCode = :intCode"), @NamedQuery(name = "HrInterviewDtSal.findByAdvtCode", query = "SELECT h FROM HrInterviewDtSal h WHERE h.hrInterviewDtSalPK.advtCode = :advtCode"), @NamedQuery(name = "HrInterviewDtSal.findByJobCode", query = "SELECT h FROM HrInterviewDtSal h WHERE h.hrInterviewDtSalPK.jobCode = :jobCode"), @NamedQuery(name = "HrInterviewDtSal.findByCandSrno", query = "SELECT h FROM HrInterviewDtSal h WHERE h.hrInterviewDtSalPK.candSrno = :candSrno"), @NamedQuery(name = "HrInterviewDtSal.findBySalCompCode", query = "SELECT h FROM HrInterviewDtSal h WHERE h.salCompCode = :salCompCode"), @NamedQuery(name = "HrInterviewDtSal.findByCompOff", query = "SELECT h FROM HrInterviewDtSal h WHERE h.compOff = :compOff"), @NamedQuery(name = "HrInterviewDtSal.findByCompNegoit", query = "SELECT h FROM HrInterviewDtSal h WHERE h.compNegoit = :compNegoit"), @NamedQuery(name = "HrInterviewDtSal.findByCompExpect", query = "SELECT h FROM HrInterviewDtSal h WHERE h.compExpect = :compExpect"), @NamedQuery(name = "HrInterviewDtSal.findByDefaultCompCode", query = "SELECT h FROM HrInterviewDtSal h WHERE h.defaultCompCode = :defaultCompCode"), @NamedQuery(name = "HrInterviewDtSal.findByStatFlag", query = "SELECT h FROM HrInterviewDtSal h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrInterviewDtSal.findByStatUpFlag", query = "SELECT h FROM HrInterviewDtSal h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrInterviewDtSal.findByModDate", query = "SELECT h FROM HrInterviewDtSal h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrInterviewDtSal.findByAuthBy", query = "SELECT h FROM HrInterviewDtSal h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrInterviewDtSal.findByEnteredBy", query = "SELECT h FROM HrInterviewDtSal h WHERE h.enteredBy = :enteredBy")})
public class HrInterviewDtSal extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrInterviewDtSalPK hrInterviewDtSalPK;
    @Column(name = "SAL_COMP_CODE")
    private String salCompCode;
    @Column(name = "COMP_OFF")
    private Double compOff;
    @Column(name = "COMP_NEGOIT")
    private Double compNegoit;
    @Column(name = "COMP_EXPECT")
    private Double compExpect;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "INT_CODE", referencedColumnName = "INT_CODE", insertable = false, updatable = false), @JoinColumn(name = "ADVT_CODE", referencedColumnName = "ADVT_CODE", insertable = false, updatable = false), @JoinColumn(name = "JOB_CODE", referencedColumnName = "JOB_CODE", insertable = false, updatable = false), @JoinColumn(name = "CAND_SRNO", referencedColumnName = "CAND_SRNO", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private HrInterviewDt hrInterviewDt;

    public HrInterviewDtSal() {
    }

    public HrInterviewDtSal(HrInterviewDtSalPK hrInterviewDtSalPK) {
        this.hrInterviewDtSalPK = hrInterviewDtSalPK;
    }

    public HrInterviewDtSal(int compCode, String intCode, String advtCode, String jobCode, String candSrno) {
        this.hrInterviewDtSalPK = new HrInterviewDtSalPK(compCode, intCode, advtCode, jobCode, candSrno);
    }

    public HrInterviewDtSalPK getHrInterviewDtSalPK() {
        return hrInterviewDtSalPK;
    }

    public void setHrInterviewDtSalPK(HrInterviewDtSalPK hrInterviewDtSalPK) {
        this.hrInterviewDtSalPK = hrInterviewDtSalPK;
    }

    public String getSalCompCode() {
        return salCompCode;
    }

    public void setSalCompCode(String salCompCode) {
        this.salCompCode = salCompCode;
    }

    public Double getCompOff() {
        return compOff;
    }

    public void setCompOff(Double compOff) {
        this.compOff = compOff;
    }

    public Double getCompNegoit() {
        return compNegoit;
    }

    public void setCompNegoit(Double compNegoit) {
        this.compNegoit = compNegoit;
    }

    public Double getCompExpect() {
        return compExpect;
    }

    public void setCompExpect(Double compExpect) {
        this.compExpect = compExpect;
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

    public HrInterviewDt getHrInterviewDt() {
        return hrInterviewDt;
    }

    public void setHrInterviewDt(HrInterviewDt hrInterviewDt) {
        this.hrInterviewDt = hrInterviewDt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrInterviewDtSalPK != null ? hrInterviewDtSalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrInterviewDtSal)) {
            return false;
        }
        HrInterviewDtSal other = (HrInterviewDtSal) object;
        if ((this.hrInterviewDtSalPK == null && other.hrInterviewDtSalPK != null) || (this.hrInterviewDtSalPK != null && !this.hrInterviewDtSalPK.equals(other.hrInterviewDtSalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrInterviewDtSal[hrInterviewDtSalPK=" + hrInterviewDtSalPK + "]";
    }

}
