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
@Table(name = "cbs_kyc_loan_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsKycLoanHis.findAll", query = "SELECT c FROM CbsKycLoanHis c"),
    @NamedQuery(name = "CbsKycLoanHis.findByCustomerId", query = "SELECT c FROM CbsKycLoanHis c WHERE c.cbsKycLoanHisPK.customerId = :customerId"),
    @NamedQuery(name = "CbsKycLoanHis.findByTxnId", query = "SELECT c FROM CbsKycLoanHis c WHERE c.cbsKycLoanHisPK.txnId = :txnId"),
    @NamedQuery(name = "CbsKycLoanHis.findByLoantype", query = "SELECT c FROM CbsKycLoanHis c WHERE c.loantype = :loantype"),
    @NamedQuery(name = "CbsKycLoanHis.findByLoanamt", query = "SELECT c FROM CbsKycLoanHis c WHERE c.loanamt = :loanamt"),
    @NamedQuery(name = "CbsKycLoanHis.findByLastChangeUserID", query = "SELECT c FROM CbsKycLoanHis c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CbsKycLoanHis.findByLastChangeTime", query = "SELECT c FROM CbsKycLoanHis c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CbsKycLoanHis.findByRecordCreaterID", query = "SELECT c FROM CbsKycLoanHis c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CbsKycLoanHis.findByCreationTime", query = "SELECT c FROM CbsKycLoanHis c WHERE c.creationTime = :creationTime")})
public class CbsKycLoanHis extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsKycLoanHisPK cbsKycLoanHisPK;
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

    public CbsKycLoanHis() {
    }

    public CbsKycLoanHis(CbsKycLoanHisPK cbsKycLoanHisPK) {
        this.cbsKycLoanHisPK = cbsKycLoanHisPK;
    }

    public CbsKycLoanHis(String customerId, int txnId) {
        this.cbsKycLoanHisPK = new CbsKycLoanHisPK(customerId, txnId);
    }

    public CbsKycLoanHisPK getCbsKycLoanHisPK() {
        return cbsKycLoanHisPK;
    }

    public void setCbsKycLoanHisPK(CbsKycLoanHisPK cbsKycLoanHisPK) {
        this.cbsKycLoanHisPK = cbsKycLoanHisPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsKycLoanHisPK != null ? cbsKycLoanHisPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsKycLoanHis)) {
            return false;
        }
        CbsKycLoanHis other = (CbsKycLoanHis) object;
        if ((this.cbsKycLoanHisPK == null && other.cbsKycLoanHisPK != null) || (this.cbsKycLoanHisPK != null && !this.cbsKycLoanHisPK.equals(other.cbsKycLoanHisPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CbsKycLoanHis[ cbsKycLoanHisPK=" + cbsKycLoanHisPK + " ]";
    }
    
}
