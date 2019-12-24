/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.web.pojo;

import java.io.Serializable;

/**
 *
 * @author Zeeshan Waris
 */
public class ExtensionEditTable implements Serializable {
    String intCode;
    String candidateSrno;
    String candName;
    String intDt;
    String intTime;
    String intVenue;
    String desgCode;
    String expectJoin;
    String extension;

    public String getCandName() {
        return candName;
    }

    public void setCandName(String candName) {
        this.candName = candName;
    }

    public String getCandidateSrno() {
        return candidateSrno;
    }

    public void setCandidateSrno(String candidateSrno) {
        this.candidateSrno = candidateSrno;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getExpectJoin() {
        return expectJoin;
    }

    public void setExpectJoin(String expectJoin) {
        this.expectJoin = expectJoin;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getIntCode() {
        return intCode;
    }

    public void setIntCode(String intCode) {
        this.intCode = intCode;
    }

    public String getIntDt() {
        return intDt;
    }

    public void setIntDt(String intDt) {
        this.intDt = intDt;
    }

    public String getIntTime() {
        return intTime;
    }

    public void setIntTime(String intTime) {
        this.intTime = intTime;
    }

    public String getIntVenue() {
        return intVenue;
    }

    public void setIntVenue(String intVenue) {
        this.intVenue = intVenue;
    }
}
