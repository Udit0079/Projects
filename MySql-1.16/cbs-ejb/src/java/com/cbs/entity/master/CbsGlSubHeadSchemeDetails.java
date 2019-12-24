/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.master;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "cbs_gl_sub_head_scheme_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsGlSubHeadSchemeDetails.findAll", query = "SELECT c FROM CbsGlSubHeadSchemeDetails c"),
    @NamedQuery(name = "CbsGlSubHeadSchemeDetails.findBySchemeCode", query = "SELECT c FROM CbsGlSubHeadSchemeDetails c WHERE c.cbsGlSubHeadSchemeDetailsPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsGlSubHeadSchemeDetails.findBySchemeType", query = "SELECT c FROM CbsGlSubHeadSchemeDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsGlSubHeadSchemeDetails.findByGlSubHeadCode", query = "SELECT c FROM CbsGlSubHeadSchemeDetails c WHERE c.cbsGlSubHeadSchemeDetailsPK.glSubHeadCode = :glSubHeadCode"),
    @NamedQuery(name = "CbsGlSubHeadSchemeDetails.findByDfltFlag", query = "SELECT c FROM CbsGlSubHeadSchemeDetails c WHERE c.dfltFlag = :dfltFlag"),
    @NamedQuery(name = "CbsGlSubHeadSchemeDetails.findByDelFlag", query = "SELECT c FROM CbsGlSubHeadSchemeDetails c WHERE c.delFlag = :delFlag")})
public class CbsGlSubHeadSchemeDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsGlSubHeadSchemeDetailsPK cbsGlSubHeadSchemeDetailsPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 1)
    @Column(name = "DFLT_FLAG")
    private String dfltFlag;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsGlSubHeadSchemeDetails() {
    }

    public CbsGlSubHeadSchemeDetails(CbsGlSubHeadSchemeDetailsPK cbsGlSubHeadSchemeDetailsPK) {
        this.cbsGlSubHeadSchemeDetailsPK = cbsGlSubHeadSchemeDetailsPK;
    }

    public CbsGlSubHeadSchemeDetails(CbsGlSubHeadSchemeDetailsPK cbsGlSubHeadSchemeDetailsPK, String schemeType) {
        this.cbsGlSubHeadSchemeDetailsPK = cbsGlSubHeadSchemeDetailsPK;
        this.schemeType = schemeType;
    }

    public CbsGlSubHeadSchemeDetails(String schemeCode, String glSubHeadCode) {
        this.cbsGlSubHeadSchemeDetailsPK = new CbsGlSubHeadSchemeDetailsPK(schemeCode, glSubHeadCode);
    }

    public CbsGlSubHeadSchemeDetailsPK getCbsGlSubHeadSchemeDetailsPK() {
        return cbsGlSubHeadSchemeDetailsPK;
    }

    public void setCbsGlSubHeadSchemeDetailsPK(CbsGlSubHeadSchemeDetailsPK cbsGlSubHeadSchemeDetailsPK) {
        this.cbsGlSubHeadSchemeDetailsPK = cbsGlSubHeadSchemeDetailsPK;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getDfltFlag() {
        return dfltFlag;
    }

    public void setDfltFlag(String dfltFlag) {
        this.dfltFlag = dfltFlag;
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
        hash += (cbsGlSubHeadSchemeDetailsPK != null ? cbsGlSubHeadSchemeDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsGlSubHeadSchemeDetails)) {
            return false;
        }
        CbsGlSubHeadSchemeDetails other = (CbsGlSubHeadSchemeDetails) object;
        if ((this.cbsGlSubHeadSchemeDetailsPK == null && other.cbsGlSubHeadSchemeDetailsPK != null) || (this.cbsGlSubHeadSchemeDetailsPK != null && !this.cbsGlSubHeadSchemeDetailsPK.equals(other.cbsGlSubHeadSchemeDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsGlSubHeadSchemeDetails[ cbsGlSubHeadSchemeDetailsPK=" + cbsGlSubHeadSchemeDetailsPK + " ]";
    }
    
}
