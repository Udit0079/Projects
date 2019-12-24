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
public class DirectRecruitmentViewTable implements Serializable{
    String companyCode;
    String arNo;
    String candidateName;
    String fatherName;
    String arDate;

    public String getArDate() {
        return arDate;
    }

    public void setArDate(String arDate) {
        this.arDate = arDate;
    }

    public String getArNo() {
        return arNo;
    }

    public void setArNo(String arNo) {
        this.arNo = arNo;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    

}
