/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_bcf_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeBcfDetails.findAll", query = "SELECT c FROM CbsSchemeBcfDetails c"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.cbsSchemeBcfDetailsPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByFixedChargeEventId", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.cbsSchemeBcfDetailsPK.fixedChargeEventId = :fixedChargeEventId"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findBySubvenRevCrPlaceholder", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.subvenRevCrPlaceholder = :subvenRevCrPlaceholder"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByAccountCrPrefInt", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.accountCrPrefInt = :accountCrPrefInt"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByAccountDrPrefInt", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.accountDrPrefInt = :accountDrPrefInt"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByPerdEventId", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.perdEventId = :perdEventId"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByPaymentFreqType", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.paymentFreqType = :paymentFreqType"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByPaymentFreqWeekNo", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.paymentFreqWeekNo = :paymentFreqWeekNo"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByPaymentFreqWeekDay", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.paymentFreqWeekDay = :paymentFreqWeekDay"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByPaymentFreqStartDate", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.paymentFreqStartDate = :paymentFreqStartDate"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByPaymentFreqNp", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.paymentFreqNp = :paymentFreqNp"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByStartDate", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.startDate = :startDate"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByEndDate", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.endDate = :endDate"),
    @NamedQuery(name = "CbsSchemeBcfDetails.findByPenalEventId", query = "SELECT c FROM CbsSchemeBcfDetails c WHERE c.penalEventId = :penalEventId")})
public class CbsSchemeBcfDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeBcfDetailsPK cbsSchemeBcfDetailsPK;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 12)
    @Column(name = "SUBVEN_REV_CR_PLACEHOLDER")
    private String subvenRevCrPlaceholder;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ACCOUNT_CR_PREF_INT")
    private BigDecimal accountCrPrefInt;
    @Column(name = "ACCOUNT_DR_PREF_INT")
    private BigDecimal accountDrPrefInt;
    @Size(max = 25)
    @Column(name = "PERD_EVENT_ID")
    private String perdEventId;
    @Size(max = 1)
    @Column(name = "PAYMENT_FREQ_TYPE")
    private String paymentFreqType;
    @Size(max = 1)
    @Column(name = "PAYMENT_FREQ_WEEK_NO")
    private String paymentFreqWeekNo;
    @Size(max = 1)
    @Column(name = "PAYMENT_FREQ_WEEK_DAY")
    private String paymentFreqWeekDay;
    @Size(max = 2)
    @Column(name = "PAYMENT_FREQ_START_DATE")
    private String paymentFreqStartDate;
    @Size(max = 1)
    @Column(name = "PAYMENT_FREQ_NP")
    private String paymentFreqNp;
    @Size(max = 2)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 2)
    @Column(name = "END_DATE")
    private String endDate;
    @Size(max = 25)
    @Column(name = "PENAL_EVENT_ID")
    private String penalEventId;

    public CbsSchemeBcfDetails() {
    }

    public CbsSchemeBcfDetails(CbsSchemeBcfDetailsPK cbsSchemeBcfDetailsPK) {
        this.cbsSchemeBcfDetailsPK = cbsSchemeBcfDetailsPK;
    }

    public CbsSchemeBcfDetails(String schemeCode, String fixedChargeEventId) {
        this.cbsSchemeBcfDetailsPK = new CbsSchemeBcfDetailsPK(schemeCode, fixedChargeEventId);
    }

    public CbsSchemeBcfDetailsPK getCbsSchemeBcfDetailsPK() {
        return cbsSchemeBcfDetailsPK;
    }

    public void setCbsSchemeBcfDetailsPK(CbsSchemeBcfDetailsPK cbsSchemeBcfDetailsPK) {
        this.cbsSchemeBcfDetailsPK = cbsSchemeBcfDetailsPK;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getSubvenRevCrPlaceholder() {
        return subvenRevCrPlaceholder;
    }

    public void setSubvenRevCrPlaceholder(String subvenRevCrPlaceholder) {
        this.subvenRevCrPlaceholder = subvenRevCrPlaceholder;
    }

    public BigDecimal getAccountCrPrefInt() {
        return accountCrPrefInt;
    }

    public void setAccountCrPrefInt(BigDecimal accountCrPrefInt) {
        this.accountCrPrefInt = accountCrPrefInt;
    }

    public BigDecimal getAccountDrPrefInt() {
        return accountDrPrefInt;
    }

    public void setAccountDrPrefInt(BigDecimal accountDrPrefInt) {
        this.accountDrPrefInt = accountDrPrefInt;
    }

    public String getPerdEventId() {
        return perdEventId;
    }

    public void setPerdEventId(String perdEventId) {
        this.perdEventId = perdEventId;
    }

    public String getPaymentFreqType() {
        return paymentFreqType;
    }

    public void setPaymentFreqType(String paymentFreqType) {
        this.paymentFreqType = paymentFreqType;
    }

    public String getPaymentFreqWeekNo() {
        return paymentFreqWeekNo;
    }

    public void setPaymentFreqWeekNo(String paymentFreqWeekNo) {
        this.paymentFreqWeekNo = paymentFreqWeekNo;
    }

    public String getPaymentFreqWeekDay() {
        return paymentFreqWeekDay;
    }

    public void setPaymentFreqWeekDay(String paymentFreqWeekDay) {
        this.paymentFreqWeekDay = paymentFreqWeekDay;
    }

    public String getPaymentFreqStartDate() {
        return paymentFreqStartDate;
    }

    public void setPaymentFreqStartDate(String paymentFreqStartDate) {
        this.paymentFreqStartDate = paymentFreqStartDate;
    }

    public String getPaymentFreqNp() {
        return paymentFreqNp;
    }

    public void setPaymentFreqNp(String paymentFreqNp) {
        this.paymentFreqNp = paymentFreqNp;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPenalEventId() {
        return penalEventId;
    }

    public void setPenalEventId(String penalEventId) {
        this.penalEventId = penalEventId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsSchemeBcfDetailsPK != null ? cbsSchemeBcfDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeBcfDetails)) {
            return false;
        }
        CbsSchemeBcfDetails other = (CbsSchemeBcfDetails) object;
        if ((this.cbsSchemeBcfDetailsPK == null && other.cbsSchemeBcfDetailsPK != null) || (this.cbsSchemeBcfDetailsPK != null && !this.cbsSchemeBcfDetailsPK.equals(other.cbsSchemeBcfDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeBcfDetails[ cbsSchemeBcfDetailsPK=" + cbsSchemeBcfDetailsPK + " ]";
    }
    
}
