/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

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
@Table(name = "accountmaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountMaster.findAll", query = "SELECT a FROM AccountMaster a"),
    @NamedQuery(name = "AccountMaster.findByACNo", query = "SELECT a FROM AccountMaster a WHERE a.aCNo = :aCNo"),
    @NamedQuery(name = "AccountMaster.findByCustname", query = "SELECT a FROM AccountMaster a WHERE a.custname = :custname"),
    @NamedQuery(name = "AccountMaster.findByOpeningDt", query = "SELECT a FROM AccountMaster a WHERE a.openingDt = :openingDt"),
    @NamedQuery(name = "AccountMaster.findByIntroAccno", query = "SELECT a FROM AccountMaster a WHERE a.introAccno = :introAccno"),
    @NamedQuery(name = "AccountMaster.findByIntDeposit", query = "SELECT a FROM AccountMaster a WHERE a.intDeposit = :intDeposit"),
    @NamedQuery(name = "AccountMaster.findByClosingDate", query = "SELECT a FROM AccountMaster a WHERE a.closingDate = :closingDate"),
    @NamedQuery(name = "AccountMaster.findByClosingBal", query = "SELECT a FROM AccountMaster a WHERE a.closingBal = :closingBal"),
    @NamedQuery(name = "AccountMaster.findByOperMode", query = "SELECT a FROM AccountMaster a WHERE a.operMode = :operMode"),
    @NamedQuery(name = "AccountMaster.findByJtName1", query = "SELECT a FROM AccountMaster a WHERE a.jtName1 = :jtName1"),
    @NamedQuery(name = "AccountMaster.findByJtName2", query = "SELECT a FROM AccountMaster a WHERE a.jtName2 = :jtName2"),
    @NamedQuery(name = "AccountMaster.findByJtName3", query = "SELECT a FROM AccountMaster a WHERE a.jtName3 = :jtName3"),
    @NamedQuery(name = "AccountMaster.findByJtName4", query = "SELECT a FROM AccountMaster a WHERE a.jtName4 = :jtName4"),
    @NamedQuery(name = "AccountMaster.findByLastOpDate", query = "SELECT a FROM AccountMaster a WHERE a.lastOpDate = :lastOpDate"),
    @NamedQuery(name = "AccountMaster.findByAccStatus", query = "SELECT a FROM AccountMaster a WHERE a.accStatus = :accStatus"),
    @NamedQuery(name = "AccountMaster.findByOptstatus", query = "SELECT a FROM AccountMaster a WHERE a.optstatus = :optstatus"),
    @NamedQuery(name = "AccountMaster.findByOrgnCode", query = "SELECT a FROM AccountMaster a WHERE a.orgnCode = :orgnCode"),
    @NamedQuery(name = "AccountMaster.findByNomination", query = "SELECT a FROM AccountMaster a WHERE a.nomination = :nomination"),
    @NamedQuery(name = "AccountMaster.findByODLimit", query = "SELECT a FROM AccountMaster a WHERE a.oDLimit = :oDLimit"),
    @NamedQuery(name = "AccountMaster.findByEnteredBy", query = "SELECT a FROM AccountMaster a WHERE a.enteredBy = :enteredBy"),
    @NamedQuery(name = "AccountMaster.findByAuthBy", query = "SELECT a FROM AccountMaster a WHERE a.authBy = :authBy"),
    @NamedQuery(name = "AccountMaster.findByLastUpdateDt", query = "SELECT a FROM AccountMaster a WHERE a.lastUpdateDt = :lastUpdateDt"),
    @NamedQuery(name = "AccountMaster.findByAccttype", query = "SELECT a FROM AccountMaster a WHERE a.accttype = :accttype"),
    @NamedQuery(name = "AccountMaster.findByRelatioship", query = "SELECT a FROM AccountMaster a WHERE a.relatioship = :relatioship"),
    @NamedQuery(name = "AccountMaster.findByLedgerFolioNo", query = "SELECT a FROM AccountMaster a WHERE a.ledgerFolioNo = :ledgerFolioNo"),
    @NamedQuery(name = "AccountMaster.findByInstruction", query = "SELECT a FROM AccountMaster a WHERE a.instruction = :instruction"),
    @NamedQuery(name = "AccountMaster.findByPenalty", query = "SELECT a FROM AccountMaster a WHERE a.penalty = :penalty"),
    @NamedQuery(name = "AccountMaster.findByMinbal", query = "SELECT a FROM AccountMaster a WHERE a.minbal = :minbal"),
    @NamedQuery(name = "AccountMaster.findByRdmatdate", query = "SELECT a FROM AccountMaster a WHERE a.rdmatdate = :rdmatdate"),
    @NamedQuery(name = "AccountMaster.findByRdInstal", query = "SELECT a FROM AccountMaster a WHERE a.rdInstal = :rdInstal"),
    @NamedQuery(name = "AccountMaster.findByChequebook", query = "SELECT a FROM AccountMaster a WHERE a.chequebook = :chequebook"),
    @NamedQuery(name = "AccountMaster.findByAdhoclimit", query = "SELECT a FROM AccountMaster a WHERE a.adhoclimit = :adhoclimit"),
    @NamedQuery(name = "AccountMaster.findByAdhoctilldt", query = "SELECT a FROM AccountMaster a WHERE a.adhoctilldt = :adhoctilldt"),
    @NamedQuery(name = "AccountMaster.findByAdhocinterest", query = "SELECT a FROM AccountMaster a WHERE a.adhocinterest = :adhocinterest"),
    @NamedQuery(name = "AccountMaster.findByCreationDt", query = "SELECT a FROM AccountMaster a WHERE a.creationDt = :creationDt"),
    @NamedQuery(name = "AccountMaster.findByName", query = "SELECT a FROM AccountMaster a WHERE a.name = :name"),
    @NamedQuery(name = "AccountMaster.findByCustType", query = "SELECT a FROM AccountMaster a WHERE a.custType = :custType"),
    @NamedQuery(name = "AccountMaster.findByPenalty1", query = "SELECT a FROM AccountMaster a WHERE a.penalty1 = :penalty1"),
    @NamedQuery(name = "AccountMaster.findByTdsflag", query = "SELECT a FROM AccountMaster a WHERE a.tdsflag = :tdsflag"),
    @NamedQuery(name = "AccountMaster.findByCustid1", query = "SELECT a FROM AccountMaster a WHERE a.custid1 = :custid1"),
    @NamedQuery(name = "AccountMaster.findByCustid2", query = "SELECT a FROM AccountMaster a WHERE a.custid2 = :custid2"),
    @NamedQuery(name = "AccountMaster.findByCustid3", query = "SELECT a FROM AccountMaster a WHERE a.custid3 = :custid3"),
    @NamedQuery(name = "AccountMaster.findByCustid4", query = "SELECT a FROM AccountMaster a WHERE a.custid4 = :custid4"),
    @NamedQuery(name = "AccountMaster.findByCurBrCode", query = "SELECT a FROM AccountMaster a WHERE a.curBrCode = :curBrCode"),
    @NamedQuery(name = "AccountMaster.findByLastTxnDate", query = "SELECT a FROM AccountMaster a WHERE a.lastTxnDate = :lastTxnDate")})
