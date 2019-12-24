/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;

/**
 *
 * @author Zeeshan Waris
 */
public class HrDatabankPKTO implements Serializable {

    private int compCode;

    private String candId;

    private String advtCode;

    private String jobCode;

    public String getAdvtCode() {
        return advtCode;
    }

    public void setAdvtCode(String advtCode) {
        this.advtCode = advtCode;
    }

    public String getCandId() {
        return candId;
    }

    public void setCandId(String candId) {
        this.candId = candId;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }



}
