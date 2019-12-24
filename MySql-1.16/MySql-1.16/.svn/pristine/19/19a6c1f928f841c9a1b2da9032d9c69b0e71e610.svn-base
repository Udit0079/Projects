/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_exception_code_for_deposits_scheme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findAll", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findBySchemeCode", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findBySchemeType", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByCurrencyCode", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByRenewalPeriodExcd", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.renewalPeriodExcd = :renewalPeriodExcd"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByMaximumPeriod", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.maximumPeriod = :maximumPeriod"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByMaximumAmount", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.maximumAmount = :maximumAmount"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByMinorDepPreclosure", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.minorDepPreclosure = :minorDepPreclosure"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByExtension", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.extension = :extension"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findBySplCatgClosure", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.splCatgClosure = :splCatgClosure"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByMatAmtTolerance", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.matAmtTolerance = :matAmtTolerance"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByNilPenalty", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.nilPenalty = :nilPenalty"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByDiscontinuedInstl", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.discontinuedInstl = :discontinuedInstl"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByTransferIn", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.transferIn = :transferIn"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByAcctVerBalCheck", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.acctVerBalCheck = :acctVerBalCheck"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findBySystemDrTransAllwd", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.systemDrTransAllwd = :systemDrTransAllwd"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByDuplicateOrReprintReport", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.duplicateOrReprintReport = :duplicateOrReprintReport"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByPrematureClosure", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.prematureClosure = :prematureClosure"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByNoticePerdBelowMinNoticePerd", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.noticePerdBelowMinNoticePerd = :noticePerdBelowMinNoticePerd"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByDefaultValueForPreferentialIntChgd", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.defaultValueForPreferentialIntChgd = :defaultValueForPreferentialIntChgd"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByBackValueDatedAccountOpening", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.backValueDatedAccountOpening = :backValueDatedAccountOpening"),
    @NamedQuery(name = "CbsSchemeExceptionCodeForDepositsScheme.findByFutureValueDatedAccountOpening", query = "SELECT c FROM CbsSchemeExceptionCodeForDepositsScheme c WHERE c.futureValueDatedAccountOpening = :futureValueDatedAccountOpening")})
