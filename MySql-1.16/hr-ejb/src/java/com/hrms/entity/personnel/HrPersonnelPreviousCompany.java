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
@Table(name = "hr_personnel_previous_company")
@NamedQueries({@NamedQuery(name = "HrPersonnelPreviousCompany.findAll", query = "SELECT h FROM HrPersonnelPreviousCompany h"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByCompCode", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.hrPersonnelPreviousCompanyPK.compCode = :compCode"),
    @NamedQuery(name = "HrPersonnelPreviousCompany.findByEmpCode", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.hrPersonnelPreviousCompanyPK.empCode = :empCode"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByPrevCompCode", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.hrPersonnelPreviousCompanyPK.prevCompCode = :prevCompCode"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByDurFrom", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.hrPersonnelPreviousCompanyPK.durFrom = :durFrom"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByDurTo", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.hrPersonnelPreviousCompanyPK.durTo = :durTo"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByCompName", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.compName = :compName"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByAnnualTurn", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.annualTurn = :annualTurn"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByCompAdd", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.compAdd = :compAdd"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByTotEmp", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.totEmp = :totEmp"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByEmpUnder", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.empUnder = :empUnder"), @NamedQuery(name = "HrPersonnelPreviousCompany.findBySalJoin", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.salJoin = :salJoin"), @NamedQuery(name = "HrPersonnelPreviousCompany.findBySalLeave", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.salLeave = :salLeave"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByReasonLeaving", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.reasonLeaving = :reasonLeaving"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByDefaultComp", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByStatFlag", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByStatUpFlag", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByModDate", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByAuthBy", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrPersonnelPreviousCompany.findByEnteredBy", query = "SELECT h FROM HrPersonnelPreviousCompany h WHERE h.enteredBy = :enteredBy")})
public class HrPersonnelPreviousCompany extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrPersonnelPreviousCompanyPK hrPersonnelPreviousCompanyPK;
    @Basic(optional = false)
    @Column(name = "COMP_NAME")
    private String compName;
    @Column(name = "ANNUAL_TURN")
    private Double annualTurn;
    @Column(name = "COMP_ADD")
    private String compAdd;
    @Column(name = "TOT_EMP")
    private Integer totEmp;
    @Column(name = "EMP_UNDER")
    private Integer empUnder;
    @Column(name = "SAL_JOIN")
    private Double salJoin;
    @Column(name = "SAL_LEAVE")
    private Double salLeave;
    @Column(name = "REASON_LEAVING")
    private String reasonLeaving;
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
    @Column(name = "DESG_JOIN")
    private String desgJoin;
    @Column(name = "DESG_LEAVE")
    private String desgLeave;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;
    //    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "DESG_JOIN", referencedColumnName = "STRUCT_CODE")})
//    @ManyToOne(optional = false)
//    private HrMstStruct hrMstStruct;
//    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "DESG_LEAVE", referencedColumnName = "STRUCT_CODE")})
//    @ManyToOne(optional = false)
//    private HrMstStruct hrMstStruct1;

    public HrPersonnelPreviousCompany() {
    }

    public HrPersonnelPreviousCompany(HrPersonnelPreviousCompanyPK hrPersonnelPreviousCompanyPK) {
        this.hrPersonnelPreviousCompanyPK = hrPersonnelPreviousCompanyPK;
    }

    public HrPersonnelPreviousCompany(HrPersonnelPreviousCompanyPK hrPersonnelPreviousCompanyPK, String compName) {
        this.hrPersonnelPreviousCompanyPK = hrPersonnelPreviousCompanyPK;
        this.compName = compName;
    }

    public HrPersonnelPreviousCompany(int compCode, long empCode, long prevCompCode, Date durFrom, Date durTo) {
        this.hrPersonnelPreviousCompanyPK = new HrPersonnelPreviousCompanyPK(compCode, empCode, prevCompCode, durFrom, durTo);
    }

    public HrPersonnelPreviousCompanyPK getHrPersonnelPreviousCompanyPK() {
        return hrPersonnelPreviousCompanyPK;
    }

    public void setHrPersonnelPreviousCompanyPK(HrPersonnelPreviousCompanyPK hrPersonnelPreviousCompanyPK) {
        this.hrPersonnelPreviousCompanyPK = hrPersonnelPreviousCompanyPK;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public Double getAnnualTurn() {
        return annualTurn;
    }

    public void setAnnualTurn(Double annualTurn) {
        this.annualTurn = annualTurn;
    }

    public String getCompAdd() {
        return compAdd;
    }

    public void setCompAdd(String compAdd) {
        this.compAdd = compAdd;
    }

    public Integer getTotEmp() {
        return totEmp;
    }

    public void setTotEmp(Integer totEmp) {
        this.totEmp = totEmp;
    }

    public Integer getEmpUnder() {
        return empUnder;
    }

    public void setEmpUnder(Integer empUnder) {
        this.empUnder = empUnder;
    }

    public Double getSalJoin() {
        return salJoin;
    }

    public void setSalJoin(Double salJoin) {
        this.salJoin = salJoin;
    }

    public Double getSalLeave() {
        return salLeave;
    }

    public void setSalLeave(Double salLeave) {
        this.salLeave = salLeave;
    }

    public String getReasonLeaving() {
        return reasonLeaving;
    }

    public void setReasonLeaving(String reasonLeaving) {
        this.reasonLeaving = reasonLeaving;
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

    public String getDesgJoin() {
        return desgJoin;
    }

    public void setDesgJoin(String desgJoin) {
        this.desgJoin = desgJoin;
    }

    public String getDesgLeave() {
        return desgLeave;
    }

    public void setDesgLeave(String desgLeave) {
        this.desgLeave = desgLeave;
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
        hash += (hrPersonnelPreviousCompanyPK != null ? hrPersonnelPreviousCompanyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelPreviousCompany)) {
            return false;
        }
        HrPersonnelPreviousCompany other = (HrPersonnelPreviousCompany) object;
        if ((this.hrPersonnelPreviousCompanyPK == null && other.hrPersonnelPreviousCompanyPK != null) || (this.hrPersonnelPreviousCompanyPK != null && !this.hrPersonnelPreviousCompanyPK.equals(other.hrPersonnelPreviousCompanyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPersonnelPreviousCompany[hrPersonnelPreviousCompanyPK=" + hrPersonnelPreviousCompanyPK + "]";
    }
}
