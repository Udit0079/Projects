/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrMstStruct;
import com.hrms.entity.hr.HrPersonnelDetails;
import com.hrms.entity.payroll.CompanyMaster;
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
 * @author admin
 */
@Entity
@Table(name = "clear_slip_det")
@NamedQueries({
    @NamedQuery(name = "ClearSlipDet.findAll", query = "SELECT c FROM ClearSlipDet c"),
    @NamedQuery(name="ClearSlipDet.findByAllPrimarykey",query="SELECT c FROM ClearSlipDet c where c.clearSlipDetPK.compCode = :compCode and c.clearSlipDetPK.empCode = :empCode and c.clearSlipDetPK.deptCode = :deptCode ")
})
public class ClearSlipDet extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClearSlipDetPK clearSlipDetPK;
    @Column(name = "DUE_AMT")
    private Double dueAmt;
    @Column(name = "RECOVERABLE_AMT")
    private Double recoverableAmt;
    @Column(name = "NET_AMT")
    private Double netAmt;
    @Column(name = "REMARKS")
    private String remarks;
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
    private ClearSlipHd clearSlipHd;
    @JoinColumn(name = "COMP_CODE", referencedColumnName = "COMPANY_CODE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CompanyMaster companyMaster;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "DEPT_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrMstStruct hrMstStruct;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public ClearSlipDet() {
    }

    public ClearSlipDet(ClearSlipDetPK clearSlipDetPK) {
        this.clearSlipDetPK = clearSlipDetPK;
    }

    public ClearSlipDet(int compCode, long empCode, String deptCode) {
        this.clearSlipDetPK = new ClearSlipDetPK(compCode, empCode, deptCode);
    }

    public ClearSlipDetPK getClearSlipDetPK() {
        return clearSlipDetPK;
    }

    public void setClearSlipDetPK(ClearSlipDetPK clearSlipDetPK) {
        this.clearSlipDetPK = clearSlipDetPK;
    }

    public Double getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(Double dueAmt) {
        this.dueAmt = dueAmt;
    }

    public Double getRecoverableAmt() {
        return recoverableAmt;
    }

    public void setRecoverableAmt(Double recoverableAmt) {
        this.recoverableAmt = recoverableAmt;
    }

    public Double getNetAmt() {
        return netAmt;
    }

    public void setNetAmt(Double netAmt) {
        this.netAmt = netAmt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public ClearSlipHd getClearSlipHd() {
        return clearSlipHd;
    }

    public void setClearSlipHd(ClearSlipHd clearSlipHd) {
        this.clearSlipHd = clearSlipHd;
    }

    public CompanyMaster getCompanyMaster() {
        return companyMaster;
    }

    public void setCompanyMaster(CompanyMaster companyMaster) {
        this.companyMaster = companyMaster;
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
        hash += (clearSlipDetPK != null ? clearSlipDetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClearSlipDet)) {
            return false;
        }
        ClearSlipDet other = (ClearSlipDet) object;
        if ((this.clearSlipDetPK == null && other.clearSlipDetPK != null) || (this.clearSlipDetPK != null && !this.clearSlipDetPK.equals(other.clearSlipDetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.personnel.ClearSlipDet[clearSlipDetPK=" + clearSlipDetPK + "]";
    }

}
