/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Athar Reza
 */
public class AreaWiseSharePojo implements Serializable{
    private String folioNo;
    private String custName;
    private String fatherName;
    private String dob;
    private String permAdd;
    private String corresspndAdd;
    private String custId;
    private String area;

   
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCorresspndAdd() {
        return corresspndAdd;
    }

    public void setCorresspndAdd(String corresspndAdd) {
        this.corresspndAdd = corresspndAdd;
    }

    public String getPermAdd() {
        return permAdd;
    }

    public void setPermAdd(String permAdd) {
        this.permAdd = permAdd;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
}
