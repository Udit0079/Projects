/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  14 JUNE 2011
 */
package com.hrms.web.pojo;

import java.io.Serializable;



/**
 *
 * @author ROHIT KRISHNA
 */
public class ProgrmDetailsGrid implements Serializable{

    public String progName;
    public String progFrom;
    public String progTo;
    public String totalCost;
    public String facultyName;
    public String compCode;
    public String progCode;
    public String instCode;
    public String inextHouse;

    public String getInextHouse() {
        return inextHouse;
    }

    public void setInextHouse(String inextHouse) {
        this.inextHouse = inextHouse;
    }
    
    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
    
    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    public String getProgName() {
        return progName;
    }

    public void setProgName(String progName) {
        this.progName = progName;
    }

    public String getProgFrom() {
        return progFrom;
    }

    public void setProgFrom(String progFrom) {
        this.progFrom = progFrom;
    }

    public String getProgTo() {
        return progTo;
    }

    public void setProgTo(String progTo) {
        this.progTo = progTo;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

       
}
