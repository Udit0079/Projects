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
@Table(name = "hr_training_plan")
@NamedQueries({@NamedQuery(name = "HrTrainingPlan.findAll", query = "SELECT h FROM HrTrainingPlan h"), @NamedQuery(name = "HrTrainingPlan.findByCompCode", query = "SELECT h FROM HrTrainingPlan h WHERE h.hrTrainingPlanPK.compCode = :compCode"), @NamedQuery(name = "HrTrainingPlan.findByEmpCode", query = "SELECT h FROM HrTrainingPlan h WHERE h.hrTrainingPlanPK.empCode = :empCode"), @NamedQuery(name = "HrTrainingPlan.findByTrngCode", query = "SELECT h FROM HrTrainingPlan h WHERE h.hrTrainingPlanPK.trngCode = :trngCode"), @NamedQuery(name = "HrTrainingPlan.findByProgCode", query = "SELECT h FROM HrTrainingPlan h WHERE h.hrTrainingPlanPK.progCode = :progCode"), @NamedQuery(name = "HrTrainingPlan.findByDateFrom", query = "SELECT h FROM HrTrainingPlan h WHERE h.hrTrainingPlanPK.dateFrom = :dateFrom"), @NamedQuery(name = "HrTrainingPlan.findByDateTo", query = "SELECT h FROM HrTrainingPlan h WHERE h.hrTrainingPlanPK.dateTo = :dateTo"), @NamedQuery(name = "HrTrainingPlan.findByTrngDur", query = "SELECT h FROM HrTrainingPlan h WHERE h.trngDur = :trngDur"), @NamedQuery(name = "HrTrainingPlan.findByTrngExec", query = "SELECT h FROM HrTrainingPlan h WHERE h.trngExec = :trngExec"), @NamedQuery(name = "HrTrainingPlan.findByApprDet", query = "SELECT h FROM HrTrainingPlan h WHERE h.apprDet = :apprDet"), @NamedQuery(name = "HrTrainingPlan.findByDefaultComp", query = "SELECT h FROM HrTrainingPlan h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrTrainingPlan.findByStatFlag", query = "SELECT h FROM HrTrainingPlan h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrTrainingPlan.findByStatUpFlag", query = "SELECT h FROM HrTrainingPlan h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrTrainingPlan.findByModDate", query = "SELECT h FROM HrTrainingPlan h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrTrainingPlan.findByAuthBy", query = "SELECT h FROM HrTrainingPlan h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrTrainingPlan.findByEnteredBy", query = "SELECT h FROM HrTrainingPlan h WHERE h.enteredBy = :enteredBy")})
public class HrTrainingPlan extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrTrainingPlanPK hrTrainingPlanPK;
    @Basic(optional = false)
    @Column(name = "TRNG_DUR")
    private int trngDur;
    @Column(name = "TRNG_EXEC")
    private Character trngExec;
    @Column(name = "APPR_DET")
    private Character apprDet;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "PROG_CODE", referencedColumnName = "PROG_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstProgram hrMstProgram;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "TRNG_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrTrainingPlan() {
    }

    public HrTrainingPlan(HrTrainingPlanPK hrTrainingPlanPK) {
        this.hrTrainingPlanPK = hrTrainingPlanPK;
    }

    public HrTrainingPlan(HrTrainingPlanPK hrTrainingPlanPK, int trngDur) {
        this.hrTrainingPlanPK = hrTrainingPlanPK;
        this.trngDur = trngDur;
    }

    public HrTrainingPlan(int compCode, long empCode, String trngCode, String progCode, String dateFrom, String dateTo) {
        this.hrTrainingPlanPK = new HrTrainingPlanPK(compCode, empCode, trngCode, progCode, dateFrom, dateTo);
    }

    public HrTrainingPlanPK getHrTrainingPlanPK() {
        return hrTrainingPlanPK;
    }

    public void setHrTrainingPlanPK(HrTrainingPlanPK hrTrainingPlanPK) {
        this.hrTrainingPlanPK = hrTrainingPlanPK;
    }

    public int getTrngDur() {
        return trngDur;
    }

    public void setTrngDur(int trngDur) {
        this.trngDur = trngDur;
    }

    public Character getTrngExec() {
        return trngExec;
    }

    public void setTrngExec(Character trngExec) {
        this.trngExec = trngExec;
    }

    public Character getApprDet() {
        return apprDet;
    }

    public void setApprDet(Character apprDet) {
        this.apprDet = apprDet;
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

    public HrMstProgram getHrMstProgram() {
        return hrMstProgram;
    }

    public void setHrMstProgram(HrMstProgram hrMstProgram) {
        this.hrMstProgram = hrMstProgram;
    }

    public HrMstStruct getHrMstStruct() {
        return hrMstStruct;
    }

    public void setHrMstStruct(HrMstStruct hrMstStruct) {
        this.hrMstStruct = hrMstStruct;
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
        hash += (hrTrainingPlanPK != null ? hrTrainingPlanPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTrainingPlan)) {
            return false;
        }
        HrTrainingPlan other = (HrTrainingPlan) object;
        if ((this.hrTrainingPlanPK == null && other.hrTrainingPlanPK != null) || (this.hrTrainingPlanPK != null && !this.hrTrainingPlanPK.equals(other.hrTrainingPlanPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrTrainingPlan[hrTrainingPlanPK=" + hrTrainingPlanPK + "]";
    }

}
