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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "mb_subscriber_tab_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MbSubscriberTabHis.findAll", query = "SELECT m FROM MbSubscriberTabHis m"),
    @NamedQuery(name = "MbSubscriberTabHis.findByTxnId", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.mbSubscriberTabHisPK.txnId = :txnId"),
    @NamedQuery(name = "MbSubscriberTabHis.findByAcno", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.mbSubscriberTabHisPK.acno = :acno"),
    @NamedQuery(name = "MbSubscriberTabHis.findByAcnoType", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.acnoType = :acnoType"),
    @NamedQuery(name = "MbSubscriberTabHis.findByMobileNo", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.mobileNo = :mobileNo"),
    @NamedQuery(name = "MbSubscriberTabHis.findByStatus", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.status = :status"),
    @NamedQuery(name = "MbSubscriberTabHis.findByBillDeductionAcno", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.billDeductionAcno = :billDeductionAcno"),
    @NamedQuery(name = "MbSubscriberTabHis.findByCashCrLimit", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.cashCrLimit = :cashCrLimit"),
    @NamedQuery(name = "MbSubscriberTabHis.findByCashDrLimit", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.cashDrLimit = :cashDrLimit"),
    @NamedQuery(name = "MbSubscriberTabHis.findByTrfCrLimit", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.trfCrLimit = :trfCrLimit"),
    @NamedQuery(name = "MbSubscriberTabHis.findByTrfDrLimit", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.trfDrLimit = :trfDrLimit"),
    @NamedQuery(name = "MbSubscriberTabHis.findByClgCrLimit", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.clgCrLimit = :clgCrLimit"),
    @NamedQuery(name = "MbSubscriberTabHis.findByClgDrLimit", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.clgDrLimit = :clgDrLimit"),
    @NamedQuery(name = "MbSubscriberTabHis.findByPinNo", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.pinNo = :pinNo"),
    @NamedQuery(name = "MbSubscriberTabHis.findByCreatedDate", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MbSubscriberTabHis.findByEnterBy", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.enterBy = :enterBy"),
    @NamedQuery(name = "MbSubscriberTabHis.findByAuth", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.auth = :auth"),
    @NamedQuery(name = "MbSubscriberTabHis.findByAuthBy", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.authBy = :authBy"),
    @NamedQuery(name = "MbSubscriberTabHis.findByAuthStatus", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.authStatus = :authStatus"),
    @NamedQuery(name = "MbSubscriberTabHis.findByInterestLimit", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.interestLimit = :interestLimit"),
    @NamedQuery(name = "MbSubscriberTabHis.findByChargeLimit", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.chargeLimit = :chargeLimit"),
    @NamedQuery(name = "MbSubscriberTabHis.findByUpdateBy", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.updateBy = :updateBy"),
    @NamedQuery(name = "MbSubscriberTabHis.findByUpdateDt", query = "SELECT m FROM MbSubscriberTabHis m WHERE m.updateDt = :updateDt")})
public class MbSubscriberTabHis extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MbSubscriberTabHisPK mbSubscriberTabHisPK;
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
    @Size(max = 1)
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

    public MbSubscriberTabHis() {
    }

    public MbSubscriberTabHis(MbSubscriberTabHisPK mbSubscriberTabHisPK) {
        this.mbSubscriberTabHisPK = mbSubscriberTabHisPK;
    }

    public MbSubscriberTabHis(MbSubscriberTabHisPK mbSubscriberTabHisPK, String acnoType, String mobileNo, String status, String billDeductionAcno, double cashCrLimit, double cashDrLimit, double trfCrLimit, double trfDrLimit, double clgCrLimit, double clgDrLimit, String pinNo, Date createdDate, String enterBy, String auth, String authBy) {
        this.mbSubscriberTabHisPK = mbSubscriberTabHisPK;
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

    public MbSubscriberTabHis(long txnId, String acno) {
        this.mbSubscriberTabHisPK = new MbSubscriberTabHisPK(txnId, acno);
    }

    public MbSubscriberTabHisPK getMbSubscriberTabHisPK() {
        return mbSubscriberTabHisPK;
    }

    public void setMbSubscriberTabHisPK(MbSubscriberTabHisPK mbSubscriberTabHisPK) {
        this.mbSubscriberTabHisPK = mbSubscriberTabHisPK;
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
        hash += (mbSubscriberTabHisPK != null ? mbSubscriberTabHisPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MbSubscriberTabHis)) {
            return false;
        }
        MbSubscriberTabHis other = (MbSubscriberTabHis) object;
        if ((this.mbSubscriberTabHisPK == null && other.mbSubscriberTabHisPK != null) || (this.mbSubscriberTabHisPK != null && !this.mbSubscriberTabHisPK.equals(other.mbSubscriberTabHisPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbSubscriberTabHis[ mbSubscriberTabHisPK=" + mbSubscriberTabHisPK + " ]";
    }
}
