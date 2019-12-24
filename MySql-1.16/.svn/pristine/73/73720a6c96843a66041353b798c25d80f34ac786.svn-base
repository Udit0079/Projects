/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
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
@Table(name = "hr_membership_detail")
@NamedQueries({@NamedQuery(name = "HrMembershipDetail.findAll", query = "SELECT h FROM HrMembershipDetail h"), @NamedQuery(name = "HrMembershipDetail.findByCompCode", query = "SELECT h FROM HrMembershipDetail h WHERE h.hrMembershipDetailPK.compCode = :compCode"), @NamedQuery(name = "HrMembershipDetail.findByEmpCode", query = "SELECT h FROM HrMembershipDetail h WHERE h.hrMembershipDetailPK.empCode = :empCode"), @NamedQuery(name = "HrMembershipDetail.findByMemNo", query = "SELECT h FROM HrMembershipDetail h WHERE h.hrMembershipDetailPK.memNo = :memNo"), @NamedQuery(name = "HrMembershipDetail.findByProfMember", query = "SELECT h FROM HrMembershipDetail h WHERE h.profMember = :profMember"), @NamedQuery(name = "HrMembershipDetail.findByIndivMember", query = "SELECT h FROM HrMembershipDetail h WHERE h.indivMember = :indivMember"), @NamedQuery(name = "HrMembershipDetail.findByPassportNo", query = "SELECT h FROM HrMembershipDetail h WHERE h.passportNo = :passportNo"), @NamedQuery(name = "HrMembershipDetail.findByPassportDate", query = "SELECT h FROM HrMembershipDetail h WHERE h.passportDate = :passportDate"), @NamedQuery(name = "HrMembershipDetail.findByAccomdType", query = "SELECT h FROM HrMembershipDetail h WHERE h.accomdType = :accomdType"), @NamedQuery(name = "HrMembershipDetail.findByInsuranceNo", query = "SELECT h FROM HrMembershipDetail h WHERE h.insuranceNo = :insuranceNo"), @NamedQuery(name = "HrMembershipDetail.findByTravelOver", query = "SELECT h FROM HrMembershipDetail h WHERE h.travelOver = :travelOver"), @NamedQuery(name = "HrMembershipDetail.findByDefaultComp", query = "SELECT h FROM HrMembershipDetail h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrMembershipDetail.findByStatFlag", query = "SELECT h FROM HrMembershipDetail h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrMembershipDetail.findByStatUpFlag", query = "SELECT h FROM HrMembershipDetail h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrMembershipDetail.findByModDate", query = "SELECT h FROM HrMembershipDetail h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrMembershipDetail.findByAuthBy", query = "SELECT h FROM HrMembershipDetail h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrMembershipDetail.findByEnteredBy", query = "SELECT h FROM HrMembershipDetail h WHERE h.enteredBy = :enteredBy")})
public class HrMembershipDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrMembershipDetailPK hrMembershipDetailPK;
    @Column(name = "PROF_MEMBER")
    private String profMember;
    @Column(name = "INDIV_MEMBER")
    private String indivMember;
    @Column(name = "PASSPORT_NO")
    private String passportNo;
    @Column(name = "PASSPORT_DATE")
    private String passportDate;
    @Column(name = "ACCOMD_TYPE")
    private String accomdType;
    @Column(name = "INSURANCE_NO")
    private String insuranceNo;
    @Column(name = "TRAVEL_OVER")
    private String travelOver;
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

    public HrMembershipDetail() {
    }

    public HrMembershipDetail(HrMembershipDetailPK hrMembershipDetailPK) {
        this.hrMembershipDetailPK = hrMembershipDetailPK;
    }

    public HrMembershipDetail(int compCode, long empCode, int memNo) {
        this.hrMembershipDetailPK = new HrMembershipDetailPK(compCode, empCode, memNo);
    }

    public HrMembershipDetailPK getHrMembershipDetailPK() {
        return hrMembershipDetailPK;
    }

    public void setHrMembershipDetailPK(HrMembershipDetailPK hrMembershipDetailPK) {
        this.hrMembershipDetailPK = hrMembershipDetailPK;
    }

    public String getProfMember() {
        return profMember;
    }

    public void setProfMember(String profMember) {
        this.profMember = profMember;
    }

    public String getIndivMember() {
        return indivMember;
    }

    public void setIndivMember(String indivMember) {
        this.indivMember = indivMember;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassportDate() {
        return passportDate;
    }

    public void setPassportDate(String passportDate) {
        this.passportDate = passportDate;
    }

    public String getAccomdType() {
        return accomdType;
    }

    public void setAccomdType(String accomdType) {
        this.accomdType = accomdType;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public String getTravelOver() {
        return travelOver;
    }

    public void setTravelOver(String travelOver) {
        this.travelOver = travelOver;
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
        hash += (hrMembershipDetailPK != null ? hrMembershipDetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMembershipDetail)) {
            return false;
        }
        HrMembershipDetail other = (HrMembershipDetail) object;
        if ((this.hrMembershipDetailPK == null && other.hrMembershipDetailPK != null) || (this.hrMembershipDetailPK != null && !this.hrMembershipDetailPK.equals(other.hrMembershipDetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrMembershipDetail[hrMembershipDetailPK=" + hrMembershipDetailPK + "]";
    }

}
