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
@Table(name = "hr_personnel_qualification")
@NamedQueries({
    @NamedQuery(name = "HrPersonnelQualification.findAll", query = "SELECT h FROM HrPersonnelQualification h"),
    @NamedQuery(name = "HrPersonnelQualification.findByCompCode", query = "SELECT h FROM HrPersonnelQualification h WHERE h.hrPersonnelQualificationPK.compCode = :compCode"),
    @NamedQuery(name = "HrPersonnelQualification.findByEmpCode", query = "SELECT h FROM HrPersonnelQualification h WHERE h.hrPersonnelQualificationPK.empCode = :empCode ORDER BY h.year"), @NamedQuery(name = "HrPersonnelQualification.findByQualCode", query = "SELECT h FROM HrPersonnelQualification h WHERE h.hrPersonnelQualificationPK.qualCode = :qualCode"), @NamedQuery(name = "HrPersonnelQualification.findByInstituteName", query = "SELECT h FROM HrPersonnelQualification h WHERE h.instituteName = :instituteName"), @NamedQuery(name = "HrPersonnelQualification.findByYear", query = "SELECT h FROM HrPersonnelQualification h WHERE h.year = :year"), @NamedQuery(name = "HrPersonnelQualification.findBySubName", query = "SELECT h FROM HrPersonnelQualification h WHERE h.subName = :subName"), @NamedQuery(name = "HrPersonnelQualification.findBySpecializationCode", query = "SELECT h FROM HrPersonnelQualification h WHERE h.specializationCode = :specializationCode"), @NamedQuery(name = "HrPersonnelQualification.findByPercentageMarks", query = "SELECT h FROM HrPersonnelQualification h WHERE h.percentageMarks = :percentageMarks"), @NamedQuery(name = "HrPersonnelQualification.findByDivision", query = "SELECT h FROM HrPersonnelQualification h WHERE h.division = :division"), @NamedQuery(name = "HrPersonnelQualification.findByDuration", query = "SELECT h FROM HrPersonnelQualification h WHERE h.duration = :duration"), @NamedQuery(name = "HrPersonnelQualification.findByDefaultComp", query = "SELECT h FROM HrPersonnelQualification h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrPersonnelQualification.findByStatFlag", query = "SELECT h FROM HrPersonnelQualification h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrPersonnelQualification.findByStatUpFlag", query = "SELECT h FROM HrPersonnelQualification h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrPersonnelQualification.findByModDate", query = "SELECT h FROM HrPersonnelQualification h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrPersonnelQualification.findByAuthBy", query = "SELECT h FROM HrPersonnelQualification h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrPersonnelQualification.findByEnteredBy", query = "SELECT h FROM HrPersonnelQualification h WHERE h.enteredBy = :enteredBy")})
public class HrPersonnelQualification extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrPersonnelQualificationPK hrPersonnelQualificationPK;
    @Basic(optional = false)
    @Column(name = "INSTITUTE_NAME")
    private String instituteName;
    @Basic(optional = false)
    @Column(name = "YEAR")
    private int year;
    @Column(name = "SUB_NAME")
    private String subName;
    @Column(name = "SPECIALIZATION_CODE")
    private String specializationCode;
    @Column(name = "PERCENTAGE_MARKS")
    private Double percentageMarks;
    @Column(name = "DIVISION")
    private String division;
    @Column(name = "DURATION")
    private Double duration;
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

    public HrPersonnelQualification() {
    }

    public HrPersonnelQualification(HrPersonnelQualificationPK hrPersonnelQualificationPK) {
        this.hrPersonnelQualificationPK = hrPersonnelQualificationPK;
    }

    public HrPersonnelQualification(HrPersonnelQualificationPK hrPersonnelQualificationPK, String instituteName, int year) {
        this.hrPersonnelQualificationPK = hrPersonnelQualificationPK;
        this.instituteName = instituteName;
        this.year = year;
    }

    public HrPersonnelQualification(int compCode, long empCode, String qualCode) {
        this.hrPersonnelQualificationPK = new HrPersonnelQualificationPK(compCode, empCode, qualCode);
    }

    public HrPersonnelQualificationPK getHrPersonnelQualificationPK() {
        return hrPersonnelQualificationPK;
    }

    public void setHrPersonnelQualificationPK(HrPersonnelQualificationPK hrPersonnelQualificationPK) {
        this.hrPersonnelQualificationPK = hrPersonnelQualificationPK;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSpecializationCode() {
        return specializationCode;
    }

    public void setSpecializationCode(String specializationCode) {
        this.specializationCode = specializationCode;
    }

    public Double getPercentageMarks() {
        return percentageMarks;
    }

    public void setPercentageMarks(Double percentageMarks) {
        this.percentageMarks = percentageMarks;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
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
        hash += (hrPersonnelQualificationPK != null ? hrPersonnelQualificationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelQualification)) {
            return false;
        }
        HrPersonnelQualification other = (HrPersonnelQualification) object;
        if ((this.hrPersonnelQualificationPK == null && other.hrPersonnelQualificationPK != null) || (this.hrPersonnelQualificationPK != null && !this.hrPersonnelQualificationPK.equals(other.hrPersonnelQualificationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPersonnelQualification[hrPersonnelQualificationPK=" + hrPersonnelQualificationPK + "]";
    }
}
