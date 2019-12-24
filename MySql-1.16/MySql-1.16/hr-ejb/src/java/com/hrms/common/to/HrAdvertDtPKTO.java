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
public class HrAdvertDtPKTO implements Serializable{

    private int compCode;

    private String advtCode;

    private String jobCode;

    private char mediaType;

    private String desgCode;

    private String deptCode;

    private String locatCode;

    public String getAdvtCode() {
        return advtCode;
    }

    public void setAdvtCode(String advtCode) {
        this.advtCode = advtCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getLocatCode() {
        return locatCode;
    }

    public void setLocatCode(String locatCode) {
        this.locatCode = locatCode;
    }

    public char getMediaType() {
        return mediaType;
    }

    public void setMediaType(char mediaType) {
        this.mediaType = mediaType;
    }

}