public class CbsSchemeExceptionCodeForDepositsScheme extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 3)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Size(max = 3)
    @Column(name = "RENEWAL_PERIOD_EXCD")
    private String renewalPeriodExcd;
    @Size(max = 3)
    @Column(name = "MAXIMUM_PERIOD")
    private String maximumPeriod;
    @Size(max = 3)
    @Column(name = "MAXIMUM_AMOUNT")
    private String maximumAmount;
    @Size(max = 3)
    @Column(name = "MINOR_DEP_PRECLOSURE")
    private String minorDepPreclosure;
    @Size(max = 3)
    @Column(name = "EXTENSION")
    private String extension;
    @Size(max = 3)
    @Column(name = "SPL_CATG_CLOSURE")
    private String splCatgClosure;
    @Size(max = 3)
    @Column(name = "MAT_AMT_TOLERANCE")
    private String matAmtTolerance;
    @Size(max = 3)
    @Column(name = "NIL_PENALTY")
    private String nilPenalty;
    @Size(max = 3)
    @Column(name = "DISCONTINUED_INSTL")
    private String discontinuedInstl;
    @Size(max = 3)
    @Column(name = "TRANSFER_IN")
    private String transferIn;
    @Size(max = 3)
    @Column(name = "ACCT_VER_BAL_CHECK")
    private String acctVerBalCheck;
    @Size(max = 3)
    @Column(name = "SYSTEM_DR_TRANS_ALLWD")
    private String systemDrTransAllwd;
    @Size(max = 3)
    @Column(name = "DUPLICATE_OR_REPRINT_REPORT")
    private String duplicateOrReprintReport;
    @Size(max = 3)
    @Column(name = "PREMATURE_CLOSURE")
    private String prematureClosure;
    @Size(max = 3)
    @Column(name = "NOTICE_PERD_BELOW_MIN_NOTICE_PERD")
    private String noticePerdBelowMinNoticePerd;
    @Size(max = 3)
    @Column(name = "DEFAULT_VALUE_FOR_PREFERENTIAL_INT_CHGD")
    private String defaultValueForPreferentialIntChgd;
    @Size(max = 3)
    @Column(name = "BACK_VALUE_DATED_ACCOUNT_OPENING")
    private String backValueDatedAccountOpening;
    @Size(max = 3)
    @Column(name = "FUTURE_VALUE_DATED_ACCOUNT_OPENING")
    private String futureValueDatedAccountOpening;

    public CbsSchemeExceptionCodeForDepositsScheme() {
    }

    public CbsSchemeExceptionCodeForDepositsScheme(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRenewalPeriodExcd() {
        return renewalPeriodExcd;
    }

    public void setRenewalPeriodExcd(String renewalPeriodExcd) {
        this.renewalPeriodExcd = renewalPeriodExcd;
    }

    public String getMaximumPeriod() {
        return maximumPeriod;
    }

    public void setMaximumPeriod(String maximumPeriod) {
        this.maximumPeriod = maximumPeriod;
    }

    public String getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(String maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public String getMinorDepPreclosure() {
        return minorDepPreclosure;
    }

    public void setMinorDepPreclosure(String minorDepPreclosure) {
        this.minorDepPreclosure = minorDepPreclosure;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getSplCatgClosure() {
        return splCatgClosure;
    }

    public void setSplCatgClosure(String splCatgClosure) {
        this.splCatgClosure = splCatgClosure;
    }

    public String getMatAmtTolerance() {
        return matAmtTolerance;
    }

    public void setMatAmtTolerance(String matAmtTolerance) {
        this.matAmtTolerance = matAmtTolerance;
    }

    public String getNilPenalty() {
        return nilPenalty;
    }

    public void setNilPenalty(String nilPenalty) {
        this.nilPenalty = nilPenalty;
    }

    public String getDiscontinuedInstl() {
        return discontinuedInstl;
    }

    public void setDiscontinuedInstl(String discontinuedInstl) {
        this.discontinuedInstl = discontinuedInstl;
    }

    public String getTransferIn() {
        return transferIn;
    }

    public void setTransferIn(String transferIn) {
        this.transferIn = transferIn;
    }

    public String getAcctVerBalCheck() {
        return acctVerBalCheck;
    }

    public void setAcctVerBalCheck(String acctVerBalCheck) {
        this.acctVerBalCheck = acctVerBalCheck;
    }

    public String getSystemDrTransAllwd() {
        return systemDrTransAllwd;
    }

    public void setSystemDrTransAllwd(String systemDrTransAllwd) {
        this.systemDrTransAllwd = systemDrTransAllwd;
    }

    public String getDuplicateOrReprintReport() {
        return duplicateOrReprintReport;
    }

    public void setDuplicateOrReprintReport(String duplicateOrReprintReport) {
        this.duplicateOrReprintReport = duplicateOrReprintReport;
    }

    public String getPrematureClosure() {
        return prematureClosure;
    }

    public void setPrematureClosure(String prematureClosure) {
        this.prematureClosure = prematureClosure;
    }

    public String getNoticePerdBelowMinNoticePerd() {
        return noticePerdBelowMinNoticePerd;
    }

    public void setNoticePerdBelowMinNoticePerd(String noticePerdBelowMinNoticePerd) {
        this.noticePerdBelowMinNoticePerd = noticePerdBelowMinNoticePerd;
    }

    public String getDefaultValueForPreferentialIntChgd() {
        return defaultValueForPreferentialIntChgd;
    }

    public void setDefaultValueForPreferentialIntChgd(String defaultValueForPreferentialIntChgd) {
        this.defaultValueForPreferentialIntChgd = defaultValueForPreferentialIntChgd;
    }

    public String getBackValueDatedAccountOpening() {
        return backValueDatedAccountOpening;
    }

    public void setBackValueDatedAccountOpening(String backValueDatedAccountOpening) {
        this.backValueDatedAccountOpening = backValueDatedAccountOpening;
    }

    public String getFutureValueDatedAccountOpening() {
        return futureValueDatedAccountOpening;
    }

    public void setFutureValueDatedAccountOpening(String futureValueDatedAccountOpening) {
        this.futureValueDatedAccountOpening = futureValueDatedAccountOpening;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeExceptionCodeForDepositsScheme)) {
            return false;
        }
        CbsSchemeExceptionCodeForDepositsScheme other = (CbsSchemeExceptionCodeForDepositsScheme) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeExceptionCodeForDepositsScheme[ schemeCode=" + schemeCode + " ]";
    }
    
}
