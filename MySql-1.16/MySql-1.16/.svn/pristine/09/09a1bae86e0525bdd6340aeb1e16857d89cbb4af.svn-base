/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_user_biometric_template")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsUserBiometricTemplate.findAll", query = "SELECT c FROM CbsUserBiometricTemplate c"),
    @NamedQuery(name = "CbsUserBiometricTemplate.findByTxnid", query = "SELECT c FROM CbsUserBiometricTemplate c WHERE c.txnid = :txnid"),
    @NamedQuery(name = "CbsUserBiometricTemplate.findByUserid", query = "SELECT c FROM CbsUserBiometricTemplate c WHERE c.userid = :userid"),
    @NamedQuery(name = "CbsUserBiometricTemplate.findByIsotemplate", query = "SELECT c FROM CbsUserBiometricTemplate c WHERE c.isotemplate = :isotemplate"),
    @NamedQuery(name = "CbsUserBiometricTemplate.findByEnterby", query = "SELECT c FROM CbsUserBiometricTemplate c WHERE c.enterby = :enterby"),
    @NamedQuery(name = "CbsUserBiometricTemplate.findByEnterydate", query = "SELECT c FROM CbsUserBiometricTemplate c WHERE c.enterydate = :enterydate")})
public class CbsUserBiometricTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "txnid")
    private Long txnid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "userid")
    private String userid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "isotemplate")
    private byte[] isotemplate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "enterby")
    private String enterby;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enterydate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enterydate;

    public CbsUserBiometricTemplate() {
    }

    public CbsUserBiometricTemplate(Long txnid) {
        this.txnid = txnid;
    }

    public CbsUserBiometricTemplate(Long txnid, String userid, byte[] isotemplate, String enterby, Date enterydate) {
        this.txnid = txnid;
        this.userid = userid;
        this.isotemplate = isotemplate;
        this.enterby = enterby;
        this.enterydate = enterydate;
    }

    public Long getTxnid() {
        return txnid;
    }

    public void setTxnid(Long txnid) {
        this.txnid = txnid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public byte[] getIsotemplate() {
        return isotemplate;
    }

    public void setIsotemplate(byte[] isotemplate) {
        this.isotemplate = isotemplate;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public Date getEnterydate() {
        return enterydate;
    }

    public void setEnterydate(Date enterydate) {
        this.enterydate = enterydate;
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
        if (!(object instanceof CbsUserBiometricTemplate)) {
            return false;
        }
        CbsUserBiometricTemplate other = (CbsUserBiometricTemplate) object;
        if ((this.txnid == null && other.txnid != null) || (this.txnid != null && !this.txnid.equals(other.txnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsUserBiometricTemplate[ txnid=" + txnid + " ]";
    }
    
}
