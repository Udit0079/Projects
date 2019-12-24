/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import com.hrms.common.to.HrSalaryProcessingDetailTO;
import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrPersonnelDetails;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author root
 */
@Entity
@Table(name = "hr_salary_processing")
@NamedQueries({
    @NamedQuery(name = "HrSalaryProcessing.findAll", query = "SELECT h FROM HrSalaryProcessing h"),
    @NamedQuery(name = "HrSalaryProcessing.findByCompCode", query = "SELECT h FROM HrSalaryProcessing h WHERE h.hrSalaryProcessingPK.compCode = :compCode"),
    @NamedQuery(name = "HrSalaryProcessing.findByEmpCode", query = "SELECT h FROM HrSalaryProcessing h WHERE h.hrSalaryProcessingPK.empCode = :empCode"),
    @NamedQuery(name = "HrSalaryProcessing.findByCalDateFrom", query = "SELECT h FROM HrSalaryProcessing h WHERE h.hrSalaryProcessingPK.calDateFrom = :calDateFrom"),
    @NamedQuery(name = "HrSalaryProcessing.findByCalDateTo", query = "SELECT h FROM HrSalaryProcessing h WHERE h.hrSalaryProcessingPK.calDateTo = :calDateTo"),
    @NamedQuery(name = "HrSalaryProcessing.findByMonths", query = "SELECT h FROM HrSalaryProcessing h WHERE h.hrSalaryProcessingPK.months = :months"),
    @NamedQuery(name = "HrSalaryProcessing.findBySalary", query = "SELECT h FROM HrSalaryProcessing h WHERE h.salary = :salary"),
    @NamedQuery(name = "HrSalaryProcessing.findByPostFlag", query = "SELECT h FROM HrSalaryProcessing h WHERE h.postFlag = :postFlag"),
    @NamedQuery(name = "HrSalaryProcessing.findByReceiptFlag", query = "SELECT h FROM HrSalaryProcessing h WHERE h.receiptFlag = :receiptFlag"),
    @NamedQuery(name = "HrSalaryProcessing.findByRefNo", query = "SELECT h FROM HrSalaryProcessing h WHERE h.refNo = :refNo"),
    @NamedQuery(name = "HrSalaryProcessing.findByDefaultComp", query = "SELECT h FROM HrSalaryProcessing h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrSalaryProcessing.findByStatFlag", query = "SELECT h FROM HrSalaryProcessing h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrSalaryProcessing.findByStatUpFlag", query = "SELECT h FROM HrSalaryProcessing h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrSalaryProcessing.findByModDate", query = "SELECT h FROM HrSalaryProcessing h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrSalaryProcessing.findByAuthBy", query = "SELECT h FROM HrSalaryProcessing h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrSalaryProcessing.findByEnteredBy", query = "SELECT h FROM HrSalaryProcessing h WHERE h.enteredBy = :enteredBy"),
    @NamedQuery(name = "HrSalaryProcessing.findEmpSalaryForTheMonthWithPostFlag", query = "select h FROM HrSalaryProcessing h where h.hrSalaryProcessingPK.compCode = :compCode"
    + " and h.hrSalaryProcessingPK.empCode = :empCode and h.hrSalaryProcessingPK.calDateFrom = :calDateFrom "
    + "and h.hrSalaryProcessingPK.calDateTo = :calDateTo and h.hrSalaryProcessingPK.months = :months "
    + "and h.postFlag = :postFlag"),
    @NamedQuery(name = "HrSalaryProcessing.findEmpSalaryTransferForTheMonth", query = "select h FROM HrSalaryProcessing h where h.hrSalaryProcessingPK.compCode = :compCode"
    + " and h.hrSalaryProcessingPK.empCode = :empCode and h.hrSalaryProcessingPK.calDateFrom = :calDateFrom "
    + "and h.hrSalaryProcessingPK.calDateTo = :calDateTo and h.receiptFlag = :receiptFlag and h.hrSalaryProcessingPK.months = :months "
    + "and h.postFlag = :postFlag"),
    @NamedQuery(name = "HrSalaryProcessing.findEmpSalaryForyear", query = "select h FROM HrSalaryProcessing h where h.hrSalaryProcessingPK.compCode = :compCode"
    + " and h.hrSalaryProcessingPK.empCode = :empCode and h.hrSalaryProcessingPK.calDateFrom = :calDateFrom "
    + "and h.hrSalaryProcessingPK.calDateTo = :calDateTo"),
//    @NamedQuery(name = "HrSalaryProcessing.findEmpSalaryForYearWithPostFlag", query = "select h FROM HrSalaryProcessing h where h.hrSalaryProcessingPK.compCode = :compCode"
//    + " and h.hrSalaryProcessingPK.empCode = :empCode and h.hrSalaryProcessingPK.calDateFrom = :calDateFrom "
//    + "and h.hrSalaryProcessingPK.calDateTo = :calDateTo and h.postFlag = :postFlag")
    @NamedQuery(name = "HrSalaryProcessing.findEmpSalaryForYearWithPostFlag", query = "select h FROM HrSalaryProcessing h where h.hrSalaryProcessingPK.compCode = :compCode"
    + " and h.hrSalaryProcessingPK.empCode = :empCode and h.postFlag = :postFlag")
})
public class HrSalaryProcessing extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrSalaryProcessingPK hrSalaryProcessingPK;
    @Basic(optional = false)
    @Column(name = "SALARY")
    private double salary;
    @Basic(optional = false)
    @Column(name = "POST_FLAG")
    private char postFlag;
    @Column(name = "RECEIPT_FLAG")
    private Character receiptFlag;
    @Column(name = "REF_NO")
    private Integer refNo;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
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
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @NotNull
    @Column(name = "GROSS_SALARY")
    private double grossSalary;
    @NotNull
    @Column(name = "DEDUCTIVE_TAX")
    private double deductiveTax;
