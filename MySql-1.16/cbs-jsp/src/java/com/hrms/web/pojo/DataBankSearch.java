/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Zeeshan Waris
 */
public class DataBankSearch implements Serializable {

    String compcode;
    String candCode;
    String candId;
    String advtCode;
    String joCode;
    String firstName;
    String midName;
    String lastname;
    String contAddress;
    String contPin;
    String contCity;
    String contState;
    String perAddress;
    String perPin;
    String perCity;
    String permState;
    String offTel;
    String offFax;
    String offMail;
    String restel;
    String resFax;
    String resMail;
    Date dateOfBirth;
    String sex;
    String specialCode1;
    String specialCode2;
    String totExpr;
    String currExpr;
    String autoExpr;
    String postApplied1;
    String postApplied2;
    String zone;
    String locataCode;
    String desgCode;
    String conscode;
    String currSalary;
    String currDesg;
    String currCName;
    String compAdd1;
    String compAdd2;
    String compcity;
    String compState;
    String compPin;
    String expSalary;
    String joinSalary;
    String JoinDesg;
    String serviceLen;
    String Status;
    String orgType;
    String skillCode;
    String freeDay;
    String nation;
    String relagion;
    String marital;
    String bloodGroup;
    String joiningPeriod;
    String passportNo;
    Date passportDt;
    String visaDet;
    String fatherName;
    String fatherOccupation;
    String fatherDesg;
    String fatherOrg;
    String fatherPhone;
    String profMem;
    String indivMember;
    String callInt;
    String prevEmp;
    String prevEmpcode;
    Date durfrom;
    Date durTo;
    String unitName;
    String prevDeptCode;
    String prevDesgcode;
    String salaryDrawn;
    String reasonLeave;
    String evalflag;
    String dataTransfer;

    public String getJoinDesg() {
        return JoinDesg;
    }

    public void setJoinDesg(String JoinDesg) {
        this.JoinDesg = JoinDesg;
    }

    public String getAdvtCode() {
        return advtCode;
    }

