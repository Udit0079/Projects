/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

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
 * @author root
 */
@Entity
@Table(name = "int_post_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IntPostHistory.findAll", query = "SELECT i FROM IntPostHistory i"),
    @NamedQuery(name = "IntPostHistory.findByActype", query = "SELECT i FROM IntPostHistory i WHERE i.intPostHistoryPK.actype = :actype"),
    @NamedQuery(name = "IntPostHistory.findByTodate", query = "SELECT i FROM IntPostHistory i WHERE i.todate = :todate"),
    @NamedQuery(name = "IntPostHistory.findByFromdate", query = "SELECT i FROM IntPostHistory i WHERE i.intPostHistoryPK.fromdate = :fromdate"),
    @NamedQuery(name = "IntPostHistory.findByEnterby", query = "SELECT i FROM IntPostHistory i WHERE i.enterby = :enterby"),
    @NamedQuery(name = "IntPostHistory.findByAuth", query = "SELECT i FROM IntPostHistory i WHERE i.auth = :auth"),
    @NamedQuery(name = "IntPostHistory.findByTrantime", query = "SELECT i FROM IntPostHistory i WHERE i.trantime = :trantime"),
    @NamedQuery(name = "IntPostHistory.findByAuthby", query = "SELECT i FROM IntPostHistory i WHERE i.authby = :authby"),
    @NamedQuery(name = "IntPostHistory.findByOpstatus", query = "SELECT i FROM IntPostHistory i WHERE i.opstatus = :opstatus"),
    @NamedQuery(name = "IntPostHistory.findByOptstatus", query = "SELECT i FROM IntPostHistory i WHERE i.optstatus = :optstatus"),
    @NamedQuery(name = "IntPostHistory.findByBrncode", query = "SELECT i FROM IntPostHistory i WHERE i.intPostHistoryPK.brncode = :brncode")})
public class IntPostHistory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IntPostHistoryPK intPostHistoryPK;
    @Column(name = "todate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date todate;
    @Size(max = 20)
    @Column(name = "enterby")
    private String enterby;
    @Size(max = 3)
    @Column(name = "auth")
    private String auth;
    @Column(name = "trantime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trantime;
    @Size(max = 20)
    @Column(name = "authby")
    private String authby;
    @Column(name = "OPSTATUS")
    private Integer opstatus;
    @Column(name = "OPTSTATUS")
    private Integer optstatus;

    public IntPostHistory() {
    }

    public IntPostHistory(IntPostHistoryPK intPostHistoryPK) {
        this.intPostHistoryPK = intPostHistoryPK;
    }

    public IntPostHistory(String actype, Date fromdate, String brncode) {
        this.intPostHistoryPK = new IntPostHistoryPK(actype, fromdate, brncode);
    }

    public IntPostHistoryPK getIntPostHistoryPK() {
        return intPostHistoryPK;
    }

    public void setIntPostHistoryPK(IntPostHistoryPK intPostHistoryPK) {
        this.intPostHistoryPK = intPostHistoryPK;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Date getTrantime() {
        return trantime;
    }

    public void setTrantime(Date trantime) {
        this.trantime = trantime;
    }

    public String getAuthby() {
        return authby;
    }

    public void setAuthby(String authby) {
        this.authby = authby;
    }

    public Integer getOpstatus() {
        return opstatus;
    }

    public void setOpstatus(Integer opstatus) {
        this.opstatus = opstatus;
    }

    public Integer getOptstatus() {
        return optstatus;
    }

    public void setOptstatus(Integer optstatus) {
        this.optstatus = optstatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intPostHistoryPK != null ? intPostHistoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntPostHistory)) {
            return false;
        }
        IntPostHistory other = (IntPostHistory) object;
        if ((this.intPostHistoryPK == null && other.intPostHistoryPK != null) || (this.intPostHistoryPK != null && !this.intPostHistoryPK.equals(other.intPostHistoryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.IntPostHistory[ intPostHistoryPK=" + intPostHistoryPK + " ]";
    }
    
}
