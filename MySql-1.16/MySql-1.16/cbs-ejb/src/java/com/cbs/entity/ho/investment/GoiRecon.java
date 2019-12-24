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
@Table(name = "goi_recon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GoiRecon.findAll", query = "SELECT g FROM GoiRecon g"),
    @NamedQuery(name = "GoiRecon.findByTxnid", query = "SELECT g FROM GoiRecon g WHERE g.goiReconPK.txnid = :txnid"),
    @NamedQuery(name = "GoiRecon.findByCtrlno", query = "SELECT g FROM GoiRecon g WHERE g.goiReconPK.ctrlno = :ctrlno"),
    @NamedQuery(name = "GoiRecon.findByAcno", query = "SELECT g FROM GoiRecon g WHERE g.goiReconPK.acno = :acno"),
    @NamedQuery(name = "GoiRecon.findByBalance", query = "SELECT g FROM GoiRecon g WHERE g.balance = :balance"),
    @NamedQuery(name = "GoiRecon.findByDt", query = "SELECT g FROM GoiRecon g WHERE g.goiReconPK.dt = :dt"),
    @NamedQuery(name = "GoiRecon.findByDramt", query = "SELECT g FROM GoiRecon g WHERE g.dramt = :dramt"),
    @NamedQuery(name = "GoiRecon.findByCramt", query = "SELECT g FROM GoiRecon g WHERE g.cramt = :cramt"),
    @NamedQuery(name = "GoiRecon.findByTy", query = "SELECT g FROM GoiRecon g WHERE g.ty = :ty"),
    @NamedQuery(name = "GoiRecon.findByTrantype", query = "SELECT g FROM GoiRecon g WHERE g.trantype = :trantype"),
    @NamedQuery(name = "GoiRecon.findByRecno", query = "SELECT g FROM GoiRecon g WHERE g.goiReconPK.recno = :recno"),
    @NamedQuery(name = "GoiRecon.findByTrsno", query = "SELECT g FROM GoiRecon g WHERE g.trsno = :trsno"),
    @NamedQuery(name = "GoiRecon.findByInstno", query = "SELECT g FROM GoiRecon g WHERE g.instno = :instno"),
    @NamedQuery(name = "GoiRecon.findByPayby", query = "SELECT g FROM GoiRecon g WHERE g.payby = :payby"),
    @NamedQuery(name = "GoiRecon.findByIy", query = "SELECT g FROM GoiRecon g WHERE g.iy = :iy"),
    @NamedQuery(name = "GoiRecon.findByTokenpaid", query = "SELECT g FROM GoiRecon g WHERE g.tokenpaid = :tokenpaid"),
    @NamedQuery(name = "GoiRecon.findByTokenpaidby", query = "SELECT g FROM GoiRecon g WHERE g.tokenpaidby = :tokenpaidby"),
    @NamedQuery(name = "GoiRecon.findByTrandesc", query = "SELECT g FROM GoiRecon g WHERE g.trandesc = :trandesc"),
    @NamedQuery(name = "GoiRecon.findByTokenno", query = "SELECT g FROM GoiRecon g WHERE g.tokenno = :tokenno"),
    @NamedQuery(name = "GoiRecon.findBySubtokenno", query = "SELECT g FROM GoiRecon g WHERE g.subtokenno = :subtokenno"),
    @NamedQuery(name = "GoiRecon.findByDetails", query = "SELECT g FROM GoiRecon g WHERE g.details = :details"),
    @NamedQuery(name = "GoiRecon.findByCheckby", query = "SELECT g FROM GoiRecon g WHERE g.checkby = :checkby"),
    @NamedQuery(name = "GoiRecon.findByTranId", query = "SELECT g FROM GoiRecon g WHERE g.tranId = :tranId"),
    @NamedQuery(name = "GoiRecon.findByTermId", query = "SELECT g FROM GoiRecon g WHERE g.termId = :termId"),
    @NamedQuery(name = "GoiRecon.findByOrgBrnid", query = "SELECT g FROM GoiRecon g WHERE g.orgBrnid = :orgBrnid"),
    @NamedQuery(name = "GoiRecon.findByDestBrnid", query = "SELECT g FROM GoiRecon g WHERE g.destBrnid = :destBrnid"),
    @NamedQuery(name = "GoiRecon.findByAdviceno", query = "SELECT g FROM GoiRecon g WHERE g.adviceno = :adviceno"),
    @NamedQuery(name = "GoiRecon.findByTrantime", query = "SELECT g FROM GoiRecon g WHERE g.trantime = :trantime"),
    @NamedQuery(name = "GoiRecon.findByAuth", query = "SELECT g FROM GoiRecon g WHERE g.auth = :auth"),
    @NamedQuery(name = "GoiRecon.findByEnterby", query = "SELECT g FROM GoiRecon g WHERE g.enterby = :enterby"),
    @NamedQuery(name = "GoiRecon.findByAuthby", query = "SELECT g FROM GoiRecon g WHERE g.authby = :authby")})
