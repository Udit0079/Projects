/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "investment_master_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentMasterHis.findAll", query = "SELECT i FROM InvestmentMasterHis i"),
    @NamedQuery(name = "InvestmentMasterHis.findByControlno", query = "SELECT i FROM InvestmentMasterHis i WHERE i.controlno = :controlno"),
    @NamedQuery(name = "InvestmentMasterHis.findBySectype", query = "SELECT i FROM InvestmentMasterHis i WHERE i.sectype = :sectype"),
    @NamedQuery(name = "InvestmentMasterHis.findByDt", query = "SELECT i FROM InvestmentMasterHis i WHERE i.dt = :dt"),
    @NamedQuery(name = "InvestmentMasterHis.findByPreviousMarking", query = "SELECT i FROM InvestmentMasterHis i WHERE i.previousMarking = :previousMarking"),
    @NamedQuery(name = "InvestmentMasterHis.findByCurrentMarking", query = "SELECT i FROM InvestmentMasterHis i WHERE i.currentMarking = :currentMarking"),
    @NamedQuery(name = "InvestmentMasterHis.findByUpdateDate", query = "SELECT i FROM InvestmentMasterHis i WHERE i.updateDate = :updateDate"),
    @NamedQuery(name = "InvestmentMasterHis.findByUpdateBy", query = "SELECT i FROM InvestmentMasterHis i WHERE i.updateBy = :updateBy"),
    @NamedQuery(name = "InvestmentMasterHis.findByTxnId", query = "SELECT i FROM InvestmentMasterHis i WHERE i.txnId = :txnId")})
public class InvestmentMasterHis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "CONTROLNO")
    private Integer controlno;
    @Size(max = 50)
    @Column(name = "SECTYPE")
    private String sectype;
    @Column(name = "dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Size(max = 2)
    @Column(name = "previousMarking")
    private String previousMarking;
    @Size(max = 2)
    @Column(name = "currentMarking")
    private String currentMarking;
    @Column(name = "updateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Size(max = 25)
    @Column(name = "updateBy")
    private String updateBy;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "txnId")
    private Long txnId;

    public InvestmentMasterHis() {
    }

    public InvestmentMasterHis(Long txnId) {
        this.txnId = txnId;
    }

    public Integer getControlno() {
        return controlno;
    }

    public void setControlno(Integer controlno) {
        this.controlno = controlno;
    }

    public String getSectype() {
        return sectype;
    }

    public void setSectype(String sectype) {
        this.sectype = sectype;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getPreviousMarking() {
        return previousMarking;
    }

    public void setPreviousMarking(String previousMarking) {
        this.previousMarking = previousMarking;
    }

    public String getCurrentMarking() {
        return currentMarking;
    }

    public void setCurrentMarking(String currentMarking) {
        this.currentMarking = currentMarking;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
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
        if (!(object instanceof InvestmentMasterHis)) {
            return false;
        }
        InvestmentMasterHis other = (InvestmentMasterHis) object;
        if ((this.txnId == null && other.txnId != null) || (this.txnId != null && !this.txnId.equals(other.txnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentMasterHis[ txnId=" + txnId + " ]";
    }
    
}
