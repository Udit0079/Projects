/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

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
 * @author Administrator
 */
@Entity
@Table(name = "investment_goidates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentGoidates.findAll", query = "SELECT i FROM InvestmentGoidates i"),
    @NamedQuery(name = "InvestmentGoidates.findByCtrlno", query = "SELECT i FROM InvestmentGoidates i WHERE i.ctrlno = :ctrlno"),
    @NamedQuery(name = "InvestmentGoidates.findByPurchasedt", query = "SELECT i FROM InvestmentGoidates i WHERE i.purchasedt = :purchasedt"),
    @NamedQuery(name = "InvestmentGoidates.findByPrintedIssuedt", query = "SELECT i FROM InvestmentGoidates i WHERE i.printedIssuedt = :printedIssuedt"),
    @NamedQuery(name = "InvestmentGoidates.findByMatdt", query = "SELECT i FROM InvestmentGoidates i WHERE i.matdt = :matdt"),
    @NamedQuery(name = "InvestmentGoidates.findByCloseddt", query = "SELECT i FROM InvestmentGoidates i WHERE i.closeddt = :closeddt"),
    @NamedQuery(name = "InvestmentGoidates.findByIntpdt1", query = "SELECT i FROM InvestmentGoidates i WHERE i.intpdt1 = :intpdt1"),
    @NamedQuery(name = "InvestmentGoidates.findByIntpdt2", query = "SELECT i FROM InvestmentGoidates i WHERE i.intpdt2 = :intpdt2"),
    @NamedQuery(name = "InvestmentGoidates.findByLastintpaiddt", query = "SELECT i FROM InvestmentGoidates i WHERE i.lastintpaiddt = :lastintpaiddt"),
    @NamedQuery(name = "InvestmentGoidates.findByFlag", query = "SELECT i FROM InvestmentGoidates i WHERE i.flag = :flag"),
    @NamedQuery(name = "InvestmentGoidates.findByCode", query = "SELECT i FROM InvestmentGoidates i WHERE i.code = :code"),
    @NamedQuery(name = "InvestmentGoidates.findByAmtinttrec", query = "SELECT i FROM InvestmentGoidates i WHERE i.amtinttrec = :amtinttrec"),
    @NamedQuery(name = "InvestmentGoidates.findByRecflag", query = "SELECT i FROM InvestmentGoidates i WHERE i.recflag = :recflag"),
    @NamedQuery(name = "InvestmentGoidates.findByIssueUnits", query = "SELECT i FROM InvestmentGoidates i WHERE i.issueUnits = :issueUnits"),
    @NamedQuery(name = "InvestmentGoidates.findByStatus", query = "SELECT i FROM InvestmentGoidates i WHERE i.status = :status"),
    @NamedQuery(name = "InvestmentGoidates.findByAmtIntAccr", query = "SELECT i FROM InvestmentGoidates i WHERE i.amtIntAccr = :amtIntAccr"),
    @NamedQuery(name = "InvestmentGoidates.findByTotAmtIntAccr", query = "SELECT i FROM InvestmentGoidates i WHERE i.totAmtIntAccr = :totAmtIntAccr"),
    @NamedQuery(name = "InvestmentGoidates.findByTotIntRec", query = "SELECT i FROM InvestmentGoidates i WHERE i.totIntRec = :totIntRec"),
    @NamedQuery(name = "InvestmentGoidates.findByLastIntPaidDtAccr", query = "SELECT i FROM InvestmentGoidates i WHERE i.lastIntPaidDtAccr = :lastIntPaidDtAccr"),
    @NamedQuery(name = "InvestmentGoidates.findByAccrIntBy", query = "SELECT i FROM InvestmentGoidates i WHERE i.accrIntBy = :accrIntBy"),
    @NamedQuery(name = "InvestmentGoidates.findByHyIntBy", query = "SELECT i FROM InvestmentGoidates i WHERE i.hyIntBy = :hyIntBy")})
