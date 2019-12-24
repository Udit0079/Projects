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
@Table(name = "customerid")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customerid.findAll", query = "SELECT c FROM Customerid c"),
    @NamedQuery(name = "Customerid.findByCustId", query = "SELECT c FROM Customerid c WHERE c.customeridPK.custId = :custId"),
    @NamedQuery(name = "Customerid.findByAcno", query = "SELECT c FROM Customerid c WHERE c.customeridPK.acno = :acno"),
    @NamedQuery(name = "Customerid.findByEnterBy", query = "SELECT c FROM Customerid c WHERE c.enterBy = :enterBy"),
    @NamedQuery(name = "Customerid.findByTranTime", query = "SELECT c FROM Customerid c WHERE c.tranTime = :tranTime"),
    @NamedQuery(name = "Customerid.findByTaxcat", query = "SELECT c FROM Customerid c WHERE c.taxcat = :taxcat"),
    @NamedQuery(name = "Customerid.findByTxnBrn", query = "SELECT c FROM Customerid c WHERE c.txnBrn = :txnBrn")})
public class Customerid extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CustomeridPK customeridPK;
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

    public Customerid() {
    }

    public Customerid(CustomeridPK customeridPK) {
        this.customeridPK = customeridPK;
    }

    public Customerid(long custId, String acno) {
        this.customeridPK = new CustomeridPK(custId, acno);
    }

    public CustomeridPK getCustomeridPK() {
        return customeridPK;
    }

    public void setCustomeridPK(CustomeridPK customeridPK) {
        this.customeridPK = customeridPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customeridPK != null ? customeridPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customerid)) {
            return false;
        }
        Customerid other = (Customerid) object;
        if ((this.customeridPK == null && other.customeridPK != null) || (this.customeridPK != null && !this.customeridPK.equals(other.customeridPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.Customerid[ customeridPK=" + customeridPK + " ]";
    }
    
}
