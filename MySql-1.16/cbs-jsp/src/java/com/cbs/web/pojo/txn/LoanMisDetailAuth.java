/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.txn;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class LoanMisDetailAuth implements Serializable{
    private String documentExpDt;
    private String relation;
    private String sancAmt;
    private String drowingPowIndicator;
    private String monthlyIncome;
    private String appliCategory;
    private String categoryOpt;
    private String minorCommunity;
    private String remarks;
    private String auth;

    public String getAppliCategory() {
        return appliCategory;
    }

    public void setAppliCategory(String appliCategory) {
        this.appliCategory = appliCategory;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getCategoryOpt() {
        return categoryOpt;
    }

    public void setCategoryOpt(String categoryOpt) {
        this.categoryOpt = categoryOpt;
    }

    public String getDocumentExpDt() {
        return documentExpDt;
    }

    public void setDocumentExpDt(String documentExpDt) {
        this.documentExpDt = documentExpDt;
    }

    public String getDrowingPowIndicator() {
        return drowingPowIndicator;
    }

    public void setDrowingPowIndicator(String drowingPowIndicator) {
        this.drowingPowIndicator = drowingPowIndicator;
    }

    public String getMinorCommunity() {
        return minorCommunity;
    }

    public void setMinorCommunity(String minorCommunity) {
        this.minorCommunity = minorCommunity;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSancAmt() {
        return sancAmt;
    }

    public void setSancAmt(String sancAmt) {
        this.sancAmt = sancAmt;
    }


}
