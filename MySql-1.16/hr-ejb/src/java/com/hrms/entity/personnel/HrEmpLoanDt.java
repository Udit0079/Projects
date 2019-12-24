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
@Table(name = "hr_emp_loan_dt")
@NamedQueries({@NamedQuery(name = "HrEmpLoanDt.findAll", query = "SELECT h FROM HrEmpLoanDt h"), @NamedQuery(name = "HrEmpLoanDt.findByEmpLoanNo", query = "SELECT h FROM HrEmpLoanDt h WHERE h.hrEmpLoanDtPK.empLoanNo = :empLoanNo"), @NamedQuery(name = "HrEmpLoanDt.findByCompCode", query = "SELECT h FROM HrEmpLoanDt h WHERE h.hrEmpLoanDtPK.compCode = :compCode"), @NamedQuery(name = "HrEmpLoanDt.findByEmpCode", query = "SELECT h FROM HrEmpLoanDt h WHERE h.hrEmpLoanDtPK.empCode = :empCode"), @NamedQuery(name = "HrEmpLoanDt.findByLoanType", query = "SELECT h FROM HrEmpLoanDt h WHERE h.hrEmpLoanDtPK.loanType = :loanType"), @NamedQuery(name = "HrEmpLoanDt.findByDueDate", query = "SELECT h FROM HrEmpLoanDt h WHERE h.hrEmpLoanDtPK.dueDate = :dueDate"), @NamedQuery(name = "HrEmpLoanDt.findByPrincipal", query = "SELECT h FROM HrEmpLoanDt h WHERE h.principal = :principal"), @NamedQuery(name = "HrEmpLoanDt.findByInterest", query = "SELECT h FROM HrEmpLoanDt h WHERE h.interest = :interest"), @NamedQuery(name = "HrEmpLoanDt.findByVarInstall", query = "SELECT h FROM HrEmpLoanDt h WHERE h.varInstall = :varInstall"), @NamedQuery(name = "HrEmpLoanDt.findByTotInstall", query = "SELECT h FROM HrEmpLoanDt h WHERE h.totInstall = :totInstall"), @NamedQuery(name = "HrEmpLoanDt.findByRepayFlag", query = "SELECT h FROM HrEmpLoanDt h WHERE h.repayFlag = :repayFlag"), @NamedQuery(name = "HrEmpLoanDt.findByStatFlag", query = "SELECT h FROM HrEmpLoanDt h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrEmpLoanDt.findByStatUpFlag", query = "SELECT h FROM HrEmpLoanDt h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrEmpLoanDt.findByModDate", query = "SELECT h FROM HrEmpLoanDt h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrEmpLoanDt.findByDefaultComp", query = "SELECT h FROM HrEmpLoanDt h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrEmpLoanDt.findByAuthBy", query = "SELECT h FROM HrEmpLoanDt h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrEmpLoanDt.findByEnteredBy", query = "SELECT h FROM HrEmpLoanDt h WHERE h.enteredBy = :enteredBy")})
public class HrEmpLoanDt extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrEmpLoanDtPK hrEmpLoanDtPK;
    @Basic(optional = false)
    @Column(name = "PRINCIPAL")
    private double principal;
    @Basic(optional = false)
    @Column(name = "INTEREST")
    private double interest;
    @Basic(optional = false)
    @Column(name = "VAR_INSTALL")
    private int varInstall;
    @Basic(optional = false)
    @Column(name = "TOT_INSTALL")
    private int totInstall;
    @Basic(optional = false)
    @Column(name = "REPAY_FLAG")
    private char repayFlag;
    @Basic(optional = false)
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Basic(optional = false)
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Basic(optional = false)
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Basic(optional = false)
    @Column(name = "DEFAULT_COMP")
    private int defaultComp;
    @Basic(optional = false)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @JoinColumns({@JoinColumn(name = "EMP_LOAN_NO", referencedColumnName = "EMP_LOAN_NO", insertable = false, updatable = false), @JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "LOAN_TYPE", referencedColumnName = "LOAN_TYPE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrEmpLoanHd hrEmpLoanHd;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "LOAN_TYPE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrEmpLoanDt() {
    }

    public HrEmpLoanDt(HrEmpLoanDtPK hrEmpLoanDtPK) {
        this.hrEmpLoanDtPK = hrEmpLoanDtPK;
    }

    public HrEmpLoanDt(HrEmpLoanDtPK hrEmpLoanDtPK, double principal, double interest, int varInstall, int totInstall, char repayFlag, String statFlag, String statUpFlag, Date modDate, int defaultComp, String authBy, String enteredBy) {
        this.hrEmpLoanDtPK = hrEmpLoanDtPK;
        this.principal = principal;
        this.interest = interest;
        this.varInstall = varInstall;
        this.totInstall = totInstall;
        this.repayFlag = repayFlag;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.defaultComp = defaultComp;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrEmpLoanDt(long empLoanNo, int compCode, long empCode, String loanType, Date dueDate) {
        this.hrEmpLoanDtPK = new HrEmpLoanDtPK(empLoanNo, compCode, empCode, loanType, dueDate);
    }

    public HrEmpLoanDtPK getHrEmpLoanDtPK() {
        return hrEmpLoanDtPK;
    }

    public void setHrEmpLoanDtPK(HrEmpLoanDtPK hrEmpLoanDtPK) {
        this.hrEmpLoanDtPK = hrEmpLoanDtPK;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getVarInstall() {
        return varInstall;
    }

    public void setVarInstall(int varInstall) {
        this.varInstall = varInstall;
    }

    public int getTotInstall() {
        return totInstall;
    }

    public void setTotInstall(int totInstall) {
        this.totInstall = totInstall;
    }

    public char getRepayFlag() {
        return repayFlag;
    }

    public void setRepayFlag(char repayFlag) {
        this.repayFlag = repayFlag;
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

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
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

    public HrEmpLoanHd getHrEmpLoanHd() {
        return hrEmpLoanHd;
    }

    public void setHrEmpLoanHd(HrEmpLoanHd hrEmpLoanHd) {
        this.hrEmpLoanHd = hrEmpLoanHd;
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
        hash += (hrEmpLoanDtPK != null ? hrEmpLoanDtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpLoanDt)) {
            return false;
        }
        HrEmpLoanDt other = (HrEmpLoanDt) object;
        if ((this.hrEmpLoanDtPK == null && other.hrEmpLoanDtPK != null) || (this.hrEmpLoanDtPK != null && !this.hrEmpLoanDtPK.equals(other.hrEmpLoanDtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.personnel.HrEmpLoanDt[hrEmpLoanDtPK=" + hrEmpLoanDtPK + "]";
    }
}
