/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.sms;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "mb_subscriber_services")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MbSubscriberServices.findAll", query = "SELECT m FROM MbSubscriberServices m"),
    @NamedQuery(name = "MbSubscriberServices.findByAcno", query = "SELECT m FROM MbSubscriberServices m WHERE m.mbSubscriberServicesPK.acno = :acno"),
    @NamedQuery(name = "MbSubscriberServices.findByServices", query = "SELECT m FROM MbSubscriberServices m WHERE m.mbSubscriberServicesPK.services = :services")})
public class MbSubscriberServices extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MbSubscriberServicesPK mbSubscriberServicesPK;
    @JoinColumn(name = "ACNO", referencedColumnName = "ACNO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MbSubscriberTab mbSubscriberTab;

    public MbSubscriberServices() {
    }

    public MbSubscriberServices(MbSubscriberServicesPK mbSubscriberServicesPK) {
        this.mbSubscriberServicesPK = mbSubscriberServicesPK;
    }

    public MbSubscriberServices(String acno, short services) {
        this.mbSubscriberServicesPK = new MbSubscriberServicesPK(acno, services);
    }

    public MbSubscriberServicesPK getMbSubscriberServicesPK() {
        return mbSubscriberServicesPK;
    }

    public void setMbSubscriberServicesPK(MbSubscriberServicesPK mbSubscriberServicesPK) {
        this.mbSubscriberServicesPK = mbSubscriberServicesPK;
    }

    public MbSubscriberTab getMbSubscriberTab() {
        return mbSubscriberTab;
    }

    public void setMbSubscriberTab(MbSubscriberTab mbSubscriberTab) {
        this.mbSubscriberTab = mbSubscriberTab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mbSubscriberServicesPK != null ? mbSubscriberServicesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MbSubscriberServices)) {
            return false;
        }
        MbSubscriberServices other = (MbSubscriberServices) object;
        if ((this.mbSubscriberServicesPK == null && other.mbSubscriberServicesPK != null) || (this.mbSubscriberServicesPK != null && !this.mbSubscriberServicesPK.equals(other.mbSubscriberServicesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbSubscriberServices[ mbSubscriberServicesPK=" + mbSubscriberServicesPK + " ]";
    }
}
