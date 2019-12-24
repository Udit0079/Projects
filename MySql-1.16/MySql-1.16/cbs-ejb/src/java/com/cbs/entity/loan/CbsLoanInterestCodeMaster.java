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
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "cbs_loan_interest_code_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findAll", query = "SELECT c FROM CbsLoanInterestCodeMaster c"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByInterestCode", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.interestCode = :interestCode"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByInterestCodeDescription", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.interestCodeDescription = :interestCodeDescription"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByInterestVersionNo", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.interestVersionNo = :interestVersionNo"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByInterestVersionDescription", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.interestVersionDescription = :interestVersionDescription"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByCurrencyCode", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByRecordStatus", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.recordStatus = :recordStatus"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByStartDate", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.startDate = :startDate"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByEndDate", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.endDate = :endDate"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByIndicatorFlag", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.indicatorFlag = :indicatorFlag"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByInterestVersionRemarks", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.interestVersionRemarks = :interestVersionRemarks"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByInterestMasterTableCode", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.interestMasterTableCode = :interestMasterTableCode"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByRecordModificationCount", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.recordModificationCount = :recordModificationCount"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByBasePercentageDebit", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.basePercentageDebit = :basePercentageDebit"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByBasePercentageCredit", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.basePercentageCredit = :basePercentageCredit"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByCreatedByUserId", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.createdByUserId = :createdByUserId"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByCreationDate", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.creationDate = :creationDate"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByLastUpdatedByUserId", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.lastUpdatedByUserId = :lastUpdatedByUserId"),
    @NamedQuery(name = "CbsLoanInterestCodeMaster.findByLastUpdatedDate", query = "SELECT c FROM CbsLoanInterestCodeMaster c WHERE c.lastUpdatedDate = :lastUpdatedDate")})
public class CbsLoanInterestCodeMaster extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "INTEREST_CODE")
    private String interestCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "INTEREST_CODE_DESCRIPTION")
    private String interestCodeDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INTEREST_VERSION_NO")
    private int interestVersionNo;
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

    public CbsLoanInterestCodeMaster() {
    }

    public CbsLoanInterestCodeMaster(String interestCode) {
        this.interestCode = interestCode;
    }

    public CbsLoanInterestCodeMaster(String interestCode, String interestCodeDescription, int interestVersionNo, String currencyCode, String recordStatus, Date startDate, Date endDate, String indicatorFlag, String interestMasterTableCode, int recordModificationCount, double basePercentageDebit, double basePercentageCredit, String createdByUserId, Date creationDate) {
        this.interestCode = interestCode;
        this.interestCodeDescription = interestCodeDescription;
        this.interestVersionNo = interestVersionNo;
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

    public String getInterestCode() {
        return interestCode;
    }

    public void setInterestCode(String interestCode) {
        this.interestCode = interestCode;
    }

    public String getInterestCodeDescription() {
        return interestCodeDescription;
    }

    public void setInterestCodeDescription(String interestCodeDescription) {
        this.interestCodeDescription = interestCodeDescription;
    }

    public int getInterestVersionNo() {
        return interestVersionNo;
    }

    public void setInterestVersionNo(int interestVersionNo) {
        this.interestVersionNo = interestVersionNo;
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
        hash += (interestCode != null ? interestCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsLoanInterestCodeMaster)) {
            return false;
        }
        CbsLoanInterestCodeMaster other = (CbsLoanInterestCodeMaster) object;
        if ((this.interestCode == null && other.interestCode != null) || (this.interestCode != null && !this.interestCode.equals(other.interestCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsLoanInterestCodeMaster[ interestCode=" + interestCode + " ]";
    }
    
}
