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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "abb_parameter_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AbbParameterInfo.findAll", query = "SELECT a FROM AbbParameterInfo a"),
    @NamedQuery(name = "AbbParameterInfo.findByPurpose", query = "SELECT a FROM AbbParameterInfo a WHERE a.purpose = :purpose"),
    @NamedQuery(name = "AbbParameterInfo.findByAcno", query = "SELECT a FROM AbbParameterInfo a WHERE a.acno = :acno"),
    @NamedQuery(name = "AbbParameterInfo.findByFlag", query = "SELECT a FROM AbbParameterInfo a WHERE a.flag = :flag")})
public class AbbParameterInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PURPOSE")
    private String purpose;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "ACNO")
    private String acno;
    @Size(max = 1)
    @Column(name = "FLAG")
    private String flag;

    public AbbParameterInfo() {
    }

    public AbbParameterInfo(String acno) {
        this.acno = acno;
    }

    public AbbParameterInfo(String acno, String purpose) {
        this.acno = acno;
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acno != null ? acno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AbbParameterInfo)) {
            return false;
        }
        AbbParameterInfo other = (AbbParameterInfo) object;
        if ((this.acno == null && other.acno != null) || (this.acno != null && !this.acno.equals(other.acno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.master.AbbParameterInfo[ acno=" + acno + " ]";
    }
}
