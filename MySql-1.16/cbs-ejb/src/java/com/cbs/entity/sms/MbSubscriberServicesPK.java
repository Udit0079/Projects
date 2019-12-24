/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.sms;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Embeddable
public class MbSubscriberServicesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "ACNO")
    private String acno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SERVICES")
    private short services;

    public MbSubscriberServicesPK() {
    }

    public MbSubscriberServicesPK(String acno, short services) {
        this.acno = acno;
        this.services = services;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public short getServices() {
        return services;
    }

    public void setServices(short services) {
        this.services = services;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acno != null ? acno.hashCode() : 0);
        hash += (int) services;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MbSubscriberServicesPK)) {
            return false;
        }
        MbSubscriberServicesPK other = (MbSubscriberServicesPK) object;
        if ((this.acno == null && other.acno != null) || (this.acno != null && !this.acno.equals(other.acno))) {
            return false;
        }
        if (this.services != other.services) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbSubscriberServicesPK[ acno=" + acno + ", services=" + services + " ]";
    }
    
}
