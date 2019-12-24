package com.cbs.dto.cdci;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerDetailsPojo", propOrder = {"lastChangeUserID", "customerid",
    "custname", "lastChangeTime", "title", "shortName", "gender", "maritalstatus", "fathername",
    "mothername", "mobilenumber", "emailId", "pan_GirNumber", "tinNumber", "perAddressLine1",
    "perAddressLine2", "perVillage", "perBlock", "perCityCode", "perStateCode", "perPostalCode",
    "perCountryCode", "perPhoneNumber", "perTelexNumber", "perFaxNumber", "mailAddressLine1",
    "mailAddressLine2", "mailVillage", "mailBlock", "mailCityCode", "mailStateCode",
    "mailPostalCode", "mailCountryCode", "mailPhoneNumber", "mailTelexNumber", "mailFaxNumber",
    "primaryBrCode", "minorflag", "dateOfBirth", "passportNo", "issueDate", "expirydate",
    "placeOfIssue", "introCustomerId", "combinedstmtFlag", "stmtFreqType", "stmtFreqWeekNo",
    "stmtFreqStartDate", "tdsExemptionEndDate", "taxSlab", "tdsCode", "tdsCustomerId",
    "tdsFormReceiveDate", "acno", "middleName", "lastName"})
public class CustomerDetailsPojo {

    private String lastChangeUserID;
    private String customerid;
    private String custname;
    private Date lastChangeTime;
    private String title;
    private String shortName;
    private String gender;
    private String maritalstatus;
    private String fathername;
    private String mothername;
    private String mobilenumber;
    private String emailId;
    private String pan_GirNumber;
    private String tinNumber;
    private String perAddressLine1;
    private String perAddressLine2;
    private String perVillage;
    private String perBlock;
    private String perCityCode;
    private String perStateCode;
    private String perPostalCode;
    private String perCountryCode;
    private String perPhoneNumber;
    private String perTelexNumber;
    private String perFaxNumber;
    private String mailAddressLine1;
    private String mailAddressLine2;
    private String mailVillage;
    private String mailBlock;
    private String mailCityCode;
    private String mailStateCode;
    private String mailPostalCode;
    private String mailCountryCode;
    private String mailPhoneNumber;
    private String mailTelexNumber;
    private String mailFaxNumber;
    private String primaryBrCode;
    private String minorflag;
    private String passportNo;
    private String placeOfIssue;
    private String introCustomerId;
    private String combinedstmtFlag;
    private String stmtFreqType;
    private String stmtFreqWeekNo;
    private String taxSlab;
    private String tdsCode;
    private String tdsCustomerId;
    private Date tdsExemptionEndDate;
    private Date tdsFormReceiveDate;
    private Date stmtFreqStartDate;
    private Date dateOfBirth;
    private Date issueDate;
    private Date expirydate;
    private String acno;
    private String middleName;
    private String lastName;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getCombinedstmtFlag() {
        return combinedstmtFlag;
    }

