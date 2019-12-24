/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "hr_salary_allocation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSalaryAllocation.findAll", query = "SELECT h FROM HrSalaryAllocation h"),
    @NamedQuery(name = "HrSalaryAllocation.findByEmpCode", query = "SELECT h FROM HrSalaryAllocation h WHERE h.hrSalaryAllocationPK.empCode = :empCode"),
    @NamedQuery(name = "HrSalaryAllocation.findBySlabCode", query = "SELECT h FROM HrSalaryAllocation h WHERE h.hrSalaryAllocationPK.slabCode = :slabCode"),
    @NamedQuery(name = "HrSalaryAllocation.findByBasicSalary", query = "SELECT h FROM HrSalaryAllocation h WHERE h.basicSalary = :basicSalary"),
    @NamedQuery(name = "HrSalaryAllocation.findByAllocationDate", query = "SELECT h FROM HrSalaryAllocation h WHERE h.allocationDate = :allocationDate"),
    @NamedQuery(name = "HrSalaryAllocation.findByStatFlag", query = "SELECT h FROM HrSalaryAllocation h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrSalaryAllocation.findByStatUpFlag", query = "SELECT h FROM HrSalaryAllocation h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrSalaryAllocation.findByAuthBy", query = "SELECT h FROM HrSalaryAllocation h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrSalaryAllocation.findByModDate", query = "SELECT h FROM HrSalaryAllocation h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrSalaryAllocation.findByEnteredBy", query = "SELECT h FROM HrSalaryAllocation h WHERE h.enteredBy = :enteredBy"),
    @NamedQuery(name = "HrSalaryAllocation.findSalaryAllocateForMonth", query = "select h from HrSalaryAllocation h where h.hrSalaryAllocationPK.compCode = :compCode and h.hrSalaryAllocationPK.empCode = :empCode"),
    @NamedQuery(name = "HrSalaryAllocation.findByEmpMonDatesComponenetType", query = "select h from HrSalaryAllocation h where h.hrSalaryAllocationPK.compCode = :compCode and h.hrSalaryAllocationPK.empCode = :empCode "),
    @NamedQuery(name = "HrSalaryAllocation.findsalaryAllocByEmpCode", query = "SELECT h FROM HrSalaryAllocation h where h.hrSalaryAllocationPK.compCode = :compCode and h.hrSalaryAllocationPK.empCode = :empCode")
})
public class HrSalaryAllocation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrSalaryAllocationPK hrSalaryAllocationPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BASIC_SALARY")
    private Float basicSalary;
    @Column(name = "ALLOCATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date allocationDate;
    @Size(max = 2)
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Size(max = 2)
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Size(max = 255)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Size(max = 255)
    @Column(name = "ENTERED_BY")
    private String enteredBy;

    public HrSalaryAllocation() {
    }

    public HrSalaryAllocation(HrSalaryAllocationPK hrSalaryAllocationPK) {
        this.hrSalaryAllocationPK = hrSalaryAllocationPK;
    }

    public HrSalaryAllocation(Long empCode, String slabCode) {
        this.hrSalaryAllocationPK = new HrSalaryAllocationPK(empCode, slabCode);
    }

    public HrSalaryAllocationPK getHrSalaryAllocationPK() {
        return hrSalaryAllocationPK;
    }

    public void setHrSalaryAllocationPK(HrSalaryAllocationPK hrSalaryAllocationPK) {
        this.hrSalaryAllocationPK = hrSalaryAllocationPK;
    }

    public Float getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Float basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Date getAllocationDate() {
        return allocationDate;
    }

    public void setAllocationDate(Date allocationDate) {
        this.allocationDate = allocationDate;
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

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
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
        hash += (hrSalaryAllocationPK != null ? hrSalaryAllocationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSalaryAllocation)) {
            return false;
        }
        HrSalaryAllocation other = (HrSalaryAllocation) object;
        if ((this.hrSalaryAllocationPK == null && other.hrSalaryAllocationPK != null) || (this.hrSalaryAllocationPK != null && !this.hrSalaryAllocationPK.equals(other.hrSalaryAllocationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.HrSalaryAllocation[ hrSalaryAllocationPK=" + hrSalaryAllocationPK + " ]";
    }
}
