/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "hr_salary_processing_dtl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSalaryProcessingDtl.findAll", query = "SELECT h FROM HrSalaryProcessingDtl h"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByRefNo", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.refNo = :refNo"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByComponentType", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.componentType = :componentType"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByEmpCode", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.empCode = :empCode"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByCompCode", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.compCode = :compCode"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByAmount", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.amount = :amount"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByDefaultComp", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByDescShortCode", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.descShortCode = :descShortCode"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByPurposeCode", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.purposeCode = :purposeCode"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByMonth", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.month = :month"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByEnteredDate", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.enteredDate = :enteredDate"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByMonthStartDate", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.monthStartDate = :monthStartDate"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByMonthLastDate", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.monthLastDate = :monthLastDate"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByGlCode", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.glCode = :glCode"),
    @NamedQuery(name = "HrSalaryProcessingDtl.findByTxnid", query = "SELECT h FROM HrSalaryProcessingDtl h WHERE h.txnid = :txnid")})
public class HrSalaryProcessingDtl extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REF_NO")
    private int refNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "COMPONENT_TYPE")
    private String componentType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMP_CODE")
    private BigInteger empCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private Float amount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Column(name = "DESC_SHORT_CODE")
    private Integer descShortCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PURPOSE_CODE")
    private String purposeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "MONTH")
    private String month;
    @Column(name = "ENTERED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enteredDate;
    @Column(name = "MONTH_START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date monthStartDate;
    @Column(name = "MONTH_LAST_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date monthLastDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "gl_code")
    private String glCode;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "txnid")
    private Long txnid;

    public HrSalaryProcessingDtl() {
    }

    public HrSalaryProcessingDtl(Long txnid) {
        this.txnid = txnid;
    }

    public HrSalaryProcessingDtl(Long txnid, int refNo, String componentType, BigInteger empCode, int compCode, float amount, int defaultComp, String purposeCode, String month, String glCode) {
        this.txnid = txnid;
        this.refNo = refNo;
        this.componentType = componentType;
        this.empCode = empCode;
        this.compCode = compCode;
        this.amount = amount;
        this.defaultComp = defaultComp;
        this.purposeCode = purposeCode;
        this.month = month;
        this.glCode = glCode;
    }

    public int getRefNo() {
        return refNo;
    }

    public void setRefNo(int refNo) {
        this.refNo = refNo;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public BigInteger getEmpCode() {
        return empCode;
    }

    public void setEmpCode(BigInteger empCode) {
        this.empCode = empCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public Integer getDescShortCode() {
        return descShortCode;
    }

    public void setDescShortCode(Integer descShortCode) {
        this.descShortCode = descShortCode;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Date getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(Date enteredDate) {
        this.enteredDate = enteredDate;
    }

    public Date getMonthStartDate() {
        return monthStartDate;
    }

    public void setMonthStartDate(Date monthStartDate) {
        this.monthStartDate = monthStartDate;
    }

    public Date getMonthLastDate() {
        return monthLastDate;
    }

    public void setMonthLastDate(Date monthLastDate) {
        this.monthLastDate = monthLastDate;
    }

    public String getGlCode() {
        return glCode;
    }

    public void setGlCode(String glCode) {
        this.glCode = glCode;
    }

    public Long getTxnid() {
        return txnid;
    }

    public void setTxnid(Long txnid) {
        this.txnid = txnid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnid != null ? txnid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSalaryProcessingDtl)) {
            return false;
        }
        HrSalaryProcessingDtl other = (HrSalaryProcessingDtl) object;
        if ((this.txnid == null && other.txnid != null) || (this.txnid != null && !this.txnid.equals(other.txnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.HrSalaryProcessingDtl[ txnid=" + txnid + " ]";
    }
    
}
