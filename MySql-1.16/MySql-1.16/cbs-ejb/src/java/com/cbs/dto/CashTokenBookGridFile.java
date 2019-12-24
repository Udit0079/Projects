/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  11 AUGUST 2011
 */

package com.cbs.dto;

/**
 *
 * @author ROHIT KRISHNA
 */
public class CashTokenBookGridFile {
    public int srNo;
    public String tokenNo;
    public String subTokenNo;
    public String acno;
    public String custName;
    public String jointName;
    public String amount;
    public String enterBy;
    public String authBy;
    public String tokenPaid;
    public String recNo;
    public String orgBrnId;
    public String destBrnId;

    public String getDestBrnId() {
        return destBrnId;
    }

    public void setDestBrnId(String destBrnId) {
        this.destBrnId = destBrnId;
    }

    public String getOrgBrnId() {
        return orgBrnId;
    }

    public void setOrgBrnId(String orgBrnId) {
        this.orgBrnId = orgBrnId;
    }
    
    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getRecNo() {
        return recNo;
    }

    public void setRecNo(String recNo) {
        this.recNo = recNo;
    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getSubTokenNo() {
        return subTokenNo;
    }

    public void setSubTokenNo(String subTokenNo) {
        this.subTokenNo = subTokenNo;
    }

    public String getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
        this.tokenNo = tokenNo;
    }

    public String getTokenPaid() {
        return tokenPaid;
    }

    public void setTokenPaid(String tokenPaid) {
        this.tokenPaid = tokenPaid;
    }

    public String getJointName() {
        return jointName;
    }

    public void setJointName(String jointName) {
        this.jointName = jointName;
    }
       
}
