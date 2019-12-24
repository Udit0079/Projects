/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "investment_fdr_dates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentFdrDates.findAll", query = "SELECT i FROM InvestmentFdrDates i"),
    @NamedQuery(name = "InvestmentFdrDates.findByCtrlNo", query = "SELECT i FROM InvestmentFdrDates i WHERE i.ctrlNo = :ctrlNo"),
    @NamedQuery(name = "InvestmentFdrDates.findByPurchaseDt", query = "SELECT i FROM InvestmentFdrDates i WHERE i.purchaseDt = :purchaseDt"),
    @NamedQuery(name = "InvestmentFdrDates.findByMatDt", query = "SELECT i FROM InvestmentFdrDates i WHERE i.matDt = :matDt"),
    @NamedQuery(name = "InvestmentFdrDates.findByClosedDt", query = "SELECT i FROM InvestmentFdrDates i WHERE i.closedDt = :closedDt"),
    @NamedQuery(name = "InvestmentFdrDates.findByIntPdt", query = "SELECT i FROM InvestmentFdrDates i WHERE i.intPdt = :intPdt"),
    @NamedQuery(name = "InvestmentFdrDates.findByLastIntPaidDt", query = "SELECT i FROM InvestmentFdrDates i WHERE i.lastIntPaidDt = :lastIntPaidDt"),
    @NamedQuery(name = "InvestmentFdrDates.findByFlag", query = "SELECT i FROM InvestmentFdrDates i WHERE i.flag = :flag"),
    @NamedQuery(name = "InvestmentFdrDates.findByIntOpt", query = "SELECT i FROM InvestmentFdrDates i WHERE i.intOpt = :intOpt"),
    @NamedQuery(name = "InvestmentFdrDates.findByAmtIntTrec", query = "SELECT i FROM InvestmentFdrDates i WHERE i.amtIntTrec = :amtIntTrec"),
    @NamedQuery(name = "InvestmentFdrDates.findByTotAmtIntRec", query = "SELECT i FROM InvestmentFdrDates i WHERE i.totAmtIntRec = :totAmtIntRec"),
    @NamedQuery(name = "InvestmentFdrDates.findByDays", query = "SELECT i FROM InvestmentFdrDates i WHERE i.days = :days"),
    @NamedQuery(name = "InvestmentFdrDates.findByMonths", query = "SELECT i FROM InvestmentFdrDates i WHERE i.months = :months"),
    @NamedQuery(name = "InvestmentFdrDates.findByYears", query = "SELECT i FROM InvestmentFdrDates i WHERE i.years = :years")})
public class InvestmentFdrDates extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTRL_NO")
    private Integer ctrlNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PURCHASE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date matDt;
    @Column(name = "CLOSED_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closedDt;
    @Column(name = "INT_PDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date intPdt;
    @Column(name = "LAST_INT_PAID_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastIntPaidDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "FLAG")
    private String flag;
    @Size(max = 1)
    @Column(name = "INT_OPT")
    private String intOpt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMT_INT_TREC")
    private double amtIntTrec;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMT_INT_ACCR")
    private double amtIntAccr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOT_AMT_INT_ACCR")
    private double totAmtIntAccr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LAST_INT_PAID_DT_ACCR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastIntPaidDtAccr;
    @Basic(optional = false)
    @Size(min = 1, max = 1)
    @Column(name = "TOT_AMT_INT_REC")
    private double totAmtIntRec;
    @Column(name = "DAYS")
    private BigInteger days;
    @Column(name = "MONTHS")
    private BigInteger months;
    @Column(name = "YEARS")
    private BigInteger years;

    public InvestmentFdrDates() {
    }

    public InvestmentFdrDates(Integer ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public InvestmentFdrDates(Integer ctrlNo, Date purchaseDt, Date matDt, String flag, double amtIntTrec, double totAmtIntRec,double amtIntAccr,double totAmtIntAccr,Date lastIntPaidDtAccr ) {
        this.ctrlNo = ctrlNo;
        this.purchaseDt = purchaseDt;
        this.matDt = matDt;
        this.flag = flag;
        this.amtIntTrec = amtIntTrec;
        this.totAmtIntRec = totAmtIntRec;
        this.amtIntAccr=amtIntAccr;
        this.totAmtIntAccr=totAmtIntAccr;
        this.lastIntPaidDtAccr=lastIntPaidDtAccr;
    }

    public Integer getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(Integer ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public Date getPurchaseDt() {
        return purchaseDt;
    }

    public void setPurchaseDt(Date purchaseDt) {
        this.purchaseDt = purchaseDt;
    }

    public Date getMatDt() {
        return matDt;
    }

    public void setMatDt(Date matDt) {
        this.matDt = matDt;
    }

    public Date getClosedDt() {
        return closedDt;
    }

    public void setClosedDt(Date closedDt) {
        this.closedDt = closedDt;
    }

    public Date getIntPdt() {
        return intPdt;
    }

    public void setIntPdt(Date intPdt) {
        this.intPdt = intPdt;
    }

    public Date getLastIntPaidDt() {
        return lastIntPaidDt;
    }

    public void setLastIntPaidDt(Date lastIntPaidDt) {
        this.lastIntPaidDt = lastIntPaidDt;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public double getAmtIntTrec() {
        return amtIntTrec;
    }

    public void setAmtIntTrec(double amtIntTrec) {
        this.amtIntTrec = amtIntTrec;
    }

    public double getTotAmtIntRec() {
        return totAmtIntRec;
    }

    public void setTotAmtIntRec(double totAmtIntRec) {
        this.totAmtIntRec = totAmtIntRec;
    }

    public BigInteger getDays() {
        return days;
    }

    public void setDays(BigInteger days) {
        this.days = days;
    }

    public BigInteger getMonths() {
        return months;
    }

    public void setMonths(BigInteger months) {
        this.months = months;
    }

    public BigInteger getYears() {
        return years;
    }

    public void setYears(BigInteger years) {
        this.years = years;
    }

    public double getAmtIntAccr() {
        return amtIntAccr;
    }

    public void setAmtIntAccr(double amtIntAccr) {
        this.amtIntAccr = amtIntAccr;
    }

    public double getTotAmtIntAccr() {
        return totAmtIntAccr;
    }

    public void setTotAmtIntAccr(double totAmtIntAccr) {
        this.totAmtIntAccr = totAmtIntAccr;
    }

    public Date getLastIntPaidDtAccr() {
        return lastIntPaidDtAccr;
    }

    public void setLastIntPaidDtAccr(Date lastIntPaidDtAccr) {
        this.lastIntPaidDtAccr = lastIntPaidDtAccr;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctrlNo != null ? ctrlNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentFdrDates)) {
            return false;
        }
        InvestmentFdrDates other = (InvestmentFdrDates) object;
        if ((this.ctrlNo == null && other.ctrlNo != null) || (this.ctrlNo != null && !this.ctrlNo.equals(other.ctrlNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentFdrDates[ ctrlNo=" + ctrlNo + " ]";
    }
}
