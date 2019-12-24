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
 * @author root
 */
@Entity
@Table(name = "investment_share")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentShare.findAll", query = "SELECT i FROM InvestmentShare i"),
    @NamedQuery(name = "InvestmentShare.findByCtrlNo", query = "SELECT i FROM InvestmentShare i WHERE i.ctrlNo = :ctrlNo"),
    @NamedQuery(name = "InvestmentShare.findBySecType", query = "SELECT i FROM InvestmentShare i WHERE i.secType = :secType"),
    @NamedQuery(name = "InvestmentShare.findBySecDesc", query = "SELECT i FROM InvestmentShare i WHERE i.secDesc = :secDesc"),
    @NamedQuery(name = "InvestmentShare.findByRoi", query = "SELECT i FROM InvestmentShare i WHERE i.roi = :roi"),
    @NamedQuery(name = "InvestmentShare.findBySellerName", query = "SELECT i FROM InvestmentShare i WHERE i.sellerName = :sellerName"),
    @NamedQuery(name = "InvestmentShare.findByFaceValue", query = "SELECT i FROM InvestmentShare i WHERE i.faceValue = :faceValue"),
    @NamedQuery(name = "InvestmentShare.findByBookValue", query = "SELECT i FROM InvestmentShare i WHERE i.bookValue = :bookValue"),
    @NamedQuery(name = "InvestmentShare.findByIntOpt", query = "SELECT i FROM InvestmentShare i WHERE i.intOpt = :intOpt"),
    @NamedQuery(name = "InvestmentShare.findByEnterBy", query = "SELECT i FROM InvestmentShare i WHERE i.enterBy = :enterBy"),
    @NamedQuery(name = "InvestmentShare.findByAuth", query = "SELECT i FROM InvestmentShare i WHERE i.auth = :auth"),
    @NamedQuery(name = "InvestmentShare.findByAuthBy", query = "SELECT i FROM InvestmentShare i WHERE i.authBy = :authBy"),
    @NamedQuery(name = "InvestmentShare.findByStatus", query = "SELECT i FROM InvestmentShare i WHERE i.status = :status"),
    @NamedQuery(name = "InvestmentShare.findByTranTime", query = "SELECT i FROM InvestmentShare i WHERE i.tranTime = :tranTime"),
    @NamedQuery(name = "InvestmentShare.findByBranch", query = "SELECT i FROM InvestmentShare i WHERE i.branch = :branch"),
    @NamedQuery(name = "InvestmentShare.findByFdrNo", query = "SELECT i FROM InvestmentShare i WHERE i.fdrNo = :fdrNo"),
    @NamedQuery(name = "InvestmentShare.findByCloseDt", query = "SELECT i FROM InvestmentShare i WHERE i.closeDt = :closeDt"),
    @NamedQuery(name = "InvestmentShare.findByPurchaseDt", query = "SELECT i FROM InvestmentShare i WHERE i.purchaseDt = :purchaseDt")})
public class InvestmentShare extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTRL_NO")
    private Integer ctrlNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SEC_TYPE")
    private String secType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "SEC_DESC")
    private String secDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROI")
    private double roi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "SELLER_NAME")
    private String sellerName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FACE_VALUE")
    private double faceValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BOOK_VALUE")
    private double bookValue;
    @Size(max = 1)
    @Column(name = "INT_OPT")
    private String intOpt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "AUTH")
    private String auth;
    @Size(max = 50)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "STATUS")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRAN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranTime;
    @Size(max = 10)
    @Column(name = "BRANCH")
    private String branch;
    @Size(max = 20)
    @Column(name = "FDR_NO")
    private String fdrNo;
    @Column(name = "CLOSE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDt;
    @Column(name = "PURCHASE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDt;

    public InvestmentShare() {
    }

    public InvestmentShare(Integer ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public InvestmentShare(Integer ctrlNo, String secType, String secDesc, double roi, String sellerName, double faceValue, double bookValue, String enterBy, String auth, String status, Date tranTime) {
        this.ctrlNo = ctrlNo;
        this.secType = secType;
        this.secDesc = secDesc;
        this.roi = roi;
        this.sellerName = sellerName;
        this.faceValue = faceValue;
        this.bookValue = bookValue;
        this.enterBy = enterBy;
        this.auth = auth;
        this.status = status;
        this.tranTime = tranTime;
    }

    public Integer getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(Integer ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public String getSecDesc() {
        return secDesc;
    }

    public void setSecDesc(String secDesc) {
        this.secDesc = secDesc;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(double faceValue) {
        this.faceValue = faceValue;
    }

    public double getBookValue() {
        return bookValue;
    }

    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFdrNo() {
        return fdrNo;
    }

    public void setFdrNo(String fdrNo) {
        this.fdrNo = fdrNo;
    }

    public Date getCloseDt() {
        return closeDt;
    }

    public void setCloseDt(Date closeDt) {
        this.closeDt = closeDt;
    }

    public Date getPurchaseDt() {
        return purchaseDt;
    }

    public void setPurchaseDt(Date purchaseDt) {
        this.purchaseDt = purchaseDt;
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
        if (!(object instanceof InvestmentShare)) {
            return false;
        }
        InvestmentShare other = (InvestmentShare) object;
        if ((this.ctrlNo == null && other.ctrlNo != null) || (this.ctrlNo != null && !this.ctrlNo.equals(other.ctrlNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentShare[ ctrlNo=" + ctrlNo + " ]";
    }
}
