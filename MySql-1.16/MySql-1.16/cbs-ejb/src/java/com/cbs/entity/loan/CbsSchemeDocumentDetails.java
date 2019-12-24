/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_document_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeDocumentDetails.findAll", query = "SELECT c FROM CbsSchemeDocumentDetails c"),
    @NamedQuery(name = "CbsSchemeDocumentDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeDocumentDetails c WHERE c.cbsSchemeDocumentDetailsPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeDocumentDetails.findByDocumentCode", query = "SELECT c FROM CbsSchemeDocumentDetails c WHERE c.cbsSchemeDocumentDetailsPK.documentCode = :documentCode"),
    @NamedQuery(name = "CbsSchemeDocumentDetails.findByDocumentDescription", query = "SELECT c FROM CbsSchemeDocumentDetails c WHERE c.documentDescription = :documentDescription"),
    @NamedQuery(name = "CbsSchemeDocumentDetails.findByMandatoryDocument", query = "SELECT c FROM CbsSchemeDocumentDetails c WHERE c.mandatoryDocument = :mandatoryDocument"),
    @NamedQuery(name = "CbsSchemeDocumentDetails.findByDelFlag", query = "SELECT c FROM CbsSchemeDocumentDetails c WHERE c.delFlag = :delFlag")})
public class CbsSchemeDocumentDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeDocumentDetailsPK cbsSchemeDocumentDetailsPK;
    @Size(max = 80)
    @Column(name = "DOCUMENT_DESCRIPTION")
    private String documentDescription;
    @Size(max = 1)
    @Column(name = "MANDATORY_DOCUMENT")
    private String mandatoryDocument;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsSchemeDocumentDetails() {
    }

    public CbsSchemeDocumentDetails(CbsSchemeDocumentDetailsPK cbsSchemeDocumentDetailsPK) {
        this.cbsSchemeDocumentDetailsPK = cbsSchemeDocumentDetailsPK;
    }

    public CbsSchemeDocumentDetails(String schemeCode, String documentCode) {
        this.cbsSchemeDocumentDetailsPK = new CbsSchemeDocumentDetailsPK(schemeCode, documentCode);
    }

    public CbsSchemeDocumentDetailsPK getCbsSchemeDocumentDetailsPK() {
        return cbsSchemeDocumentDetailsPK;
    }

    public void setCbsSchemeDocumentDetailsPK(CbsSchemeDocumentDetailsPK cbsSchemeDocumentDetailsPK) {
        this.cbsSchemeDocumentDetailsPK = cbsSchemeDocumentDetailsPK;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public String getMandatoryDocument() {
        return mandatoryDocument;
    }

    public void setMandatoryDocument(String mandatoryDocument) {
        this.mandatoryDocument = mandatoryDocument;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsSchemeDocumentDetailsPK != null ? cbsSchemeDocumentDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeDocumentDetails)) {
            return false;
        }
        CbsSchemeDocumentDetails other = (CbsSchemeDocumentDetails) object;
        if ((this.cbsSchemeDocumentDetailsPK == null && other.cbsSchemeDocumentDetailsPK != null) || (this.cbsSchemeDocumentDetailsPK != null && !this.cbsSchemeDocumentDetailsPK.equals(other.cbsSchemeDocumentDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeDocumentDetails[ cbsSchemeDocumentDetailsPK=" + cbsSchemeDocumentDetailsPK + " ]";
    }
    
}
