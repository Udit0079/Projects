/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.customer;

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
 * @author ROHIT KRISHNA
 */
@Entity
@Table(name = "cbs_cust_delivery_channel_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findAll", query = "SELECT c FROM CBSCustDeliveryChannelDetails c"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByCustomerId", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByATMDebitCardHolder", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.aTMDebitCardHolder = :aTMDebitCardHolder"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByCreditCardEnabled", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.creditCardEnabled = :creditCardEnabled"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByTeleBankingEnabled", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.teleBankingEnabled = :teleBankingEnabled"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findBySmsBankingEnabled", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.smsBankingEnabled = :smsBankingEnabled"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByINetBankingEnabled", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.iNetBankingEnabled = :iNetBankingEnabled"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByMobileBankingEnabled", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.mobileBankingEnabled = :mobileBankingEnabled"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findBySelfServiceEnabled", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.selfServiceEnabled = :selfServiceEnabled"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByECSEnabled", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.eCSEnabled = :eCSEnabled"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByLastChangeUserID", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByLastChangeTime", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByRecordCreaterID", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByCreationTime", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "CBSCustDeliveryChannelDetails.findByTsCnt", query = "SELECT c FROM CBSCustDeliveryChannelDetails c WHERE c.tsCnt = :tsCnt")})
public class CBSCustDeliveryChannelDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Size(max = 1)
    @Column(name = "ATM_DebitCardHolder")
    private String aTMDebitCardHolder;
    @Size(max = 1)
    @Column(name = "CreditCardEnabled")
    private String creditCardEnabled;
    @Size(max = 1)
    @Column(name = "Tele_BankingEnabled")
    private String teleBankingEnabled;
    @Size(max = 1)
    @Column(name = "SmsBankingEnabled")
    private String smsBankingEnabled;
    @Size(max = 1)
    @Column(name = "I_NetBankingEnabled")
    private String iNetBankingEnabled;
    @Size(max = 1)
    @Column(name = "MobileBankingEnabled")
    private String mobileBankingEnabled;
    @Size(max = 1)
    @Column(name = "SelfServiceEnabled")
    private String selfServiceEnabled;
    @Size(max = 1)
    @Column(name = "ECSEnabled")
    private String eCSEnabled;
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

    public CBSCustDeliveryChannelDetails() {
    }

    public CBSCustDeliveryChannelDetails(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getATMDebitCardHolder() {
        return aTMDebitCardHolder;
    }

    public void setATMDebitCardHolder(String aTMDebitCardHolder) {
        this.aTMDebitCardHolder = aTMDebitCardHolder;
    }

    public String getCreditCardEnabled() {
        return creditCardEnabled;
    }

    public void setCreditCardEnabled(String creditCardEnabled) {
        this.creditCardEnabled = creditCardEnabled;
    }

    public String getTeleBankingEnabled() {
        return teleBankingEnabled;
    }

    public void setTeleBankingEnabled(String teleBankingEnabled) {
        this.teleBankingEnabled = teleBankingEnabled;
    }

    public String getSmsBankingEnabled() {
        return smsBankingEnabled;
    }

    public void setSmsBankingEnabled(String smsBankingEnabled) {
        this.smsBankingEnabled = smsBankingEnabled;
    }

    public String getINetBankingEnabled() {
        return iNetBankingEnabled;
    }

    public void setINetBankingEnabled(String iNetBankingEnabled) {
        this.iNetBankingEnabled = iNetBankingEnabled;
    }

    public String getMobileBankingEnabled() {
        return mobileBankingEnabled;
    }

    public void setMobileBankingEnabled(String mobileBankingEnabled) {
        this.mobileBankingEnabled = mobileBankingEnabled;
    }

    public String getSelfServiceEnabled() {
        return selfServiceEnabled;
    }

    public void setSelfServiceEnabled(String selfServiceEnabled) {
        this.selfServiceEnabled = selfServiceEnabled;
    }

    public String getECSEnabled() {
        return eCSEnabled;
    }

    public void setECSEnabled(String eCSEnabled) {
        this.eCSEnabled = eCSEnabled;
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
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBSCustDeliveryChannelDetails)) {
            return false;
        }
        CBSCustDeliveryChannelDetails other = (CBSCustDeliveryChannelDetails) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CBSCustDeliveryChannelDetails[ customerId=" + customerId + " ]";
    }
    
}
