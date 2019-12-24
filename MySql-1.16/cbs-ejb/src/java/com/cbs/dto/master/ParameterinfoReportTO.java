/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.master;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class ParameterinfoReportTO implements Serializable {

    private String reportName;
    private Short code;

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
