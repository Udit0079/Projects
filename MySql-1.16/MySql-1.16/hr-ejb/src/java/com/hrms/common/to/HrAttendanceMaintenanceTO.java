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
public class HrAttendanceMaintenanceTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected HrAttendanceMaintenancePKTO hrAttendanceMaintenancePKTO;
    private Date timeIn;
    private Date timeOut;
    private char attenStatusFixed;
    private Integer defaultComp;
    private String statFlag;
    private String statUpFlag;
    private Date modDate;
    private String authBy;
    private String enteredBy;
    private HrPersonnelDetailsTO hrPersonnelDetailsTO;

    public HrPersonnelDetailsTO getHrPersonnelDetailsTO() {
        return hrPersonnelDetailsTO;
    }

    public void setHrPersonnelDetailsTO(HrPersonnelDetailsTO hrPersonnelDetailsTO) {
        this.hrPersonnelDetailsTO = hrPersonnelDetailsTO;
    }

    public char getAttenStatusFixed() {
        return attenStatusFixed;
    }

    public void setAttenStatusFixed(char attenStatusFixed) {
        this.attenStatusFixed = attenStatusFixed;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public HrAttendanceMaintenancePKTO getHrAttendanceMaintenancePKTO() {
        return hrAttendanceMaintenancePKTO;
    }

    public void setHrAttendanceMaintenancePKTO(HrAttendanceMaintenancePKTO hrAttendanceMaintenancePKTO) {
        this.hrAttendanceMaintenancePKTO = hrAttendanceMaintenancePKTO;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
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

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }
}
