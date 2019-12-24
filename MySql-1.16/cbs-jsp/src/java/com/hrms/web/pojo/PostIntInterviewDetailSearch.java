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
public class PostIntInterviewDetailSearch implements Serializable {
    String intCode;
    String advmntCode;
    String jobdtlCode;
    String candSrno;
    String frstName;
    String midName;
    String lastName;

    public String getAdvmntCode() {
        return advmntCode;
    }

    public void setAdvmntCode(String advmntCode) {
        this.advmntCode = advmntCode;
    }

    public String getJobdtlCode() {
        return jobdtlCode;
    }

    public void setJobdtlCode(String jobdtlCode) {
        this.jobdtlCode = jobdtlCode;
    }

    public String getCandSrno() {
        return candSrno;
    }

    public void setCandSrno(String candSrno) {
        this.candSrno = candSrno;
    }

    


    public String getFrstName() {
        return frstName;
    }

    public void setFrstName(String frstName) {
        this.frstName = frstName;
    }

    public String getIntCode() {
        return intCode;
    }

    public void setIntCode(String intCode) {
        this.intCode = intCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }
}
