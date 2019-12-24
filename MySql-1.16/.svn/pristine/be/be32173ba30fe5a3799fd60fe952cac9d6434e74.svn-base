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
@Table(name = "fdr_recon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FdrRecon.findAll", query = "SELECT f FROM FdrRecon f"),
    @NamedQuery(name = "FdrRecon.findByCtrlNo", query = "SELECT f FROM FdrRecon f WHERE f.ctrlNo = :ctrlNo"),
    @NamedQuery(name = "FdrRecon.findByAcno", query = "SELECT f FROM FdrRecon f WHERE f.fdrReconPK.acno = :acno"),
    @NamedQuery(name = "FdrRecon.findByBalance", query = "SELECT f FROM FdrRecon f WHERE f.balance = :balance"),
    @NamedQuery(name = "FdrRecon.findByDt", query = "SELECT f FROM FdrRecon f WHERE f.fdrReconPK.dt = :dt"),
    @NamedQuery(name = "FdrRecon.findByDrAmt", query = "SELECT f FROM FdrRecon f WHERE f.drAmt = :drAmt"),
    @NamedQuery(name = "FdrRecon.findByCrAmt", query = "SELECT f FROM FdrRecon f WHERE f.crAmt = :crAmt"),
    @NamedQuery(name = "FdrRecon.findByTy", query = "SELECT f FROM FdrRecon f WHERE f.ty = :ty"),
    @NamedQuery(name = "FdrRecon.findByTrantype", query = "SELECT f FROM FdrRecon f WHERE f.trantype = :trantype"),
    @NamedQuery(name = "FdrRecon.findByRecno", query = "SELECT f FROM FdrRecon f WHERE f.fdrReconPK.recno = :recno"),
    @NamedQuery(name = "FdrRecon.findByTrsno", query = "SELECT f FROM FdrRecon f WHERE f.trsno = :trsno"),
    @NamedQuery(name = "FdrRecon.findByInstno", query = "SELECT f FROM FdrRecon f WHERE f.instno = :instno"),
    @NamedQuery(name = "FdrRecon.findByPayBy", query = "SELECT f FROM FdrRecon f WHERE f.payBy = :payBy"),
    @NamedQuery(name = "FdrRecon.findByIy", query = "SELECT f FROM FdrRecon f WHERE f.iy = :iy"),
    @NamedQuery(name = "FdrRecon.findByTokenPaid", query = "SELECT f FROM FdrRecon f WHERE f.tokenPaid = :tokenPaid"),
    @NamedQuery(name = "FdrRecon.findByTokenPaidBy", query = "SELECT f FROM FdrRecon f WHERE f.tokenPaidBy = :tokenPaidBy"),
    @NamedQuery(name = "FdrRecon.findByTranDesc", query = "SELECT f FROM FdrRecon f WHERE f.tranDesc = :tranDesc"),
    @NamedQuery(name = "FdrRecon.findByTokenNo", query = "SELECT f FROM FdrRecon f WHERE f.tokenNo = :tokenNo"),
    @NamedQuery(name = "FdrRecon.findBySubTokenNo", query = "SELECT f FROM FdrRecon f WHERE f.subTokenNo = :subTokenNo"),
    @NamedQuery(name = "FdrRecon.findByDetails", query = "SELECT f FROM FdrRecon f WHERE f.details = :details"),
    @NamedQuery(name = "FdrRecon.findByCheckby", query = "SELECT f FROM FdrRecon f WHERE f.checkby = :checkby"),
    @NamedQuery(name = "FdrRecon.findByTranId", query = "SELECT f FROM FdrRecon f WHERE f.tranId = :tranId"),
    @NamedQuery(name = "FdrRecon.findByTermId", query = "SELECT f FROM FdrRecon f WHERE f.termId = :termId"),
    @NamedQuery(name = "FdrRecon.findByOrgBrnid", query = "SELECT f FROM FdrRecon f WHERE f.orgBrnid = :orgBrnid"),
    @NamedQuery(name = "FdrRecon.findByDestBrnid", query = "SELECT f FROM FdrRecon f WHERE f.destBrnid = :destBrnid"),
    @NamedQuery(name = "FdrRecon.findByAdviceNo", query = "SELECT f FROM FdrRecon f WHERE f.adviceNo = :adviceNo"),
    @NamedQuery(name = "FdrRecon.findByTranTime", query = "SELECT f FROM FdrRecon f WHERE f.tranTime = :tranTime"),
    @NamedQuery(name = "FdrRecon.findByAuth", query = "SELECT f FROM FdrRecon f WHERE f.auth = :auth"),
    @NamedQuery(name = "FdrRecon.findByEnterBy", query = "SELECT f FROM FdrRecon f WHERE f.enterBy = :enterBy"),
    @NamedQuery(name = "FdrRecon.findByAuthBy", query = "SELECT f FROM FdrRecon f WHERE f.authBy = :authBy")})
