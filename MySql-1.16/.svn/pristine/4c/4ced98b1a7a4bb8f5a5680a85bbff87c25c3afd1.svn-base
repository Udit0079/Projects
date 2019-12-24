/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "investment_frd_dates_and_details_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findAll", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByTxnId", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.txnId = :txnId"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByCtrlNo", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.ctrlNo = :ctrlNo"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByLastIntPaidDt", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.lastIntPaidDt = :lastIntPaidDt"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByIntOpt", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.intOpt = :intOpt"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByTotAmtIntRec", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.totAmtIntRec = :totAmtIntRec"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByPurchaseDt", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.purchaseDt = :purchaseDt"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByMatDt", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.matDt = :matDt"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByFaceValue", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.faceValue = :faceValue"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByRoi", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.roi = :roi"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findBySellerName", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.sellerName = :sellerName"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findBySecDesc", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.secDesc = :secDesc"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByBookValue", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.bookValue = :bookValue"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByFdrNo", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.fdrNo = :fdrNo"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByEnterBy", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.enterBy = :enterBy"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByAuthBy", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.authBy = :authBy"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByCreatedTranTime", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.createdTranTime = :createdTranTime"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByUpdateBy", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.updateBy = :updateBy"),
    @NamedQuery(name = "InvestmentFrdDatesAndDetailsHistory.findByUpdateTranTime", query = "SELECT i FROM InvestmentFrdDatesAndDetailsHistory i WHERE i.updateTranTime = :updateTranTime")})
public class InvestmentFrdDatesAndDetailsHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TXN_ID")
    private Long txnId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTRL_NO")
    private long ctrlNo;
    @Column(name = "LAST_INT_PAID_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastIntPaidDt;
    @Size(max = 1)
    @Column(name = "INT_OPT")
    private String intOpt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOT_AMT_INT_REC")
    private BigDecimal totAmtIntRec;
    @Column(name = "PURCHASE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDt;
    @Column(name = "MAT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date matDt;
    @Column(name = "FACE_VALUE")
    private BigDecimal faceValue;
    @Column(name = "ROI")
    private Double roi;
    @Size(max = 200)
    @Column(name = "SELLER_NAME")
    private String sellerName;
    @Size(max = 200)
    @Column(name = "SEC_DESC")
    private String secDesc;
    @Column(name = "BOOK_VALUE")
    private BigDecimal bookValue;
    @Size(max = 20)
    @Column(name = "FDR_NO")
    private String fdrNo;
    @Size(max = 50)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Size(max = 50)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Column(name = "CREATED_TRAN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTranTime;
    @Size(max = 50)
    @Column(name = "UPDATE_BY")
    private String updateBy;
    @Column(name = "UPDATE_TRAN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTranTime;

    public InvestmentFrdDatesAndDetailsHistory() {
    }

    public InvestmentFrdDatesAndDetailsHistory(Long txnId) {
        this.txnId = txnId;
    }

    public InvestmentFrdDatesAndDetailsHistory(Long txnId, long ctrlNo) {
        this.txnId = txnId;
        this.ctrlNo = ctrlNo;
    }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public long getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(long ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public Date getLastIntPaidDt() {
        return lastIntPaidDt;
    }

    public void setLastIntPaidDt(Date lastIntPaidDt) {
        this.lastIntPaidDt = lastIntPaidDt;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public BigDecimal getTotAmtIntRec() {
        return totAmtIntRec;
    }

    public void setTotAmtIntRec(BigDecimal totAmtIntRec) {
        this.totAmtIntRec = totAmtIntRec;
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

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSecDesc() {
        return secDesc;
    }

    public void setSecDesc(String secDesc) {
        this.secDesc = secDesc;
    }

    public BigDecimal getBookValue() {
        return bookValue;
    }

    public void setBookValue(BigDecimal bookValue) {
        this.bookValue = bookValue;
    }

    public String getFdrNo() {
        return fdrNo;
    }

    public void setFdrNo(String fdrNo) {
        this.fdrNo = fdrNo;
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

    public Date getCreatedTranTime() {
        return createdTranTime;
    }

    public void setCreatedTranTime(Date createdTranTime) {
        this.createdTranTime = createdTranTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTranTime() {
        return updateTranTime;
    }

    public void setUpdateTranTime(Date updateTranTime) {
        this.updateTranTime = updateTranTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnId != null ? txnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentFrdDatesAndDetailsHistory)) {
            return false;
        }
        InvestmentFrdDatesAndDetailsHistory other = (InvestmentFrdDatesAndDetailsHistory) object;
        if ((this.txnId == null && other.txnId != null) || (this.txnId != null && !this.txnId.equals(other.txnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentFrdDatesAndDetailsHistory[ txnId=" + txnId + " ]";
    }
}
