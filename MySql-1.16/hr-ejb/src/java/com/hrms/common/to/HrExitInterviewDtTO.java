/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author admin
 */
public class HrExitInterviewDtTO implements Serializable{
    protected HrExitInterviewDtPKTO hrExitInterviewDtPK;
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

    public HrExitInterviewDtPKTO getHrExitInterviewDtPK() {
        return hrExitInterviewDtPK;
    }

    public void setHrExitInterviewDtPK(HrExitInterviewDtPKTO hrExitInterviewDtPK) {
        this.hrExitInterviewDtPK = hrExitInterviewDtPK;
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
    

}
