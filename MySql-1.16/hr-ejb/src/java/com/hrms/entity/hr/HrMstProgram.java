/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "hr_mst_program")
@NamedQueries({@NamedQuery(name = "HrMstProgram.findAll", query = "SELECT h FROM HrMstProgram h"), @NamedQuery(name = "HrMstProgram.findByCompCode", query = "SELECT h FROM HrMstProgram h WHERE h.hrMstProgramPK.compCode = :compCode"), @NamedQuery(name = "HrMstProgram.findByProgCode", query = "SELECT h FROM HrMstProgram h WHERE h.hrMstProgramPK.progCode = :progCode"), @NamedQuery(name = "HrMstProgram.findByProgName", query = "SELECT h FROM HrMstProgram h WHERE h.progName = :progName"), @NamedQuery(name = "HrMstProgram.findByInstCode", query = "SELECT h FROM HrMstProgram h WHERE h.instCode = :instCode"), @NamedQuery(name = "HrMstProgram.findByTrngFrom", query = "SELECT h FROM HrMstProgram h WHERE h.trngFrom = :trngFrom"), @NamedQuery(name = "HrMstProgram.findByTrngTo", query = "SELECT h FROM HrMstProgram h WHERE h.trngTo = :trngTo"), @NamedQuery(name = "HrMstProgram.findByInextHouse", query = "SELECT h FROM HrMstProgram h WHERE h.inextHouse = :inextHouse"), @NamedQuery(name = "HrMstProgram.findByTrngCost", query = "SELECT h FROM HrMstProgram h WHERE h.trngCost = :trngCost"), @NamedQuery(name = "HrMstProgram.findByFacuName", query = "SELECT h FROM HrMstProgram h WHERE h.facuName = :facuName"), @NamedQuery(name = "HrMstProgram.findByDefaultComp", query = "SELECT h FROM HrMstProgram h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrMstProgram.findByStatFlag", query = "SELECT h FROM HrMstProgram h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrMstProgram.findByStatUpFlag", query = "SELECT h FROM HrMstProgram h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrMstProgram.findByModDate", query = "SELECT h FROM HrMstProgram h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrMstProgram.findByAuthBy", query = "SELECT h FROM HrMstProgram h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrMstProgram.findByEnteredBy", query = "SELECT h FROM HrMstProgram h WHERE h.enteredBy = :enteredBy")})
public class HrMstProgram extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrMstProgramPK hrMstProgramPK;
    @Basic(optional = false)
    @Column(name = "PROG_NAME")
    private String progName;
    @Column(name = "INST_CODE")
    private String instCode;
    @Basic(optional = false)
    @Column(name = "TRNG_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trngFrom;
    @Basic(optional = false)
    @Column(name = "TRNG_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trngTo;
    @Column(name = "INEXT_HOUSE")
    private Character inextHouse;
    @Column(name = "TRNG_COST")
    private Double trngCost;
    @Column(name = "FACU_NAME")
    private String facuName;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hrMstProgram")
    private Collection<HrMstTrngProgram> hrMstTrngProgramCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hrMstProgram")
    private Collection<HrDetailsProgram> hrDetailsProgramCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hrMstProgram")
    private Collection<HrTrainingPlan> hrTrainingPlanCollection;

    public HrMstProgram() {
    }

    public HrMstProgram(HrMstProgramPK hrMstProgramPK) {
        this.hrMstProgramPK = hrMstProgramPK;
    }

    public HrMstProgram(HrMstProgramPK hrMstProgramPK, String progName, Date trngFrom, Date trngTo) {
        this.hrMstProgramPK = hrMstProgramPK;
        this.progName = progName;
        this.trngFrom = trngFrom;
        this.trngTo = trngTo;
    }

    public HrMstProgram(int compCode, String progCode) {
        this.hrMstProgramPK = new HrMstProgramPK(compCode, progCode);
    }

    public HrMstProgramPK getHrMstProgramPK() {
        return hrMstProgramPK;
    }

    public void setHrMstProgramPK(HrMstProgramPK hrMstProgramPK) {
        this.hrMstProgramPK = hrMstProgramPK;
    }

    public String getProgName() {
        return progName;
    }

    public void setProgName(String progName) {
        this.progName = progName;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public Date getTrngFrom() {
        return trngFrom;
    }

    public void setTrngFrom(Date trngFrom) {
        this.trngFrom = trngFrom;
    }

    public Date getTrngTo() {
        return trngTo;
    }

    public void setTrngTo(Date trngTo) {
        this.trngTo = trngTo;
    }

    public Character getInextHouse() {
        return inextHouse;
    }

    public void setInextHouse(Character inextHouse) {
        this.inextHouse = inextHouse;
    }

    public Double getTrngCost() {
        return trngCost;
    }

    public void setTrngCost(Double trngCost) {
        this.trngCost = trngCost;
    }

    public String getFacuName() {
        return facuName;
    }

    public void setFacuName(String facuName) {
        this.facuName = facuName;
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

    public Collection<HrMstTrngProgram> getHrMstTrngProgramCollection() {
        return hrMstTrngProgramCollection;
    }

    public void setHrMstTrngProgramCollection(Collection<HrMstTrngProgram> hrMstTrngProgramCollection) {
        this.hrMstTrngProgramCollection = hrMstTrngProgramCollection;
    }

    public Collection<HrDetailsProgram> getHrDetailsProgramCollection() {
        return hrDetailsProgramCollection;
    }

    public void setHrDetailsProgramCollection(Collection<HrDetailsProgram> hrDetailsProgramCollection) {
        this.hrDetailsProgramCollection = hrDetailsProgramCollection;
    }

    public Collection<HrTrainingPlan> getHrTrainingPlanCollection() {
        return hrTrainingPlanCollection;
    }

    public void setHrTrainingPlanCollection(Collection<HrTrainingPlan> hrTrainingPlanCollection) {
        this.hrTrainingPlanCollection = hrTrainingPlanCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrMstProgramPK != null ? hrMstProgramPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstProgram)) {
            return false;
        }
        HrMstProgram other = (HrMstProgram) object;
        if ((this.hrMstProgramPK == null && other.hrMstProgramPK != null) || (this.hrMstProgramPK != null && !this.hrMstProgramPK.equals(other.hrMstProgramPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrMstProgram[hrMstProgramPK=" + hrMstProgramPK + "]";
    }

}