    public void setCombinedstmtFlag(String combinedstmtFlag) {
        this.combinedstmtFlag = combinedstmtFlag;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIntroCustomerId() {
        return introCustomerId;
    }

    public void setIntroCustomerId(String introCustomerId) {
        this.introCustomerId = introCustomerId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public String getLastChangeUserID() {
        return lastChangeUserID;
    }

    public void setLastChangeUserID(String lastChangeUserID) {
        this.lastChangeUserID = lastChangeUserID;
    }

    public String getMailAddressLine1() {
        return mailAddressLine1;
    }

    public void setMailAddressLine1(String mailAddressLine1) {
        this.mailAddressLine1 = mailAddressLine1;
    }

    public String getMailAddressLine2() {
        return mailAddressLine2;
    }

    public void setMailAddressLine2(String mailAddressLine2) {
        this.mailAddressLine2 = mailAddressLine2;
    }

    public String getMailBlock() {
        return mailBlock;
    }

    public void setMailBlock(String mailBlock) {
        this.mailBlock = mailBlock;
    }

    public String getMailCityCode() {
        return mailCityCode;
    }

    public void setMailCityCode(String mailCityCode) {
        this.mailCityCode = mailCityCode;
    }

    public String getMailCountryCode() {
        return mailCountryCode;
    }

    public void setMailCountryCode(String mailCountryCode) {
        this.mailCountryCode = mailCountryCode;
    }

    public String getMailFaxNumber() {
        return mailFaxNumber;
    }

    public void setMailFaxNumber(String mailFaxNumber) {
        this.mailFaxNumber = mailFaxNumber;
    }

    public String getMailPhoneNumber() {
        return mailPhoneNumber;
    }

    public void setMailPhoneNumber(String mailPhoneNumber) {
        this.mailPhoneNumber = mailPhoneNumber;
    }

    public String getMailPostalCode() {
        return mailPostalCode;
    }

    public void setMailPostalCode(String mailPostalCode) {
        this.mailPostalCode = mailPostalCode;
    }

    public String getMailStateCode() {
        return mailStateCode;
    }

    public void setMailStateCode(String mailStateCode) {
        this.mailStateCode = mailStateCode;
    }

    public String getMailTelexNumber() {
        return mailTelexNumber;
    }

    public void setMailTelexNumber(String mailTelexNumber) {
        this.mailTelexNumber = mailTelexNumber;
    }

    public String getMailVillage() {
        return mailVillage;
    }

    public void setMailVillage(String mailVillage) {
        this.mailVillage = mailVillage;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getMinorflag() {
        return minorflag;
    }

    public void setMinorflag(String minorflag) {
        this.minorflag = minorflag;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getPan_GirNumber() {
        return pan_GirNumber;
    }

    public void setPan_GirNumber(String pan_GirNumber) {
        this.pan_GirNumber = pan_GirNumber;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPerAddressLine1() {
        return perAddressLine1;
    }

    public void setPerAddressLine1(String perAddressLine1) {
        this.perAddressLine1 = perAddressLine1;
    }

    public String getPerAddressLine2() {
        return perAddressLine2;
    }

    public void setPerAddressLine2(String perAddressLine2) {
        this.perAddressLine2 = perAddressLine2;
    }

    public String getPerBlock() {
        return perBlock;
    }

    public void setPerBlock(String perBlock) {
        this.perBlock = perBlock;
    }

    public String getPerCityCode() {
        return perCityCode;
    }

    public void setPerCityCode(String perCityCode) {
        this.perCityCode = perCityCode;
    }

    public String getPerCountryCode() {
        return perCountryCode;
    }

    public void setPerCountryCode(String perCountryCode) {
        this.perCountryCode = perCountryCode;
    }

    public String getPerFaxNumber() {
        return perFaxNumber;
    }

    public void setPerFaxNumber(String perFaxNumber) {
        this.perFaxNumber = perFaxNumber;
    }

    public String getPerPhoneNumber() {
        return perPhoneNumber;
    }

    public void setPerPhoneNumber(String perPhoneNumber) {
        this.perPhoneNumber = perPhoneNumber;
    }

    public String getPerPostalCode() {
        return perPostalCode;
    }

    public void setPerPostalCode(String perPostalCode) {
        this.perPostalCode = perPostalCode;
    }

    public String getPerStateCode() {
        return perStateCode;
    }

    public void setPerStateCode(String perStateCode) {
        this.perStateCode = perStateCode;
    }

    public String getPerTelexNumber() {
        return perTelexNumber;
    }

    public void setPerTelexNumber(String perTelexNumber) {
        this.perTelexNumber = perTelexNumber;
    }

    public String getPerVillage() {
        return perVillage;
    }

    public void setPerVillage(String perVillage) {
        this.perVillage = perVillage;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public String getPrimaryBrCode() {
        return primaryBrCode;
    }

    public void setPrimaryBrCode(String primaryBrCode) {
        this.primaryBrCode = primaryBrCode;
    }

    public Date getStmtFreqStartDate() {
        return stmtFreqStartDate;
    }

    public void setStmtFreqStartDate(Date stmtFreqStartDate) {
        this.stmtFreqStartDate = stmtFreqStartDate;
    }

    public String getStmtFreqType() {
        return stmtFreqType;
    }

    public void setStmtFreqType(String stmtFreqType) {
        this.stmtFreqType = stmtFreqType;
    }

    public String getStmtFreqWeekNo() {
        return stmtFreqWeekNo;
    }

    public void setStmtFreqWeekNo(String stmtFreqWeekNo) {
        this.stmtFreqWeekNo = stmtFreqWeekNo;
    }

    public String getTaxSlab() {
        return taxSlab;
    }

    public void setTaxSlab(String taxSlab) {
        this.taxSlab = taxSlab;
    }

    public String getTdsCode() {
        return tdsCode;
    }

    public void setTdsCode(String tdsCode) {
        this.tdsCode = tdsCode;
    }

    public String getTdsCustomerId() {
        return tdsCustomerId;
    }

    public void setTdsCustomerId(String tdsCustomerId) {
        this.tdsCustomerId = tdsCustomerId;
    }

    public Date getTdsExemptionEndDate() {
        return tdsExemptionEndDate;
    }

    public void setTdsExemptionEndDate(Date tdsExemptionEndDate) {
        this.tdsExemptionEndDate = tdsExemptionEndDate;
    }

    public Date getTdsFormReceiveDate() {
        return tdsFormReceiveDate;
    }

    public void setTdsFormReceiveDate(Date tdsFormReceiveDate) {
        this.tdsFormReceiveDate = tdsFormReceiveDate;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
