/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.web.pojo;

import java.io.Serializable;


/**
 *
 * @author Administrator
 */
public class LeaveTableGrid implements Serializable{
private String empidD;
private String empnameD;
private String empaddD;
private String empphD;
private String fromdate;
private String todate;
private String leavecode;
private String postingdate;
private String empid;
private String empname;

    public LeaveTableGrid() {
    }



    public String getEmpaddD() {
        return empaddD;
    }

    public void setEmpaddD(String empaddD) {
        this.empaddD = empaddD;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpidD() {
        return empidD;
    }

    public void setEmpidD(String empidD) {
        this.empidD = empidD;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getEmpnameD() {
        return empnameD;
    }

    public void setEmpnameD(String empnameD) {
        this.empnameD = empnameD;
    }

    public String getEmpphD() {
        return empphD;
    }

    public void setEmpphD(String empphD) {
        this.empphD = empphD;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getLeavecode() {
        return leavecode;
    }

    public void setLeavecode(String leavecode) {
        this.leavecode = leavecode;
    }

    public String getPostingdate() {
        return postingdate;
    }

    public void setPostingdate(String postingdate) {
        this.postingdate = postingdate;
    }


   public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

}
