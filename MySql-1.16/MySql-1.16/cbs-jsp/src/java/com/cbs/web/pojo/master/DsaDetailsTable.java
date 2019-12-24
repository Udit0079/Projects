/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.master;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class DsaDetailsTable implements Serializable {

    String schemeType;
    String perdEventId;
    String acCrPrefInt;
    String acDrPrefInt;
    String subvenRevCrPlaceholder;
    String paymentFreqType;
    String paymentFreqWeekNo;
    String paymentFreqWeekDay;
    String paymentFreqStartDate;
    String paymentFreqNP;
    String startDate;
    String endDate;
    String penalEventId;
    String fixedChargeEventId;
    String counterSaveUpdate;

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getAcCrPrefInt() {
        return acCrPrefInt;
    }

    public void setAcCrPrefInt(String acCrPrefInt) {
        this.acCrPrefInt = acCrPrefInt;
    }

    public String getAcDrPrefInt() {
        return acDrPrefInt;
    }

    public void setAcDrPrefInt(String acDrPrefInt) {
        this.acDrPrefInt = acDrPrefInt;
    }

    public String getSubvenRevCrPlaceholder() {
        return subvenRevCrPlaceholder;
    }

    public void setSubvenRevCrPlaceholder(String subvenRevCrPlaceholder) {
        this.subvenRevCrPlaceholder = subvenRevCrPlaceholder;
    }

    public String getCounterSaveUpdate() {
        return counterSaveUpdate;
    }

    public void setCounterSaveUpdate(String counterSaveUpdate) {
        this.counterSaveUpdate = counterSaveUpdate;
    }

    public String getFixedChargeEventId() {
        return fixedChargeEventId;
    }

    public void setFixedChargeEventId(String fixedChargeEventId) {
        this.fixedChargeEventId = fixedChargeEventId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getPaymentFreqNP() {
        return paymentFreqNP;
    }

    public void setPaymentFreqNP(String paymentFreqNP) {
        this.paymentFreqNP = paymentFreqNP;
    }

    public String getPaymentFreqStartDate() {
        return paymentFreqStartDate;
    }

    public void setPaymentFreqStartDate(String paymentFreqStartDate) {
        this.paymentFreqStartDate = paymentFreqStartDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
