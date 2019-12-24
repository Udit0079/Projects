/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.master;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_exception_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsExceptionMaster.findAll", query = "SELECT c FROM CbsExceptionMaster c"),
    @NamedQuery(name = "CbsExceptionMaster.findByExceptionCode", query = "SELECT c FROM CbsExceptionMaster c WHERE c.exceptionCode = :exceptionCode"),
    @NamedQuery(name = "CbsExceptionMaster.findByExceptionDesc", query = "SELECT c FROM CbsExceptionMaster c WHERE c.exceptionDesc = :exceptionDesc"),
    @NamedQuery(name = "CbsExceptionMaster.findByExceptionType", query = "SELECT c FROM CbsExceptionMaster c WHERE c.exceptionType = :exceptionType")})
public class CbsExceptionMaster extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "EXCEPTION_CODE")
    private String exceptionCode;
    @Size(max = 40)
    @Column(name = "EXCEPTION_DESC")
    private String exceptionDesc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "EXCEPTION_TYPE")
    private String exceptionType;

    public CbsExceptionMaster() {
    }

    public CbsExceptionMaster(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public CbsExceptionMaster(String exceptionCode, String exceptionType) {
        this.exceptionCode = exceptionCode;
        this.exceptionType = exceptionType;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionDesc() {
        return exceptionDesc;
    }

    public void setExceptionDesc(String exceptionDesc) {
        this.exceptionDesc = exceptionDesc;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exceptionCode != null ? exceptionCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsExceptionMaster)) {
            return false;
        }
        CbsExceptionMaster other = (CbsExceptionMaster) object;
        if ((this.exceptionCode == null && other.exceptionCode != null) || (this.exceptionCode != null && !this.exceptionCode.equals(other.exceptionCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsExceptionMaster[ exceptionCode=" + exceptionCode + " ]";
    }
    
}
