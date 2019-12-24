/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author sipl
 */
@Entity
@Table(name = "investment_amortization_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentAmortizationDetails.findAll", query = "SELECT i FROM InvestmentAmortizationDetails i"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findByCtrlNo", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.investmentAmortizationDetailsPK.ctrlNo = :ctrlNo"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findByYears", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.years = :years"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findByDays", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.days = :days"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findByAmortAmt", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.amortAmt = :amortAmt"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findByStatus", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.status = :status"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findByLastUpdateBy", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.lastUpdateBy = :lastUpdateBy"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findByLastUpdateDt", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.lastUpdateDt = :lastUpdateDt"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findByEnterBy", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.enterBy = :enterBy"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findByTranTime", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.tranTime = :tranTime"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findBySlno", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.investmentAmortizationDetailsPK.slno = :slno"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findByMode", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.mode = :mode"),
    @NamedQuery(name = "InvestmentAmortizationDetails.findByOldCtrl", query = "SELECT i FROM InvestmentAmortizationDetails i WHERE i.oldCtrl = :oldCtrl")})
public class InvestmentAmortizationDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvestmentAmortizationDetailsPK investmentAmortizationDetailsPK;
    @Column(name = "YEARS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date years;
    @Column(name = "DAYS")
    private BigInteger days;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMORT_AMT")
    private BigDecimal amortAmt;
    @Size(max = 10)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 20)
    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;
    @Column(name = "LAST_UPDATE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDt;
    @Size(max = 20)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Column(name = "TRAN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranTime;
    @Size(max = 3)
    @Column(name = "MODE")
    private String mode;
    @Column(name = "OLD_CTRL")
    private BigInteger oldCtrl;

    public InvestmentAmortizationDetails() {
    }

    public InvestmentAmortizationDetails(InvestmentAmortizationDetailsPK investmentAmortizationDetailsPK) {
        this.investmentAmortizationDetailsPK = investmentAmortizationDetailsPK;
    }

    public InvestmentAmortizationDetails(long ctrlNo, long slno) {
        this.investmentAmortizationDetailsPK = new InvestmentAmortizationDetailsPK(ctrlNo, slno);
    }

    public InvestmentAmortizationDetailsPK getInvestmentAmortizationDetailsPK() {
        return investmentAmortizationDetailsPK;
    }

    public void setInvestmentAmortizationDetailsPK(InvestmentAmortizationDetailsPK investmentAmortizationDetailsPK) {
        this.investmentAmortizationDetailsPK = investmentAmortizationDetailsPK;
    }

    public Date getYears() {
        return years;
    }

    public void setYears(Date years) {
        this.years = years;
    }

    public BigInteger getDays() {
        return days;
    }

    public void setDays(BigInteger days) {
        this.days = days;
    }

    public BigDecimal getAmortAmt() {
        return amortAmt;
    }

    public void setAmortAmt(BigDecimal amortAmt) {
        this.amortAmt = amortAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(Date lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public BigInteger getOldCtrl() {
        return oldCtrl;
    }

    public void setOldCtrl(BigInteger oldCtrl) {
        this.oldCtrl = oldCtrl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (investmentAmortizationDetailsPK != null ? investmentAmortizationDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentAmortizationDetails)) {
            return false;
        }
        InvestmentAmortizationDetails other = (InvestmentAmortizationDetails) object;
        if ((this.investmentAmortizationDetailsPK == null && other.investmentAmortizationDetailsPK != null) || (this.investmentAmortizationDetailsPK != null && !this.investmentAmortizationDetailsPK.equals(other.investmentAmortizationDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentAmortizationDetails[ investmentAmortizationDetailsPK=" + investmentAmortizationDetailsPK + " ]";
    }
    
}
