/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

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
@Table(name = "hr_personnel_dependent")
@NamedQueries({@NamedQuery(name = "HrPersonnelDependent.findAll", query = "SELECT h FROM HrPersonnelDependent h"), @NamedQuery(name = "HrPersonnelDependent.findByCompCode", query = "SELECT h FROM HrPersonnelDependent h WHERE h.hrPersonnelDependentPK.compCode = :compCode"), @NamedQuery(name = "HrPersonnelDependent.findByEmpCode", query = "SELECT h FROM HrPersonnelDependent h WHERE h.hrPersonnelDependentPK.empCode = :empCode"), @NamedQuery(name = "HrPersonnelDependent.findByDependentCode", query = "SELECT h FROM HrPersonnelDependent h WHERE h.hrPersonnelDependentPK.dependentCode = :dependentCode"), @NamedQuery(name = "HrPersonnelDependent.findByDepName", query = "SELECT h FROM HrPersonnelDependent h WHERE h.depName = :depName"), @NamedQuery(name = "HrPersonnelDependent.findByPetName", query = "SELECT h FROM HrPersonnelDependent h WHERE h.petName = :petName"), @NamedQuery(name = "HrPersonnelDependent.findByDepAge", query = "SELECT h FROM HrPersonnelDependent h WHERE h.depAge = :depAge"), @NamedQuery(name = "HrPersonnelDependent.findByDepRel", query = "SELECT h FROM HrPersonnelDependent h WHERE h.depRel = :depRel"), @NamedQuery(name = "HrPersonnelDependent.findByOccupation", query = "SELECT h FROM HrPersonnelDependent h WHERE h.occupation = :occupation"), @NamedQuery(name = "HrPersonnelDependent.findByDefaultComp", query = "SELECT h FROM HrPersonnelDependent h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrPersonnelDependent.findByStatFlag", query = "SELECT h FROM HrPersonnelDependent h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrPersonnelDependent.findByStatUpFlag", query = "SELECT h FROM HrPersonnelDependent h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrPersonnelDependent.findByModDate", query = "SELECT h FROM HrPersonnelDependent h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrPersonnelDependent.findByAuthBy", query = "SELECT h FROM HrPersonnelDependent h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrPersonnelDependent.findByEnteredBy", query = "SELECT h FROM HrPersonnelDependent h WHERE h.enteredBy = :enteredBy")})
public class HrPersonnelDependent extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrPersonnelDependentPK hrPersonnelDependentPK;
    @Basic(optional = false)
    @Column(name = "DEP_NAME")
    private String depName;
    @Column(name = "PET_NAME")
    private String petName;
    @Column(name = "DEP_AGE")
    private Integer depAge;
    @Column(name = "DEP_REL")
    private String depRel;
    @Column(name = "OCCUPATION")
    private String occupation;
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

    public HrPersonnelDependent() {
    }

    public HrPersonnelDependent(HrPersonnelDependentPK hrPersonnelDependentPK) {
        this.hrPersonnelDependentPK = hrPersonnelDependentPK;
    }

    public HrPersonnelDependent(HrPersonnelDependentPK hrPersonnelDependentPK, String depName) {
        this.hrPersonnelDependentPK = hrPersonnelDependentPK;
        this.depName = depName;
    }

    public HrPersonnelDependent(int compCode, long empCode, long dependentCode) {
        this.hrPersonnelDependentPK = new HrPersonnelDependentPK(compCode, empCode, dependentCode);
    }

    public HrPersonnelDependentPK getHrPersonnelDependentPK() {
        return hrPersonnelDependentPK;
    }

    public void setHrPersonnelDependentPK(HrPersonnelDependentPK hrPersonnelDependentPK) {
        this.hrPersonnelDependentPK = hrPersonnelDependentPK;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Integer getDepAge() {
        return depAge;
    }

    public void setDepAge(Integer depAge) {
        this.depAge = depAge;
    }

    public String getDepRel() {
        return depRel;
    }

    public void setDepRel(String depRel) {
        this.depRel = depRel;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
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
        hash += (hrPersonnelDependentPK != null ? hrPersonnelDependentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelDependent)) {
            return false;
        }
        HrPersonnelDependent other = (HrPersonnelDependent) object;
        if ((this.hrPersonnelDependentPK == null && other.hrPersonnelDependentPK != null) || (this.hrPersonnelDependentPK != null && !this.hrPersonnelDependentPK.equals(other.hrPersonnelDependentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPersonnelDependent[hrPersonnelDependentPK=" + hrPersonnelDependentPK + "]";
    }

}
