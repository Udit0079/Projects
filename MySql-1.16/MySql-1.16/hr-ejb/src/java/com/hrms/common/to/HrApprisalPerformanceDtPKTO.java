/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class HrApprisalPerformanceDtPKTO implements Serializable {

    private int compCode;

    private long empCode;

    private String perfCode;

    private String ratingCode;

    private String appraisalDt;

    public String getAppraisalDt() {
        return appraisalDt;
    }

    public void setAppraisalDt(String appraisalDt) {
        this.appraisalDt = appraisalDt;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public String getPerfCode() {
        return perfCode;
    }

    public void setPerfCode(String perfCode) {
        this.perfCode = perfCode;
    }

    public String getRatingCode() {
        return ratingCode;
    }

    public void setRatingCode(String ratingCode) {
        this.ratingCode = ratingCode;
    }

    
}
