/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "accounttypemaster")
@NamedQueries({
    @NamedQuery(name = "AccountTypeMaster.findAll", query = "SELECT a FROM AccountTypeMaster a"),
    @NamedQuery(name = "AccountTypeMaster.findByAcctCode", query = "SELECT a FROM AccountTypeMaster a WHERE a.acctCode = :acctCode"),
    @NamedQuery(name = "AccountTypeMaster.findByAcctDesc", query = "SELECT a FROM AccountTypeMaster a WHERE a.acctDesc = :acctDesc"),
    @NamedQuery(name = "AccountTypeMaster.findByMaxPay", query = "SELECT a FROM AccountTypeMaster a WHERE a.maxPay = :maxPay"),
    @NamedQuery(name = "AccountTypeMaster.findByMinBal", query = "SELECT a FROM AccountTypeMaster a WHERE a.minBal = :minBal"),
    @NamedQuery(name = "AccountTypeMaster.findByMinBalchq", query = "SELECT a FROM AccountTypeMaster a WHERE a.minBalchq = :minBalchq"),
    @NamedQuery(name = "AccountTypeMaster.findByMinInt", query = "SELECT a FROM AccountTypeMaster a WHERE a.minInt = :minInt"),
    @NamedQuery(name = "AccountTypeMaster.findByStaffint", query = "SELECT a FROM AccountTypeMaster a WHERE a.staffint = :staffint"),
    @NamedQuery(name = "AccountTypeMaster.findByLastUpdateDt", query = "SELECT a FROM AccountTypeMaster a WHERE a.lastUpdateDt = :lastUpdateDt"),
    @NamedQuery(name = "AccountTypeMaster.findByLastUpdateBy", query = "SELECT a FROM AccountTypeMaster a WHERE a.lastUpdateBy = :lastUpdateBy"),
    @NamedQuery(name = "AccountTypeMaster.findByMaxLendIntPeriod", query = "SELECT a FROM AccountTypeMaster a WHERE a.maxLendIntPeriod = :maxLendIntPeriod"),
    @NamedQuery(name = "AccountTypeMaster.findByChequeBounceAmt", query = "SELECT a FROM AccountTypeMaster a WHERE a.chequeBounceAmt = :chequeBounceAmt"),
    @NamedQuery(name = "AccountTypeMaster.findByMinIntPeriod", query = "SELECT a FROM AccountTypeMaster a WHERE a.minIntPeriod = :minIntPeriod"),
    @NamedQuery(name = "AccountTypeMaster.findByCrDbFlag", query = "SELECT a FROM AccountTypeMaster a WHERE a.crDbFlag = :crDbFlag"),
    @NamedQuery(name = "AccountTypeMaster.findByPenalty", query = "SELECT a FROM AccountTypeMaster a WHERE a.penalty = :penalty"),
    @NamedQuery(name = "AccountTypeMaster.findByChqsrno", query = "SELECT a FROM AccountTypeMaster a WHERE a.chqsrno = :chqsrno"),
    @NamedQuery(name = "AccountTypeMaster.findByEnterby", query = "SELECT a FROM AccountTypeMaster a WHERE a.enterby = :enterby"),
    @NamedQuery(name = "AccountTypeMaster.findByAcctNature", query = "SELECT a FROM AccountTypeMaster a WHERE a.acctNature = :acctNature"),
    @NamedQuery(name = "AccountTypeMaster.findByGLHead", query = "SELECT a FROM AccountTypeMaster a WHERE a.gLHead = :gLHead"),
    @NamedQuery(name = "AccountTypeMaster.findByGLHeadInt", query = "SELECT a FROM AccountTypeMaster a WHERE a.gLHeadInt = :gLHeadInt"),
    @NamedQuery(name = "AccountTypeMaster.findByOfAcctnature", query = "SELECT a FROM AccountTypeMaster a WHERE a.ofAcctnature = :ofAcctnature"),
    @NamedQuery(name = "AccountTypeMaster.findByGLHeadProv", query = "SELECT a FROM AccountTypeMaster a WHERE a.gLHeadProv = :gLHeadProv"),
    @NamedQuery(name = "AccountTypeMaster.findByProductCode", query = "SELECT a FROM AccountTypeMaster a WHERE a.productCode = :productCode"),
    @NamedQuery(name = "AccountTypeMaster.findBySno", query = "SELECT a FROM AccountTypeMaster a WHERE a.sno = :sno"),
    @NamedQuery(name = "AccountTypeMaster.findByFlagIntGiven", query = "SELECT a FROM AccountTypeMaster a WHERE a.flagIntGiven = :flagIntGiven"),
    @NamedQuery(name = "AccountTypeMaster.findByLastIntGiven", query = "SELECT a FROM AccountTypeMaster a WHERE a.lastIntGiven = :lastIntGiven"),
    @NamedQuery(name = "AccountTypeMaster.findByLedgerCode", query = "SELECT a FROM AccountTypeMaster a WHERE a.ledgerCode = :ledgerCode"),
    @NamedQuery(name = "AccountTypeMaster.findByLoanAuth", query = "SELECT a FROM AccountTypeMaster a WHERE a.loanAuth = :loanAuth"),
    @NamedQuery(name = "AccountTypeMaster.findByMaxLendInt", query = "SELECT a FROM AccountTypeMaster a WHERE a.maxLendInt = :maxLendInt"),
    @NamedQuery(name = "AccountTypeMaster.findByMaxTrans", query = "SELECT a FROM AccountTypeMaster a WHERE a.maxTrans = :maxTrans"),
    @NamedQuery(name = "AccountTypeMaster.findByMinbalcharge", query = "SELECT a FROM AccountTypeMaster a WHERE a.minbalcharge = :minbalcharge"),
    @NamedQuery(name = "AccountTypeMaster.findByOpcgBal", query = "SELECT a FROM AccountTypeMaster a WHERE a.opcgBal = :opcgBal"),
    @NamedQuery(name = "AccountTypeMaster.findByOpcgCurr", query = "SELECT a FROM AccountTypeMaster a WHERE a.opcgCurr = :opcgCurr"),
    @NamedQuery(name = "AccountTypeMaster.findByOpcgMaxtrn", query = "SELECT a FROM AccountTypeMaster a WHERE a.opcgMaxtrn = :opcgMaxtrn"),
    @NamedQuery(name = "AccountTypeMaster.findByGLHeadName", query = "SELECT a FROM AccountTypeMaster a WHERE a.gLHeadName = :gLHeadName"),
    @NamedQuery(name = "AccountTypeMaster.findByGlheaduri", query = "SELECT a FROM AccountTypeMaster a WHERE a.glheaduri = :glheaduri")})
