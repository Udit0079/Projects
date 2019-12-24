/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.sms;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author root
 */
@Entity
@Table(name = "mb_subscriber_tab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MbSubscriberTab.findAll", query = "SELECT m FROM MbSubscriberTab m"),
    @NamedQuery(name = "MbSubscriberTab.findByAcno", query = "SELECT m FROM MbSubscriberTab m WHERE m.acno = :acno"),
    @NamedQuery(name = "MbSubscriberTab.findByAcnoType", query = "SELECT m FROM MbSubscriberTab m WHERE m.acnoType = :acnoType"),
    @NamedQuery(name = "MbSubscriberTab.findByMobileNo", query = "SELECT m FROM MbSubscriberTab m WHERE m.mobileNo = :mobileNo"),
    @NamedQuery(name = "MbSubscriberTab.findByStatus", query = "SELECT m FROM MbSubscriberTab m WHERE m.status = :status"),
    @NamedQuery(name = "MbSubscriberTab.findByBillDeductionAcno", query = "SELECT m FROM MbSubscriberTab m WHERE m.billDeductionAcno = :billDeductionAcno"),
    @NamedQuery(name = "MbSubscriberTab.findByCashCrLimit", query = "SELECT m FROM MbSubscriberTab m WHERE m.cashCrLimit = :cashCrLimit"),
    @NamedQuery(name = "MbSubscriberTab.findByCashDrLimit", query = "SELECT m FROM MbSubscriberTab m WHERE m.cashDrLimit = :cashDrLimit"),
    @NamedQuery(name = "MbSubscriberTab.findByTrfCrLimit", query = "SELECT m FROM MbSubscriberTab m WHERE m.trfCrLimit = :trfCrLimit"),
    @NamedQuery(name = "MbSubscriberTab.findByTrfDrLimit", query = "SELECT m FROM MbSubscriberTab m WHERE m.trfDrLimit = :trfDrLimit"),
    @NamedQuery(name = "MbSubscriberTab.findByClgCrLimit", query = "SELECT m FROM MbSubscriberTab m WHERE m.clgCrLimit = :clgCrLimit"),
    @NamedQuery(name = "MbSubscriberTab.findByClgDrLimit", query = "SELECT m FROM MbSubscriberTab m WHERE m.clgDrLimit = :clgDrLimit"),
    @NamedQuery(name = "MbSubscriberTab.findByPinNo", query = "SELECT m FROM MbSubscriberTab m WHERE m.pinNo = :pinNo"),
    @NamedQuery(name = "MbSubscriberTab.findByCreatedDate", query = "SELECT m FROM MbSubscriberTab m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MbSubscriberTab.findByEnterBy", query = "SELECT m FROM MbSubscriberTab m WHERE m.enterBy = :enterBy"),
    @NamedQuery(name = "MbSubscriberTab.findByAuth", query = "SELECT m FROM MbSubscriberTab m WHERE m.auth = :auth"),
    @NamedQuery(name = "MbSubscriberTab.findByAuthBy", query = "SELECT m FROM MbSubscriberTab m WHERE m.authBy = :authBy"),
    @NamedQuery(name = "MbSubscriberTab.findByAuthStatus", query = "SELECT m FROM MbSubscriberTab m WHERE m.authStatus = :authStatus"),
    @NamedQuery(name = "MbSubscriberTab.findByInterestLimit", query = "SELECT m FROM MbSubscriberTab m WHERE m.interestLimit = :interestLimit"),
    @NamedQuery(name = "MbSubscriberTab.findByChargeLimit", query = "SELECT m FROM MbSubscriberTab m WHERE m.chargeLimit = :chargeLimit"),
    @NamedQuery(name = "MbSubscriberTab.findByUpdateBy", query = "SELECT m FROM MbSubscriberTab m WHERE m.updateBy = :updateBy"),
    @NamedQuery(name = "MbSubscriberTab.findByUpdateDt", query = "SELECT m FROM MbSubscriberTab m WHERE m.updateDt = :updateDt")})
