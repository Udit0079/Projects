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
@Table(name = "cbs_scheme_type_form_mapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeTypeFormMapping.findAll", query = "SELECT c FROM CbsSchemeTypeFormMapping c"),
    @NamedQuery(name = "CbsSchemeTypeFormMapping.findById", query = "SELECT c FROM CbsSchemeTypeFormMapping c WHERE c.id = :id"),
    @NamedQuery(name = "CbsSchemeTypeFormMapping.findBySchemeType", query = "SELECT c FROM CbsSchemeTypeFormMapping c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeTypeFormMapping.findByFormId", query = "SELECT c FROM CbsSchemeTypeFormMapping c WHERE c.formId = :formId")})
public class CbsSchemeTypeFormMapping extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 12)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 20)
    @Column(name = "FORM_ID")
    private String formId;

    public CbsSchemeTypeFormMapping() {
    }

    public CbsSchemeTypeFormMapping(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeTypeFormMapping)) {
            return false;
        }
        CbsSchemeTypeFormMapping other = (CbsSchemeTypeFormMapping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.master.CbsSchemeTypeFormMapping[ id=" + id + " ]";
    }
    
}