public class FdrRecon extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FdrReconPK fdrReconPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTRL_NO")
    private int ctrlNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BALANCE")
    private double balance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DR_AMT")
    private double drAmt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CR_AMT")
    private double crAmt;
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
    @Column(name = "PAY_BY")
    private double payBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IY")
    private int iy;
    @Size(max = 1)
    @Column(name = "TOKEN_PAID")
    private String tokenPaid;
    @Size(max = 20)
    @Column(name = "TOKEN_PAID_BY")
    private String tokenPaidBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRAN_DESC")
    private int tranDesc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOKEN_NO")
    private Double tokenNo;
    @Size(max = 5)
    @Column(name = "SUB_TOKEN_NO")
    private String subTokenNo;
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
    @Column(name = "ADVICE_NO")
    private String adviceNo;
    @Column(name = "TRAN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "AUTH")
    private String auth;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Size(max = 20)
    @Column(name = "AUTH_BY")
    private String authBy;

    public FdrRecon() {
    }

    public FdrRecon(FdrReconPK fdrReconPK) {
        this.fdrReconPK = fdrReconPK;
    }

    public FdrRecon(FdrReconPK fdrReconPK, int ctrlNo, double balance, double drAmt, double crAmt, int ty, int trantype, int trsno, double payBy, int iy, int tranDesc, String auth, String enterBy) {
        this.fdrReconPK = fdrReconPK;
        this.ctrlNo = ctrlNo;
        this.balance = balance;
        this.drAmt = drAmt;
        this.crAmt = crAmt;
        this.ty = ty;
        this.trantype = trantype;
        this.trsno = trsno;
        this.payBy = payBy;
        this.iy = iy;
        this.tranDesc = tranDesc;
        this.auth = auth;
        this.enterBy = enterBy;
    }

    public FdrRecon(String acno, Date dt, double recno) {
        this.fdrReconPK = new FdrReconPK(acno, dt, recno);
    }

    public FdrReconPK getFdrReconPK() {
        return fdrReconPK;
    }

    public void setFdrReconPK(FdrReconPK fdrReconPK) {
        this.fdrReconPK = fdrReconPK;
    }

    public int getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(int ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

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

    public double getPayBy() {
        return payBy;
    }

    public void setPayBy(double payBy) {
        this.payBy = payBy;
    }

    public int getIy() {
        return iy;
    }

    public void setIy(int iy) {
        this.iy = iy;
    }

    public String getTokenPaid() {
        return tokenPaid;
    }

    public void setTokenPaid(String tokenPaid) {
        this.tokenPaid = tokenPaid;
    }

    public String getTokenPaidBy() {
        return tokenPaidBy;
    }

    public void setTokenPaidBy(String tokenPaidBy) {
        this.tokenPaidBy = tokenPaidBy;
    }

    public int getTranDesc() {
        return tranDesc;
    }

    public void setTranDesc(int tranDesc) {
        this.tranDesc = tranDesc;
    }

    public Double getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(Double tokenNo) {
        this.tokenNo = tokenNo;
    }

    public String getSubTokenNo() {
        return subTokenNo;
    }

    public void setSubTokenNo(String subTokenNo) {
        this.subTokenNo = subTokenNo;
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

    public String getAdviceNo() {
        return adviceNo;
    }

    public void setAdviceNo(String adviceNo) {
        this.adviceNo = adviceNo;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
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

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fdrReconPK != null ? fdrReconPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FdrRecon)) {
            return false;
        }
        FdrRecon other = (FdrRecon) object;
        if ((this.fdrReconPK == null && other.fdrReconPK != null) || (this.fdrReconPK != null && !this.fdrReconPK.equals(other.fdrReconPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.FdrRecon[ fdrReconPK=" + fdrReconPK + " ]";
    }
}
