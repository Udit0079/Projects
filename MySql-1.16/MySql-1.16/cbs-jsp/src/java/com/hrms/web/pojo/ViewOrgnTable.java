/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.web.pojo;

import java.io.Serializable;

/**
 *
 * @author Zeeshan Waris
 */
public class ViewOrgnTable implements Serializable{
    String empName;
    String desgcode;
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesgcode() {
        return desgcode;
    }

    public void setDesgcode(String desgcode) {
        this.desgcode = desgcode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
    
}
