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
public class HrInterviewHdTO implements Serializable{
 private static final long serialVersionUID = 1L;

    protected HrInterviewHdPKTO hrInterviewHdPKTO;

    private String desgCode;

    private Date intDate;

    private Date intTime;

    private Integer timePerCand;

    private Date timeBreak;

    private String fareCatg;

    private String intVenue;

    private String empCode;

    private String empCode2;

    private String emp1Desg;

    private String emp2Desg;

    private String recType;

    private Integer defaultCompcode;

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

    public Integer getDefaultCompcode() {
        return defaultCompcode;
    }

    public void setDefaultCompcode(Integer defaultCompcode) {
        this.defaultCompcode = defaultCompcode;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getEmp1Desg() {
        return emp1Desg;
    }

    public void setEmp1Desg(String emp1Desg) {
        this.emp1Desg = emp1Desg;
    }

    public String getEmp2Desg() {
        return emp2Desg;
    }

    public void setEmp2Desg(String emp2Desg) {
        this.emp2Desg = emp2Desg;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpCode2() {
        return empCode2;
    }

    public void setEmpCode2(String empCode2) {
        this.empCode2 = empCode2;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getFareCatg() {
        return fareCatg;
    }

    public void setFareCatg(String fareCatg) {
        this.fareCatg = fareCatg;
    }

    public HrInterviewHdPKTO getHrInterviewHdPKTO() {
        return hrInterviewHdPKTO;
    }

    public void setHrInterviewHdPKTO(HrInterviewHdPKTO hrInterviewHdPKTO) {
        this.hrInterviewHdPKTO = hrInterviewHdPKTO;
    }

    public Date getIntDate() {
        return intDate;
    }

    public void setIntDate(Date intDate) {
        this.intDate = intDate;
    }

    public Date getIntTime() {
        return intTime;
    }

    public void setIntTime(Date intTime) {
        this.intTime = intTime;
    }

    public String getIntVenue() {
        return intVenue;
    }

    public void setIntVenue(String intVenue) {
        this.intVenue = intVenue;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getRecType() {
        return recType;
    }

    public void setRecType(String recType) {
        this.recType = recType;
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

    public Date getTimeBreak() {
        return timeBreak;
    }

    public void setTimeBreak(Date timeBreak) {
        this.timeBreak = timeBreak;
    }

    public Integer getTimePerCand() {
        return timePerCand;
    }

    public void setTimePerCand(Integer timePerCand) {
        this.timePerCand = timePerCand;
    }

    
}
