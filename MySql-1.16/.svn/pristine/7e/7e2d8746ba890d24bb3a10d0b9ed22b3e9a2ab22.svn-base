/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.transaction;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "gl_recon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GlRecon.findAll", query = "SELECT g FROM GlRecon g"),
    //@NamedQuery(name = "GlRecon.findByTxnid", query = "SELECT g FROM GlRecon g WHERE g.txnid = :txnid"),
    @NamedQuery(name = "GlRecon.findByACNo", query = "SELECT g FROM GlRecon g WHERE g.glReconPK.aCNo = :aCNo"),
    @NamedQuery(name = "GlRecon.findByBalance", query = "SELECT g FROM GlRecon g WHERE g.balance = :balance"),
    @NamedQuery(name = "GlRecon.findByDt", query = "SELECT g FROM GlRecon g WHERE g.glReconPK.dt = :dt"),
    @NamedQuery(name = "GlRecon.findByDrAmt", query = "SELECT g FROM GlRecon g WHERE g.drAmt = :drAmt"),
    @NamedQuery(name = "GlRecon.findByCrAmt", query = "SELECT g FROM GlRecon g WHERE g.crAmt = :crAmt"),
    @NamedQuery(name = "GlRecon.findByTy", query = "SELECT g FROM GlRecon g WHERE g.ty = :ty"),
    @NamedQuery(name = "GlRecon.findByTranType", query = "SELECT g FROM GlRecon g WHERE g.tranType = :tranType"),
    @NamedQuery(name = "GlRecon.findByRecno", query = "SELECT g FROM GlRecon g WHERE g.glReconPK.recno = :recno"),
    @NamedQuery(name = "GlRecon.findByTrsno", query = "SELECT g FROM GlRecon g WHERE g.trsno = :trsno"),
    @NamedQuery(name = "GlRecon.findByInstno", query = "SELECT g FROM GlRecon g WHERE g.instno = :instno"),
    @NamedQuery(name = "GlRecon.findByPayby", query = "SELECT g FROM GlRecon g WHERE g.payby = :payby"),
    @NamedQuery(name = "GlRecon.findByIy", query = "SELECT g FROM GlRecon g WHERE g.iy = :iy"),
    @NamedQuery(name = "GlRecon.findByAuth", query = "SELECT g FROM GlRecon g WHERE g.auth = :auth"),
    @NamedQuery(name = "GlRecon.findByEnterBy", query = "SELECT g FROM GlRecon g WHERE g.enterBy = :enterBy"),
    @NamedQuery(name = "GlRecon.findByAuthby", query = "SELECT g FROM GlRecon g WHERE g.authby = :authby"),
    @NamedQuery(name = "GlRecon.findByTokenpaidBy", query = "SELECT g FROM GlRecon g WHERE g.tokenpaidBy = :tokenpaidBy"),
    @NamedQuery(name = "GlRecon.findByTokenpaid", query = "SELECT g FROM GlRecon g WHERE g.tokenpaid = :tokenpaid"),
    @NamedQuery(name = "GlRecon.findByTranDesc", query = "SELECT g FROM GlRecon g WHERE g.tranDesc = :tranDesc"),
    @NamedQuery(name = "GlRecon.findByTokenno", query = "SELECT g FROM GlRecon g WHERE g.tokenno = :tokenno"),
    @NamedQuery(name = "GlRecon.findBySubTokenNo", query = "SELECT g FROM GlRecon g WHERE g.subTokenNo = :subTokenNo"),
    @NamedQuery(name = "GlRecon.findByTrantime", query = "SELECT g FROM GlRecon g WHERE g.trantime = :trantime"),
    @NamedQuery(name = "GlRecon.findByDetails", query = "SELECT g FROM GlRecon g WHERE g.details = :details"),
    @NamedQuery(name = "GlRecon.findByCheckBy", query = "SELECT g FROM GlRecon g WHERE g.checkBy = :checkBy"),
    @NamedQuery(name = "GlRecon.findByTranid", query = "SELECT g FROM GlRecon g WHERE g.tranid = :tranid"),
    @NamedQuery(name = "GlRecon.findByTermid", query = "SELECT g FROM GlRecon g WHERE g.termid = :termid"),
    @NamedQuery(name = "GlRecon.findByOrgBrnid", query = "SELECT g FROM GlRecon g WHERE g.orgBrnid = :orgBrnid"),
    @NamedQuery(name = "GlRecon.findByDestBrnid", query = "SELECT g FROM GlRecon g WHERE g.destBrnid = :destBrnid"),
    @NamedQuery(name = "GlRecon.findByClgReason", query = "SELECT g FROM GlRecon g WHERE g.clgReason = :clgReason"),
    @NamedQuery(name = "GlRecon.findByFavorof", query = "SELECT g FROM GlRecon g WHERE g.favorof = :favorof"),
    @NamedQuery(name = "GlRecon.findByValueDt", query = "SELECT g FROM GlRecon g WHERE g.valueDt = :valueDt"),
    @NamedQuery(name = "GlRecon.findByInstDt", query = "SELECT g FROM GlRecon g WHERE g.instDt = :instDt"),
    @NamedQuery(name = "GlRecon.findByAdviceNo", query = "SELECT g FROM GlRecon g WHERE g.adviceNo = :adviceNo"),
    @NamedQuery(name = "GlRecon.findByAdviceBrnCode", query = "SELECT g FROM GlRecon g WHERE g.adviceBrnCode = :adviceBrnCode")})
