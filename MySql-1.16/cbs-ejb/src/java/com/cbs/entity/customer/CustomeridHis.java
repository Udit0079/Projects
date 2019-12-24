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
@Table(name = "customerid_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomeridHis.findAll", query = "SELECT c FROM CustomeridHis c"),
    @NamedQuery(name = "CustomeridHis.findByCustId", query = "SELECT c FROM CustomeridHis c WHERE c.custId = :custId"),
    @NamedQuery(name = "CustomeridHis.findByAcno", query = "SELECT c FROM CustomeridHis c WHERE c.acno = :acno"),
    @NamedQuery(name = "CustomeridHis.findByEnterBy", query = "SELECT c FROM CustomeridHis c WHERE c.enterBy = :enterBy"),
    @NamedQuery(name = "CustomeridHis.findByTranTime", query = "SELECT c FROM CustomeridHis c WHERE c.tranTime = :tranTime"),
    @NamedQuery(name = "CustomeridHis.findByTaxcat", query = "SELECT c FROM CustomeridHis c WHERE c.taxcat = :taxcat"),
    @NamedQuery(name = "CustomeridHis.findByTxnBrn", query = "SELECT c FROM CustomeridHis c WHERE c.txnBrn = :txnBrn"),
    @NamedQuery(name = "CustomeridHis.findByTxnid", query = "SELECT c FROM CustomeridHis c WHERE c.txnid = :txnid")})
public class CustomeridHis extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CustId")
    private long custId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "Acno")
    private String acno;
    @Size(max = 20)
    @Column(name = "EnterBy")
    private String enterBy;
    @Column(name = "TranTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranTime;
    @Column(name = "taxcat")
    private Integer taxcat;
    @Size(max = 6)
    @Column(name = "TxnBrn")
    private String txnBrn;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TXNID")
    private Long txnid;

    public CustomeridHis() {
    }

    public CustomeridHis(Long txnid) {
        this.txnid = txnid;
    }

    public CustomeridHis(Long txnid, long custId, String acno) {
        this.txnid = txnid;
        this.custId = custId;
        this.acno = acno;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public Integer getTaxcat() {
        return taxcat;
    }

    public void setTaxcat(Integer taxcat) {
        this.taxcat = taxcat;
    }

    public String getTxnBrn() {
        return txnBrn;
    }

    public void setTxnBrn(String txnBrn) {
        this.txnBrn = txnBrn;
    }

    public Long getTxnid() {
        return txnid;
    }

    public void setTxnid(Long txnid) {
        this.txnid = txnid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnid != null ? txnid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomeridHis)) {
            return false;
        }
        CustomeridHis other = (CustomeridHis) object;
        if ((this.txnid == null && other.txnid != null) || (this.txnid != null && !this.txnid.equals(other.txnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CustomeridHis[ txnid=" + txnid + " ]";
    }
    
}
