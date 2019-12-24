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
@Table(name = "hr_emp_advance_hd")
@NamedQueries({
    @NamedQuery(name = "HrEmpAdvanceHd.findHrEmpAdvanceHdByCompCodeEmpAdvNo",
    query = "SELECT h FROM HrEmpAdvanceHd h" +
    " WHERE h.hrEmpAdvanceHdPK.compCode = :compCode AND" +
    " h.hrEmpAdvanceHdPK.empAdvNo = :empAdvNo"),
    @NamedQuery(name = "HrEmpAdvanceHd.findAll", query = "SELECT h FROM HrEmpAdvanceHd h"), @NamedQuery(name = "HrEmpAdvanceHd.findByEmpAdvNo", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.hrEmpAdvanceHdPK.empAdvNo = :empAdvNo"), @NamedQuery(name = "HrEmpAdvanceHd.findByCompCode", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.hrEmpAdvanceHdPK.compCode = :compCode"), @NamedQuery(name = "HrEmpAdvanceHd.findByEmpCode", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.hrEmpAdvanceHdPK.empCode = :empCode"), @NamedQuery(name = "HrEmpAdvanceHd.findByAdvance", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.hrEmpAdvanceHdPK.advance = :advance"), @NamedQuery(name = "HrEmpAdvanceHd.findBySanctionDate", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.sanctionDate = :sanctionDate"), @NamedQuery(name = "HrEmpAdvanceHd.findBySanctionAmt", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.sanctionAmt = :sanctionAmt"), @NamedQuery(name = "HrEmpAdvanceHd.findByNoInstall", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.noInstall = :noInstall"), @NamedQuery(name = "HrEmpAdvanceHd.findByPeriodicity", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.periodicity = :periodicity"), @NamedQuery(name = "HrEmpAdvanceHd.findByStartDate", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.startDate = :startDate"), @NamedQuery(name = "HrEmpAdvanceHd.findByRepayFlag", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.repayFlag = :repayFlag"), @NamedQuery(name = "HrEmpAdvanceHd.findByStatFlag", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrEmpAdvanceHd.findByStatUpFlag", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrEmpAdvanceHd.findByModDate", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrEmpAdvanceHd.findByDefaultComp", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrEmpAdvanceHd.findByAuthBy", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrEmpAdvanceHd.findByEnteredBy", query = "SELECT h FROM HrEmpAdvanceHd h WHERE h.enteredBy = :enteredBy")})
public class HrEmpAdvanceHd extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrEmpAdvanceHdPK hrEmpAdvanceHdPK;
    @Basic(optional = false)
    @Column(name = "SANCTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sanctionDate;
    @Basic(optional = false)
    @Column(name = "SANCTION_AMT")
    private double sanctionAmt;
    @Basic(optional = false)
    @Column(name = "NO_INSTALL")
    private int noInstall;
    @Basic(optional = false)
    @Column(name = "PERIODICITY")
    private char periodicity;
    @Basic(optional = false)
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "ADVANCE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrEmpAdvanceHd() {
    }

    public HrEmpAdvanceHd(HrEmpAdvanceHdPK hrEmpAdvanceHdPK) {
        this.hrEmpAdvanceHdPK = hrEmpAdvanceHdPK;
    }

    public HrEmpAdvanceHd(HrEmpAdvanceHdPK hrEmpAdvanceHdPK, Date sanctionDate, double sanctionAmt, int noInstall, char periodicity, Date startDate, char repayFlag, String statFlag, String statUpFlag, Date modDate, int defaultComp, String authBy, String enteredBy) {
        this.hrEmpAdvanceHdPK = hrEmpAdvanceHdPK;
        this.sanctionDate = sanctionDate;
        this.sanctionAmt = sanctionAmt;
        this.noInstall = noInstall;
        this.periodicity = periodicity;
        this.startDate = startDate;
        this.repayFlag = repayFlag;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.defaultComp = defaultComp;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrEmpAdvanceHd(long empAdvNo, int compCode, long empCode, String advance) {
        this.hrEmpAdvanceHdPK = new HrEmpAdvanceHdPK(empAdvNo, compCode, empCode, advance);
    }

    public HrEmpAdvanceHdPK getHrEmpAdvanceHdPK() {
        return hrEmpAdvanceHdPK;
    }

    public void setHrEmpAdvanceHdPK(HrEmpAdvanceHdPK hrEmpAdvanceHdPK) {
        this.hrEmpAdvanceHdPK = hrEmpAdvanceHdPK;
    }

    public Date getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(Date sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public double getSanctionAmt() {
        return sanctionAmt;
    }

    public void setSanctionAmt(double sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public int getNoInstall() {
        return noInstall;
    }

    public void setNoInstall(int noInstall) {
        this.noInstall = noInstall;
    }

    public char getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(char periodicity) {
        this.periodicity = periodicity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
        hash += (hrEmpAdvanceHdPK != null ? hrEmpAdvanceHdPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpAdvanceHd)) {
            return false;
        }
        HrEmpAdvanceHd other = (HrEmpAdvanceHd) object;
        if ((this.hrEmpAdvanceHdPK == null && other.hrEmpAdvanceHdPK != null) || (this.hrEmpAdvanceHdPK != null && !this.hrEmpAdvanceHdPK.equals(other.hrEmpAdvanceHdPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrEmpAdvanceHd[hrEmpAdvanceHdPK=" + hrEmpAdvanceHdPK + "]";
    }
}
