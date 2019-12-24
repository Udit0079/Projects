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
public class CbsSchemeBcfDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected CbsSchemeBcfDetailsPKTO cbsSchemeBcfDetailsPKTO;
    private String schemeType;
    private String subvenRevCrPlaceholder;
    private BigDecimal accountCrPrefInt;
    private BigDecimal accountDrPrefInt;
    private String perdEventId;
    private String paymentFreqType;
    private String paymentFreqWeekNo;
    private String paymentFreqWeekDay;
    private String paymentFreqStartDate;
    private String paymentFreqNp;
    private String startDate;
    private String endDate;
    private String penalEventId;

    public BigDecimal getAccountCrPrefInt() {
        return accountCrPrefInt;
    }

    public void setAccountCrPrefInt(BigDecimal accountCrPrefInt) {
        this.accountCrPrefInt = accountCrPrefInt;
    }

    public BigDecimal getAccountDrPrefInt() {
        return accountDrPrefInt;
    }

    public void setAccountDrPrefInt(BigDecimal accountDrPrefInt) {
        this.accountDrPrefInt = accountDrPrefInt;
    }

    public CbsSchemeBcfDetailsPKTO getCbsSchemeBcfDetailsPKTO() {
        return cbsSchemeBcfDetailsPKTO;
    }

    public void setCbsSchemeBcfDetailsPKTO(CbsSchemeBcfDetailsPKTO cbsSchemeBcfDetailsPKTO) {
        this.cbsSchemeBcfDetailsPKTO = cbsSchemeBcfDetailsPKTO;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPaymentFreqNp() {
        return paymentFreqNp;
    }

    public void setPaymentFreqNp(String paymentFreqNp) {
        this.paymentFreqNp = paymentFreqNp;
    }

    public String getPaymentFreqStartDate() {
        return paymentFreqStartDate;
    }

    public void setPaymentFreqStartDate(String paymentFreqStartDate) {
        this.paymentFreqStartDate = paymentFreqStartDate;
    }

    public String getPaymentFreqType() {
        return paymentFreqType;
    }

    public void setPaymentFreqType(String paymentFreqType) {
        this.paymentFreqType = paymentFreqType;
    }

    public String getPaymentFreqWeekDay() {
        return paymentFreqWeekDay;
    }

    public void setPaymentFreqWeekDay(String paymentFreqWeekDay) {
        this.paymentFreqWeekDay = paymentFreqWeekDay;
    }

    public String getPaymentFreqWeekNo() {
        return paymentFreqWeekNo;
    }

    public void setPaymentFreqWeekNo(String paymentFreqWeekNo) {
        this.paymentFreqWeekNo = paymentFreqWeekNo;
    }

    public String getPenalEventId() {
        return penalEventId;
    }

    public void setPenalEventId(String penalEventId) {
        this.penalEventId = penalEventId;
    }

    public String getPerdEventId() {
        return perdEventId;
    }

    public void setPerdEventId(String perdEventId) {
        this.perdEventId = perdEventId;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSubvenRevCrPlaceholder() {
        return subvenRevCrPlaceholder;
    }

    public void setSubvenRevCrPlaceholder(String subvenRevCrPlaceholder) {
        this.subvenRevCrPlaceholder = subvenRevCrPlaceholder;
    }
}
