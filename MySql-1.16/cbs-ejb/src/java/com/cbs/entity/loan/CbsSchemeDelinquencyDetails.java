/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_delinquency_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeDelinquencyDetails.findAll", query = "SELECT c FROM CbsSchemeDelinquencyDetails c"),
    @NamedQuery(name = "CbsSchemeDelinquencyDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeDelinquencyDetails c WHERE c.cbsSchemeDelinquencyDetailsPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeDelinquencyDetails.findByDelinqCycle", query = "SELECT c FROM CbsSchemeDelinquencyDetails c WHERE c.cbsSchemeDelinquencyDetailsPK.delinqCycle = :delinqCycle"),
    @NamedQuery(name = "CbsSchemeDelinquencyDetails.findByDaysPastDue", query = "SELECT c FROM CbsSchemeDelinquencyDetails c WHERE c.daysPastDue = :daysPastDue"),
    @NamedQuery(name = "CbsSchemeDelinquencyDetails.findByDelFlag", query = "SELECT c FROM CbsSchemeDelinquencyDetails c WHERE c.delFlag = :delFlag"),
    @NamedQuery(name = "CbsSchemeDelinquencyDetails.findByPlaceholder", query = "SELECT c FROM CbsSchemeDelinquencyDetails c WHERE c.placeholder = :placeholder"),
    @NamedQuery(name = "CbsSchemeDelinquencyDetails.findByProvInPercent", query = "SELECT c FROM CbsSchemeDelinquencyDetails c WHERE c.provInPercent = :provInPercent")})
public class CbsSchemeDelinquencyDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeDelinquencyDetailsPK cbsSchemeDelinquencyDetailsPK;
    @Size(max = 3)
    @Column(name = "DAYS_PAST_DUE")
    private String daysPastDue;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;
    @Size(max = 8)
    @Column(name = "PLACEHOLDER")
    private String placeholder;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PROV_IN_PERCENT")
    private BigDecimal provInPercent;

    public CbsSchemeDelinquencyDetails() {
    }

    public CbsSchemeDelinquencyDetails(CbsSchemeDelinquencyDetailsPK cbsSchemeDelinquencyDetailsPK) {
        this.cbsSchemeDelinquencyDetailsPK = cbsSchemeDelinquencyDetailsPK;
    }

    public CbsSchemeDelinquencyDetails(String schemeCode, String delinqCycle) {
        this.cbsSchemeDelinquencyDetailsPK = new CbsSchemeDelinquencyDetailsPK(schemeCode, delinqCycle);
    }

    public CbsSchemeDelinquencyDetailsPK getCbsSchemeDelinquencyDetailsPK() {
        return cbsSchemeDelinquencyDetailsPK;
    }

    public void setCbsSchemeDelinquencyDetailsPK(CbsSchemeDelinquencyDetailsPK cbsSchemeDelinquencyDetailsPK) {
        this.cbsSchemeDelinquencyDetailsPK = cbsSchemeDelinquencyDetailsPK;
    }

    public String getDaysPastDue() {
        return daysPastDue;
    }

    public void setDaysPastDue(String daysPastDue) {
        this.daysPastDue = daysPastDue;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public BigDecimal getProvInPercent() {
        return provInPercent;
    }

    public void setProvInPercent(BigDecimal provInPercent) {
        this.provInPercent = provInPercent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsSchemeDelinquencyDetailsPK != null ? cbsSchemeDelinquencyDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeDelinquencyDetails)) {
            return false;
        }
        CbsSchemeDelinquencyDetails other = (CbsSchemeDelinquencyDetails) object;
        if ((this.cbsSchemeDelinquencyDetailsPK == null && other.cbsSchemeDelinquencyDetailsPK != null) || (this.cbsSchemeDelinquencyDetailsPK != null && !this.cbsSchemeDelinquencyDetailsPK.equals(other.cbsSchemeDelinquencyDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.loan.CbsSchemeDelinquencyDetails[ cbsSchemeDelinquencyDetailsPK=" + cbsSchemeDelinquencyDetailsPK + " ]";
    }
}