public class GoiRecon extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GoiReconPK goiReconPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BALANCE")
    private double balance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DRAMT")
    private double dramt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CRAMT")
    private double cramt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TY")
    private int ty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANTYPE")
    private int trantype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRSNO")
    private int trsno;
    @Size(max = 10)
    @Column(name = "INSTNO")
    private String instno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYBY")
    private double payby;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IY")
    private int iy;
    @Size(max = 1)
    @Column(name = "TOKENPAID")
    private String tokenpaid;
    @Size(max = 20)
    @Column(name = "TOKENPAIDBY")
    private String tokenpaidby;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANDESC")
    private int trandesc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOKENNO")
    private Double tokenno;
    @Size(max = 5)
    @Column(name = "SUBTOKENNO")
    private String subtokenno;
    @Size(max = 255)
    @Column(name = "DETAILS")
    private String details;
    @Size(max = 20)
    @Column(name = "CHECKBY")
    private String checkby;
    @Column(name = "TRAN_ID")
    private BigInteger tranId;
    @Size(max = 6)
    @Column(name = "TERM_ID")
    private String termId;
    @Size(max = 6)
    @Column(name = "ORG_BRNID")
    private String orgBrnid;
    @Size(max = 6)
    @Column(name = "DEST_BRNID")
    private String destBrnid;
    @Size(max = 8)
    @Column(name = "ADVICENO")
    private String adviceno;
    @Column(name = "TRANTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trantime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "AUTH")
    private String auth;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ENTERBY")
    private String enterby;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "AUTHBY")
    private String authby;

    public GoiRecon() {
    }

    public GoiRecon(GoiReconPK goiReconPK) {
        this.goiReconPK = goiReconPK;
    }

    public GoiRecon(GoiReconPK goiReconPK, double balance, double dramt, double cramt, int ty, int trantype, int trsno, double payby, int iy, int trandesc, String auth, String enterby, String authby) {
        this.goiReconPK = goiReconPK;
        this.balance = balance;
        this.dramt = dramt;
        this.cramt = cramt;
        this.ty = ty;
        this.trantype = trantype;
        this.trsno = trsno;
        this.payby = payby;
        this.iy = iy;
        this.trandesc = trandesc;
        this.auth = auth;
        this.enterby = enterby;
        this.authby = authby;
    }

    public GoiRecon(long txnid, long ctrlno, String acno, Date dt, double recno) {
        this.goiReconPK = new GoiReconPK(txnid, ctrlno, acno, dt, recno);
    }

    public GoiReconPK getGoiReconPK() {
        return goiReconPK;
    }

    public void setGoiReconPK(GoiReconPK goiReconPK) {
        this.goiReconPK = goiReconPK;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getDramt() {
        return dramt;
    }

    public void setDramt(double dramt) {
        this.dramt = dramt;
    }

    public double getCramt() {
        return cramt;
    }

    public void setCramt(double cramt) {
        this.cramt = cramt;
    }

    public int getTy() {
        return ty;
    }

    public void setTy(int ty) {
        this.ty = ty;
    }

    public int getTrantype() {
        return trantype;
    }

    public void setTrantype(int trantype) {
        this.trantype = trantype;
    }

    public int getTrsno() {
        return trsno;
    }

    public void setTrsno(int trsno) {
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

    public int getIy() {
        return iy;
    }

    public void setIy(int iy) {
        this.iy = iy;
    }

    public String getTokenpaid() {
        return tokenpaid;
    }

    public void setTokenpaid(String tokenpaid) {
        this.tokenpaid = tokenpaid;
    }

    public String getTokenpaidby() {
        return tokenpaidby;
    }

    public void setTokenpaidby(String tokenpaidby) {
        this.tokenpaidby = tokenpaidby;
    }

    public int getTrandesc() {
        return trandesc;
    }

    public void setTrandesc(int trandesc) {
        this.trandesc = trandesc;
    }

    public Double getTokenno() {
        return tokenno;
    }

    public void setTokenno(Double tokenno) {
        this.tokenno = tokenno;
    }

    public String getSubtokenno() {
        return subtokenno;
    }

    public void setSubtokenno(String subtokenno) {
        this.subtokenno = subtokenno;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCheckby() {
        return checkby;
    }

    public void setCheckby(String checkby) {
        this.checkby = checkby;
    }

    public BigInteger getTranId() {
        return tranId;
    }

    public void setTranId(BigInteger tranId) {
        this.tranId = tranId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
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

    public String getAdviceno() {
        return adviceno;
    }

    public void setAdviceno(String adviceno) {
        this.adviceno = adviceno;
    }

    public Date getTrantime() {
        return trantime;
    }

    public void setTrantime(Date trantime) {
        this.trantime = trantime;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public String getAuthby() {
        return authby;
    }

    public void setAuthby(String authby) {
        this.authby = authby;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (goiReconPK != null ? goiReconPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GoiRecon)) {
            return false;
        }
        GoiRecon other = (GoiRecon) object;
        if ((this.goiReconPK == null && other.goiReconPK != null) || (this.goiReconPK != null && !this.goiReconPK.equals(other.goiReconPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.GoiRecon[ goiReconPK=" + goiReconPK + " ]";
    }
}
