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
public class HrAdvertDetDtPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "ADVT_CODE")
    private String advtCode;
    @Basic(optional = false)
    @Column(name = "MEDIA_TYPE")
    private char mediaType;
    @Basic(optional = false)
    @Column(name = "JOB_CODE")
    private String jobCode;
    @Basic(optional = false)
    @Column(name = "CITY")
    private String city;
    @Basic(optional = false)
    @Column(name = "MEDIA_NAME")
    private String mediaName;

    public HrAdvertDetDtPK() {
    }

    public HrAdvertDetDtPK(int compCode, String advtCode, char mediaType, String jobCode, String city, String mediaName) {
        this.compCode = compCode;
        this.advtCode = advtCode;
        this.mediaType = mediaType;
        this.jobCode = jobCode;
        this.city = city;
        this.mediaName = mediaName;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getAdvtCode() {
        return advtCode;
    }

    public void setAdvtCode(String advtCode) {
        this.advtCode = advtCode;
    }

    public char getMediaType() {
        return mediaType;
    }

    public void setMediaType(char mediaType) {
        this.mediaType = mediaType;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (advtCode != null ? advtCode.hashCode() : 0);
        hash += (int) mediaType;
        hash += (jobCode != null ? jobCode.hashCode() : 0);
        hash += (city != null ? city.hashCode() : 0);
        hash += (mediaName != null ? mediaName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAdvertDetDtPK)) {
            return false;
        }
        HrAdvertDetDtPK other = (HrAdvertDetDtPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.advtCode == null && other.advtCode != null) || (this.advtCode != null && !this.advtCode.equals(other.advtCode))) {
            return false;
        }
        if (this.mediaType != other.mediaType) {
            return false;
        }
        if ((this.jobCode == null && other.jobCode != null) || (this.jobCode != null && !this.jobCode.equals(other.jobCode))) {
            return false;
        }
        if ((this.city == null && other.city != null) || (this.city != null && !this.city.equals(other.city))) {
            return false;
        }
        if ((this.mediaName == null && other.mediaName != null) || (this.mediaName != null && !this.mediaName.equals(other.mediaName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrAdvertDetDtPK[compCode=" + compCode + ", advtCode=" + advtCode + ", mediaType=" + mediaType + ", jobCode=" + jobCode + ", city=" + city + ", mediaName=" + mediaName + "]";
    }

}
