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
public class HrAdvertDtPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "ADVT_CODE")
    private String advtCode;
    @Basic(optional = false)
    @Column(name = "JOB_CODE")
    private String jobCode;
    @Basic(optional = false)
    @Column(name = "MEDIA_TYPE")
    private char mediaType;
    @Basic(optional = false)
    @Column(name = "DESG_CODE")
    private String desgCode;
    @Basic(optional = false)
    @Column(name = "DEPT_CODE")
    private String deptCode;
    @Basic(optional = false)
    @Column(name = "LOCAT_CODE")
    private String locatCode;

    public HrAdvertDtPK() {
    }

    public HrAdvertDtPK(int compCode, String advtCode, String jobCode, char mediaType, String desgCode, String deptCode, String locatCode) {
        this.compCode = compCode;
        this.advtCode = advtCode;
        this.jobCode = jobCode;
        this.mediaType = mediaType;
        this.desgCode = desgCode;
        this.deptCode = deptCode;
        this.locatCode = locatCode;
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

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public char getMediaType() {
        return mediaType;
    }

    public void setMediaType(char mediaType) {
        this.mediaType = mediaType;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getLocatCode() {
        return locatCode;
    }

    public void setLocatCode(String locatCode) {
        this.locatCode = locatCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (advtCode != null ? advtCode.hashCode() : 0);
        hash += (jobCode != null ? jobCode.hashCode() : 0);
        hash += (int) mediaType;
        hash += (desgCode != null ? desgCode.hashCode() : 0);
        hash += (deptCode != null ? deptCode.hashCode() : 0);
        hash += (locatCode != null ? locatCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAdvertDtPK)) {
            return false;
        }
        HrAdvertDtPK other = (HrAdvertDtPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.advtCode == null && other.advtCode != null) || (this.advtCode != null && !this.advtCode.equals(other.advtCode))) {
            return false;
        }
        if ((this.jobCode == null && other.jobCode != null) || (this.jobCode != null && !this.jobCode.equals(other.jobCode))) {
            return false;
        }
        if (this.mediaType != other.mediaType) {
            return false;
        }
        if ((this.desgCode == null && other.desgCode != null) || (this.desgCode != null && !this.desgCode.equals(other.desgCode))) {
            return false;
        }
        if ((this.deptCode == null && other.deptCode != null) || (this.deptCode != null && !this.deptCode.equals(other.deptCode))) {
            return false;
        }
        if ((this.locatCode == null && other.locatCode != null) || (this.locatCode != null && !this.locatCode.equals(other.locatCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrAdvertDtPK[compCode=" + compCode + ", advtCode=" + advtCode + ", jobCode=" + jobCode + ", mediaType=" + mediaType + ", desgCode=" + desgCode + ", deptCode=" + deptCode + ", locatCode=" + locatCode + "]";
    }

}
