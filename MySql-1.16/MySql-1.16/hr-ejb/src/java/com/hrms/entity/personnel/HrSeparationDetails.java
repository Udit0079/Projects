/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
import com.hrms.entity.payroll.CompanyMaster;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "hr_separation_details")
@NamedQueries({
    @NamedQuery(name = "HrSeparationDetails.findAll", query = "SELECT h FROM HrSeparationDetails h"),
    @NamedQuery(name= "HrSeparationDetails.findBycompCodeempCode" ,query= "SELECT h FROM HrSeparationDetails h where h.hrSeparationDetailsPK.compCode = :compCode and h.hrSeparationDetailsPK.empCode = :empCode"),
    @NamedQuery(name="HrSeparationDetails.updateflag",query="update HrSeparationDetails h set h.statFlag = :statFlag where h.hrSeparationDetailsPK.compCode = :compCode and h.hrSeparationDetailsPK.empCode = :empCode ")
})
public class HrSeparationDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrSeparationDetailsPK hrSeparationDetailsPK;
    @Column(name = "REASON")
    private String reason;
    @Basic(optional = false)
    @Column(name = "SEPARATION")
    private String separation;
    @Column(name = "NOTICE_PRD")
    private Double noticePrd;
    @Column(name = "NOTICE_GVN")
    private Double noticeGvn;
    @Column(name = "SEPA_CODE")
    private String sepaCode;
    @Column(name = "NOTICE_PAY")
    private Double noticePay;
    @Column(name = "NET_DUES")
    private Double netDues;
    @Column(name = "PRD_REC_PAY")
    private Character prdRecPay;
    @Column(name = "RESIGNATION")
    private String resignation;
    @Column(name = "RESIG_ACCEPT")
    private String resigAccept;
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    @Column(name = "UPDATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
    @Column(name = "LOAN_RECOVERY")
    private Double loanRecovery;
    @Column(name = "LOAN_FLG")
    private Character loanFlg;
    @Column(name = "DEFAULT_COMP")
    private String defaultComp;
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

    public HrSeparationDetails() {
    }

    public HrSeparationDetails(HrSeparationDetailsPK hrSeparationDetailsPK) {
        this.hrSeparationDetailsPK = hrSeparationDetailsPK;
    }

    public HrSeparationDetails(HrSeparationDetailsPK hrSeparationDetailsPK, String separation) {
        this.hrSeparationDetailsPK = hrSeparationDetailsPK;
        this.separation = separation;
    }

    public HrSeparationDetails(int compCode, long empCode) {
        this.hrSeparationDetailsPK = new HrSeparationDetailsPK(compCode, empCode);
    }

    public HrSeparationDetailsPK getHrSeparationDetailsPK() {
        return hrSeparationDetailsPK;
    }

    public void setHrSeparationDetailsPK(HrSeparationDetailsPK hrSeparationDetailsPK) {
        this.hrSeparationDetailsPK = hrSeparationDetailsPK;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSeparation() {
        return separation;
    }

    public void setSeparation(String separation) {
        this.separation = separation;
    }

    public Double getNoticePrd() {
        return noticePrd;
    }

    public void setNoticePrd(Double noticePrd) {
        this.noticePrd = noticePrd;
    }

    public Double getNoticeGvn() {
        return noticeGvn;
    }

    public void setNoticeGvn(Double noticeGvn) {
        this.noticeGvn = noticeGvn;
    }

    public String getSepaCode() {
        return sepaCode;
    }

    public void setSepaCode(String sepaCode) {
        this.sepaCode = sepaCode;
    }

    public Double getNoticePay() {
        return noticePay;
    }

    public void setNoticePay(Double noticePay) {
        this.noticePay = noticePay;
    }

    public Double getNetDues() {
        return netDues;
    }

    public void setNetDues(Double netDues) {
        this.netDues = netDues;
    }

    public Character getPrdRecPay() {
        return prdRecPay;
    }

    public void setPrdRecPay(Character prdRecPay) {
        this.prdRecPay = prdRecPay;
    }

    public String getResignation() {
        return resignation;
    }

    public void setResignation(String resignation) {
        this.resignation = resignation;
    }

    public String getResigAccept() {
        return resigAccept;
    }

    public void setResigAccept(String resigAccept) {
        this.resigAccept = resigAccept;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Double getLoanRecovery() {
        return loanRecovery;
    }

    public void setLoanRecovery(Double loanRecovery) {
        this.loanRecovery = loanRecovery;
    }

    public Character getLoanFlg() {
        return loanFlg;
    }

    public void setLoanFlg(Character loanFlg) {
        this.loanFlg = loanFlg;
    }

    public String getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(String defaultComp) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrSeparationDetailsPK != null ? hrSeparationDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSeparationDetails)) {
            return false;
        }
        HrSeparationDetails other = (HrSeparationDetails) object;
        if ((this.hrSeparationDetailsPK == null && other.hrSeparationDetailsPK != null) || (this.hrSeparationDetailsPK != null && !this.hrSeparationDetailsPK.equals(other.hrSeparationDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.personnel.HrSeparationDetails[hrSeparationDetailsPK=" + hrSeparationDetailsPK + "]";
    }

}
