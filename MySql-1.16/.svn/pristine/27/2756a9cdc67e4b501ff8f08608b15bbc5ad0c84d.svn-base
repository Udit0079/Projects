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
@Table(name = "hr_emp_loan_hd")
@NamedQueries({@NamedQuery(name = "HrEmpLoanHd.findHrEmpLoanHdByCompCodeEmpLoanNo",
    query = "SELECT h FROM HrEmpLoanHd h" +
    " WHERE h.hrEmpLoanHdPK.compCode = :compCode AND" +
    " h.hrEmpLoanHdPK.empLoanNo = :empLoanNo"), @NamedQuery(name = "HrEmpLoanHd.findAll", query = "SELECT h FROM HrEmpLoanHd h"), @NamedQuery(name = "HrEmpLoanHd.findByEmpLoanNo", query = "SELECT h FROM HrEmpLoanHd h WHERE h.hrEmpLoanHdPK.empLoanNo = :empLoanNo"), @NamedQuery(name = "HrEmpLoanHd.findByCompCode", query = "SELECT h FROM HrEmpLoanHd h WHERE h.hrEmpLoanHdPK.compCode = :compCode"), @NamedQuery(name = "HrEmpLoanHd.findByEmpCode", query = "SELECT h FROM HrEmpLoanHd h WHERE h.hrEmpLoanHdPK.empCode = :empCode"), @NamedQuery(name = "HrEmpLoanHd.findByLoanType", query = "SELECT h FROM HrEmpLoanHd h WHERE h.hrEmpLoanHdPK.loanType = :loanType"), @NamedQuery(name = "HrEmpLoanHd.findBySanctionAmt", query = "SELECT h FROM HrEmpLoanHd h WHERE h.sanctionAmt = :sanctionAmt"), @NamedQuery(name = "HrEmpLoanHd.findBySanctionDate", query = "SELECT h FROM HrEmpLoanHd h WHERE h.sanctionDate = :sanctionDate"), @NamedQuery(name = "HrEmpLoanHd.findByRoi", query = "SELECT h FROM HrEmpLoanHd h WHERE h.roi = :roi"), @NamedQuery(name = "HrEmpLoanHd.findByNoInst", query = "SELECT h FROM HrEmpLoanHd h WHERE h.noInst = :noInst"), @NamedQuery(name = "HrEmpLoanHd.findByInstPlan", query = "SELECT h FROM HrEmpLoanHd h WHERE h.instPlan = :instPlan"), @NamedQuery(name = "HrEmpLoanHd.findByPeriodicity", query = "SELECT h FROM HrEmpLoanHd h WHERE h.periodicity = :periodicity"), @NamedQuery(name = "HrEmpLoanHd.findByRepayFlag", query = "SELECT h FROM HrEmpLoanHd h WHERE h.repayFlag = :repayFlag"), @NamedQuery(name = "HrEmpLoanHd.findByStartDate", query = "SELECT h FROM HrEmpLoanHd h WHERE h.startDate = :startDate"), @NamedQuery(name = "HrEmpLoanHd.findByStatFlag", query = "SELECT h FROM HrEmpLoanHd h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrEmpLoanHd.findByStatUpFlag", query = "SELECT h FROM HrEmpLoanHd h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrEmpLoanHd.findByModDate", query = "SELECT h FROM HrEmpLoanHd h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrEmpLoanHd.findByDefaultComp", query = "SELECT h FROM HrEmpLoanHd h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrEmpLoanHd.findByAuthBy", query = "SELECT h FROM HrEmpLoanHd h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrEmpLoanHd.findByEnteredBy", query = "SELECT h FROM HrEmpLoanHd h WHERE h.enteredBy = :enteredBy")})
public class HrEmpLoanHd extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrEmpLoanHdPK hrEmpLoanHdPK;
    @Basic(optional = false)
    @Column(name = "SANCTION_AMT")
    private double sanctionAmt;
    @Basic(optional = false)
    @Column(name = "SANCTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sanctionDate;
    @Basic(optional = false)
    @Column(name = "ROI")
    private double roi;
    @Basic(optional = false)
    @Column(name = "NO_INST")
    private int noInst;
    @Basic(optional = false)
    @Column(name = "INST_PLAN")
    private String instPlan;
    @Basic(optional = false)
    @Column(name = "PERIODICITY")
    private char periodicity;
    @Basic(optional = false)
    @Column(name = "REPAY_FLAG")
    private char repayFlag;
    @Basic(optional = false)
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "LOAN_TYPE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrEmpLoanHd() {
    }

    public HrEmpLoanHd(HrEmpLoanHdPK hrEmpLoanHdPK) {
        this.hrEmpLoanHdPK = hrEmpLoanHdPK;
    }

    public HrEmpLoanHd(HrEmpLoanHdPK hrEmpLoanHdPK, double sanctionAmt, Date sanctionDate, double roi, int noInst, String instPlan, char periodicity, char repayFlag, Date startDate, String statFlag, String statUpFlag, Date modDate, int defaultComp, String authBy, String enteredBy) {
        this.hrEmpLoanHdPK = hrEmpLoanHdPK;
        this.sanctionAmt = sanctionAmt;
        this.sanctionDate = sanctionDate;
        this.roi = roi;
        this.noInst = noInst;
        this.instPlan = instPlan;
        this.periodicity = periodicity;
        this.repayFlag = repayFlag;
        this.startDate = startDate;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.defaultComp = defaultComp;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrEmpLoanHd(long empLoanNo, int compCode, long empCode, String loanType) {
        this.hrEmpLoanHdPK = new HrEmpLoanHdPK(empLoanNo, compCode, empCode, loanType);
    }

    public HrEmpLoanHdPK getHrEmpLoanHdPK() {
        return hrEmpLoanHdPK;
    }

    public void setHrEmpLoanHdPK(HrEmpLoanHdPK hrEmpLoanHdPK) {
        this.hrEmpLoanHdPK = hrEmpLoanHdPK;
    }

    public double getSanctionAmt() {
        return sanctionAmt;
    }

    public void setSanctionAmt(double sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public Date getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(Date sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public int getNoInst() {
        return noInst;
    }

    public void setNoInst(int noInst) {
        this.noInst = noInst;
    }

    public String getInstPlan() {
        return instPlan;
    }

    public void setInstPlan(String instPlan) {
        this.instPlan = instPlan;
    }

    public char getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(char periodicity) {
        this.periodicity = periodicity;
    }

    public char getRepayFlag() {
        return repayFlag;
    }

    public void setRepayFlag(char repayFlag) {
        this.repayFlag = repayFlag;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
        hash += (hrEmpLoanHdPK != null ? hrEmpLoanHdPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpLoanHd)) {
            return false;
        }
        HrEmpLoanHd other = (HrEmpLoanHd) object;
        if ((this.hrEmpLoanHdPK == null && other.hrEmpLoanHdPK != null) || (this.hrEmpLoanHdPK != null && !this.hrEmpLoanHdPK.equals(other.hrEmpLoanHdPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.personnel.HrEmpLoanHd[hrEmpLoanHdPK=" + hrEmpLoanHdPK + "]";
    }
}
