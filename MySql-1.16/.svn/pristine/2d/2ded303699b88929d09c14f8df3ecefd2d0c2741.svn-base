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
@Table(name = "cbs_scheme_deposit_interest_definition_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findAll", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.cbsSchemeDepositInterestDefinitionDetailsPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findByInterestMethod", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.cbsSchemeDepositInterestDefinitionDetailsPK.interestMethod = :interestMethod"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findByMaxDepositPeriodMonths", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.maxDepositPeriodMonths = :maxDepositPeriodMonths"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findByMaxDepositPeriodDays", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.maxDepositPeriodDays = :maxDepositPeriodDays"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findByBaseAmountInd", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.baseAmountInd = :baseAmountInd"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findByCompoundingPeriod", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.compoundingPeriod = :compoundingPeriod"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findByCompoundingBase", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.compoundingBase = :compoundingBase"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findByMinCompoundingPeriod", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.minCompoundingPeriod = :minCompoundingPeriod"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findByMinCompoundingBase", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.minCompoundingBase = :minCompoundingBase"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findByBrokenPeriodMthd", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.brokenPeriodMthd = :brokenPeriodMthd"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findByBrokenPeriodBase", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.brokenPeriodBase = :brokenPeriodBase"),
    @NamedQuery(name = "CbsSchemeDepositInterestDefinitionDetails.findByDelFlag", query = "SELECT c FROM CbsSchemeDepositInterestDefinitionDetails c WHERE c.delFlag = :delFlag")})
public class CbsSchemeDepositInterestDefinitionDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeDepositInterestDefinitionDetailsPK cbsSchemeDepositInterestDefinitionDetailsPK;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 3)
    @Column(name = "MAX_DEPOSIT_PERIOD_MONTHS")
    private String maxDepositPeriodMonths;
    @Size(max = 3)
    @Column(name = "MAX_DEPOSIT_PERIOD_DAYS")
    private String maxDepositPeriodDays;
    @Size(max = 1)
    @Column(name = "BASE_AMOUNT_IND")
    private String baseAmountInd;
    @Size(max = 3)
    @Column(name = "COMPOUNDING_PERIOD")
    private String compoundingPeriod;
    @Size(max = 3)
    @Column(name = "COMPOUNDING_BASE")
    private String compoundingBase;
    @Size(max = 3)
    @Column(name = "MIN_COMPOUNDING_PERIOD")
    private String minCompoundingPeriod;
    @Size(max = 3)
    @Column(name = "MIN_COMPOUNDING_BASE")
    private String minCompoundingBase;
    @Size(max = 2)
    @Column(name = "BROKEN_PERIOD_MTHD")
    private String brokenPeriodMthd;
    @Size(max = 3)
    @Column(name = "BROKEN_PERIOD_BASE")
    private String brokenPeriodBase;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public CbsSchemeDepositInterestDefinitionDetails() {
    }

    public CbsSchemeDepositInterestDefinitionDetails(CbsSchemeDepositInterestDefinitionDetailsPK cbsSchemeDepositInterestDefinitionDetailsPK) {
        this.cbsSchemeDepositInterestDefinitionDetailsPK = cbsSchemeDepositInterestDefinitionDetailsPK;
    }

    public CbsSchemeDepositInterestDefinitionDetails(String schemeCode, String interestMethod) {
        this.cbsSchemeDepositInterestDefinitionDetailsPK = new CbsSchemeDepositInterestDefinitionDetailsPK(schemeCode, interestMethod);
    }

    public CbsSchemeDepositInterestDefinitionDetailsPK getCbsSchemeDepositInterestDefinitionDetailsPK() {
        return cbsSchemeDepositInterestDefinitionDetailsPK;
    }

    public void setCbsSchemeDepositInterestDefinitionDetailsPK(CbsSchemeDepositInterestDefinitionDetailsPK cbsSchemeDepositInterestDefinitionDetailsPK) {
        this.cbsSchemeDepositInterestDefinitionDetailsPK = cbsSchemeDepositInterestDefinitionDetailsPK;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getMaxDepositPeriodMonths() {
        return maxDepositPeriodMonths;
    }

    public void setMaxDepositPeriodMonths(String maxDepositPeriodMonths) {
        this.maxDepositPeriodMonths = maxDepositPeriodMonths;
    }

    public String getMaxDepositPeriodDays() {
        return maxDepositPeriodDays;
    }

    public void setMaxDepositPeriodDays(String maxDepositPeriodDays) {
        this.maxDepositPeriodDays = maxDepositPeriodDays;
    }

    public String getBaseAmountInd() {
        return baseAmountInd;
    }

    public void setBaseAmountInd(String baseAmountInd) {
        this.baseAmountInd = baseAmountInd;
    }

    public String getCompoundingPeriod() {
        return compoundingPeriod;
    }

    public void setCompoundingPeriod(String compoundingPeriod) {
        this.compoundingPeriod = compoundingPeriod;
    }

    public String getCompoundingBase() {
        return compoundingBase;
    }

    public void setCompoundingBase(String compoundingBase) {
        this.compoundingBase = compoundingBase;
    }

    public String getMinCompoundingPeriod() {
        return minCompoundingPeriod;
    }

    public void setMinCompoundingPeriod(String minCompoundingPeriod) {
        this.minCompoundingPeriod = minCompoundingPeriod;
    }

    public String getMinCompoundingBase() {
        return minCompoundingBase;
    }

    public void setMinCompoundingBase(String minCompoundingBase) {
        this.minCompoundingBase = minCompoundingBase;
    }

    public String getBrokenPeriodMthd() {
        return brokenPeriodMthd;
    }

    public void setBrokenPeriodMthd(String brokenPeriodMthd) {
        this.brokenPeriodMthd = brokenPeriodMthd;
    }

    public String getBrokenPeriodBase() {
        return brokenPeriodBase;
    }

    public void setBrokenPeriodBase(String brokenPeriodBase) {
        this.brokenPeriodBase = brokenPeriodBase;
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
        hash += (cbsSchemeDepositInterestDefinitionDetailsPK != null ? cbsSchemeDepositInterestDefinitionDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeDepositInterestDefinitionDetails)) {
            return false;
        }
        CbsSchemeDepositInterestDefinitionDetails other = (CbsSchemeDepositInterestDefinitionDetails) object;
        if ((this.cbsSchemeDepositInterestDefinitionDetailsPK == null && other.cbsSchemeDepositInterestDefinitionDetailsPK != null) || (this.cbsSchemeDepositInterestDefinitionDetailsPK != null && !this.cbsSchemeDepositInterestDefinitionDetailsPK.equals(other.cbsSchemeDepositInterestDefinitionDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeDepositInterestDefinitionDetails[ cbsSchemeDepositInterestDefinitionDetailsPK=" + cbsSchemeDepositInterestDefinitionDetailsPK + " ]";
    }
    
}
