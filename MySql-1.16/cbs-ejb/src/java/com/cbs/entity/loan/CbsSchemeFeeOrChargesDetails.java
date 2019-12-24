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

import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_fee_or_charges_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findAll", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.cbsSchemeFeeOrChargesDetailsPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByCurrencyCode", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByChargeType", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.cbsSchemeFeeOrChargesDetailsPK.chargeType = :chargeType"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByAmortize", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.amortize = :amortize"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByChargeDescription", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.chargeDescription = :chargeDescription"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByAmortMethod", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.amortMethod = :amortMethod"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByChargeEventId", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.chargeEventId = :chargeEventId"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByDrPlaceholder", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.drPlaceholder = :drPlaceholder"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByCrPlaceholder", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.crPlaceholder = :crPlaceholder"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByAssessOrDmd", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.assessOrDmd = :assessOrDmd"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByDeductible", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.deductible = :deductible"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByConsiderForIrr", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.considerForIrr = :considerForIrr"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByMultipleFlag", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.multipleFlag = :multipleFlag"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByDelFlag", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.delFlag = :delFlag"),
    @NamedQuery(name = "CbsSchemeFeeOrChargesDetails.findByPrepaymentFee", query = "SELECT c FROM CbsSchemeFeeOrChargesDetails c WHERE c.prepaymentFee = :prepaymentFee")})
public class CbsSchemeFeeOrChargesDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeFeeOrChargesDetailsPK cbsSchemeFeeOrChargesDetailsPK;
    @Size(max = 3)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Size(max = 1)
    @Column(name = "AMORTIZE")
    private String amortize;
    @Size(max = 80)
    @Column(name = "CHARGE_DESCRIPTION")
    private String chargeDescription;
    @Size(max = 3)
    @Column(name = "AMORT_METHOD")
    private String amortMethod;
    @Size(max = 12)
    @Column(name = "CHARGE_EVENT_ID")
    private String chargeEventId;
    @Size(max = 12)
    @Column(name = "DR_PLACEHOLDER")
    private String drPlaceholder;
    @Size(max = 12)
    @Column(name = "CR_PLACEHOLDER")
    private String crPlaceholder;
    @Size(max = 1)
    @Column(name = "ASSESS_OR_DMD")
    private String assessOrDmd;
    @Size(max = 1)
    @Column(name = "DEDUCTIBLE")
    private String deductible;
    @Size(max = 1)
    @Column(name = "CONSIDER_FOR_IRR")
    private String considerForIrr;
    @Size(max = 1)
    @Column(name = "MULTIPLE_FLAG")
    private String multipleFlag;
    @Size(max = 1)
    @Column(name = "DEL_FLAG")
    private String delFlag;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PREPAYMENT_FEE")
    private BigDecimal prepaymentFee;

    public CbsSchemeFeeOrChargesDetails() {
    }

    public CbsSchemeFeeOrChargesDetails(CbsSchemeFeeOrChargesDetailsPK cbsSchemeFeeOrChargesDetailsPK) {
        this.cbsSchemeFeeOrChargesDetailsPK = cbsSchemeFeeOrChargesDetailsPK;
    }

    public CbsSchemeFeeOrChargesDetails(String schemeCode, String chargeType) {
        this.cbsSchemeFeeOrChargesDetailsPK = new CbsSchemeFeeOrChargesDetailsPK(schemeCode, chargeType);
    }

    public CbsSchemeFeeOrChargesDetailsPK getCbsSchemeFeeOrChargesDetailsPK() {
        return cbsSchemeFeeOrChargesDetailsPK;
    }

    public void setCbsSchemeFeeOrChargesDetailsPK(CbsSchemeFeeOrChargesDetailsPK cbsSchemeFeeOrChargesDetailsPK) {
        this.cbsSchemeFeeOrChargesDetailsPK = cbsSchemeFeeOrChargesDetailsPK;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAmortize() {
        return amortize;
    }

    public void setAmortize(String amortize) {
        this.amortize = amortize;
    }

    public String getChargeDescription() {
        return chargeDescription;
    }

    public void setChargeDescription(String chargeDescription) {
        this.chargeDescription = chargeDescription;
    }

    public String getAmortMethod() {
        return amortMethod;
    }

    public void setAmortMethod(String amortMethod) {
        this.amortMethod = amortMethod;
    }

    public String getChargeEventId() {
        return chargeEventId;
    }

    public void setChargeEventId(String chargeEventId) {
        this.chargeEventId = chargeEventId;
    }

    public String getDrPlaceholder() {
        return drPlaceholder;
    }

    public void setDrPlaceholder(String drPlaceholder) {
        this.drPlaceholder = drPlaceholder;
    }

    public String getCrPlaceholder() {
        return crPlaceholder;
    }

    public void setCrPlaceholder(String crPlaceholder) {
        this.crPlaceholder = crPlaceholder;
    }

    public String getAssessOrDmd() {
        return assessOrDmd;
    }

    public void setAssessOrDmd(String assessOrDmd) {
        this.assessOrDmd = assessOrDmd;
    }

    public String getDeductible() {
        return deductible;
    }

    public void setDeductible(String deductible) {
        this.deductible = deductible;
    }

    public String getConsiderForIrr() {
        return considerForIrr;
    }

    public void setConsiderForIrr(String considerForIrr) {
        this.considerForIrr = considerForIrr;
    }

    public String getMultipleFlag() {
        return multipleFlag;
    }

    public void setMultipleFlag(String multipleFlag) {
        this.multipleFlag = multipleFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public BigDecimal getPrepaymentFee() {
        return prepaymentFee;
    }

    public void setPrepaymentFee(BigDecimal prepaymentFee) {
        this.prepaymentFee = prepaymentFee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsSchemeFeeOrChargesDetailsPK != null ? cbsSchemeFeeOrChargesDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeFeeOrChargesDetails)) {
            return false;
        }
        CbsSchemeFeeOrChargesDetails other = (CbsSchemeFeeOrChargesDetails) object;
        if ((this.cbsSchemeFeeOrChargesDetailsPK == null && other.cbsSchemeFeeOrChargesDetailsPK != null) || (this.cbsSchemeFeeOrChargesDetailsPK != null && !this.cbsSchemeFeeOrChargesDetailsPK.equals(other.cbsSchemeFeeOrChargesDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeFeeOrChargesDetails[ cbsSchemeFeeOrChargesDetailsPK=" + cbsSchemeFeeOrChargesDetailsPK + " ]";
    }
    
}