public class GlRecon extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GlReconPK glReconPK;
//    @Column(name = "TXNID", unique = true)
//    private long txnid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Balance")
    private double balance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DrAmt")
    private double drAmt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CrAmt")
    private double crAmt;
    @Column(name = "Ty")
    private Integer ty;
    @Column(name = "TranType")
    private Integer tranType;
    @Column(name = "trsno")
    private Integer trsno;
    @Size(max = 10)
    @Column(name = "instno")
    private String instno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "payby")
    private double payby;
    @Column(name = "iy")
    private Integer iy;
    @Size(max = 1)
    @Column(name = "auth")
    private String auth;
    @Size(max = 20)
    @Column(name = "EnterBy")
    private String enterBy;
    @Size(max = 20)
    @Column(name = "authby")
    private String authby;
    @Size(max = 20)
    @Column(name = "tokenpaidBy")
    private String tokenpaidBy;
    @Size(max = 1)
    @Column(name = "tokenpaid")
    private String tokenpaid;
    @Column(name = "TranDesc")
    private Integer tranDesc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tokenno")
    private Double tokenno;
    @Size(max = 5)
    @Column(name = "SubTokenNo")
    private String subTokenNo;
    @Column(name = "Trantime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trantime;
    @Size(max = 255)
    @Column(name = "Details")
    private String details;
    @Size(max = 20)
    @Column(name = "CheckBy")
    private String checkBy;
    @Column(name = "Tran_id")
    private BigInteger tranid;
    @Size(max = 6)
    @Column(name = "Term_id")
    private String termid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "org_brnid")
    private String orgBrnid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "dest_brnid")
    private String destBrnid;
    @Column(name = "ClgReason")
    private Integer clgReason;
    @Size(max = 175)
    @Column(name = "favorof")
    private String favorof;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ValueDt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDt;
    @Column(name = "InstDt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date instDt;
    @Size(max = 8)
    @Column(name = "AdviceNo")
    private String adviceNo;
    @Size(max = 6)
    @Column(name = "AdviceBrnCode")
    private String adviceBrnCode;

    public GlRecon() {
    }

    public GlRecon(GlReconPK glReconPK) {
        this.glReconPK = glReconPK;
    }

    public GlRecon(GlReconPK glReconPK, long txnid, double balance, double drAmt, double crAmt, double payby, String orgBrnid, String destBrnid, Date valueDt) {
        this.glReconPK = glReconPK;
        //this.txnid = txnid;
        this.balance = balance;
        this.drAmt = drAmt;
        this.crAmt = crAmt;
        this.payby = payby;
        this.orgBrnid = orgBrnid;
        this.destBrnid = destBrnid;
        this.valueDt = valueDt;
    }

    public GlRecon(String aCNo, Date dt, double recno) {
        this.glReconPK = new GlReconPK(aCNo, dt, recno);
    }

    public GlReconPK getGlReconPK() {
        return glReconPK;
    }

    public void setGlReconPK(GlReconPK glReconPK) {
        this.glReconPK = glReconPK;
    }

//    public long getTxnid() {
//        return txnid;
//    }
//
//    public void setTxnid(long txnid) {
//        this.txnid = txnid;
//    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(double drAmt) {
        this.drAmt = drAmt;
    }

    public double getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(double crAmt) {
        this.crAmt = crAmt;
    }

    public Integer getTy() {
        return ty;
    }

    public void setTy(Integer ty) {
        this.ty = ty;
    }

    public Integer getTranType() {
        return tranType;
    }

    public void setTranType(Integer tranType) {
        this.tranType = tranType;
    }

    public Integer getTrsno() {
        return trsno;
    }

    public void setTrsno(Integer trsno) {
        this.trsno = trsno;
    }

    public String getInstno() {
        return instno;
    }

    public void setInstno(String instno) {
        this.instno = instno;
    }

    public double getPayby() {
        return payby;
    }

    public void setPayby(double payby) {
        this.payby = payby;
    }

    public Integer getIy() {
        return iy;
    }

    public void setIy(Integer iy) {
        this.iy = iy;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getAuthby() {
        return authby;
    }

    public void setAuthby(String authby) {
        this.authby = authby;
    }

    public String getTokenpaidBy() {
        return tokenpaidBy;
    }

    public void setTokenpaidBy(String tokenpaidBy) {
        this.tokenpaidBy = tokenpaidBy;
    }

    public String getTokenpaid() {
        return tokenpaid;
    }

    public void setTokenpaid(String tokenpaid) {
        this.tokenpaid = tokenpaid;
    }

    public Integer getTranDesc() {
        return tranDesc;
    }

    public void setTranDesc(Integer tranDesc) {
        this.tranDesc = tranDesc;
    }

    public Double getTokenno() {
        return tokenno;
    }

    public void setTokenno(Double tokenno) {
        this.tokenno = tokenno;
    }

    public String getSubTokenNo() {
        return subTokenNo;
    }

    public void setSubTokenNo(String subTokenNo) {
        this.subTokenNo = subTokenNo;
    }

    public Date getTrantime() {
        return trantime;
    }

    public void setTrantime(Date trantime) {
        this.trantime = trantime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }

    public BigInteger getTranid() {
        return tranid;
    }

    public void setTranid(BigInteger tranid) {
        this.tranid = tranid;
    }

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public String getOrgBrnid() {
        return orgBrnid;
    }

    public void setOrgBrnid(String orgBrnid) {
        this.orgBrnid = orgBrnid;
    }

    public String getDestBrnid() {
        return destBrnid;
    }

    public void setDestBrnid(String destBrnid) {
        this.destBrnid = destBrnid;
    }

    public Integer getClgReason() {
        return clgReason;
    }

    public void setClgReason(Integer clgReason) {
        this.clgReason = clgReason;
    }

    public String getFavorof() {
        return favorof;
    }

    public void setFavorof(String favorof) {
        this.favorof = favorof;
    }

    public Date getValueDt() {
        return valueDt;
    }

    public void setValueDt(Date valueDt) {
        this.valueDt = valueDt;
    }

    public Date getInstDt() {
        return instDt;
    }

    public void setInstDt(Date instDt) {
        this.instDt = instDt;
    }

    public String getAdviceNo() {
        return adviceNo;
    }

    public void setAdviceNo(String adviceNo) {
        this.adviceNo = adviceNo;
    }

    public String getAdviceBrnCode() {
        return adviceBrnCode;
    }

    public void setAdviceBrnCode(String adviceBrnCode) {
        this.adviceBrnCode = adviceBrnCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (glReconPK != null ? glReconPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GlRecon)) {
            return false;
        }
        GlRecon other = (GlRecon) object;
        if ((this.glReconPK == null && other.glReconPK != null) || (this.glReconPK != null && !this.glReconPK.equals(other.glReconPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.transaction.GlRecon[ glReconPK=" + glReconPK + " ]";
    }
}