public class AccountTypeMaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "AcctCode")
    private String acctCode;
    @Column(name = "AcctDesc")
    private String acctDesc;
    @Column(name = "MaxPay")
    private Integer maxPay;
    @Column(name = "MinBal")
    private Short minBal;
    @Basic(optional = false)
    @Column(name = "MinBal_chq")
    private double minBalchq;
    @Basic(optional = false)
    @Column(name = "MinInt")
    private double minInt;
    @Basic(optional = false)
    @Column(name = "staffint")
    private double staffint;
    @Column(name = "LastUpdateDt")
    private String lastUpdateDt;
    @Column(name = "LastUpdateBy")
    private String lastUpdateBy;
    @Column(name = "maxLendIntPeriod")
    private Short maxLendIntPeriod;
    @Column(name = "ChequeBounceAmt")
    private Double chequeBounceAmt;
    @Column(name = "MinIntPeriod")
    private Integer minIntPeriod;
    @Column(name = "CrDbFlag")
    private String crDbFlag;
    @Column(name = "penalty")
    private Double penalty;
    @Column(name = "chqsrno")
    private String chqsrno;
    @Column(name = "enterby")
    private String enterby;
    @Column(name = "acctNature")
    private String acctNature;
    @Column(name = "GLHead")
    private String gLHead;
    @Column(name = "GLHeadInt")
    private String gLHeadInt;
    @Column(name = "ofAcctnature")
    private String ofAcctnature;
    @Column(name = "GLHeadProv")
    private String gLHeadProv;
    @Column(name = "ProductCode")
    private String productCode;
    @Column(name = "sno")
    private Integer sno;
    @Column(name = "FlagIntGiven")
    private String flagIntGiven;
    @Column(name = "LastIntGiven")
    private String lastIntGiven;
    @Column(name = "LedgerCode")
    private Integer ledgerCode;
    @Column(name = "LoanAuth")
    private String loanAuth;
    @Column(name = "MaxLendInt")
    private Float maxLendInt;
    @Column(name = "MaxTrans")
    private Short maxTrans;
    @Column(name = "minbalcharge")
    private Double minbalcharge;
    @Column(name = "OpcgBal")
    private Float opcgBal;
    @Column(name = "OpcgCurr")
    private Float opcgCurr;
    @Column(name = "OpcgMaxtrn")
    private Float opcgMaxtrn;
    @Column(name = "GLHeadName")
    private String gLHeadName;
    @Column(name = "GLHEADURI")
    private String glheaduri;

    public AccountTypeMaster() {
    }

    public AccountTypeMaster(String acctCode) {
        this.acctCode = acctCode;
    }

    public AccountTypeMaster(String acctCode, double minBalchq, double minInt, double staffint) {
        this.acctCode = acctCode;
        this.minBalchq = minBalchq;
        this.minInt = minInt;
        this.staffint = staffint;
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public String getAcctDesc() {
        return acctDesc;
    }

    public void setAcctDesc(String acctDesc) {
        this.acctDesc = acctDesc;
    }

    public Integer getMaxPay() {
        return maxPay;
    }

    public void setMaxPay(Integer maxPay) {
        this.maxPay = maxPay;
    }

    public Short getMinBal() {
        return minBal;
    }

    public void setMinBal(Short minBal) {
        this.minBal = minBal;
    }

    public double getMinBalchq() {
        return minBalchq;
    }

    public void setMinBalchq(double minBalchq) {
        this.minBalchq = minBalchq;
    }

    public double getMinInt() {
        return minInt;
    }

    public void setMinInt(double minInt) {
        this.minInt = minInt;
    }

    public double getStaffint() {
        return staffint;
    }

    public void setStaffint(double staffint) {
        this.staffint = staffint;
    }

    public String getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(String lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Short getMaxLendIntPeriod() {
        return maxLendIntPeriod;
    }

    public void setMaxLendIntPeriod(Short maxLendIntPeriod) {
        this.maxLendIntPeriod = maxLendIntPeriod;
    }

    public Double getChequeBounceAmt() {
        return chequeBounceAmt;
    }

    public void setChequeBounceAmt(Double chequeBounceAmt) {
        this.chequeBounceAmt = chequeBounceAmt;
    }

    public Integer getMinIntPeriod() {
        return minIntPeriod;
    }

    public void setMinIntPeriod(Integer minIntPeriod) {
        this.minIntPeriod = minIntPeriod;
    }

    public String getCrDbFlag() {
        return crDbFlag;
    }

    public void setCrDbFlag(String crDbFlag) {
        this.crDbFlag = crDbFlag;
    }

    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(Double penalty) {
        this.penalty = penalty;
    }

    public String getChqsrno() {
        return chqsrno;
    }

    public void setChqsrno(String chqsrno) {
        this.chqsrno = chqsrno;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public String getAcctNature() {
        return acctNature;
    }

    public void setAcctNature(String acctNature) {
        this.acctNature = acctNature;
    }

    public String getGLHead() {
        return gLHead;
    }

    public void setGLHead(String gLHead) {
        this.gLHead = gLHead;
    }

    public String getGLHeadInt() {
        return gLHeadInt;
    }

    public void setGLHeadInt(String gLHeadInt) {
        this.gLHeadInt = gLHeadInt;
    }

    public String getOfAcctnature() {
        return ofAcctnature;
    }

    public void setOfAcctnature(String ofAcctnature) {
        this.ofAcctnature = ofAcctnature;
    }

    public String getGLHeadProv() {
        return gLHeadProv;
    }

    public void setGLHeadProv(String gLHeadProv) {
        this.gLHeadProv = gLHeadProv;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getFlagIntGiven() {
        return flagIntGiven;
    }

    public void setFlagIntGiven(String flagIntGiven) {
        this.flagIntGiven = flagIntGiven;
    }

    public String getLastIntGiven() {
        return lastIntGiven;
    }

    public void setLastIntGiven(String lastIntGiven) {
        this.lastIntGiven = lastIntGiven;
    }

    public Integer getLedgerCode() {
        return ledgerCode;
    }

    public void setLedgerCode(Integer ledgerCode) {
        this.ledgerCode = ledgerCode;
    }

    public String getLoanAuth() {
        return loanAuth;
    }

    public void setLoanAuth(String loanAuth) {
        this.loanAuth = loanAuth;
    }

    public Float getMaxLendInt() {
        return maxLendInt;
    }

    public void setMaxLendInt(Float maxLendInt) {
        this.maxLendInt = maxLendInt;
    }

    public Short getMaxTrans() {
        return maxTrans;
    }

    public void setMaxTrans(Short maxTrans) {
        this.maxTrans = maxTrans;
    }

    public Double getMinbalcharge() {
        return minbalcharge;
    }

    public void setMinbalcharge(Double minbalcharge) {
        this.minbalcharge = minbalcharge;
    }

    public Float getOpcgBal() {
        return opcgBal;
    }

    public void setOpcgBal(Float opcgBal) {
        this.opcgBal = opcgBal;
    }

    public Float getOpcgCurr() {
        return opcgCurr;
    }

    public void setOpcgCurr(Float opcgCurr) {
        this.opcgCurr = opcgCurr;
    }

    public Float getOpcgMaxtrn() {
        return opcgMaxtrn;
    }

    public void setOpcgMaxtrn(Float opcgMaxtrn) {
        this.opcgMaxtrn = opcgMaxtrn;
    }

    public String getGLHeadName() {
        return gLHeadName;
    }

    public void setGLHeadName(String gLHeadName) {
        this.gLHeadName = gLHeadName;
    }

    public String getGlheaduri() {
        return glheaduri;
    }

    public void setGlheaduri(String glheaduri) {
        this.glheaduri = glheaduri;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acctCode != null ? acctCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountTypeMaster)) {
            return false;
        }
        AccountTypeMaster other = (AccountTypeMaster) object;
        if ((this.acctCode == null && other.acctCode != null) || (this.acctCode != null && !this.acctCode.equals(other.acctCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.AccountTypeMaster[acctCode=" + acctCode + "]";
    }
}
