/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.admin;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class KccParticularHoldingTable implements Serializable{

    private String sequenceNo;
    private String institution;
    private String purpose;
    private String osAmount;
    private String overDue;
    private String security;
    private String installAmt;

    public String getInstallAmt() {
        return installAmt;
    }

    public void setInstallAmt(String installAmt) {
        this.installAmt = installAmt;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getOsAmount() {
        return osAmount;
    }

    public void setOsAmount(String osAmount) {
        this.osAmount = osAmount;
    }

    public String getOverDue() {
        return overDue;
    }

    public void setOverDue(String overDue) {
        this.overDue = overDue;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    

    

}
