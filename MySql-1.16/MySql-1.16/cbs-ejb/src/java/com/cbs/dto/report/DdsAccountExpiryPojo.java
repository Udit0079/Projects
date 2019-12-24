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
public class DdsAccountExpiryPojo implements Serializable {

    private String agCode;
    private String agName;
    private String acNo;
    private String custName;
    private String dateOfExp;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAgCode() {
        return agCode;
    }

    public void setAgCode(String agCode) {
        this.agCode = agCode;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDateOfExp() {
        return dateOfExp;
    }

    public void setDateOfExp(String dateOfExp) {
        this.dateOfExp = dateOfExp;
    }
}
