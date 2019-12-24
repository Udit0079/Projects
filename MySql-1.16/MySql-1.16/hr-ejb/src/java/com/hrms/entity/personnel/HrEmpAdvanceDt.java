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
import javax.persistence.CascadeType;
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
 * @author Administrator
 */
@Entity
@Table(name = "hr_emp_advance_dt")
@NamedQueries({
    @NamedQuery(name = "HrEmpAdvanceDt.findAll", query = "SELECT h FROM HrEmpAdvanceDt h"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByEmpAdvNo", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.hrEmpAdvanceDtPK.empAdvNo = :empAdvNo"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByCompCode", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.hrEmpAdvanceDtPK.compCode = :compCode"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByEmpCode", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.hrEmpAdvanceDtPK.empCode = :empCode"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByAdvance", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.hrEmpAdvanceDtPK.advance = :advance"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByDueDate", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.hrEmpAdvanceDtPK.dueDate = :dueDate"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByAmount", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.amount = :amount"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByVarInstall", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.varInstall = :varInstall"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByTotInstall", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.totInstall = :totInstall"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByRepayFlag", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.repayFlag = :repayFlag"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByStatFlag", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByStatUpFlag", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByModDate", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByDefaultComp", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByAuthBy", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrEmpAdvanceDt.findByEnteredBy", query = "SELECT h FROM HrEmpAdvanceDt h WHERE h.enteredBy = :enteredBy")
})
public class HrEmpAdvanceDt extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrEmpAdvanceDtPK hrEmpAdvanceDtPK;
    @Basic(optional = false)
    @Column(name = "AMOUNT", nullable = false)
    private double amount;
    @Basic(optional = false)
    @Column(name = "VAR_INSTALL", nullable = false)
    private int varInstall;
    @Basic(optional = false)
    @Column(name = "TOT_INSTALL", nullable = false)
    private int totInstall;
    @Basic(optional = false)
    @Column(name = "REPAY_FLAG", nullable = false)
    private char repayFlag;
    @Basic(optional = false)
    @Column(name = "STAT_FLAG", nullable = false, length = 2)
    private String statFlag;
    @Basic(optional = false)
    @Column(name = "STAT_UP_FLAG", nullable = false, length = 2)
    private String statUpFlag;
    @Basic(optional = false)
    @Column(name = "MOD_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Basic(optional = false)
    @Column(name = "DEFAULT_COMP", nullable = false)
    private int defaultComp;
    @Basic(optional = false)
    @Column(name = "AUTH_BY", nullable = false, length = 100)
    private String authBy;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY", nullable = false, length = 100)
    private String enteredBy;
    @JoinColumns({@JoinColumn(name = "EMP_ADV_NO", referencedColumnName = "EMP_ADV_NO", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "ADVANCE", referencedColumnName = "ADVANCE", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private HrEmpAdvanceHd hrEmpAdvanceHd;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "ADVANCE", referencedColumnName = "STRUCT_CODE", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "hrEmpAdvanceDt")
//    private HrEmpAdvanceHd hrEmpAdvanceHd;
    public HrEmpAdvanceDt() {
    }

    public HrEmpAdvanceDt(HrEmpAdvanceDtPK hrEmpAdvanceDtPK) {
        this.hrEmpAdvanceDtPK = hrEmpAdvanceDtPK;
    }

    public HrEmpAdvanceDt(HrEmpAdvanceDtPK hrEmpAdvanceDtPK, double amount, int varInstall, int totInstall, char repayFlag, String statFlag, String statUpFlag, Date modDate, int defaultComp, String authBy, String enteredBy) {
        this.hrEmpAdvanceDtPK = hrEmpAdvanceDtPK;
        this.amount = amount;
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

    public HrEmpAdvanceDt(long empAdvNo, int compCode, long empCode, String advance, Date dueDate) {
        this.hrEmpAdvanceDtPK = new HrEmpAdvanceDtPK(empAdvNo, compCode, empCode, advance, dueDate);
    }

    public HrEmpAdvanceDtPK getHrEmpAdvanceDtPK() {
        return hrEmpAdvanceDtPK;
    }

    public void setHrEmpAdvanceDtPK(HrEmpAdvanceDtPK hrEmpAdvanceDtPK) {
        this.hrEmpAdvanceDtPK = hrEmpAdvanceDtPK;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public HrEmpAdvanceHd getHrEmpAdvanceHd() {
        return hrEmpAdvanceHd;
    }

    public void setHrEmpAdvanceHd(HrEmpAdvanceHd hrEmpAdvanceHd) {
        this.hrEmpAdvanceHd = hrEmpAdvanceHd;
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
        hash += (hrEmpAdvanceDtPK != null ? hrEmpAdvanceDtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpAdvanceDt)) {
            return false;
        }
        HrEmpAdvanceDt other = (HrEmpAdvanceDt) object;
        if ((this.hrEmpAdvanceDtPK == null && other.hrEmpAdvanceDtPK != null) || (this.hrEmpAdvanceDtPK != null && !this.hrEmpAdvanceDtPK.equals(other.hrEmpAdvanceDtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.personnel.HrEmpAdvanceDt[hrEmpAdvanceDtPK=" + hrEmpAdvanceDtPK + "]";
    }
}
