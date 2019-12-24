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
public class HrPersonnelReferenceTO implements Serializable {
 private static final long serialVersionUID = 1L;

    protected HrPersonnelReferencePKTO hrPersonnelReferencePKTO;

    private String refName;

    private String refAdd;

    private String refPin;

    private String refCity;

    private String refState;

    private String refPhone;

    private String refOcc;

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

    public HrPersonnelDetailsTO getHrPersonnelDetailsTO() {
        return hrPersonnelDetailsTO;
    }

    public void setHrPersonnelDetailsTO(HrPersonnelDetailsTO hrPersonnelDetailsTO) {
        this.hrPersonnelDetailsTO = hrPersonnelDetailsTO;
    }

    public HrPersonnelReferencePKTO getHrPersonnelReferencePKTO() {
        return hrPersonnelReferencePKTO;
    }

    public void setHrPersonnelReferencePKTO(HrPersonnelReferencePKTO hrPersonnelReferencePKTO) {
        this.hrPersonnelReferencePKTO = hrPersonnelReferencePKTO;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getRefAdd() {
        return refAdd;
    }

    public void setRefAdd(String refAdd) {
        this.refAdd = refAdd;
    }

    public String getRefCity() {
        return refCity;
    }

    public void setRefCity(String refCity) {
        this.refCity = refCity;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefOcc() {
        return refOcc;
    }

    public void setRefOcc(String refOcc) {
        this.refOcc = refOcc;
    }

    public String getRefPhone() {
        return refPhone;
    }

    public void setRefPhone(String refPhone) {
        this.refPhone = refPhone;
    }

    public String getRefPin() {
        return refPin;
    }

    public void setRefPin(String refPin) {
        this.refPin = refPin;
    }

    public String getRefState() {
        return refState;
    }

    public void setRefState(String refState) {
        this.refState = refState;
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

}
