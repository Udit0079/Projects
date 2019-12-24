/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Zeeshan Waris
 */
public class HrAdvertDetDtTO implements Serializable {

     private static final long serialVersionUID = 1L;
   
    protected HrAdvertDetDtPKTO hrAdvertDetDtPKTO;
    
    private Integer response;
    
    private Integer pageNo;
   
    private Integer columnNo;
   
    private String languageIn;
   
    private Double cost;
    
    private Integer timeIn;
   
    private Integer duration;
    
    private Integer defaultComp;
    
    private String statFlag;
   
    private String statUpFlag;
    
    private Date modDate;
    
    private String authBy;
   
    private String enteredBy;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Integer getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(Integer columnNo) {
        this.columnNo = columnNo;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public HrAdvertDetDtPKTO getHrAdvertDetDtPKTO() {
        return hrAdvertDetDtPKTO;
    }

    public void setHrAdvertDetDtPKTO(HrAdvertDetDtPKTO hrAdvertDetDtPKTO) {
        this.hrAdvertDetDtPKTO = hrAdvertDetDtPKTO;
    }

    public String getLanguageIn() {
        return languageIn;
    }

    public void setLanguageIn(String languageIn) {
        this.languageIn = languageIn;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
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

    public Integer getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Integer timeIn) {
        this.timeIn = timeIn;
    }



}