    public void setAdvtCode(String advtCode) {
        this.advtCode = advtCode;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCallInt() {
        return callInt;
    }

    public void setCallInt(String callInt) {
        this.callInt = callInt;
    }

    public String getCandCode() {
        return candCode;
    }

    public void setCandCode(String candCode) {
        this.candCode = candCode;
    }

    public String getCandId() {
        return candId;
    }

    public void setCandId(String candId) {
        this.candId = candId;
    }

    public String getCompAdd1() {
        return compAdd1;
    }

    public void setCompAdd1(String compAdd1) {
        this.compAdd1 = compAdd1;
    }

    public String getCompAdd2() {
        return compAdd2;
    }

    public void setCompAdd2(String compAdd2) {
        this.compAdd2 = compAdd2;
    }

    public String getCompPin() {
        return compPin;
    }

    public void setCompPin(String compPin) {
        this.compPin = compPin;
    }

    public String getCompState() {
        return compState;
    }

    public void setCompState(String compState) {
        this.compState = compState;
    }

    public String getCompcity() {
        return compcity;
    }

    public void setCompcity(String compcity) {
        this.compcity = compcity;
    }

    public String getCompcode() {
        return compcode;
    }

    public void setCompcode(String compcode) {
        this.compcode = compcode;
    }

    public String getConscode() {
        return conscode;
    }

    public void setConscode(String conscode) {
        this.conscode = conscode;
    }

    public String getContAddress() {
        return contAddress;
    }

    public void setContAddress(String contAddress) {
        this.contAddress = contAddress;
    }

    public String getContCity() {
        return contCity;
    }

    public void setContCity(String contCity) {
        this.contCity = contCity;
    }

    public String getContPin() {
        return contPin;
    }

    public void setContPin(String contPin) {
        this.contPin = contPin;
    }

    public String getContState() {
        return contState;
    }

    public void setContState(String contState) {
        this.contState = contState;
    }

    public String getCurrCName() {
        return currCName;
    }

    public void setCurrCName(String currCName) {
        this.currCName = currCName;
    }

    public String getCurrDesg() {
        return currDesg;
    }

    public void setCurrDesg(String currDesg) {
        this.currDesg = currDesg;
    }

    public String getDataTransfer() {
        return dataTransfer;
    }

    public void setDataTransfer(String dataTransfer) {
        this.dataTransfer = dataTransfer;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public Date getDurTo() {
        return durTo;
    }

    public void setDurTo(Date durTo) {
        this.durTo = durTo;
    }

    public Date getDurfrom() {
        return durfrom;
    }

    public void setDurfrom(Date durfrom) {
        this.durfrom = durfrom;
    }

    public String getEvalflag() {
        return evalflag;
    }

    public void setEvalflag(String evalflag) {
        this.evalflag = evalflag;
    }

    public String getFatherDesg() {
        return fatherDesg;
    }

    public void setFatherDesg(String fatherDesg) {
        this.fatherDesg = fatherDesg;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherOccupation() {
        return fatherOccupation;
    }

    public void setFatherOccupation(String fatherOccupation) {
        this.fatherOccupation = fatherOccupation;
    }

    public String getFatherOrg() {
        return fatherOrg;
    }

    public void setFatherOrg(String fatherOrg) {
        this.fatherOrg = fatherOrg;
    }

    public String getFatherPhone() {
        return fatherPhone;
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFreeDay() {
        return freeDay;
    }

    public void setFreeDay(String freeDay) {
        this.freeDay = freeDay;
    }

    public String getIndivMember() {
        return indivMember;
    }

    public void setIndivMember(String indivMember) {
        this.indivMember = indivMember;
    }

    public String getJoCode() {
        return joCode;
    }

    public void setJoCode(String joCode) {
        this.joCode = joCode;
    }

    public String getJoiningPeriod() {
        return joiningPeriod;
    }

    public void setJoiningPeriod(String joiningPeriod) {
        this.joiningPeriod = joiningPeriod;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLocataCode() {
        return locataCode;
    }

    public void setLocataCode(String locataCode) {
        this.locataCode = locataCode;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getOffFax() {
        return offFax;
    }

    public void setOffFax(String offFax) {
        this.offFax = offFax;
    }

    public String getOffMail() {
        return offMail;
    }

    public void setOffMail(String offMail) {
        this.offMail = offMail;
    }

    public String getOffTel() {
        return offTel;
    }

    public void setOffTel(String offTel) {
        this.offTel = offTel;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Date getPassportDt() {
        return passportDt;
    }

    public void setPassportDt(Date passportDt) {
        this.passportDt = passportDt;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPerAddress() {
        return perAddress;
    }

    public void setPerAddress(String perAddress) {
        this.perAddress = perAddress;
    }

    public String getPerCity() {
        return perCity;
    }

    public void setPerCity(String perCity) {
        this.perCity = perCity;
    }

    public String getPerPin() {
        return perPin;
    }

    public void setPerPin(String perPin) {
        this.perPin = perPin;
    }

    public String getPermState() {
        return permState;
    }

    public void setPermState(String permState) {
        this.permState = permState;
    }

    public String getPostApplied1() {
        return postApplied1;
    }

    public void setPostApplied1(String postApplied1) {
        this.postApplied1 = postApplied1;
    }

    public String getPostApplied2() {
        return postApplied2;
    }

    public void setPostApplied2(String postApplied2) {
        this.postApplied2 = postApplied2;
    }

    public String getPrevDeptCode() {
        return prevDeptCode;
    }

    public void setPrevDeptCode(String prevDeptCode) {
        this.prevDeptCode = prevDeptCode;
    }

    public String getPrevDesgcode() {
        return prevDesgcode;
    }

    public void setPrevDesgcode(String prevDesgcode) {
        this.prevDesgcode = prevDesgcode;
    }

    public String getPrevEmp() {
        return prevEmp;
    }

    public void setPrevEmp(String prevEmp) {
        this.prevEmp = prevEmp;
    }

    public String getPrevEmpcode() {
        return prevEmpcode;
    }

    public void setPrevEmpcode(String prevEmpcode) {
        this.prevEmpcode = prevEmpcode;
    }

    public String getProfMem() {
        return profMem;
    }

    public void setProfMem(String profMem) {
        this.profMem = profMem;
    }

    public String getReasonLeave() {
        return reasonLeave;
    }

    public void setReasonLeave(String reasonLeave) {
        this.reasonLeave = reasonLeave;
    }

    public String getRelagion() {
        return relagion;
    }

    public void setRelagion(String relagion) {
        this.relagion = relagion;
    }

    public String getResFax() {
        return resFax;
    }

    public void setResFax(String resFax) {
        this.resFax = resFax;
    }

    public String getResMail() {
        return resMail;
    }

    public void setResMail(String resMail) {
        this.resMail = resMail;
    }

    public String getRestel() {
        return restel;
    }

    public void setRestel(String restel) {
        this.restel = restel;
    }

    public String getSalaryDrawn() {
        return salaryDrawn;
    }

    public void setSalaryDrawn(String salaryDrawn) {
        this.salaryDrawn = salaryDrawn;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
    }

    public String getSpecialCode1() {
        return specialCode1;
    }

    public void setSpecialCode1(String specialCode1) {
        this.specialCode1 = specialCode1;
    }

    public String getSpecialCode2() {
        return specialCode2;
    }

    public void setSpecialCode2(String specialCode2) {
        this.specialCode2 = specialCode2;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getVisaDet() {
        return visaDet;
    }

    public void setVisaDet(String visaDet) {
        this.visaDet = visaDet;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getAutoExpr() {
        return autoExpr;
    }

    public void setAutoExpr(String autoExpr) {
        this.autoExpr = autoExpr;
    }

    public String getCurrExpr() {
        return currExpr;
    }

    public void setCurrExpr(String currExpr) {
        this.currExpr = currExpr;
    }

    public String getCurrSalary() {
        return currSalary;
    }

    public void setCurrSalary(String currSalary) {
        this.currSalary = currSalary;
    }

    public String getExpSalary() {
        return expSalary;
    }

    public void setExpSalary(String expSalary) {
        this.expSalary = expSalary;
    }

    public String getJoinSalary() {
        return joinSalary;
    }

    public void setJoinSalary(String joinSalary) {
        this.joinSalary = joinSalary;
    }

    public String getServiceLen() {
        return serviceLen;
    }

    public void setServiceLen(String serviceLen) {
        this.serviceLen = serviceLen;
    }

    public String getTotExpr() {
        return totExpr;
    }

    public void setTotExpr(String totExpr) {
        this.totExpr = totExpr;
    }
}
