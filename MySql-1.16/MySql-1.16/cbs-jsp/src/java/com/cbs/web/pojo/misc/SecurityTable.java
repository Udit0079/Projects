/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.misc;

/**
 *
 * @author root
 */
public class SecurityTable {

    String sno;
    String securityType;
    String matValue;
    String matDate;
    String lienValue;
    String status;
    String issueDate;

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getLienValue() {
        return lienValue;
    }

    public void setLienValue(String lienValue) {
        this.lienValue = lienValue;
    }

    public String getMatDate() {
        return matDate;
    }

    public void setMatDate(String matDate) {
        this.matDate = matDate;
    }

    public String getMatValue() {
        return matValue;
    }

    public void setMatValue(String matValue) {
        this.matValue = matValue;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
