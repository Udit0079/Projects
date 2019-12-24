/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author root
 */
@Embeddable
public class HrMstZoneLocationPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "ZONE_CODE")
    private String zoneCode;
    @Basic(optional = false)
    @Column(name = "LOCATION_CODE")
    private String locationCode;

    public HrMstZoneLocationPK() {
    }

    public HrMstZoneLocationPK(int compCode, String zoneCode, String locationCode) {
        this.compCode = compCode;
        this.zoneCode = zoneCode;
        this.locationCode = locationCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (zoneCode != null ? zoneCode.hashCode() : 0);
        hash += (locationCode != null ? locationCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstZoneLocationPK)) {
            return false;
        }
        HrMstZoneLocationPK other = (HrMstZoneLocationPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.zoneCode == null && other.zoneCode != null) || (this.zoneCode != null && !this.zoneCode.equals(other.zoneCode))) {
            return false;
        }
        if ((this.locationCode == null && other.locationCode != null) || (this.locationCode != null && !this.locationCode.equals(other.locationCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrMstZoneLocationPK[compCode=" + compCode + ", zoneCode=" + zoneCode + ", locationCode=" + locationCode + "]";
    }

}
