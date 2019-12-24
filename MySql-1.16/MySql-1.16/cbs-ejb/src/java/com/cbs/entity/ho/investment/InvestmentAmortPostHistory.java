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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "investment_amort_post_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentAmortPostHistory.findAll", query = "SELECT i FROM InvestmentAmortPostHistory i"),
    @NamedQuery(name = "InvestmentAmortPostHistory.findByCtrlno", query = "SELECT i FROM InvestmentAmortPostHistory i WHERE i.ctrlno = :ctrlno"),
    @NamedQuery(name = "InvestmentAmortPostHistory.findByMonthAmort", query = "SELECT i FROM InvestmentAmortPostHistory i WHERE i.monthAmort = :monthAmort"),
    @NamedQuery(name = "InvestmentAmortPostHistory.findByYearAmort", query = "SELECT i FROM InvestmentAmortPostHistory i WHERE i.yearAmort = :yearAmort"),
    @NamedQuery(name = "InvestmentAmortPostHistory.findByPostedby", query = "SELECT i FROM InvestmentAmortPostHistory i WHERE i.postedby = :postedby"),
    @NamedQuery(name = "InvestmentAmortPostHistory.findByPostingdate", query = "SELECT i FROM InvestmentAmortPostHistory i WHERE i.postingdate = :postingdate"),
    @NamedQuery(name = "InvestmentAmortPostHistory.findByTxnid", query = "SELECT i FROM InvestmentAmortPostHistory i WHERE i.txnid = :txnid")})
public class InvestmentAmortPostHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "CTRLNO")
    private BigInteger ctrlno;
    @Size(max = 15)
    @Column(name = "MONTH_AMORT")
    private String monthAmort;
    @Column(name = "YEAR_AMORT")
    private BigInteger yearAmort;
    @Size(max = 20)
    @Column(name = "POSTEDBY")
    private String postedby;
    @Column(name = "POSTINGDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postingdate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TXNID")
    private Long txnid;

    public InvestmentAmortPostHistory() {
    }

    public InvestmentAmortPostHistory(Long txnid) {
        this.txnid = txnid;
    }

    public BigInteger getCtrlno() {
        return ctrlno;
    }

    public void setCtrlno(BigInteger ctrlno) {
        this.ctrlno = ctrlno;
    }

    public String getMonthAmort() {
        return monthAmort;
    }

    public void setMonthAmort(String monthAmort) {
        this.monthAmort = monthAmort;
    }

    public BigInteger getYearAmort() {
        return yearAmort;
    }

    public void setYearAmort(BigInteger yearAmort) {
        this.yearAmort = yearAmort;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public Date getPostingdate() {
        return postingdate;
    }

    public void setPostingdate(Date postingdate) {
        this.postingdate = postingdate;
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
        if (!(object instanceof InvestmentAmortPostHistory)) {
            return false;
        }
        InvestmentAmortPostHistory other = (InvestmentAmortPostHistory) object;
        if ((this.txnid == null && other.txnid != null) || (this.txnid != null && !this.txnid.equals(other.txnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentAmortPostHistory[ txnid=" + txnid + " ]";
    }
}
