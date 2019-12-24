/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrMstStruct;
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
@Table(name = "hr_apprisal_performance_dt")
@NamedQueries({@NamedQuery(name = "HrApprisalPerformanceDt.findAll", query = "SELECT h FROM HrApprisalPerformanceDt h"), @NamedQuery(name = "HrApprisalPerformanceDt.findByCompCode", query = "SELECT h FROM HrApprisalPerformanceDt h WHERE h.hrApprisalPerformanceDtPK.compCode = :compCode"), @NamedQuery(name = "HrApprisalPerformanceDt.findByEmpCode", query = "SELECT h FROM HrApprisalPerformanceDt h WHERE h.hrApprisalPerformanceDtPK.empCode = :empCode"), @NamedQuery(name = "HrApprisalPerformanceDt.findByPerfCode", query = "SELECT h FROM HrApprisalPerformanceDt h WHERE h.hrApprisalPerformanceDtPK.perfCode = :perfCode"), @NamedQuery(name = "HrApprisalPerformanceDt.findByRatingCode", query = "SELECT h FROM HrApprisalPerformanceDt h WHERE h.hrApprisalPerformanceDtPK.ratingCode = :ratingCode"), @NamedQuery(name = "HrApprisalPerformanceDt.findByAppraisalDt", query = "SELECT h FROM HrApprisalPerformanceDt h WHERE h.hrApprisalPerformanceDtPK.appraisalDt = :appraisalDt"), @NamedQuery(name = "HrApprisalPerformanceDt.findByDefaultComp", query = "SELECT h FROM HrApprisalPerformanceDt h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrApprisalPerformanceDt.findByStatFlag", query = "SELECT h FROM HrApprisalPerformanceDt h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrApprisalPerformanceDt.findByStatUpFlag", query = "SELECT h FROM HrApprisalPerformanceDt h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrApprisalPerformanceDt.findByModDate", query = "SELECT h FROM HrApprisalPerformanceDt h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrApprisalPerformanceDt.findByAuthBy", query = "SELECT h FROM HrApprisalPerformanceDt h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrApprisalPerformanceDt.findByEnteredBy", query = "SELECT h FROM HrApprisalPerformanceDt h WHERE h.enteredBy = :enteredBy")})
public class HrApprisalPerformanceDt extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrApprisalPerformanceDtPK hrApprisalPerformanceDtPK;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "APPRAISAL_DT", referencedColumnName = "APPRAISAL_DT", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrApprisalDetails hrApprisalDetails;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "RATING_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "PERF_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct1;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrApprisalPerformanceDt() {
    }

    public HrApprisalPerformanceDt(HrApprisalPerformanceDtPK hrApprisalPerformanceDtPK) {
        this.hrApprisalPerformanceDtPK = hrApprisalPerformanceDtPK;
    }

    public HrApprisalPerformanceDt(int compCode, long empCode, String perfCode, String ratingCode, String appraisalDt) {
        this.hrApprisalPerformanceDtPK = new HrApprisalPerformanceDtPK(compCode, empCode, perfCode, ratingCode, appraisalDt);
    }

    public HrApprisalPerformanceDtPK getHrApprisalPerformanceDtPK() {
        return hrApprisalPerformanceDtPK;
    }

    public void setHrApprisalPerformanceDtPK(HrApprisalPerformanceDtPK hrApprisalPerformanceDtPK) {
        this.hrApprisalPerformanceDtPK = hrApprisalPerformanceDtPK;
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

    public HrApprisalDetails getHrApprisalDetails() {
        return hrApprisalDetails;
    }

    public void setHrApprisalDetails(HrApprisalDetails hrApprisalDetails) {
        this.hrApprisalDetails = hrApprisalDetails;
    }

    public HrMstStruct getHrMstStruct() {
        return hrMstStruct;
    }

    public void setHrMstStruct(HrMstStruct hrMstStruct) {
        this.hrMstStruct = hrMstStruct;
    }

    public HrMstStruct getHrMstStruct1() {
        return hrMstStruct1;
    }

    public void setHrMstStruct1(HrMstStruct hrMstStruct1) {
        this.hrMstStruct1 = hrMstStruct1;
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
        hash += (hrApprisalPerformanceDtPK != null ? hrApprisalPerformanceDtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrApprisalPerformanceDt)) {
            return false;
        }
        HrApprisalPerformanceDt other = (HrApprisalPerformanceDt) object;
        if ((this.hrApprisalPerformanceDtPK == null && other.hrApprisalPerformanceDtPK != null) || (this.hrApprisalPerformanceDtPK != null && !this.hrApprisalPerformanceDtPK.equals(other.hrApprisalPerformanceDtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrApprisalPerformanceDt[hrApprisalPerformanceDtPK=" + hrApprisalPerformanceDtPK + "]";
    }

}
