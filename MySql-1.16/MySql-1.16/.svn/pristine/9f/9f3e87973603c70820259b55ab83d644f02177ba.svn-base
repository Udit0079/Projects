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
@Table(name = "cbs_scheme_asset_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeAssetDetails.findAll", query = "SELECT c FROM CbsSchemeAssetDetails c"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.cbsSchemeAssetDetailsPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findByDpdCounter", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.cbsSchemeAssetDetailsPK.dpdCounter = :dpdCounter"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findByMainClass", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.mainClass = :mainClass"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findBySubClass", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.subClass = :subClass"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findByIntAccre", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.intAccre = :intAccre"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findByIntFlagBk", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.intFlagBk = :intFlagBk"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findByIntFlagColl", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.intFlagColl = :intFlagColl"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findByPdFlag", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.pdFlag = :pdFlag"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findByIntSuspPlaceholder", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.intSuspPlaceholder = :intSuspPlaceholder"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findByProvisionDr", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.provisionDr = :provisionDr"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findByPlaceholdersCr", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.placeholdersCr = :placeholdersCr"),
    @NamedQuery(name = "CbsSchemeAssetDetails.findByDelFlag", query = "SELECT c FROM CbsSchemeAssetDetails c WHERE c.delFlag = :delFlag")})
public class CbsSchemeAssetDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeAssetDetailsPK cbsSchemeAssetDetailsPK;
    @Size(max = 5)
    @Column(name = "MAIN_CLASS")
    private String mainClass;
    @Size(max = 5)
    @Column(name = "SUB_CLASS")
    private String subClass;
    @Size(max = 1)
    @Column(name = "INT_ACCRE")
    private String intAccre;
    @Size(max = 1)
    @Column(name = "INT_FLAG_BK")
    private String intFlagBk;
    @Size(max = 1)
    @Column(name = "INT_FLAG_COLL")
    private String intFlagColl;
    @Size(max = 1)
    @Column(name = "PD_FLAG")
    private String pdFlag;
    @Size(max = 12)
    @Column(name = "INT_SUSP_PLACEHOLDER")
    private String intSuspPlaceholder;
    @Size(max = 12)
    @Column(name = "PROVISION_DR")
    private String provisionDr;
    @Size(max = 12)
    @Column(name = "PLACEHOLDERS_CR")
    private String placeholdersCr;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsSchemeAssetDetails() {
    }

    public CbsSchemeAssetDetails(CbsSchemeAssetDetailsPK cbsSchemeAssetDetailsPK) {
        this.cbsSchemeAssetDetailsPK = cbsSchemeAssetDetailsPK;
    }

    public CbsSchemeAssetDetails(String schemeCode, String dpdCounter) {
        this.cbsSchemeAssetDetailsPK = new CbsSchemeAssetDetailsPK(schemeCode, dpdCounter);
    }

    public CbsSchemeAssetDetailsPK getCbsSchemeAssetDetailsPK() {
        return cbsSchemeAssetDetailsPK;
    }

    public void setCbsSchemeAssetDetailsPK(CbsSchemeAssetDetailsPK cbsSchemeAssetDetailsPK) {
        this.cbsSchemeAssetDetailsPK = cbsSchemeAssetDetailsPK;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public String getIntAccre() {
        return intAccre;
    }

    public void setIntAccre(String intAccre) {
        this.intAccre = intAccre;
    }

    public String getIntFlagBk() {
        return intFlagBk;
    }

    public void setIntFlagBk(String intFlagBk) {
        this.intFlagBk = intFlagBk;
    }

    public String getIntFlagColl() {
        return intFlagColl;
    }

    public void setIntFlagColl(String intFlagColl) {
        this.intFlagColl = intFlagColl;
    }

    public String getPdFlag() {
        return pdFlag;
    }

    public void setPdFlag(String pdFlag) {
        this.pdFlag = pdFlag;
    }

    public String getIntSuspPlaceholder() {
        return intSuspPlaceholder;
    }

    public void setIntSuspPlaceholder(String intSuspPlaceholder) {
        this.intSuspPlaceholder = intSuspPlaceholder;
    }

    public String getProvisionDr() {
        return provisionDr;
    }

    public void setProvisionDr(String provisionDr) {
        this.provisionDr = provisionDr;
    }

    public String getPlaceholdersCr() {
        return placeholdersCr;
    }

    public void setPlaceholdersCr(String placeholdersCr) {
        this.placeholdersCr = placeholdersCr;
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
        hash += (cbsSchemeAssetDetailsPK != null ? cbsSchemeAssetDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeAssetDetails)) {
            return false;
        }
        CbsSchemeAssetDetails other = (CbsSchemeAssetDetails) object;
        if ((this.cbsSchemeAssetDetailsPK == null && other.cbsSchemeAssetDetailsPK != null) || (this.cbsSchemeAssetDetailsPK != null && !this.cbsSchemeAssetDetailsPK.equals(other.cbsSchemeAssetDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeAssetDetails[ cbsSchemeAssetDetailsPK=" + cbsSchemeAssetDetailsPK + " ]";
    }
    
}