public class AccountMaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "ACNo")
    private String aCNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "custname")
    private String custname;
    @Size(max = 8)
    @Column(name = "OpeningDt")
    private String openingDt;
    @Size(max = 12)
    @Column(name = "IntroAccno")
    private String introAccno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IntDeposit")
    private float intDeposit;
    @Size(max = 8)
    @Column(name = "ClosingDate")
    private String closingDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ClosingBal")
    private Double closingBal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OperMode")
    private short operMode;
    @Size(max = 40)
    @Column(name = "JtName1")
    private String jtName1;
    @Size(max = 40)
    @Column(name = "JtName2")
    private String jtName2;
    @Size(max = 40)
    @Column(name = "JtName3")
    private String jtName3;
    @Size(max = 40)
    @Column(name = "JtName4")
    private String jtName4;
    @Size(max = 8)
    @Column(name = "LastOpDate")
    private String lastOpDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AccStatus")
    private short accStatus;
    @Column(name = "optstatus")
    private Short optstatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrgnCode")
    private short orgnCode;
    @Size(max = 40)
    @Column(name = "Nomination")
    private String nomination;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ODLimit")
    private double oDLimit;
    @Size(max = 20)
    @Column(name = "EnteredBy")
    private String enteredBy;
    @Size(max = 20)
    @Column(name = "AuthBy")
    private String authBy;
    @Size(max = 8)
    @Column(name = "LastUpdateDt")
    private String lastUpdateDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "Accttype")
    private String accttype;
    @Size(max = 30)
    @Column(name = "Relatioship")
    private String relatioship;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LedgerFolioNo")
    private short ledgerFolioNo;
    @Size(max = 150)
    @Column(name = "Instruction")
    private String instruction;
    @Basic(optional = false)
    @NotNull
    @Column(name = "penalty")
    private double penalty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "minbal")
    private int minbal;
    @Column(name = "Rdmatdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rdmatdate;
    @Column(name = "RdInstal")
    private Double rdInstal;
    @Column(name = "chequebook")
    private Short chequebook;
    @Basic(optional = false)
    @NotNull
    @Column(name = "adhoclimit")
    private double adhoclimit;
    @Column(name = "adhoctilldt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adhoctilldt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "adhocinterest")
    private double adhocinterest;
    @Size(max = 8)
    @Column(name = "CreationDt")
    private String creationDt;
    @Size(max = 40)
    @Column(name = "Name")
    private String name;
    @Size(max = 2)
    @Column(name = "CUST_TYPE")
    private String custType;
    @Column(name = "penalty1")
    private Integer penalty1;
    @Size(max = 1)
    @Column(name = "TDSFLAG")
    private String tdsflag;
    @Size(max = 10)
    @Column(name = "custid1")
    private String custid1;
    @Size(max = 10)
    @Column(name = "custid2")
    private String custid2;
    @Size(max = 10)
    @Column(name = "custid3")
    private String custid3;
    @Size(max = 10)
    @Column(name = "custid4")
    private String custid4;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CurBrCode")
    private String curBrCode;
    @Column(name = "Last_Txn_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTxnDate;

    public AccountMaster() {
    }

    public AccountMaster(String aCNo) {
        this.aCNo = aCNo;
    }

    public AccountMaster(String aCNo, String custname, float intDeposit, short operMode, short accStatus, short orgnCode, double oDLimit, String accttype, short ledgerFolioNo, double penalty, int minbal, double adhoclimit, double adhocinterest, String curBrCode) {
        this.aCNo = aCNo;
        this.custname = custname;
        this.intDeposit = intDeposit;
        this.operMode = operMode;
        this.accStatus = accStatus;
        this.orgnCode = orgnCode;
        this.oDLimit = oDLimit;
        this.accttype = accttype;
        this.ledgerFolioNo = ledgerFolioNo;
        this.penalty = penalty;
        this.minbal = minbal;
        this.adhoclimit = adhoclimit;
        this.adhocinterest = adhocinterest;
        this.curBrCode = curBrCode;
    }

    public String getACNo() {
        return aCNo;
    }

    public void setACNo(String aCNo) {
        this.aCNo = aCNo;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getOpeningDt() {
        return openingDt;
    }

    public void setOpeningDt(String openingDt) {
        this.openingDt = openingDt;
    }

    public String getIntroAccno() {
        return introAccno;
    }

    public void setIntroAccno(String introAccno) {
        this.introAccno = introAccno;
    }

    public float getIntDeposit() {
        return intDeposit;
    }

    public void setIntDeposit(float intDeposit) {
        this.intDeposit = intDeposit;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public Double getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(Double closingBal) {
        this.closingBal = closingBal;
    }

    public short getOperMode() {
        return operMode;
    }

    public void setOperMode(short operMode) {
        this.operMode = operMode;
    }

    public String getJtName1() {
        return jtName1;
    }

    public void setJtName1(String jtName1) {
        this.jtName1 = jtName1;
    }

    public String getJtName2() {
        return jtName2;
    }

    public void setJtName2(String jtName2) {
        this.jtName2 = jtName2;
    }

    public String getJtName3() {
        return jtName3;
    }

    public void setJtName3(String jtName3) {
        this.jtName3 = jtName3;
    }

    public String getJtName4() {
        return jtName4;
    }

    public void setJtName4(String jtName4) {
        this.jtName4 = jtName4;
    }

    public String getLastOpDate() {
        return lastOpDate;
    }

    public void setLastOpDate(String lastOpDate) {
        this.lastOpDate = lastOpDate;
    }

    public short getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(short accStatus) {
        this.accStatus = accStatus;
    }

    public Short getOptstatus() {
        return optstatus;
    }

    public void setOptstatus(Short optstatus) {
        this.optstatus = optstatus;
    }

    public short getOrgnCode() {
        return orgnCode;
    }

    public void setOrgnCode(short orgnCode) {
        this.orgnCode = orgnCode;
    }

    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }

    public double getODLimit() {
        return oDLimit;
    }

    public void setODLimit(double oDLimit) {
        this.oDLimit = oDLimit;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(String lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    public String getAccttype() {
        return accttype;
    }

    public void setAccttype(String accttype) {
        this.accttype = accttype;
    }

    public String getRelatioship() {
        return relatioship;
    }

    public void setRelatioship(String relatioship) {
        this.relatioship = relatioship;
    }

    public short getLedgerFolioNo() {
        return ledgerFolioNo;
    }

    public void setLedgerFolioNo(short ledgerFolioNo) {
        this.ledgerFolioNo = ledgerFolioNo;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public int getMinbal() {
        return minbal;
    }

    public void setMinbal(int minbal) {
        this.minbal = minbal;
    }

    public Date getRdmatdate() {
        return rdmatdate;
    }

    public void setRdmatdate(Date rdmatdate) {
        this.rdmatdate = rdmatdate;
    }

    public Double getRdInstal() {
        return rdInstal;
    }

    public void setRdInstal(Double rdInstal) {
        this.rdInstal = rdInstal;
    }

    public Short getChequebook() {
        return chequebook;
    }

    public void setChequebook(Short chequebook) {
        this.chequebook = chequebook;
    }

    public double getAdhoclimit() {
        return adhoclimit;
    }

    public void setAdhoclimit(double adhoclimit) {
        this.adhoclimit = adhoclimit;
    }

    public Date getAdhoctilldt() {
        return adhoctilldt;
    }

    public void setAdhoctilldt(Date adhoctilldt) {
        this.adhoctilldt = adhoctilldt;
    }

    public double getAdhocinterest() {
        return adhocinterest;
    }

    public void setAdhocinterest(double adhocinterest) {
        this.adhocinterest = adhocinterest;
    }

    public String getCreationDt() {
        return creationDt;
    }

    public void setCreationDt(String creationDt) {
        this.creationDt = creationDt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public Integer getPenalty1() {
        return penalty1;
    }

    public void setPenalty1(Integer penalty1) {
        this.penalty1 = penalty1;
    }

    public String getTdsflag() {
        return tdsflag;
    }

    public void setTdsflag(String tdsflag) {
        this.tdsflag = tdsflag;
    }

    public String getCustid1() {
        return custid1;
    }

    public void setCustid1(String custid1) {
        this.custid1 = custid1;
    }

    public String getCustid2() {
        return custid2;
    }

    public void setCustid2(String custid2) {
        this.custid2 = custid2;
    }

    public String getCustid3() {
        return custid3;
    }

    public void setCustid3(String custid3) {
        this.custid3 = custid3;
    }

    public String getCustid4() {
        return custid4;
    }

    public void setCustid4(String custid4) {
        this.custid4 = custid4;
    }

    public String getCurBrCode() {
        return curBrCode;
    }

    public void setCurBrCode(String curBrCode) {
        this.curBrCode = curBrCode;
    }

    public Date getLastTxnDate() {
        return lastTxnDate;
    }

    public void setLastTxnDate(Date lastTxnDate) {
        this.lastTxnDate = lastTxnDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aCNo != null ? aCNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountMaster)) {
            return false;
        }
        AccountMaster other = (AccountMaster) object;
        if ((this.aCNo == null && other.aCNo != null) || (this.aCNo != null && !this.aCNo.equals(other.aCNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.AccountMaster[ aCNo=" + aCNo + " ]";
    }
}
