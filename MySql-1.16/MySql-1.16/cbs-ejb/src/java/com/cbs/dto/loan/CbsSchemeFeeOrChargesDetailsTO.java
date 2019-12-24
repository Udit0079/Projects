/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class CbsSchemeFeeOrChargesDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected CbsSchemeFeeOrChargesDetailsPKTO cbsSchemeFeeOrChargesDetailsPKTO;
    private String currencyCode;
    private String amortize;
    private String chargeDescription;
    private String amortMethod;
    private String chargeEventId;
    private String drPlaceholder;
    private String crPlaceholder;
    private String assessOrDmd;
    private String deductible;
    private String considerForIrr;
    private String multipleFlag;
    private String delFlag;
    private BigDecimal prepaymentFee;

    public String getAmortMethod() {
        return amortMethod;
    }

    public void setAmortMethod(String amortMethod) {
        this.amortMethod = amortMethod;
    }

    public String getAmortize() {
        return amortize;
    }

    public void setAmortize(String amortize) {
        this.amortize = amortize;
    }

    public String getAssessOrDmd() {
        return assessOrDmd;
    }

    public void setAssessOrDmd(String assessOrDmd) {
        this.assessOrDmd = assessOrDmd;
    }

    public CbsSchemeFeeOrChargesDetailsPKTO getCbsSchemeFeeOrChargesDetailsPKTO() {
        return cbsSchemeFeeOrChargesDetailsPKTO;
    }

    public void setCbsSchemeFeeOrChargesDetailsPKTO(CbsSchemeFeeOrChargesDetailsPKTO cbsSchemeFeeOrChargesDetailsPKTO) {
        this.cbsSchemeFeeOrChargesDetailsPKTO = cbsSchemeFeeOrChargesDetailsPKTO;
    }

    public String getChargeDescription() {
        return chargeDescription;
    }

    public void setChargeDescription(String chargeDescription) {
        this.chargeDescription = chargeDescription;
    }

    public String getChargeEventId() {
        return chargeEventId;
    }

    public void setChargeEventId(String chargeEventId) {
        this.chargeEventId = chargeEventId;
    }

    public String getConsiderForIrr() {
        return considerForIrr;
    }

    public void setConsiderForIrr(String considerForIrr) {
        this.considerForIrr = considerForIrr;
    }

    public String getCrPlaceholder() {
        return crPlaceholder;
    }

    public void setCrPlaceholder(String crPlaceholder) {
        this.crPlaceholder = crPlaceholder;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDeductible() {
        return deductible;
    }

    public void setDeductible(String deductible) {
        this.deductible = deductible;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDrPlaceholder() {
        return drPlaceholder;
    }

    public void setDrPlaceholder(String drPlaceholder) {
        this.drPlaceholder = drPlaceholder;
    }

    public String getMultipleFlag() {
        return multipleFlag;
    }

    public void setMultipleFlag(String multipleFlag) {
        this.multipleFlag = multipleFlag;
    }

    public BigDecimal getPrepaymentFee() {
        return prepaymentFee;
    }

    public void setPrepaymentFee(BigDecimal prepaymentFee) {
        this.prepaymentFee = prepaymentFee;
    }
}