public class InvestmentGoidates extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTRLNO")
    private Integer ctrlno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PURCHASEDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchasedt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRINTED_ISSUEDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date printedIssuedt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MATDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date matdt;
    @Column(name = "CLOSEDDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeddt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INTPDT1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date intpdt1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INTPDT2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date intpdt2;
    @Column(name = "LASTINTPAIDDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastintpaiddt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FLAG")
    private char flag;
    @Column(name = "CODE")
    private Character code;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMTINTTREC")
    private double amtinttrec;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECFLAG")
    private char recflag;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ISSUE_UNITS")
    private Double issueUnits;
    @Size(max = 10)
    @Column(name = "STATUS")
    private String status;
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
    @Column(name = "TOT_INT_REC")
    private double totIntRec;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LAST_INT_PAID_DT_ACCR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastIntPaidDtAccr;
    @Size(max = 30)
    @Column(name = "ACCR_INT_BY")
    private String accrIntBy;
    @Size(max = 30)
    @Column(name = "HY_INT_BY")
    private String hyIntBy;

    public InvestmentGoidates() {
    }

    public InvestmentGoidates(Integer ctrlno) {
        this.ctrlno = ctrlno;
    }

    public InvestmentGoidates(Integer ctrlno, Date purchasedt, Date printedIssuedt, Date matdt, Date intpdt1, Date intpdt2, char flag, double amtinttrec, char recflag, double amtIntAccr, double totAmtIntAccr, double totIntRec, Date lastIntPaidDtAccr) {
        this.ctrlno = ctrlno;
        this.purchasedt = purchasedt;
        this.printedIssuedt = printedIssuedt;
        this.matdt = matdt;
        this.intpdt1 = intpdt1;
        this.intpdt2 = intpdt2;
        this.flag = flag;
        this.amtinttrec = amtinttrec;
        this.recflag = recflag;
        this.amtIntAccr = amtIntAccr;
        this.totAmtIntAccr = totAmtIntAccr;
        this.totIntRec = totIntRec;
        this.lastIntPaidDtAccr = lastIntPaidDtAccr;
    }

    public Integer getCtrlno() {
        return ctrlno;
    }

    public void setCtrlno(Integer ctrlno) {
        this.ctrlno = ctrlno;
    }

    public Date getPurchasedt() {
        return purchasedt;
    }

    public void setPurchasedt(Date purchasedt) {
        this.purchasedt = purchasedt;
    }

    public Date getPrintedIssuedt() {
        return printedIssuedt;
    }

    public void setPrintedIssuedt(Date printedIssuedt) {
        this.printedIssuedt = printedIssuedt;
    }

    public Date getMatdt() {
        return matdt;
    }

    public void setMatdt(Date matdt) {
        this.matdt = matdt;
    }

    public Date getCloseddt() {
        return closeddt;
    }

    public void setCloseddt(Date closeddt) {
        this.closeddt = closeddt;
    }

    public Date getIntpdt1() {
        return intpdt1;
    }

    public void setIntpdt1(Date intpdt1) {
        this.intpdt1 = intpdt1;
    }

    public Date getIntpdt2() {
        return intpdt2;
    }

    public void setIntpdt2(Date intpdt2) {
        this.intpdt2 = intpdt2;
    }

    public Date getLastintpaiddt() {
        return lastintpaiddt;
    }

    public void setLastintpaiddt(Date lastintpaiddt) {
        this.lastintpaiddt = lastintpaiddt;
    }

    public char getFlag() {
        return flag;
    }

    public void setFlag(char flag) {
        this.flag = flag;
    }

    public Character getCode() {
        return code;
    }

    public void setCode(Character code) {
        this.code = code;
    }

    public double getAmtinttrec() {
        return amtinttrec;
    }

    public void setAmtinttrec(double amtinttrec) {
        this.amtinttrec = amtinttrec;
    }

    public char getRecflag() {
        return recflag;
    }

    public void setRecflag(char recflag) {
        this.recflag = recflag;
    }

    public Double getIssueUnits() {
        return issueUnits;
    }

    public void setIssueUnits(Double issueUnits) {
        this.issueUnits = issueUnits;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public double getTotIntRec() {
        return totIntRec;
    }

    public void setTotIntRec(double totIntRec) {
        this.totIntRec = totIntRec;
    }

    public Date getLastIntPaidDtAccr() {
        return lastIntPaidDtAccr;
    }

    public void setLastIntPaidDtAccr(Date lastIntPaidDtAccr) {
        this.lastIntPaidDtAccr = lastIntPaidDtAccr;
    }

    public String getAccrIntBy() {
        return accrIntBy;
    }

    public void setAccrIntBy(String accrIntBy) {
        this.accrIntBy = accrIntBy;
    }

    public String getHyIntBy() {
        return hyIntBy;
    }

    public void setHyIntBy(String hyIntBy) {
        this.hyIntBy = hyIntBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctrlno != null ? ctrlno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentGoidates)) {
            return false;
        }
        InvestmentGoidates other = (InvestmentGoidates) object;
        if ((this.ctrlno == null && other.ctrlno != null) || (this.ctrlno != null && !this.ctrlno.equals(other.ctrlno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentGoidates[ ctrlno=" + ctrlno + " ]";
    }    
}
