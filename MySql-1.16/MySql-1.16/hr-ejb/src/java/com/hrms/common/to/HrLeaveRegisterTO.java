/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author stellar
 */
public class HrLeaveRegisterTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected HrLeaveRegisterPKTO hrLeaveRegisterPK;
    private String fromDate;
    private String toDate;
    private String leaveCode;
    private int daysTaken;
    private String reasLeave;
    private Character paid;
    private String remarks;
    private char departRecom;
    private String sanctAuth;
    private Integer defaultComp;
    private String statFlag;
    private String statUpFlag;
    private Date modDate;
    private String authBy;
    private String enteredBy;
    private HrPersonnelDetailsTO hrPersonnelDetailsTO;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public int getDaysTaken() {
        return daysTaken;
    }

    public void setDaysTaken(int daysTaken) {
        this.daysTaken = daysTaken;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public char getDepartRecom() {
        return departRecom;
    }

    public void setDepartRecom(char departRecom) {
        this.departRecom = departRecom;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public HrLeaveRegisterPKTO getHrLeaveRegisterPK() {
        return hrLeaveRegisterPK;
    }

    public void setHrLeaveRegisterPK(HrLeaveRegisterPKTO hrLeaveRegisterPK) {
        this.hrLeaveRegisterPK = hrLeaveRegisterPK;
    }

    public HrPersonnelDetailsTO getHrPersonnelDetailsTO() {
        return hrPersonnelDetailsTO;
    }

    public void setHrPersonnelDetailsTO(HrPersonnelDetailsTO hrPersonnelDetailsTO) {
        this.hrPersonnelDetailsTO = hrPersonnelDetailsTO;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public Character getPaid() {
        return paid;
    }

    public void setPaid(Character paid) {
        this.paid = paid;
    }

    public String getReasLeave() {
        return reasLeave;
    }

    public void setReasLeave(String reasLeave) {
        this.reasLeave = reasLeave;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSanctAuth() {
        return sanctAuth;
    }

    public void setSanctAuth(String sanctAuth) {
        this.sanctAuth = sanctAuth;
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

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
