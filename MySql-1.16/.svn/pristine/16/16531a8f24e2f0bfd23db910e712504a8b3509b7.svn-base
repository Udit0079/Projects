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
@Table(name = "cbs_scheme_tod_reference_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findAll", query = "SELECT c FROM CbsSchemeTodReferenceDetails c"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.cbsSchemeTodReferenceDetailsPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findByReferenceType", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.cbsSchemeTodReferenceDetailsPK.referenceType = :referenceType"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findByDiscretNumberOfDays", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.discretNumberOfDays = :discretNumberOfDays"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findByPenalDays", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.penalDays = :penalDays"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findByDiscretAdvnType", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.discretAdvnType = :discretAdvnType"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findByDiscretAdvnCategory", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.discretAdvnCategory = :discretAdvnCategory"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findByIntFlag", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.intFlag = :intFlag"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findByInterestTableCode", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.interestTableCode = :interestTableCode"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findByToLevelIntTblCode", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.toLevelIntTblCode = :toLevelIntTblCode"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findByFreeTextCode", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.freeTextCode = :freeTextCode"),
    @NamedQuery(name = "CbsSchemeTodReferenceDetails.findByDelFlag", query = "SELECT c FROM CbsSchemeTodReferenceDetails c WHERE c.delFlag = :delFlag")})
public class CbsSchemeTodReferenceDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeTodReferenceDetailsPK cbsSchemeTodReferenceDetailsPK;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 3)
    @Column(name = "DISCRET_NUMBER_OF_DAYS")
    private String discretNumberOfDays;
    @Size(max = 3)
    @Column(name = "PENAL_DAYS")
    private String penalDays;
    @Size(max = 1)
    @Column(name = "DISCRET_ADVN_TYPE")
    private String discretAdvnType;
    @Size(max = 1)
    @Column(name = "DISCRET_ADVN_CATEGORY")
    private String discretAdvnCategory;
    @Size(max = 1)
    @Column(name = "INT_FLAG")
    private String intFlag;
    @Size(max = 5)
    @Column(name = "INTEREST_TABLE_CODE")
    private String interestTableCode;
    @Size(max = 1)
    @Column(name = "TO_LEVEL_INT_TBL_CODE")
    private String toLevelIntTblCode;
    @Size(max = 50)
    @Column(name = "FREE_TEXT_CODE")
    private String freeTextCode;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsSchemeTodReferenceDetails() {
    }

    public CbsSchemeTodReferenceDetails(CbsSchemeTodReferenceDetailsPK cbsSchemeTodReferenceDetailsPK) {
        this.cbsSchemeTodReferenceDetailsPK = cbsSchemeTodReferenceDetailsPK;
    }

    public CbsSchemeTodReferenceDetails(String schemeCode, String referenceType) {
        this.cbsSchemeTodReferenceDetailsPK = new CbsSchemeTodReferenceDetailsPK(schemeCode, referenceType);
    }

    public CbsSchemeTodReferenceDetailsPK getCbsSchemeTodReferenceDetailsPK() {
        return cbsSchemeTodReferenceDetailsPK;
    }

    public void setCbsSchemeTodReferenceDetailsPK(CbsSchemeTodReferenceDetailsPK cbsSchemeTodReferenceDetailsPK) {
        this.cbsSchemeTodReferenceDetailsPK = cbsSchemeTodReferenceDetailsPK;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getDiscretNumberOfDays() {
        return discretNumberOfDays;
    }

    public void setDiscretNumberOfDays(String discretNumberOfDays) {
        this.discretNumberOfDays = discretNumberOfDays;
    }

    public String getPenalDays() {
        return penalDays;
    }

    public void setPenalDays(String penalDays) {
        this.penalDays = penalDays;
    }

    public String getDiscretAdvnType() {
        return discretAdvnType;
    }

    public void setDiscretAdvnType(String discretAdvnType) {
        this.discretAdvnType = discretAdvnType;
    }

    public String getDiscretAdvnCategory() {
        return discretAdvnCategory;
    }

    public void setDiscretAdvnCategory(String discretAdvnCategory) {
        this.discretAdvnCategory = discretAdvnCategory;
    }

    public String getIntFlag() {
        return intFlag;
    }

    public void setIntFlag(String intFlag) {
        this.intFlag = intFlag;
    }

    public String getInterestTableCode() {
        return interestTableCode;
    }

    public void setInterestTableCode(String interestTableCode) {
        this.interestTableCode = interestTableCode;
    }

    public String getToLevelIntTblCode() {
        return toLevelIntTblCode;
    }

    public void setToLevelIntTblCode(String toLevelIntTblCode) {
        this.toLevelIntTblCode = toLevelIntTblCode;
    }

    public String getFreeTextCode() {
        return freeTextCode;
    }

    public void setFreeTextCode(String freeTextCode) {
        this.freeTextCode = freeTextCode;
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
        hash += (cbsSchemeTodReferenceDetailsPK != null ? cbsSchemeTodReferenceDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeTodReferenceDetails)) {
            return false;
        }
        CbsSchemeTodReferenceDetails other = (CbsSchemeTodReferenceDetails) object;
        if ((this.cbsSchemeTodReferenceDetailsPK == null && other.cbsSchemeTodReferenceDetailsPK != null) || (this.cbsSchemeTodReferenceDetailsPK != null && !this.cbsSchemeTodReferenceDetailsPK.equals(other.cbsSchemeTodReferenceDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeTodReferenceDetails[ cbsSchemeTodReferenceDetailsPK=" + cbsSchemeTodReferenceDetailsPK + " ]";
    }
    
}
