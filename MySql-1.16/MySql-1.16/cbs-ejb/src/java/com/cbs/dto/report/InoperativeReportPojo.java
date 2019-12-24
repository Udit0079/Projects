/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author sipl
 */
public class InoperativeReportPojo implements Serializable {
    private String accNo;
    private String custName;
    private String date;
    private BigDecimal closeBal;
    private BigDecimal closeBalAsOn;

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public BigDecimal getCloseBal() {
        return closeBal;
    }

    public void setCloseBal(BigDecimal closeBal) {
        this.closeBal = closeBal;
    }

    public BigDecimal getCloseBalAsOn() {
        return closeBalAsOn;
    }

    public void setCloseBalAsOn(BigDecimal closeBalAsOn) {
        this.closeBalAsOn = closeBalAsOn;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }    
}