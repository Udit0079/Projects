/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_loan_interest_code_master_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findAll", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByInterestCode", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.cbsLoanInterestCodeMasterHistoryPK.interestCode = :interestCode"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByInterestCodeDescription", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.interestCodeDescription = :interestCodeDescription"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByInterestVersionNo", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.cbsLoanInterestCodeMasterHistoryPK.interestVersionNo = :interestVersionNo"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByInterestVersionDescription", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.interestVersionDescription = :interestVersionDescription"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByCurrencyCode", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByRecordStatus", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.recordStatus = :recordStatus"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByStartDate", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.startDate = :startDate"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByEndDate", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.endDate = :endDate"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByIndicatorFlag", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.indicatorFlag = :indicatorFlag"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByInterestVersionRemarks", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.interestVersionRemarks = :interestVersionRemarks"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByInterestMasterTableCode", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.interestMasterTableCode = :interestMasterTableCode"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByRecordModificationCount", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.recordModificationCount = :recordModificationCount"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByBasePercentageDebit", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.basePercentageDebit = :basePercentageDebit"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByBasePercentageCredit", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.basePercentageCredit = :basePercentageCredit"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByCreatedByUserId", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.createdByUserId = :createdByUserId"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByCreationDate", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.creationDate = :creationDate"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByLastUpdatedByUserId", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.lastUpdatedByUserId = :lastUpdatedByUserId"),
    @NamedQuery(name = "CbsLoanInterestCodeMasterHistory.findByLastUpdatedDate", query = "SELECT c FROM CbsLoanInterestCodeMasterHistory c WHERE c.lastUpdatedDate = :lastUpdatedDate")})
public class CbsLoanInterestCodeMasterHistory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsLoanInterestCodeMasterHistoryPK cbsLoanInterestCodeMasterHistoryPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "INTEREST_CODE_DESCRIPTION")
    private String interestCodeDescription;
    @Size(max = 60)
    @Column(name = "INTEREST_VERSION_DESCRIPTION")
    private String interestVersionDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "RECORD_STATUS")
    private String recordStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "INDICATOR_FLAG")
    private String indicatorFlag;
    @Size(max = 60)
    @Column(name = "INTEREST_VERSION_REMARKS")
    private String interestVersionRemarks;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "INTEREST_MASTER_TABLE_CODE")
    private String interestMasterTableCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECORD_MODIFICATION_COUNT")
    private int recordModificationCount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BASE_PERCENTAGE_DEBIT")
    private double basePercentageDebit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BASE_PERCENTAGE_CREDIT")
    private double basePercentageCredit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "CREATED_BY_USER_ID")
    private String createdByUserId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Size(max = 15)
    @Column(name = "LAST_UPDATED_BY_USER_ID")
    private String lastUpdatedByUserId;
    @Column(name = "LAST_UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;

    public CbsLoanInterestCodeMasterHistory() {
    }

    public CbsLoanInterestCodeMasterHistory(CbsLoanInterestCodeMasterHistoryPK cbsLoanInterestCodeMasterHistoryPK) {
        this.cbsLoanInterestCodeMasterHistoryPK = cbsLoanInterestCodeMasterHistoryPK;
    }

    public CbsLoanInterestCodeMasterHistory(CbsLoanInterestCodeMasterHistoryPK cbsLoanInterestCodeMasterHistoryPK, String interestCodeDescription, String currencyCode, String recordStatus, Date startDate, Date endDate, String indicatorFlag, String interestMasterTableCode, int recordModificationCount, double basePercentageDebit, double basePercentageCredit, String createdByUserId, Date creationDate) {
        this.cbsLoanInterestCodeMasterHistoryPK = cbsLoanInterestCodeMasterHistoryPK;
        this.interestCodeDescription = interestCodeDescription;
        this.currencyCode = currencyCode;
        this.recordStatus = recordStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.indicatorFlag = indicatorFlag;
        this.interestMasterTableCode = interestMasterTableCode;
        this.recordModificationCount = recordModificationCount;
        this.basePercentageDebit = basePercentageDebit;
        this.basePercentageCredit = basePercentageCredit;
        this.createdByUserId = createdByUserId;
        this.creationDate = creationDate;
    }

    public CbsLoanInterestCodeMasterHistory(String interestCode, int interestVersionNo) {
        this.cbsLoanInterestCodeMasterHistoryPK = new CbsLoanInterestCodeMasterHistoryPK(interestCode, interestVersionNo);
    }

    public CbsLoanInterestCodeMasterHistoryPK getCbsLoanInterestCodeMasterHistoryPK() {
        return cbsLoanInterestCodeMasterHistoryPK;
    }

    public void setCbsLoanInterestCodeMasterHistoryPK(CbsLoanInterestCodeMasterHistoryPK cbsLoanInterestCodeMasterHistoryPK) {
        this.cbsLoanInterestCodeMasterHistoryPK = cbsLoanInterestCodeMasterHistoryPK;
    }

    public String getInterestCodeDescription() {
        return interestCodeDescription;
    }

    public void setInterestCodeDescription(String interestCodeDescription) {
        this.interestCodeDescription = interestCodeDescription;
    }

    public String getInterestVersionDescription() {
        return interestVersionDescription;
    }

    public void setInterestVersionDescription(String interestVersionDescription) {
        this.interestVersionDescription = interestVersionDescription;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIndicatorFlag() {
        return indicatorFlag;
    }

    public void setIndicatorFlag(String indicatorFlag) {
        this.indicatorFlag = indicatorFlag;
    }

    public String getInterestVersionRemarks() {
        return interestVersionRemarks;
    }

    public void setInterestVersionRemarks(String interestVersionRemarks) {
        this.interestVersionRemarks = interestVersionRemarks;
    }

    public String getInterestMasterTableCode() {
        return interestMasterTableCode;
    }

    public void setInterestMasterTableCode(String interestMasterTableCode) {
        this.interestMasterTableCode = interestMasterTableCode;
    }

    public int getRecordModificationCount() {
        return recordModificationCount;
    }

    public void setRecordModificationCount(int recordModificationCount) {
        this.recordModificationCount = recordModificationCount;
    }

    public double getBasePercentageDebit() {
        return basePercentageDebit;
    }

    public void setBasePercentageDebit(double basePercentageDebit) {
        this.basePercentageDebit = basePercentageDebit;
    }

    public double getBasePercentageCredit() {
        return basePercentageCredit;
    }

    public void setBasePercentageCredit(double basePercentageCredit) {
        this.basePercentageCredit = basePercentageCredit;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedByUserId() {
        return lastUpdatedByUserId;
    }

    public void setLastUpdatedByUserId(String lastUpdatedByUserId) {
        this.lastUpdatedByUserId = lastUpdatedByUserId;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsLoanInterestCodeMasterHistoryPK != null ? cbsLoanInterestCodeMasterHistoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsLoanInterestCodeMasterHistory)) {
            return false;
        }
        CbsLoanInterestCodeMasterHistory other = (CbsLoanInterestCodeMasterHistory) object;
        if ((this.cbsLoanInterestCodeMasterHistoryPK == null && other.cbsLoanInterestCodeMasterHistoryPK != null) || (this.cbsLoanInterestCodeMasterHistoryPK != null && !this.cbsLoanInterestCodeMasterHistoryPK.equals(other.cbsLoanInterestCodeMasterHistoryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsLoanInterestCodeMasterHistory[ cbsLoanInterestCodeMasterHistoryPK=" + cbsLoanInterestCodeMasterHistoryPK + " ]";
    }
    
}
