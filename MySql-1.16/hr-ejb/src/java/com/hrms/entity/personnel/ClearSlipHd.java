/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrPersonnelDetails;
import com.hrms.entity.payroll.CompanyMaster;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "clear_slip_hd")
@NamedQueries({
@NamedQuery(name = "ClearSlipHd.findAll", query = "SELECT c FROM ClearSlipHd c"),
@NamedQuery(name="ClearSlipHd.findbyPrimarykey",query="select c from ClearSlipHd c where c.clearSlipHdPK.compCode = :compCode and c.clearSlipHdPK.empCode = :empCode ")
})
public class ClearSlipHd extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClearSlipHdPK clearSlipHdPK;
    @Column(name = "SETTLEMENT_DT")
    private String settlementDt;
    @Column(name = "TOT_AMT")
    private Double totAmt;
    @Basic(optional = false)
    @Column(name = "DD_CHEQUE_NU")
    private String ddChequeNu;
    @Column(name = "DD_CHEQUE")
    private String ddCheque;
    @Column(name = "DD_AMT")
    private Double ddAmt;
    @Column(name = "BANK_NAME")
    private String bankName;
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
    @JoinColumn(name = "COMP_CODE", referencedColumnName = "COMPANY_CODE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CompanyMaster companyMaster;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public ClearSlipHd() {
    }

    public ClearSlipHd(ClearSlipHdPK clearSlipHdPK) {
        this.clearSlipHdPK = clearSlipHdPK;
    }

    public ClearSlipHd(ClearSlipHdPK clearSlipHdPK, String ddChequeNu) {
        this.clearSlipHdPK = clearSlipHdPK;
        this.ddChequeNu = ddChequeNu;
    }

    public ClearSlipHd(int compCode, long empCode) {
        this.clearSlipHdPK = new ClearSlipHdPK(compCode, empCode);
    }

    public ClearSlipHdPK getClearSlipHdPK() {
        return clearSlipHdPK;
    }

    public void setClearSlipHdPK(ClearSlipHdPK clearSlipHdPK) {
        this.clearSlipHdPK = clearSlipHdPK;
    }

    public String getSettlementDt() {
        return settlementDt;
    }

    public void setSettlementDt(String settlementDt) {
        this.settlementDt = settlementDt;
    }

    public Double getTotAmt() {
        return totAmt;
    }

    public void setTotAmt(Double totAmt) {
        this.totAmt = totAmt;
    }

    public String getDdChequeNu() {
        return ddChequeNu;
    }

    public void setDdChequeNu(String ddChequeNu) {
        this.ddChequeNu = ddChequeNu;
    }

    public String getDdCheque() {
        return ddCheque;
    }

    public void setDdCheque(String ddCheque) {
        this.ddCheque = ddCheque;
    }

    public Double getDdAmt() {
        return ddAmt;
    }

    public void setDdAmt(Double ddAmt) {
        this.ddAmt = ddAmt;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public CompanyMaster getCompanyMaster() {
        return companyMaster;
    }

    public void setCompanyMaster(CompanyMaster companyMaster) {
        this.companyMaster = companyMaster;
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
        hash += (clearSlipHdPK != null ? clearSlipHdPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClearSlipHd)) {
            return false;
        }
        ClearSlipHd other = (ClearSlipHd) object;
        if ((this.clearSlipHdPK == null && other.clearSlipHdPK != null) || (this.clearSlipHdPK != null && !this.clearSlipHdPK.equals(other.clearSlipHdPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.personnel.ClearSlipHd[clearSlipHdPK=" + clearSlipHdPK + "]";
    }

}
