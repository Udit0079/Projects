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
@Table(name = "cbs_scheme_pop_up_forms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemePopUpForms.findAll", query = "SELECT c FROM CbsSchemePopUpForms c"),
    @NamedQuery(name = "CbsSchemePopUpForms.findByFormId", query = "SELECT c FROM CbsSchemePopUpForms c WHERE c.formId = :formId"),
    @NamedQuery(name = "CbsSchemePopUpForms.findByFormName", query = "SELECT c FROM CbsSchemePopUpForms c WHERE c.formName = :formName")})
public class CbsSchemePopUpForms extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FORM_ID")
    private String formId;
    @Size(max = 50)
    @Column(name = "FORM_NAME")
    private String formName;

    public CbsSchemePopUpForms() {
    }

    public CbsSchemePopUpForms(String formId) {
        this.formId = formId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formId != null ? formId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemePopUpForms)) {
            return false;
        }
        CbsSchemePopUpForms other = (CbsSchemePopUpForms) object;
        if ((this.formId == null && other.formId != null) || (this.formId != null && !this.formId.equals(other.formId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.master.CbsSchemePopUpForms[ formId=" + formId + " ]";
    }
}
