/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "hr_mst_comp_loan")
@NamedQueries({
    @NamedQuery(name = "HrMstCompLoan.findAll", query = "SELECT h FROM HrMstCompLoan h"),
    @NamedQuery(name = "HrMstCompLoan.findHrMstCompLoanByCompCode", query = "SELECT h FROM HrMstCompLoan h WHERE h.hrMstCompLoanPK.compCode = :compCode"),
    @NamedQuery(name = "HrMstCompLoan.findByFromDate", query = "SELECT h FROM HrMstCompLoan h WHERE h.hrMstCompLoanPK.fromDate = :fromDate"),
    @NamedQuery(name = "HrMstCompLoan.findByToDate", query = "SELECT h FROM HrMstCompLoan h WHERE h.hrMstCompLoanPK.toDate = :toDate"),
    @NamedQuery(name = "HrMstCompLoan.findByLoanBudget", query = "SELECT h FROM HrMstCompLoan h WHERE h.loanBudget = :loanBudget"),
    @NamedQuery(name = "HrMstCompLoan.findByLoanAvailable", query = "SELECT h FROM HrMstCompLoan h WHERE h.loanAvailable = :loanAvailable"),
    @NamedQuery(name = "HrMstCompLoan.findByPrincipleCollection", query = "SELECT h FROM HrMstCompLoan h WHERE h.principleCollection = :principleCollection"),
    @NamedQuery(name = "HrMstCompLoan.findByStatFlag", query = "SELECT h FROM HrMstCompLoan h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrMstCompLoan.findByStatUpFlag", query = "SELECT h FROM HrMstCompLoan h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrMstCompLoan.findByModDate", query = "SELECT h FROM HrMstCompLoan h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrMstCompLoan.findByDefaultComp", query = "SELECT h FROM HrMstCompLoan h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrMstCompLoan.findByAuthBy", query = "SELECT h FROM HrMstCompLoan h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrMstCompLoan.findByEnteredBy", query = "SELECT h FROM HrMstCompLoan h WHERE h.enteredBy = :enteredBy")})
public class HrMstCompLoan extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrMstCompLoanPK hrMstCompLoanPK;
    @Basic(optional = false)
    @Column(name = "LOAN_BUDGET")
    private double loanBudget;
    @Basic(optional = false)
    @Column(name = "LOAN_AVAILABLE")
    private double loanAvailable;
    @Basic(optional = false)
    @Column(name = "PRINCIPLE_COLLECTION")
    private double principleCollection;
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

    public HrMstCompLoan() {
    }

    public HrMstCompLoan(HrMstCompLoanPK hrMstCompLoanPK) {
        this.hrMstCompLoanPK = hrMstCompLoanPK;
    }

    public HrMstCompLoan(HrMstCompLoanPK hrMstCompLoanPK, double loanBudget, double loanAvailable, double principleCollection, String statFlag, String statUpFlag, Date modDate, int defaultComp, String authBy, String enteredBy) {
        this.hrMstCompLoanPK = hrMstCompLoanPK;
        this.loanBudget = loanBudget;
        this.loanAvailable = loanAvailable;
        this.principleCollection = principleCollection;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.defaultComp = defaultComp;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrMstCompLoan(int compCode, Date fromDate, Date toDate) {
        this.hrMstCompLoanPK = new HrMstCompLoanPK(compCode, fromDate, toDate);
    }

    public HrMstCompLoanPK getHrMstCompLoanPK() {
        return hrMstCompLoanPK;
    }

    public void setHrMstCompLoanPK(HrMstCompLoanPK hrMstCompLoanPK) {
        this.hrMstCompLoanPK = hrMstCompLoanPK;
    }

    public double getLoanBudget() {
        return loanBudget;
    }

    public void setLoanBudget(double loanBudget) {
        this.loanBudget = loanBudget;
    }

    public double getLoanAvailable() {
        return loanAvailable;
    }

    public void setLoanAvailable(double loanAvailable) {
        this.loanAvailable = loanAvailable;
    }

    public double getPrincipleCollection() {
        return principleCollection;
    }

    public void setPrincipleCollection(double principleCollection) {
        this.principleCollection = principleCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrMstCompLoanPK != null ? hrMstCompLoanPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstCompLoan)) {
            return false;
        }
        HrMstCompLoan other = (HrMstCompLoan) object;
        if ((this.hrMstCompLoanPK == null && other.hrMstCompLoanPK != null) || (this.hrMstCompLoanPK != null && !this.hrMstCompLoanPK.equals(other.hrMstCompLoanPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.personnel.HrMstCompLoan[hrMstCompLoanPK=" + hrMstCompLoanPK + "]";
    }
}
