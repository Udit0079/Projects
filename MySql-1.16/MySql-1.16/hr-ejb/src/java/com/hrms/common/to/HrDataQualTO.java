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
public class HrDataQualTO implements Serializable {
     private static final long serialVersionUID = 1L;

    protected HrDataQualPKTO hrDataQualPKTO;

    private String instName;

    private int year;

    private String subName;

    private String special;

    private Double perMarks;

    private String div;

    private Integer defaultCompCode;

    private String statFlag;

    private String statUpFlag;


    private Date modDate;

    private String authBy;

    private String enteredBy;

    private HrDatabankTO hrDatabankTO;

    private HrMstStructTO hrMstStructTO;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Integer getDefaultCompCode() {
        return defaultCompCode;
    }

    public void setDefaultCompCode(Integer defaultCompCode) {
        this.defaultCompCode = defaultCompCode;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public HrDataQualPKTO getHrDataQualPKTO() {
        return hrDataQualPKTO;
    }

    public void setHrDataQualPKTO(HrDataQualPKTO hrDataQualPKTO) {
        this.hrDataQualPKTO = hrDataQualPKTO;
    }

    public HrDatabankTO getHrDatabankTO() {
        return hrDatabankTO;
    }

    public void setHrDatabankTO(HrDatabankTO hrDatabankTO) {
        this.hrDatabankTO = hrDatabankTO;
    }

    public HrMstStructTO getHrMstStructTO() {
        return hrMstStructTO;
    }

    public void setHrMstStructTO(HrMstStructTO hrMstStructTO) {
        this.hrMstStructTO = hrMstStructTO;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public Double getPerMarks() {
        return perMarks;
    }

    public void setPerMarks(Double perMarks) {
        this.perMarks = perMarks;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
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

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    

}
