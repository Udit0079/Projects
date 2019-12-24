/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.customer;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ROHIT KRISHNA
 */
@Entity
@Table(name = "cbs_kyc_loan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsKycLoan.findAll", query = "SELECT c FROM CbsKycLoan c"),
    @NamedQuery(name = "CbsKycLoan.findByCustomerId", query = "SELECT c FROM CbsKycLoan c WHERE c.cbsKycLoanPK.customerId = :customerId"),
    @NamedQuery(name = "CbsKycLoan.findByTxnId", query = "SELECT c FROM CbsKycLoan c WHERE c.cbsKycLoanPK.txnId = :txnId"),
    @NamedQuery(name = "CbsKycLoan.findByLoantype", query = "SELECT c FROM CbsKycLoan c WHERE c.loantype = :loantype"),
    @NamedQuery(name = "CbsKycLoan.findByLoanamt", query = "SELECT c FROM CbsKycLoan c WHERE c.loanamt = :loanamt"),
    @NamedQuery(name = "CbsKycLoan.findByLastChangeUserID", query = "SELECT c FROM CbsKycLoan c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CbsKycLoan.findByLastChangeTime", query = "SELECT c FROM CbsKycLoan c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CbsKycLoan.findByRecordCreaterID", query = "SELECT c FROM CbsKycLoan c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CbsKycLoan.findByCreationTime", query = "SELECT c FROM CbsKycLoan c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "CbsKycLoan.getMaxTxnIdByCustomerId", query = "SELECT max(c.cbsKycLoanPK.txnId) FROM CbsKycLoan c WHERE c.cbsKycLoanPK.customerId = :customerId"), 
    @NamedQuery(name = "CbsKycLoan.findByTsCnt", query = "SELECT c FROM CbsKycLoan c WHERE c.tsCnt = :tsCnt")})
public class CbsKycLoan extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsKycLoanPK cbsKycLoanPK;
    @Size(max = 255)
    @Column(name = "loantype")
    private String loantype;
    @Size(max = 255)
    @Column(name = "loanamt")
    private String loanamt;
    @Size(max = 15)
    @Column(name = "LastChangeUserID")
    private String lastChangeUserID;
    @Column(name = "LastChangeTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChangeTime;
    @Size(max = 15)
    @Column(name = "RecordCreaterID")
    private String recordCreaterID;
    @Column(name = "CreationTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    @Size(max = 5)
    @Column(name = "TsCnt")
    private String tsCnt;

    public CbsKycLoan() {
    }

    public CbsKycLoan(CbsKycLoanPK cbsKycLoanPK) {
        this.cbsKycLoanPK = cbsKycLoanPK;
    }

    public CbsKycLoan(String customerId, int txnId) {
        this.cbsKycLoanPK = new CbsKycLoanPK(customerId,txnId);
    }

    public CbsKycLoanPK getCbsKycLoanPK() {
        return cbsKycLoanPK;
    }

    public void setCbsKycLoanPK(CbsKycLoanPK cbsKycLoanPK) {
        this.cbsKycLoanPK = cbsKycLoanPK;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public String getLoanamt() {
        return loanamt;
    }

    public void setLoanamt(String loanamt) {
        this.loanamt = loanamt;
    }

    public String getLastChangeUserID() {
        return lastChangeUserID;
    }

    public void setLastChangeUserID(String lastChangeUserID) {
        this.lastChangeUserID = lastChangeUserID;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getTsCnt() {
        return tsCnt;
    }

    public void setTsCnt(String tsCnt) {
        this.tsCnt = tsCnt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsKycLoanPK != null ? cbsKycLoanPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsKycLoan)) {
            return false;
        }
        CbsKycLoan other = (CbsKycLoan) object;
        if ((this.cbsKycLoanPK == null && other.cbsKycLoanPK != null) || (this.cbsKycLoanPK != null && !this.cbsKycLoanPK.equals(other.cbsKycLoanPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsKycLoan[ cbsKycLoanPK=" + cbsKycLoanPK + " ]";
    }
    
}
