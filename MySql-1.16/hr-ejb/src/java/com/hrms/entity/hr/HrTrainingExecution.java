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
@Table(name = "hr_training_execution")
@NamedQueries({@NamedQuery(name = "HrTrainingExecution.findAll", query = "SELECT h FROM HrTrainingExecution h"), @NamedQuery(name = "HrTrainingExecution.findByCompCode", query = "SELECT h FROM HrTrainingExecution h WHERE h.hrTrainingExecutionPK.compCode = :compCode"), @NamedQuery(name = "HrTrainingExecution.findByTrngexecCode", query = "SELECT h FROM HrTrainingExecution h WHERE h.hrTrainingExecutionPK.trngexecCode = :trngexecCode"), @NamedQuery(name = "HrTrainingExecution.findByEmpCode", query = "SELECT h FROM HrTrainingExecution h WHERE h.hrTrainingExecutionPK.empCode = :empCode"), @NamedQuery(name = "HrTrainingExecution.findByTrngCode", query = "SELECT h FROM HrTrainingExecution h WHERE h.trngCode = :trngCode"), @NamedQuery(name = "HrTrainingExecution.findByProgCode", query = "SELECT h FROM HrTrainingExecution h WHERE h.progCode = :progCode"), @NamedQuery(name = "HrTrainingExecution.findByFacuName", query = "SELECT h FROM HrTrainingExecution h WHERE h.facuName = :facuName"), @NamedQuery(name = "HrTrainingExecution.findByInextHouse", query = "SELECT h FROM HrTrainingExecution h WHERE h.inextHouse = :inextHouse"), @NamedQuery(name = "HrTrainingExecution.findByDatePlanFrom", query = "SELECT h FROM HrTrainingExecution h WHERE h.datePlanFrom = :datePlanFrom"), @NamedQuery(name = "HrTrainingExecution.findByDatePlanTo", query = "SELECT h FROM HrTrainingExecution h WHERE h.datePlanTo = :datePlanTo"), @NamedQuery(name = "HrTrainingExecution.findByCommence", query = "SELECT h FROM HrTrainingExecution h WHERE h.commence = :commence"), @NamedQuery(name = "HrTrainingExecution.findByEndDate", query = "SELECT h FROM HrTrainingExecution h WHERE h.endDate = :endDate"), @NamedQuery(name = "HrTrainingExecution.findByActDura", query = "SELECT h FROM HrTrainingExecution h WHERE h.actDura = :actDura"), @NamedQuery(name = "HrTrainingExecution.findByTrngCost", query = "SELECT h FROM HrTrainingExecution h WHERE h.trngCost = :trngCost"), @NamedQuery(name = "HrTrainingExecution.findByApprDet", query = "SELECT h FROM HrTrainingExecution h WHERE h.apprDet = :apprDet"), @NamedQuery(name = "HrTrainingExecution.findByTrainerAss", query = "SELECT h FROM HrTrainingExecution h WHERE h.trainerAss = :trainerAss"), @NamedQuery(name = "HrTrainingExecution.findByTraineeAss", query = "SELECT h FROM HrTrainingExecution h WHERE h.traineeAss = :traineeAss"), @NamedQuery(name = "HrTrainingExecution.findByDefaultComp", query = "SELECT h FROM HrTrainingExecution h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrTrainingExecution.findByStatFlag", query = "SELECT h FROM HrTrainingExecution h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrTrainingExecution.findByStatUpFlag", query = "SELECT h FROM HrTrainingExecution h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrTrainingExecution.findByModDate", query = "SELECT h FROM HrTrainingExecution h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrTrainingExecution.findByAuthBy", query = "SELECT h FROM HrTrainingExecution h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrTrainingExecution.findByEnteredBy", query = "SELECT h FROM HrTrainingExecution h WHERE h.enteredBy = :enteredBy")})
public class HrTrainingExecution extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrTrainingExecutionPK hrTrainingExecutionPK;
    @Column(name = "TRNG_CODE")
    private String trngCode;
    @Column(name = "PROG_CODE")
    private String progCode;
    @Column(name = "FACU_NAME")
    private String facuName;
    @Column(name = "INEXT_HOUSE")
    private Character inextHouse;
    @Basic(optional = false)
    @Column(name = "DATE_PLAN_FROM")
    private String datePlanFrom;
    @Basic(optional = false)
    @Column(name = "DATE_PLAN_TO")
    private String datePlanTo;
    @Basic(optional = false)
    @Column(name = "COMMENCE")
    private String commence;
    @Basic(optional = false)
    @Column(name = "END_DATE")
    private String endDate;
    @Column(name = "ACT_DURA")
    private Integer actDura;
    @Basic(optional = false)
    @Column(name = "TRNG_COST")
    private int trngCost;
    @Column(name = "APPR_DET")
    private Character apprDet;
    @Column(name = "TRAINER_ASS")
    private String trainerAss;
    @Column(name = "TRAINEE_ASS")
    private String traineeAss;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrTrainingExecution() {
    }

    public HrTrainingExecution(HrTrainingExecutionPK hrTrainingExecutionPK) {
        this.hrTrainingExecutionPK = hrTrainingExecutionPK;
    }

    public HrTrainingExecution(HrTrainingExecutionPK hrTrainingExecutionPK, String datePlanFrom, String datePlanTo, String commence, String endDate, int trngCost) {
        this.hrTrainingExecutionPK = hrTrainingExecutionPK;
        this.datePlanFrom = datePlanFrom;
        this.datePlanTo = datePlanTo;
        this.commence = commence;
        this.endDate = endDate;
        this.trngCost = trngCost;
    }

    public HrTrainingExecution(int compCode, String trngexecCode, long empCode) {
        this.hrTrainingExecutionPK = new HrTrainingExecutionPK(compCode, trngexecCode, empCode);
    }

    public HrTrainingExecutionPK getHrTrainingExecutionPK() {
        return hrTrainingExecutionPK;
    }

    public void setHrTrainingExecutionPK(HrTrainingExecutionPK hrTrainingExecutionPK) {
        this.hrTrainingExecutionPK = hrTrainingExecutionPK;
    }

    public String getTrngCode() {
        return trngCode;
    }

    public void setTrngCode(String trngCode) {
        this.trngCode = trngCode;
    }

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    public String getFacuName() {
        return facuName;
    }

    public void setFacuName(String facuName) {
        this.facuName = facuName;
    }

    public Character getInextHouse() {
        return inextHouse;
    }

    public void setInextHouse(Character inextHouse) {
        this.inextHouse = inextHouse;
    }

    public String getDatePlanFrom() {
        return datePlanFrom;
    }

    public void setDatePlanFrom(String datePlanFrom) {
        this.datePlanFrom = datePlanFrom;
    }

    public String getDatePlanTo() {
        return datePlanTo;
    }

    public void setDatePlanTo(String datePlanTo) {
        this.datePlanTo = datePlanTo;
    }

    public String getCommence() {
        return commence;
    }

    public void setCommence(String commence) {
        this.commence = commence;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getActDura() {
        return actDura;
    }

    public void setActDura(Integer actDura) {
        this.actDura = actDura;
    }

    public int getTrngCost() {
        return trngCost;
    }

    public void setTrngCost(int trngCost) {
        this.trngCost = trngCost;
    }

    public Character getApprDet() {
        return apprDet;
    }

    public void setApprDet(Character apprDet) {
        this.apprDet = apprDet;
    }

    public String getTrainerAss() {
        return trainerAss;
    }

    public void setTrainerAss(String trainerAss) {
        this.trainerAss = trainerAss;
    }

    public String getTraineeAss() {
        return traineeAss;
    }

    public void setTraineeAss(String traineeAss) {
        this.traineeAss = traineeAss;
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
        hash += (hrTrainingExecutionPK != null ? hrTrainingExecutionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTrainingExecution)) {
            return false;
        }
        HrTrainingExecution other = (HrTrainingExecution) object;
        if ((this.hrTrainingExecutionPK == null && other.hrTrainingExecutionPK != null) || (this.hrTrainingExecutionPK != null && !this.hrTrainingExecutionPK.equals(other.hrTrainingExecutionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrTrainingExecution[hrTrainingExecutionPK=" + hrTrainingExecutionPK + "]";
    }

}
