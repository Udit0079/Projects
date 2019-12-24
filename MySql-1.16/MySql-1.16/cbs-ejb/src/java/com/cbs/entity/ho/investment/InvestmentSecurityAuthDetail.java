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
@Table(name = "investment_security_auth_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findAll", query = "SELECT i FROM InvestmentSecurityAuthDetail i"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByMaxSec", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.maxSec = :maxSec"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByCrGlVal", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.crGlVal = :crGlVal"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findBySecTpDd", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.secTpDd = :secTpDd"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findBySecDtl", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.secDtl = :secDtl"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByFaceValue", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.faceValue = :faceValue"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByOrgnBrCode", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.orgnBrCode = :orgnBrCode"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByCrBranchDd", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.crBranchDd = :crBranchDd"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByBookValue", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.bookValue = :bookValue"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByAccrIntVal", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.accrIntVal = :accrIntVal"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByCodeNoVal", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.codeNoVal = :codeNoVal"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByRoiVal", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.roiVal = :roiVal"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByTransactionDate", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.transactionDate = :transactionDate"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findBySetFlag", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.setFlag = :setFlag"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findBySettlementDate", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.settlementDate = :settlementDate"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findBySellerNmVal", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.sellerNmVal = :sellerNmVal"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByConAcNoVal", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.conAcNoVal = :conAcNoVal"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByBrokerAcVal", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.brokerAcVal = :brokerAcVal"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByBrokerageAmtVal", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.brokerageAmtVal = :brokerageAmtVal"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByNoOfShrVal", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.noOfShrVal = :noOfShrVal"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByMatDate", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.matDate = :matDate"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByConSgAccountDd", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.conSgAccountDd = :conSgAccountDd"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByPriceVal", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.priceVal = :priceVal"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByPurchaseDate", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.purchaseDate = :purchaseDate"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByPrnIssueDate", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.prnIssueDate = :prnIssueDate"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByFInttPayDate", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.fInttPayDate = :fInttPayDate"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findBySinttPaydate", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.sinttPaydate = :sinttPaydate"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByIssuePrVal", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.issuePrVal = :issuePrVal"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByDetail", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.detail = :detail"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByYtm", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.ytm = :ytm"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByAuth", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.auth = :auth"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByAuthBy", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.authBy = :authBy"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByEnterBy", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.enterBy = :enterBy"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByEnterDate", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.enterDate = :enterDate"),
    @NamedQuery(name = "InvestmentSecurityAuthDetail.findByMarking", query = "SELECT i FROM InvestmentSecurityAuthDetail i WHERE i.marking = :marking")})
public class InvestmentSecurityAuthDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_sec")
    private Integer maxSec;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "cr_gl_val")
    private String crGlVal;
    @Size(max = 200)
    @Column(name = "sec_tp_dd")
    private String secTpDd;
    @Size(max = 200)
    @Column(name = "sec_dtl")
    private String secDtl;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "face_value")
    private BigDecimal faceValue;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "orgn_br_code")
    private String orgnBrCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "cr_branch_dd")
    private String crBranchDd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "book_value")
    private BigDecimal bookValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "accr_int_val")
    private BigDecimal accrIntVal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "code_no_val")
    private String codeNoVal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "roi_val")
    private double roiVal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "set_flag")
    private String setFlag;
    @Basic(optional = false)
    @NotNull
    @Column(name = "settlement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date settlementDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "seller_nm_val")
    private String sellerNmVal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "con_ac_no_val")
    private String conAcNoVal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "broker_ac_val")
    private String brokerAcVal;
    @Column(name = "brokerage_amt_val")
    private BigDecimal brokerageAmtVal;
    @Column(name = "no_of_shr_val")
    private Integer noOfShrVal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mat_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date matDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "con_sg_account_dd")
    private String conSgAccountDd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price_val")
    private BigDecimal priceVal;
    @Size(max = 10)
    @Column(name = "purchase_date")
    private String purchaseDate;
    @Size(max = 10)
    @Column(name = "prn_issue_date")
    private String prnIssueDate;
    @Size(max = 10)
    @Column(name = "f_intt_pay_date")
    private String fInttPayDate;
    @Size(max = 10)
    @Column(name = "s_intt_Pay_date")
    private String sinttPaydate;
    @Column(name = "issue_pr_val")
    private BigDecimal issuePrVal;
    @Size(max = 200)
    @Column(name = "detail")
    private String detail;
    @Column(name = "ytm")
    private Double ytm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "auth")
    private String auth;
    @Size(max = 30)
    @Column(name = "auth_by")
    private String authBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "enter_by")
    private String enterBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enter_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enterDate;
    @Column(name = "marking")
    private String marking;

    public InvestmentSecurityAuthDetail() {
    }

    public InvestmentSecurityAuthDetail(Integer maxSec) {
        this.maxSec = maxSec;
    }

    public InvestmentSecurityAuthDetail(Integer maxSec, String crGlVal, BigDecimal faceValue, String orgnBrCode, String crBranchDd, BigDecimal bookValue, BigDecimal accrIntVal, String codeNoVal, double roiVal, Date transactionDate, String setFlag, Date settlementDate, String sellerNmVal, String conAcNoVal, String brokerAcVal, Date matDate, String conSgAccountDd, BigDecimal priceVal, String auth, String enterBy, Date enterDate, String marking) {
        this.maxSec = maxSec;
        this.crGlVal = crGlVal;
        this.faceValue = faceValue;
        this.orgnBrCode = orgnBrCode;
        this.crBranchDd = crBranchDd;
        this.bookValue = bookValue;
        this.accrIntVal = accrIntVal;
        this.codeNoVal = codeNoVal;
        this.roiVal = roiVal;
        this.transactionDate = transactionDate;
        this.setFlag = setFlag;
        this.settlementDate = settlementDate;
        this.sellerNmVal = sellerNmVal;
        this.conAcNoVal = conAcNoVal;
        this.brokerAcVal = brokerAcVal;
        this.matDate = matDate;
        this.conSgAccountDd = conSgAccountDd;
        this.priceVal = priceVal;
        this.auth = auth;
        this.enterBy = enterBy;
        this.enterDate = enterDate;
        this.marking = marking;
    }

    public Integer getMaxSec() {
        return maxSec;
    }

    public void setMaxSec(Integer maxSec) {
        this.maxSec = maxSec;
    }

    public String getCrGlVal() {
        return crGlVal;
    }

    public void setCrGlVal(String crGlVal) {
        this.crGlVal = crGlVal;
    }

    public String getSecTpDd() {
        return secTpDd;
    }

    public void setSecTpDd(String secTpDd) {
        this.secTpDd = secTpDd;
    }

    public String getSecDtl() {
        return secDtl;
    }

    public void setSecDtl(String secDtl) {
        this.secDtl = secDtl;
    }

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getCrBranchDd() {
        return crBranchDd;
    }

    public void setCrBranchDd(String crBranchDd) {
        this.crBranchDd = crBranchDd;
    }

    public BigDecimal getBookValue() {
        return bookValue;
    }

    public void setBookValue(BigDecimal bookValue) {
        this.bookValue = bookValue;
    }

    public BigDecimal getAccrIntVal() {
        return accrIntVal;
    }

    public void setAccrIntVal(BigDecimal accrIntVal) {
        this.accrIntVal = accrIntVal;
    }

    public String getCodeNoVal() {
        return codeNoVal;
    }

    public void setCodeNoVal(String codeNoVal) {
        this.codeNoVal = codeNoVal;
    }

    public double getRoiVal() {
        return roiVal;
    }

    public void setRoiVal(double roiVal) {
        this.roiVal = roiVal;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getSetFlag() {
        return setFlag;
    }

    public void setSetFlag(String setFlag) {
        this.setFlag = setFlag;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getSellerNmVal() {
        return sellerNmVal;
    }

    public void setSellerNmVal(String sellerNmVal) {
        this.sellerNmVal = sellerNmVal;
    }

    public String getConAcNoVal() {
        return conAcNoVal;
    }

    public void setConAcNoVal(String conAcNoVal) {
        this.conAcNoVal = conAcNoVal;
    }

    public String getBrokerAcVal() {
        return brokerAcVal;
    }

    public void setBrokerAcVal(String brokerAcVal) {
        this.brokerAcVal = brokerAcVal;
    }

    public BigDecimal getBrokerageAmtVal() {
        return brokerageAmtVal;
    }

    public void setBrokerageAmtVal(BigDecimal brokerageAmtVal) {
        this.brokerageAmtVal = brokerageAmtVal;
    }

    public Integer getNoOfShrVal() {
        return noOfShrVal;
    }

    public void setNoOfShrVal(Integer noOfShrVal) {
        this.noOfShrVal = noOfShrVal;
    }

    public Date getMatDate() {
        return matDate;
    }

    public void setMatDate(Date matDate) {
        this.matDate = matDate;
    }

    public String getConSgAccountDd() {
        return conSgAccountDd;
    }

    public void setConSgAccountDd(String conSgAccountDd) {
        this.conSgAccountDd = conSgAccountDd;
    }

    public BigDecimal getPriceVal() {
        return priceVal;
    }

    public void setPriceVal(BigDecimal priceVal) {
        this.priceVal = priceVal;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPrnIssueDate() {
        return prnIssueDate;
    }

    public void setPrnIssueDate(String prnIssueDate) {
        this.prnIssueDate = prnIssueDate;
    }

    public String getFInttPayDate() {
        return fInttPayDate;
    }

    public void setFInttPayDate(String fInttPayDate) {
        this.fInttPayDate = fInttPayDate;
    }

    public String getSinttPaydate() {
        return sinttPaydate;
    }

    public void setSinttPaydate(String sinttPaydate) {
        this.sinttPaydate = sinttPaydate;
    }

    public BigDecimal getIssuePrVal() {
        return issuePrVal;
    }

    public void setIssuePrVal(BigDecimal issuePrVal) {
        this.issuePrVal = issuePrVal;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getYtm() {
        return ytm;
    }

    public void setYtm(Double ytm) {
        this.ytm = ytm;
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

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public String getMarking() {
        return marking;
    }

    public void setMarking(String marking) {
        this.marking = marking;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maxSec != null ? maxSec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentSecurityAuthDetail)) {
            return false;
        }
        InvestmentSecurityAuthDetail other = (InvestmentSecurityAuthDetail) object;
        if ((this.maxSec == null && other.maxSec != null) || (this.maxSec != null && !this.maxSec.equals(other.maxSec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentSecurityAuthDetail[ maxSec=" + maxSec + " ]";
    }
    
}