//    @JoinColumns({
//        @JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false),
//        @JoinColumn(name = "CAL_DATE_FROM", referencedColumnName = "DATE_FROM", insertable = false, updatable = false),
//        @JoinColumn(name = "CAL_DATE_TO", referencedColumnName = "DATE_TO", insertable = false, updatable = false)})
//    @ManyToOne(optional = false)
//    private HrPayrollCalendar hrPayrollCalendar;
    @JoinColumns({
        @JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false),
        @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;    

    public HrSalaryProcessing() {
    }

    public HrSalaryProcessing(HrSalaryProcessingPK hrSalaryProcessingPK) {
        this.hrSalaryProcessingPK = hrSalaryProcessingPK;
    }

    public HrSalaryProcessing(HrSalaryProcessingPK hrSalaryProcessingPK, double salary, char postFlag, String statFlag, String statUpFlag, Date modDate, String authBy, String enteredBy) {
        this.hrSalaryProcessingPK = hrSalaryProcessingPK;
        this.salary = salary;
        this.postFlag = postFlag;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrSalaryProcessing(int compCode, long empCode, Date calDateFrom, Date calDateTo, String months) {
        this.hrSalaryProcessingPK = new HrSalaryProcessingPK(compCode, empCode, calDateFrom, calDateTo, months);
    }

    public HrSalaryProcessingPK getHrSalaryProcessingPK() {
        return hrSalaryProcessingPK;
    }

    public void setHrSalaryProcessingPK(HrSalaryProcessingPK hrSalaryProcessingPK) {
        this.hrSalaryProcessingPK = hrSalaryProcessingPK;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public char getPostFlag() {
        return postFlag;
    }

    public void setPostFlag(char postFlag) {
        this.postFlag = postFlag;
    }

    public Character getReceiptFlag() {
        return receiptFlag;
    }

    public void setReceiptFlag(Character receiptFlag) {
        this.receiptFlag = receiptFlag;
    }

    public Integer getRefNo() {
        return refNo;
    }

    public void setRefNo(Integer refNo) {
        this.refNo = refNo;
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

//    public HrPayrollCalendar getHrPayrollCalendar() {
//        return hrPayrollCalendar;
//    }
//
//    public void setHrPayrollCalendar(HrPayrollCalendar hrPayrollCalendar) {
//        this.hrPayrollCalendar = hrPayrollCalendar;
//    }

    public HrPersonnelDetails getHrPersonnelDetails() {
        return hrPersonnelDetails;
    }

    public void setHrPersonnelDetails(HrPersonnelDetails hrPersonnelDetails) {
        this.hrPersonnelDetails = hrPersonnelDetails;
    }

    public double getDeductiveTax() {
        return deductiveTax;
    }

    public void setDeductiveTax(double deductiveTax) {
        this.deductiveTax = deductiveTax;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrSalaryProcessingPK != null ? hrSalaryProcessingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSalaryProcessing)) {
            return false;
        }
        HrSalaryProcessing other = (HrSalaryProcessing) object;
        if ((this.hrSalaryProcessingPK == null && other.hrSalaryProcessingPK != null) || (this.hrSalaryProcessingPK != null && !this.hrSalaryProcessingPK.equals(other.hrSalaryProcessingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrSalaryProcessing[hrSalaryProcessingPK=" + hrSalaryProcessingPK + "]";
    }
}
