/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 package com.hrms.web.pojo;;

/**
 *
 * @author Administrator
 */
public class AttendenceCallenderGrid {
    private String month;
    private String date;
    private String timein;
    private String timeout;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTimein() {
        return timein;
    }

    public void setTimein(String timein) {
        this.timein = timein;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

}
