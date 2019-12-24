/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.web.pojo;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class EmpSearchTable implements Serializable {
    private String empidD;
    private String empCode;
    private String empnameD;
    private String empphD;
    private String empaddD;

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    
    public String getEmpaddD() {
        return empaddD;
    }

    public void setEmpaddD(String empaddD) {
        this.empaddD = empaddD;
    }

    public String getEmpidD() {
        return empidD;
    }

    public void setEmpidD(String empidD) {
        this.empidD = empidD;
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


}
