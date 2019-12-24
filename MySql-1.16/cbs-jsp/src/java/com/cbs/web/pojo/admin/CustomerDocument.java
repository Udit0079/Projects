/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.admin;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Dhirendra Singh
 */
public class CustomerDocument  implements Serializable{

    private String custId;

    private String name;

    private String dob;

    private String gender;

    private String location;

    private String customerImgName;

    private String addProofImgName;

    private String idProofImgName;

    private String enrollment;

    private String enrollmentDate;

    private String sent = "FALSE";

    private String accountId = "";

    private ArrayList<String> fingerPrintImgName = new ArrayList<String>();

    public ArrayList<String> getFingerPrintImgName() {
        return fingerPrintImgName;
    }

    public void setFingerPrintImgName(ArrayList<String> fingerPrintImgName) {
        this.fingerPrintImgName = fingerPrintImgName;
    }
    

    public String getAddProofImgName() {
        return addProofImgName;
    }

    public void setAddProofImgName(String addProofImgName) {
        this.addProofImgName = addProofImgName;
    }

    public String getCustomerImgName() {
        return customerImgName;
    }

    public void setCustomerImgName(String customerImgName) {
        this.customerImgName = customerImgName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    
    public String getIdProofImgName() {
        return idProofImgName;
    }

    public void setIdProofImgName(String idProofImgName) {
        this.idProofImgName = idProofImgName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }
}
