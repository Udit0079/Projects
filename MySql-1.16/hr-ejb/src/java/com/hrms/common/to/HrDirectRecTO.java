/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Zeeshan Waris
 */
public class HrDirectRecTO implements Serializable{
     private static final long serialVersionUID = 1L;

    protected HrDirectRecPKTO hrDirectRecPKTO;

    private Date ardate;

    private String zoneCode;

    private String desigCode;

    private String locationCode;

    private long candCode;

    private String candName;

    private String fatherName;

    private Date appointmentDate;

    private String contactNo;

    private Integer basicSalary;

    private Integer hra;

    private Integer ta;

    private Integer medicalAllw;

    private Integer total;

    private String address;

    private String city;

    private String state;

    private String pin;

    private String emailId;

    private Character jobStatus;

    private String remarks;

    private Date effectiveDate;

    private String qualCode;

    private BigInteger superId;

    private BigInteger initiatorId;

    private BigInteger deptHeadId;

    private Character hrdApproval;

    private Character mdApproval;

    private String statFlag;

    private String statUpFlag;

    private Date modDate;

    private int defaultComp;

    private String authBy;

    private String enteredBy;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getArdate() {
        return ardate;
    }

    public void setArdate(Date ardate) {
        this.ardate = ardate;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Integer getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Integer basicSalary) {
        this.basicSalary = basicSalary;
    }

    public long getCandCode() {
        return candCode;
    }

    public void setCandCode(long candCode) {
        this.candCode = candCode;
    }

    public String getCandName() {
        return candName;
    }

    public void setCandName(String candName) {
        this.candName = candName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public BigInteger getDeptHeadId() {
        return deptHeadId;
    }

    public void setDeptHeadId(BigInteger deptHeadId) {
        this.deptHeadId = deptHeadId;
    }

    public String getDesigCode() {
        return desigCode;
    }

    public void setDesigCode(String desigCode) {
        this.desigCode = desigCode;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public HrDirectRecPKTO getHrDirectRecPKTO() {
        return hrDirectRecPKTO;
    }

    public void setHrDirectRecPKTO(HrDirectRecPKTO hrDirectRecPKTO) {
        this.hrDirectRecPKTO = hrDirectRecPKTO;
    }

    public Integer getHra() {
        return hra;
    }

    public void setHra(Integer hra) {
        this.hra = hra;
    }

    public Character getHrdApproval() {
        return hrdApproval;
    }

    public void setHrdApproval(Character hrdApproval) {
        this.hrdApproval = hrdApproval;
    }

    public BigInteger getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(BigInteger initiatorId) {
        this.initiatorId = initiatorId;
    }

    public Character getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Character jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Character getMdApproval() {
        return mdApproval;
    }

    public void setMdApproval(Character mdApproval) {
        this.mdApproval = mdApproval;
    }

    public Integer getMedicalAllw() {
        return medicalAllw;
    }

    public void setMedicalAllw(Integer medicalAllw) {
        this.medicalAllw = medicalAllw;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getQualCode() {
        return qualCode;
    }

    public void setQualCode(String qualCode) {
        this.qualCode = qualCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigInteger getSuperId() {
        return superId;
    }

    public void setSuperId(BigInteger superId) {
        this.superId = superId;
    }

    public Integer getTa() {
        return ta;
    }

    public void setTa(Integer ta) {
        this.ta = ta;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    

}
