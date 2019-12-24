/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.ho;

import java.io.Serializable;

public class GridReportingFriday implements Serializable {

    private int serial;
    private String reportingFriday;
    private double netLiabilities;
    private String enterBy;

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public double getNetLiabilities() {
        return netLiabilities;
    }

    public void setNetLiabilities(double netLiabilities) {
        this.netLiabilities = netLiabilities;
    }

    public String getReportingFriday() {
        return reportingFriday;
    }

    public void setReportingFriday(String reportingFriday) {
        this.reportingFriday = reportingFriday;
    }
}