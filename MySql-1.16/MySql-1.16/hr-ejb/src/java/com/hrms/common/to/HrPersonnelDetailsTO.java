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
public class HrPersonnelDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO;
    private String empId;
    private String empCardNo;
    private String empName;
    private String block;
    private String unitName;
    private String empFirName;
    private String empMidName;
    private String empLastName;
    private String empContAdd;
    private String empContPin;
    private String empContCity;
    private String empContState;
    private String empContTel;
    private String empPermAdd;
    private String empPermPin;
    private String empPermCity;
    private String empPermState;
    private String empPermTel;
    private Date birthDate;
    private char sex;
    private String empType;
    private String fathHusName;
    private String fatherHusOcc;
    private String fatherHusDesig;
    private String fatherHusOrg;
    private String fatherHusPhone;
    private Double totExpr;
    private Double autoExpr;
    private String zones;
    private String locatCode;
    private String deptCode;
    private String gradeCode;
    private String desgCode;
    private String catgCode;
    private String subdeptCode;
    private Character travOverseasStatus;
    private String height;
    private String nation;
    private String weight;
    private String chest;
    private String health;
    private String religion;
    private Character maritalStatus;
    private String bloodGrp;
    private String birthCity;
    private String birthState;
    private Date weddingDate;
    private Date joinDate;
    private Date payrollDate;
    private String payMode;
    private Character otEligibility;
    private String specialCode;
    private String skillCode;
    private Double probPeriod;
    private Double noticePeriod;
    private Date confirmationDate;
    private Integer children;
    private String emailId;
    private Double basicSal;
    private Double grossSal;
    private String repTo;
    private String workStatus;
    private String passportNo;
    private Date passportDate;
    private String visaDet;
    private String profMember;
    private String indivMember;
    private Date timeIn;
    private Date timeOut;
    private String shiftCode;
    private String accomdType;
    private String insuranceNo;
    private String career;
    private String jobdesc;
    private String bankCode;
    private String bankAccountCode;
    private String companyLease;
    private Double hraAmt;
    private Character esiMember;
    private Character pfMember;
    private Character vpfMember;
    private Character trustMember;
    private Double householdAmt;
    private Character metro;
    private String town;
    private String area;
    private String occCode;
    private BigInteger empImage;
    private String certAdosNo;
    private Date certAdosDate;
    private String certTokNo;
    private String certRef;
    private String relay;
    private Date relayDate;
    private Integer defaultComp;
    private String statFlag;
    private String statUpFlag;
    private Date modDate;
    private String authBy;
    private String finAccountCode;
    private char empStatus;
    private String password;
    private String enteredBy;
    private String customerId;
    private String pfAccount;
    private String baseBranch;
    private String designation;
    private Date retirementDate;
    private String uanNumber;

    public String getUanNumber() {
        return uanNumber;
    }

    public void setUanNumber(String uanNumber) {
        this.uanNumber = uanNumber;
    }
    

    public String getAccomdType() {
        return accomdType;
    }

    public void setAccomdType(String accomdType) {
        this.accomdType = accomdType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(Date retirementDate) {
        this.retirementDate = retirementDate;
    }

   

    
    
    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Double getAutoExpr() {
        return autoExpr;
    }

    public void setAutoExpr(Double autoExpr) {
        this.autoExpr = autoExpr;
    }

    public String getBankAccountCode() {
        return bankAccountCode;
    }

    public void setBankAccountCode(String bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Double getBasicSal() {
        return basicSal;
    }

    public void setBasicSal(Double basicSal) {
        this.basicSal = basicSal;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthState() {
        return birthState;
    }

    public void setBirthState(String birthState) {
        this.birthState = birthState;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBloodGrp() {
        return bloodGrp;
    }

    public void setBloodGrp(String bloodGrp) {
        this.bloodGrp = bloodGrp;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }

    public Date getCertAdosDate() {
        return certAdosDate;
    }

    public void setCertAdosDate(Date certAdosDate) {
        this.certAdosDate = certAdosDate;
    }

    public String getCertAdosNo() {
        return certAdosNo;
    }

    public void setCertAdosNo(String certAdosNo) {
        this.certAdosNo = certAdosNo;
    }

    public String getCertRef() {
        return certRef;
    }

    public void setCertRef(String certRef) {
        this.certRef = certRef;
    }

    public String getCertTokNo() {
        return certTokNo;
    }

    public void setCertTokNo(String certTokNo) {
        this.certTokNo = certTokNo;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public String getCompanyLease() {
        return companyLease;
    }

    public void setCompanyLease(String companyLease) {
        this.companyLease = companyLease;
    }

    public Date getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmpCardNo() {
        return empCardNo;
    }

    public void setEmpCardNo(String empCardNo) {
        this.empCardNo = empCardNo;
    }

    public String getEmpContAdd() {
        return empContAdd;
    }

    public void setEmpContAdd(String empContAdd) {
        this.empContAdd = empContAdd;
    }

    public String getEmpContCity() {
        return empContCity;
    }

    public void setEmpContCity(String empContCity) {
        this.empContCity = empContCity;
    }

    public String getEmpContPin() {
        return empContPin;
    }

    public void setEmpContPin(String empContPin) {
        this.empContPin = empContPin;
    }

    public String getEmpContState() {
        return empContState;
    }

    public void setEmpContState(String empContState) {
        this.empContState = empContState;
    }

    public String getEmpContTel() {
        return empContTel;
    }

    public void setEmpContTel(String empContTel) {
        this.empContTel = empContTel;
    }

    public String getEmpFirName() {
        return empFirName;
    }

    public void setEmpFirName(String empFirName) {
        this.empFirName = empFirName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public BigInteger getEmpImage() {
        return empImage;
    }

    public void setEmpImage(BigInteger empImage) {
        this.empImage = empImage;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    public String getEmpMidName() {
        return empMidName;
    }

    public void setEmpMidName(String empMidName) {
        this.empMidName = empMidName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPermAdd() {
        return empPermAdd;
    }

    public void setEmpPermAdd(String empPermAdd) {
        this.empPermAdd = empPermAdd;
    }

    public String getEmpPermCity() {
        return empPermCity;
    }

    public void setEmpPermCity(String empPermCity) {
        this.empPermCity = empPermCity;
    }

    public String getEmpPermPin() {
        return empPermPin;
    }

    public void setEmpPermPin(String empPermPin) {
        this.empPermPin = empPermPin;
    }

    public String getEmpPermState() {
        return empPermState;
    }

    public void setEmpPermState(String empPermState) {
        this.empPermState = empPermState;
    }

    public String getEmpPermTel() {
        return empPermTel;
    }

    public void setEmpPermTel(String empPermTel) {
        this.empPermTel = empPermTel;
    }

    public char getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(char empStatus) {
        this.empStatus = empStatus;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Character getEsiMember() {
        return esiMember;
    }

    public void setEsiMember(Character esiMember) {
        this.esiMember = esiMember;
    }

    public String getFathHusName() {
        return fathHusName;
    }

    public void setFathHusName(String fathHusName) {
        this.fathHusName = fathHusName;
    }

    public String getFatherHusDesig() {
        return fatherHusDesig;
    }

    public void setFatherHusDesig(String fatherHusDesig) {
        this.fatherHusDesig = fatherHusDesig;
    }

    public String getFatherHusOcc() {
        return fatherHusOcc;
    }

    public void setFatherHusOcc(String fatherHusOcc) {
        this.fatherHusOcc = fatherHusOcc;
    }

    public String getFatherHusOrg() {
        return fatherHusOrg;
    }

    public void setFatherHusOrg(String fatherHusOrg) {
        this.fatherHusOrg = fatherHusOrg;
    }

    public String getFatherHusPhone() {
        return fatherHusPhone;
    }

    public void setFatherHusPhone(String fatherHusPhone) {
        this.fatherHusPhone = fatherHusPhone;
    }

    public String getFinAccountCode() {
        return finAccountCode;
    }

    public void setFinAccountCode(String finAccountCode) {
        this.finAccountCode = finAccountCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Double getGrossSal() {
        return grossSal;
    }

    public void setGrossSal(Double grossSal) {
        this.grossSal = grossSal;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Double getHouseholdAmt() {
        return householdAmt;
    }

    public void setHouseholdAmt(Double householdAmt) {
        this.householdAmt = householdAmt;
    }

    public HrPersonnelDetailsPKTO getHrPersonnelDetailsPKTO() {
        return hrPersonnelDetailsPKTO;
    }

    public void setHrPersonnelDetailsPKTO(HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO) {
        this.hrPersonnelDetailsPKTO = hrPersonnelDetailsPKTO;
    }

    public Double getHraAmt() {
        return hraAmt;
    }

    public void setHraAmt(Double hraAmt) {
        this.hraAmt = hraAmt;
    }

    public String getIndivMember() {
        return indivMember;
    }

    public void setIndivMember(String indivMember) {
        this.indivMember = indivMember;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public String getJobdesc() {
        return jobdesc;
    }

    public void setJobdesc(String jobdesc) {
        this.jobdesc = jobdesc;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getLocatCode() {
        return locatCode;
    }

    public void setLocatCode(String locatCode) {
        this.locatCode = locatCode;
    }

    public Character getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Character maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Character getMetro() {
        return metro;
    }

    public void setMetro(Character metro) {
        this.metro = metro;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Double getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(Double noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public String getOccCode() {
        return occCode;
    }

    public void setOccCode(String occCode) {
        this.occCode = occCode;
    }

    public Character getOtEligibility() {
        return otEligibility;
    }

    public void setOtEligibility(Character otEligibility) {
        this.otEligibility = otEligibility;
    }

    public Date getPassportDate() {
        return passportDate;
    }

    public void setPassportDate(Date passportDate) {
        this.passportDate = passportDate;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public Date getPayrollDate() {
        return payrollDate;
    }

    public void setPayrollDate(Date payrollDate) {
        this.payrollDate = payrollDate;
    }

    public Character getPfMember() {
        return pfMember;
    }

    public void setPfMember(Character pfMember) {
        this.pfMember = pfMember;
    }

    public Double getProbPeriod() {
        return probPeriod;
    }

    public void setProbPeriod(Double probPeriod) {
        this.probPeriod = probPeriod;
    }

    public String getProfMember() {
        return profMember;
    }

    public void setProfMember(String profMember) {
        this.profMember = profMember;
    }

    public String getRelay() {
        return relay;
    }

    public void setRelay(String relay) {
        this.relay = relay;
    }

    public Date getRelayDate() {
        return relayDate;
    }

    public void setRelayDate(Date relayDate) {
        this.relayDate = relayDate;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getRepTo() {
        return repTo;
    }

    public void setRepTo(String repTo) {
        this.repTo = repTo;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
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

    public String getSubdeptCode() {
        return subdeptCode;
    }

    public void setSubdeptCode(String subdeptCode) {
        this.subdeptCode = subdeptCode;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public Double getTotExpr() {
        return totExpr;
    }

    public void setTotExpr(Double totExpr) {
        this.totExpr = totExpr;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Character getTravOverseasStatus() {
        return travOverseasStatus;
    }

    public void setTravOverseasStatus(Character travOverseasStatus) {
        this.travOverseasStatus = travOverseasStatus;
    }

    public Character getTrustMember() {
        return trustMember;
    }

    public void setTrustMember(Character trustMember) {
        this.trustMember = trustMember;
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

    public Character getVpfMember() {
        return vpfMember;
    }

    public void setVpfMember(Character vpfMember) {
        this.vpfMember = vpfMember;
    }

    public Date getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(Date weddingDate) {
        this.weddingDate = weddingDate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getZones() {
        return zones;
    }

    public void setZones(String zones) {
        this.zones = zones;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPfAccount() {
        return pfAccount;
    }

    public void setPfAccount(String pfAccount) {
        this.pfAccount = pfAccount;
    }

    public String getBaseBranch() {
        return baseBranch;
    }

    public void setBaseBranch(String baseBranch) {
        this.baseBranch = baseBranch;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
