/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.web.controller.masters;

import java.io.Serializable;

/**
 *
 * @author Dhirendra Singh
 */
public class FinYearDetail implements Serializable{

    private String fromDate;

    private String toDate;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromdate) {
        this.fromDate = fromdate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String todate) {
        this.toDate = todate;
    }

}
