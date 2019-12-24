/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class HrSlabMasterTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected HrSlabMasterPKTO hrSlabMasterPK;
    private String slabCriteria;
    private double slabCriteriaAmt;
    private Character appFlg;
    private Integer defaultComp;
    private String statFlag;
    private String statUpFlag;
    private Date modDate;
    private String authBy;
    private String enteredBy;
    private double slabCriteriaMaxAmt; 
    private String alDesc;
    private double rangeFrom;
    private double rangeTo;
    private String baseComponent;
    private String depnComponent;
    private String desgId;

      
 // aditya add
    private double slabCriteriaMinAmt ;
    private String  purpose;
    private String description;
    private String componentOrder;
    private String nature;
    private String startRange;
    private String endRange;

    public HrSlabMasterPKTO getHrSlabMasterPK() {
        return hrSlabMasterPK;
    }

    public void setHrSlabMasterPK(HrSlabMasterPKTO hrSlabMasterPK) {
        this.hrSlabMasterPK = hrSlabMasterPK;
    }

    public String getSlabCriteria() {
        return slabCriteria;
    }

    public void setSlabCriteria(String slabCriteria) {
        this.slabCriteria = slabCriteria;
    }

    public double getSlabCriteriaAmt() {
        return slabCriteriaAmt;
    }

    public void setSlabCriteriaAmt(double slabCriteriaAmt) {
        this.slabCriteriaAmt = slabCriteriaAmt;
    }

    public Character getAppFlg() {
        return appFlg;
    }

    public void setAppFlg(Character appFlg) {
        this.appFlg = appFlg;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public double getSlabCriteriaMaxAmt() {
        return slabCriteriaMaxAmt;
    }

    public void setSlabCriteriaMaxAmt(double slabCriteriaMaxAmt) {
        this.slabCriteriaMaxAmt = slabCriteriaMaxAmt;
    }

    public double getSlabCriteriaMinAmt() {
        return slabCriteriaMinAmt;
    }

    public void setSlabCriteriaMinAmt(double slabCriteriaMinAmt) {
        this.slabCriteriaMinAmt = slabCriteriaMinAmt;
    }
         
    public String getAlDesc() {
        return alDesc;
    }

    public void setAlDesc(String alDesc) {
        this.alDesc = alDesc;
    }

    public double getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(double rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public double getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(double rangeTo) {
        this.rangeTo = rangeTo;
    }

    public String getBaseComponent() {
        return baseComponent;
    }

    public void setBaseComponent(String baseComponent) {
        this.baseComponent = baseComponent;
    }

    public String getDepnComponent() {
        return depnComponent;
    }

    public void setDepnComponent(String depnComponent) {
        this.depnComponent = depnComponent;
    }

    public String getDesgId() {
        return desgId;
    }

    public void setDesgId(String desgId) {
        this.desgId = desgId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComponentOrder() {
        return componentOrder;
    }

    public void setComponentOrder(String componentOrder) {
        this.componentOrder = componentOrder;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getStartRange() {
        return startRange;
    }

    public void setStartRange(String startRange) {
        this.startRange = startRange;
    }

    public String getEndRange() {
        return endRange;
    }

    public void setEndRange(String endRange) {
        this.endRange = endRange;
    }

}
