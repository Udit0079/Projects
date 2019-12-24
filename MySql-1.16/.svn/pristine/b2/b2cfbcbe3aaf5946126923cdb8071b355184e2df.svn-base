/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrPersonnelDetails;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@Table(name = "hr_apprisal_details")
@NamedQueries({@NamedQuery(name = "HrApprisalDetails.findAll", query = "SELECT h FROM HrApprisalDetails h"),
@NamedQuery(name = "HrApprisalDetails.findByCompCodeEmpCodeAppdt", query = "SELECT h FROM HrApprisalDetails h WHERE h.hrApprisalDetailsPK.compCode = :compCode and h.hrApprisalDetailsPK.empCode = :empCode and h.hrApprisalDetailsPK.appraisalDt = :appraisalDt "),
@NamedQuery(name = "HrApprisalDetails.findByCompCode", query = "SELECT h FROM HrApprisalDetails h WHERE h.hrApprisalDetailsPK.compCode = :compCode"),
@NamedQuery(name = "HrApprisalDetails.findByEmpCode", query = "SELECT h FROM HrApprisalDetails h WHERE h.hrApprisalDetailsPK.empCode = :empCode"),
@NamedQuery(name = "HrApprisalDetails.findByRatingCode", query = "SELECT h FROM HrApprisalDetails h WHERE h.ratingCode = :ratingCode"),
@NamedQuery(name = "HrApprisalDetails.findByApprResult", query = "SELECT h FROM HrApprisalDetails h WHERE h.apprResult = :apprResult"),
@NamedQuery(name = "HrApprisalDetails.findByTrngCode", query = "SELECT h FROM HrApprisalDetails h WHERE h.trngCode = :trngCode"),
@NamedQuery(name = "HrApprisalDetails.findByRecHead", query = "SELECT h FROM HrApprisalDetails h WHERE h.recHead = :recHead"), @NamedQuery(name = "HrApprisalDetails.findByRecHod", query = "SELECT h FROM HrApprisalDetails h WHERE h.recHod = :recHod"), @NamedQuery(name = "HrApprisalDetails.findByRecHrd", query = "SELECT h FROM HrApprisalDetails h WHERE h.recHrd = :recHrd"), @NamedQuery(name = "HrApprisalDetails.findByRecPersonnel", query = "SELECT h FROM HrApprisalDetails h WHERE h.recPersonnel = :recPersonnel"), @NamedQuery(name = "HrApprisalDetails.findByIncrAmt", query = "SELECT h FROM HrApprisalDetails h WHERE h.incrAmt = :incrAmt"), @NamedQuery(name = "HrApprisalDetails.findByAppraisalDt", query = "SELECT h FROM HrApprisalDetails h WHERE h.hrApprisalDetailsPK.appraisalDt = :appraisalDt"), @NamedQuery(name = "HrApprisalDetails.findByPromWef", query = "SELECT h FROM HrApprisalDetails h WHERE h.promWef = :promWef"), @NamedQuery(name = "HrApprisalDetails.findByDefaultComp", query = "SELECT h FROM HrApprisalDetails h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrApprisalDetails.findByStatFlag", query = "SELECT h FROM HrApprisalDetails h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrApprisalDetails.findByStatUpFlag", query = "SELECT h FROM HrApprisalDetails h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrApprisalDetails.findByModDate", query = "SELECT h FROM HrApprisalDetails h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrApprisalDetails.findByAuthBy", query = "SELECT h FROM HrApprisalDetails h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrApprisalDetails.findByEnteredBy", query = "SELECT h FROM HrApprisalDetails h WHERE h.enteredBy = :enteredBy")})
public class HrApprisalDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrApprisalDetailsPK hrApprisalDetailsPK;
    @Column(name = "RATING_CODE")
    private String ratingCode;
    @Column(name = "APPR_RESULT")
    private Character apprResult;
    @Column(name = "TRNG_CODE")
    private String trngCode;
    @Column(name = "REC_HEAD")
    private String recHead;
    @Column(name = "REC_HOD")
    private String recHod;
    @Column(name = "REC_HRD")
    private String recHrd;
    @Column(name = "REC_PERSONNEL")
    private String recPersonnel;
    @Column(name = "INCR_AMT")
    private Double incrAmt;
    @Column(name = "PROM_WEF")
    private String promWef;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hrApprisalDetails")
    private Collection<HrApprisalPerformanceDt> hrApprisalPerformanceDtCollection;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrApprisalDetails() {
    }

    public HrApprisalDetails(HrApprisalDetailsPK hrApprisalDetailsPK) {
        this.hrApprisalDetailsPK = hrApprisalDetailsPK;
    }

    public HrApprisalDetails(int compCode, long empCode, String appraisalDt) {
        this.hrApprisalDetailsPK = new HrApprisalDetailsPK(compCode, empCode, appraisalDt);
    }

    public HrApprisalDetailsPK getHrApprisalDetailsPK() {
        return hrApprisalDetailsPK;
    }

    public void setHrApprisalDetailsPK(HrApprisalDetailsPK hrApprisalDetailsPK) {
        this.hrApprisalDetailsPK = hrApprisalDetailsPK;
    }

    public String getRatingCode() {
        return ratingCode;
    }

    public void setRatingCode(String ratingCode) {
        this.ratingCode = ratingCode;
    }

    public Character getApprResult() {
        return apprResult;
    }

    public void setApprResult(Character apprResult) {
        this.apprResult = apprResult;
    }

    public String getTrngCode() {
        return trngCode;
    }

    public void setTrngCode(String trngCode) {
        this.trngCode = trngCode;
    }

    public String getRecHead() {
        return recHead;
    }

    public void setRecHead(String recHead) {
        this.recHead = recHead;
    }

    public String getRecHod() {
        return recHod;
    }

    public void setRecHod(String recHod) {
        this.recHod = recHod;
    }

    public String getRecHrd() {
        return recHrd;
    }

    public void setRecHrd(String recHrd) {
        this.recHrd = recHrd;
    }

    public String getRecPersonnel() {
        return recPersonnel;
    }

    public void setRecPersonnel(String recPersonnel) {
        this.recPersonnel = recPersonnel;
    }

    public Double getIncrAmt() {
        return incrAmt;
    }

    public void setIncrAmt(Double incrAmt) {
        this.incrAmt = incrAmt;
    }

    public String getPromWef() {
        return promWef;
    }

    public void setPromWef(String promWef) {
        this.promWef = promWef;
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

    public Collection<HrApprisalPerformanceDt> getHrApprisalPerformanceDtCollection() {
        return hrApprisalPerformanceDtCollection;
    }

    public void setHrApprisalPerformanceDtCollection(Collection<HrApprisalPerformanceDt> hrApprisalPerformanceDtCollection) {
        this.hrApprisalPerformanceDtCollection = hrApprisalPerformanceDtCollection;
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
        hash += (hrApprisalDetailsPK != null ? hrApprisalDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrApprisalDetails)) {
            return false;
        }
        HrApprisalDetails other = (HrApprisalDetails) object;
        if ((this.hrApprisalDetailsPK == null && other.hrApprisalDetailsPK != null) || (this.hrApprisalDetailsPK != null && !this.hrApprisalDetailsPK.equals(other.hrApprisalDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrApprisalDetails[hrApprisalDetailsPK=" + hrApprisalDetailsPK + "]";
    }

}
