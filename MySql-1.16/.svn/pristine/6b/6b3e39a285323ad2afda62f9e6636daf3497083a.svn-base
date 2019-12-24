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
@Table(name = "share_recon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShareRecon.findAll", query = "SELECT s FROM ShareRecon s"),
    @NamedQuery(name = "ShareRecon.findByCtrlno", query = "SELECT s FROM ShareRecon s WHERE s.ctrlno = :ctrlno"),
    @NamedQuery(name = "ShareRecon.findByAcno", query = "SELECT s FROM ShareRecon s WHERE s.shareReconPK.acno = :acno"),
    @NamedQuery(name = "ShareRecon.findByBalance", query = "SELECT s FROM ShareRecon s WHERE s.balance = :balance"),
    @NamedQuery(name = "ShareRecon.findByDt", query = "SELECT s FROM ShareRecon s WHERE s.shareReconPK.dt = :dt"),
    @NamedQuery(name = "ShareRecon.findByDrAmt", query = "SELECT s FROM ShareRecon s WHERE s.drAmt = :drAmt"),
    @NamedQuery(name = "ShareRecon.findByCrAmt", query = "SELECT s FROM ShareRecon s WHERE s.crAmt = :crAmt"),
    @NamedQuery(name = "ShareRecon.findByTy", query = "SELECT s FROM ShareRecon s WHERE s.ty = :ty"),
    @NamedQuery(name = "ShareRecon.findByTranType", query = "SELECT s FROM ShareRecon s WHERE s.tranType = :tranType"),
    @NamedQuery(name = "ShareRecon.findByRecno", query = "SELECT s FROM ShareRecon s WHERE s.shareReconPK.recno = :recno"),
    @NamedQuery(name = "ShareRecon.findByTrsno", query = "SELECT s FROM ShareRecon s WHERE s.trsno = :trsno"),
    @NamedQuery(name = "ShareRecon.findByInstno", query = "SELECT s FROM ShareRecon s WHERE s.instno = :instno"),
    @NamedQuery(name = "ShareRecon.findByPayby", query = "SELECT s FROM ShareRecon s WHERE s.payby = :payby"),
    @NamedQuery(name = "ShareRecon.findByIy", query = "SELECT s FROM ShareRecon s WHERE s.iy = :iy"),
    @NamedQuery(name = "ShareRecon.findByTokenPaid", query = "SELECT s FROM ShareRecon s WHERE s.tokenPaid = :tokenPaid"),
    @NamedQuery(name = "ShareRecon.findByTokenPaidBy", query = "SELECT s FROM ShareRecon s WHERE s.tokenPaidBy = :tokenPaidBy"),
    @NamedQuery(name = "ShareRecon.findByTranDesc", query = "SELECT s FROM ShareRecon s WHERE s.tranDesc = :tranDesc"),
    @NamedQuery(name = "ShareRecon.findByTokenNo", query = "SELECT s FROM ShareRecon s WHERE s.tokenNo = :tokenNo"),
    @NamedQuery(name = "ShareRecon.findBySubTokenNo", query = "SELECT s FROM ShareRecon s WHERE s.subTokenNo = :subTokenNo"),
    @NamedQuery(name = "ShareRecon.findByDetails", query = "SELECT s FROM ShareRecon s WHERE s.details = :details"),
    @NamedQuery(name = "ShareRecon.findByCheckBy", query = "SELECT s FROM ShareRecon s WHERE s.checkBy = :checkBy"),
    @NamedQuery(name = "ShareRecon.findByTranId", query = "SELECT s FROM ShareRecon s WHERE s.tranId = :tranId"),
    @NamedQuery(name = "ShareRecon.findByTermId", query = "SELECT s FROM ShareRecon s WHERE s.termId = :termId"),
    @NamedQuery(name = "ShareRecon.findByOrgBrnid", query = "SELECT s FROM ShareRecon s WHERE s.orgBrnid = :orgBrnid"),
    @NamedQuery(name = "ShareRecon.findByDestBrnid", query = "SELECT s FROM ShareRecon s WHERE s.destBrnid = :destBrnid"),
    @NamedQuery(name = "ShareRecon.findByAdviceNo", query = "SELECT s FROM ShareRecon s WHERE s.adviceNo = :adviceNo"),
    @NamedQuery(name = "ShareRecon.findByTranTime", query = "SELECT s FROM ShareRecon s WHERE s.tranTime = :tranTime"),
    @NamedQuery(name = "ShareRecon.findByAuth", query = "SELECT s FROM ShareRecon s WHERE s.auth = :auth"),
    @NamedQuery(name = "ShareRecon.findByEnterBy", query = "SELECT s FROM ShareRecon s WHERE s.enterBy = :enterBy"),
    @NamedQuery(name = "ShareRecon.findByAuthBy", query = "SELECT s FROM ShareRecon s WHERE s.authBy = :authBy")})
public class ShareRecon extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ShareReconPK shareReconPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTRLNO")
    private long ctrlno;
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
    @Column(name = "TRAN_TYPE")
    private int tranType;
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
    @Column(name = "CHECK_BY")
    private String checkBy;
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

    public ShareRecon() {
    }

    public ShareRecon(ShareReconPK shareReconPK) {
        this.shareReconPK = shareReconPK;
    }

    public ShareRecon(ShareReconPK shareReconPK, long ctrlno, double balance, double drAmt, double crAmt, int ty, int tranType, int trsno, double payby, int iy, int tranDesc, String auth, String enterBy) {
        this.shareReconPK = shareReconPK;
        this.ctrlno = ctrlno;
        this.balance = balance;
        this.drAmt = drAmt;
        this.crAmt = crAmt;
        this.ty = ty;
        this.tranType = tranType;
        this.trsno = trsno;
        this.payby = payby;
        this.iy = iy;
        this.tranDesc = tranDesc;
        this.auth = auth;
        this.enterBy = enterBy;
    }

    public ShareRecon(String acno, Date dt, double recno) {
        this.shareReconPK = new ShareReconPK(acno, dt, recno);
    }

    public ShareReconPK getShareReconPK() {
        return shareReconPK;
    }

    public void setShareReconPK(ShareReconPK shareReconPK) {
        this.shareReconPK = shareReconPK;
    }

    public long getCtrlno() {
        return ctrlno;
    }

    public void setCtrlno(long ctrlno) {
        this.ctrlno = ctrlno;
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

    public int getTranType() {
        return tranType;
    }

    public void setTranType(int tranType) {
        this.tranType = tranType;
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

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
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
        hash += (shareReconPK != null ? shareReconPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShareRecon)) {
            return false;
        }
        ShareRecon other = (ShareRecon) object;
        if ((this.shareReconPK == null && other.shareReconPK != null) || (this.shareReconPK != null && !this.shareReconPK.equals(other.shareReconPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.ShareRecon[ shareReconPK=" + shareReconPK + " ]";
    }
}
