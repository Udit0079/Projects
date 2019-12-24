/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.sms;

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
 * @author root
 */
@Entity
@Table(name = "mb_services_description")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MbServicesDescription.findAll", query = "SELECT m FROM MbServicesDescription m"),
    @NamedQuery(name = "MbServicesDescription.findByServiceCode", query = "SELECT m FROM MbServicesDescription m WHERE m.serviceCode = :serviceCode"),
    @NamedQuery(name = "MbServicesDescription.findByServiceName", query = "SELECT m FROM MbServicesDescription m WHERE m.serviceName = :serviceName"),
    @NamedQuery(name = "MbServicesDescription.findByServiceType", query = "SELECT m FROM MbServicesDescription m WHERE m.serviceType = :serviceType"),
    @NamedQuery(name = "MbServicesDescription.findByServiceStatus", query = "SELECT m FROM MbServicesDescription m WHERE m.serviceStatus = :serviceStatus"),
    @NamedQuery(name = "MbServicesDescription.findByEnterBy", query = "SELECT m FROM MbServicesDescription m WHERE m.enterBy = :enterBy"),
    @NamedQuery(name = "MbServicesDescription.findByLastUpdateBy", query = "SELECT m FROM MbServicesDescription m WHERE m.lastUpdateBy = :lastUpdateBy"),
    @NamedQuery(name = "MbServicesDescription.findByLastUpdateDt", query = "SELECT m FROM MbServicesDescription m WHERE m.lastUpdateDt = :lastUpdateDt")})
public class MbServicesDescription extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SERVICE_CODE")
    private Integer serviceCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SERVICE_NAME")
    private String serviceName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SERVICE_TYPE")
    private String serviceType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SERVICE_STATUS")
    private String serviceStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Size(max = 50)
    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;
    @Column(name = "LAST_UPDATE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDt;

    public MbServicesDescription() {
    }

    public MbServicesDescription(Integer serviceCode) {
        this.serviceCode = serviceCode;
    }

    public MbServicesDescription(Integer serviceCode, String serviceName, String serviceType, String serviceStatus, String enterBy) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.serviceStatus = serviceStatus;
        this.enterBy = enterBy;
    }

    public Integer getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Integer serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(Date lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceCode != null ? serviceCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MbServicesDescription)) {
            return false;
        }
        MbServicesDescription other = (MbServicesDescription) object;
        if ((this.serviceCode == null && other.serviceCode != null) || (this.serviceCode != null && !this.serviceCode.equals(other.serviceCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbServicesDescription[ serviceCode=" + serviceCode + " ]";
    }
    
}