public class MbSubscriberTab extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "ACNO")
    private String acno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ACNO_TYPE")
    private String acnoType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "MOBILE_NO")
    private String mobileNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "STATUS")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "BILL_DEDUCTION_ACNO")
    private String billDeductionAcno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CASH_CR_LIMIT")
    private double cashCrLimit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CASH_DR_LIMIT")
    private double cashDrLimit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRF_CR_LIMIT")
    private double trfCrLimit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRF_DR_LIMIT")
    private double trfDrLimit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLG_CR_LIMIT")
    private double clgCrLimit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLG_DR_LIMIT")
    private double clgDrLimit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PIN_NO")
    private String pinNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "AUTH")
    private String auth;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Size(max = 10)
    @Column(name = "AUTH_STATUS")
    private String authStatus;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "INTEREST_LIMIT")
    private Double interestLimit;
    @Column(name = "CHARGE_LIMIT")
    private Double chargeLimit;
    @Size(max = 50)
    @Column(name = "UPDATE_BY")
    private String updateBy;
    @Column(name = "UPDATE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDt;

    public MbSubscriberTab() {
    }

    public MbSubscriberTab(String acno) {
        this.acno = acno;
    }

    public MbSubscriberTab(String acno, String acnoType, String mobileNo, String status, String billDeductionAcno, double cashCrLimit, double cashDrLimit, double trfCrLimit, double trfDrLimit, double clgCrLimit, double clgDrLimit, String pinNo, Date createdDate, String enterBy, String auth, String authBy) {
        this.acno = acno;
        this.acnoType = acnoType;
        this.mobileNo = mobileNo;
        this.status = status;
        this.billDeductionAcno = billDeductionAcno;
        this.cashCrLimit = cashCrLimit;
        this.cashDrLimit = cashDrLimit;
        this.trfCrLimit = trfCrLimit;
        this.trfDrLimit = trfDrLimit;
        this.clgCrLimit = clgCrLimit;
        this.clgDrLimit = clgDrLimit;
        this.pinNo = pinNo;
        this.createdDate = createdDate;
        this.enterBy = enterBy;
        this.auth = auth;
        this.authBy = authBy;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAcnoType() {
        return acnoType;
    }

    public void setAcnoType(String acnoType) {
        this.acnoType = acnoType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBillDeductionAcno() {
        return billDeductionAcno;
    }

    public void setBillDeductionAcno(String billDeductionAcno) {
        this.billDeductionAcno = billDeductionAcno;
    }

    public double getCashCrLimit() {
        return cashCrLimit;
    }

    public void setCashCrLimit(double cashCrLimit) {
        this.cashCrLimit = cashCrLimit;
    }

    public double getCashDrLimit() {
        return cashDrLimit;
    }

    public void setCashDrLimit(double cashDrLimit) {
        this.cashDrLimit = cashDrLimit;
    }

    public double getTrfCrLimit() {
        return trfCrLimit;
    }

    public void setTrfCrLimit(double trfCrLimit) {
        this.trfCrLimit = trfCrLimit;
    }

    public double getTrfDrLimit() {
        return trfDrLimit;
    }

    public void setTrfDrLimit(double trfDrLimit) {
        this.trfDrLimit = trfDrLimit;
    }

    public double getClgCrLimit() {
        return clgCrLimit;
    }

    public void setClgCrLimit(double clgCrLimit) {
        this.clgCrLimit = clgCrLimit;
    }

    public double getClgDrLimit() {
        return clgDrLimit;
    }

    public void setClgDrLimit(double clgDrLimit) {
        this.clgDrLimit = clgDrLimit;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public Double getInterestLimit() {
        return interestLimit;
    }

    public void setInterestLimit(Double interestLimit) {
        this.interestLimit = interestLimit;
    }

    public Double getChargeLimit() {
        return chargeLimit;
    }

    public void setChargeLimit(Double chargeLimit) {
        this.chargeLimit = chargeLimit;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acno != null ? acno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MbSubscriberTab)) {
            return false;
        }
        MbSubscriberTab other = (MbSubscriberTab) object;
        if ((this.acno == null && other.acno != null) || (this.acno != null && !this.acno.equals(other.acno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbSubscriberTab[ acno=" + acno + " ]";
    }
}
