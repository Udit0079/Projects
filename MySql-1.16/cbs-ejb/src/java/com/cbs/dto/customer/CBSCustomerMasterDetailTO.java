/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.customer;

import java.util.Date;

public class CBSCustomerMasterDetailTO {

    private static final long serialVersionUID = 1L;
    private String customerid;
    private String title;
    private String custname;
    private String shortname;
    private String gender;
    private String maritalstatus;
    private String fathername;
    private String mothername;
    private String staffflag;
    private String staffid;
    private String minorflag;
    private Date dateOfBirth;
    private String nriflag;
    private String uINCardNo;
    private String communicationPreference;
    private String employerid;
    private String employeeNo;
    private String mobilenumber;
    private String custStatus;
    private String passportNo;
    private Date issueDate;
    private String issuingAuthority;
    private Date expirydate;
    private String placeOfIssue;
    private String preferredLanguage;
    private String nameInPreferredLanguage;
    private String chgTurnOver;
    private String purgeAllowed;
    private String accountManager;
    private String allowSweeps;
    private String tradeFinanceFlag;
    private String swiftCodeStatus;
    private String swiftCode;
    private String bcbfid;
    private String combinedStmtFlag;
    private String stmtFreqType;
    private String stmtFreqWeekNo;
    private String stmtFreqWeekDay;
    private String stmtFreqStartDate;
    private String stmtFreqNP;
    private String introCustomerId;
    private String custTitle;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String village;
    private String block;
    private String cityCode;
    private String stateCode;
    private String postalCode;
    private String countryCode;
    private String phoneNumber;
    private String telexNumber;
    private String faxNumber;
    private Double salary;
    private String chargeStatus;
    private String chargeLevelCode;
    private String aBBChargeCode;
    private String ePSChargeCode;
    private String paperRemittance;
    private String deliveryChannelChargeCode;
    private String accountLevelCharges;
    private String custLevelCharges;
    private Date tDSExemptionEndDate;
    private String taxSlab;
    private String tDSCode;
    private String tDSCustomerId;
    private Date tDSFormReceiveDate;
    private String tDSExemptionReferenceNo;
    private String exemptionRemarks;
    private String iTFileNo;
    private Double tDSFloorLimit;
    private String custFinancialDetails;
    private String financialYearAndMonth;
    private String currencyCodeType;
    private Double propertyAssets;
    private Double businessAssets;
    private Double investments;
    private Double networth;
    private Double deposits;
    private String otherBankCode;
    private Double limitsWithOtherBank;
    private Double fundBasedLimit;
    private Double nonFundBasedLimit;
    private Double offLineCustDebitLimit;
    private Double custSalary;
    private Date custFinancialDate;
    private String pANGIRNumber;
    private String virtualId;
    private String tINNumber;
    private String salesTaxNo;
    private String exciseNo;
    private String voterIDNo;
    private String drivingLicenseNo;
    private String creditCard;
    private String cardNumber;
    private String cardIssuer;
    private String bankName;
    private String acctId;
    private String branchName;
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
    private String empAddressLine1;
    private String empAddressLine2;
    private String empVillage;
    private String empBlock;
    private String empCityCode;
    private String empStateCode;
    private String empPostalCode;
    private String empCountryCode;
    private String empPhoneNumber;
    private String empTelexNumber;
    private String empFaxNumber;
    private String emailID;
    private String operationalRiskRating;
    private Date ratingAsOn;
    private String creditRiskRatingInternal;
    private Date creditRatingAsOn;
    private String externalRatingShortTerm;
    private Date externShortRatingAsOn;
    private String externalRatingLongTerm;
    private Date externLongRatingAsOn;
    private Date custAcquistionDate;
    private Double thresoldTransactionLimit;
    private Date thresoldLimitUpdationDate;
    private Date custUpdationDate;
    private String suspensionFlg;
    private String primaryBrCode;
    private String lastUpdatedBr;
    private String firstAccountDate;
    private String auth;
    private String lastChangeUserID;
    private Date lastChangeTime;
    private String recordCreaterID;
    private Date creationTime;
    private String tsCnt;
    private String adhaarNo;
    private String lpgId;
    private String adhaarLpgAcno;
    private String mandateFlag;
    private String mandateDt;
    private String regType;
    private String middleName;
    private String lastName;
    private String spouseName;
    private String maidenName;
    private String nregaJobCard;
    private String dlExpiry;
    private String legalDocument;
    private String incomeRange;
    private String networthAsOn;
    private String qualification;
    private String politicalExposed;
    private String juriAdd1;
    private String juriAdd2;
    private String juriCity;
    private String juriState;
    private String juriPostal;
    private String juriCountry;
    private String tan;
    private String cin;
    private String perEmail;
    private String mailEmail;
    private String nationality;
    private String otherIdentity;
    private String poa;
    private String strFlag;
    //Addition on 02/01/2017
    private String perAddType;
    private String mailAddType;
    private String mailPoa;
    private String juriAddBasedOnFlag;
    private String juriAddType;
    private String juriPoa;
    //Addition on 05/01/2017
    private String custEntityType;
    private String acHolderTypeFlag;
    private String acHolderType;
    private String acType;
    private String cKYCNo;
    private String fatherMiddleName;
    private String fatherLastName;
    private String spouseMiddleName;
    private String spouseLastName;
    private String motherMiddleName;
    private String motherLastName;
    private String tinIssuingCountry;
    private String identityNo;
    private String idExpiryDate;
    private String perMailAddSameFlagIndicate;
    private String perDistrict;
    private String mailDistrict;
    private String empDistrict;
    private String juriDistrict;
    private String perOtherPOA;
    private String mailOtherPOA;
    private String juriOtherPOA;
    private String isdCode;
    private String fatherSpouseFlag;
    private String custImage;
    private String custFullName;
    private String gstIdentificationNumber;
    //add by rahul
    private String aadhaarMode;
    private String aadhaarBankIin;

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public String getNregaJobCard() {
        return nregaJobCard;
    }

    public void setNregaJobCard(String nregaJobCard) {
        this.nregaJobCard = nregaJobCard;
    }

    public String getLegalDocument() {
        return legalDocument;
    }

    public void setLegalDocument(String legalDocument) {
        this.legalDocument = legalDocument;
    }

    public String getIncomeRange() {
        return incomeRange;
    }

    public void setIncomeRange(String incomeRange) {
        this.incomeRange = incomeRange;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPoliticalExposed() {
        return politicalExposed;
    }

    public void setPoliticalExposed(String politicalExposed) {
        this.politicalExposed = politicalExposed;
    }

    public String getJuriAdd1() {
        return juriAdd1;
    }

    public void setJuriAdd1(String juriAdd1) {
        this.juriAdd1 = juriAdd1;
    }

    public String getJuriAdd2() {
        return juriAdd2;
    }

    public void setJuriAdd2(String juriAdd2) {
        this.juriAdd2 = juriAdd2;
    }

    public String getJuriCity() {
        return juriCity;
    }

    public void setJuriCity(String juriCity) {
        this.juriCity = juriCity;
    }

    public String getJuriState() {
        return juriState;
    }

    public void setJuriState(String juriState) {
        this.juriState = juriState;
    }

    public String getJuriPostal() {
        return juriPostal;
    }

    public void setJuriPostal(String juriPostal) {
        this.juriPostal = juriPostal;
    }

    public String getJuriCountry() {
        return juriCountry;
    }

    public void setJuriCountry(String juriCountry) {
        this.juriCountry = juriCountry;
    }

    public String getTan() {
        return tan;
    }

    public void setTan(String tan) {
        this.tan = tan;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPerEmail() {
        return perEmail;
    }

    public void setPerEmail(String perEmail) {
        this.perEmail = perEmail;
    }

    public String getMailEmail() {
        return mailEmail;
    }

    public void setMailEmail(String mailEmail) {
        this.mailEmail = mailEmail;
    }

    public String getaBBChargeCode() {
        return aBBChargeCode;
    }

    public void setaBBChargeCode(String aBBChargeCode) {
        this.aBBChargeCode = aBBChargeCode;
    }

    public String getAccountLevelCharges() {
        return accountLevelCharges;
    }

    public void setAccountLevelCharges(String accountLevelCharges) {
        this.accountLevelCharges = accountLevelCharges;
    }

    public String getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(String accountManager) {
        this.accountManager = accountManager;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAllowSweeps() {
        return allowSweeps;
    }

    public void setAllowSweeps(String allowSweeps) {
        this.allowSweeps = allowSweeps;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBcbfid() {
        return bcbfid;
    }

    public void setBcbfid(String bcbfid) {
        this.bcbfid = bcbfid;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Double getBusinessAssets() {
        return businessAssets;
    }

    public void setBusinessAssets(Double businessAssets) {
        this.businessAssets = businessAssets;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getChargeLevelCode() {
        return chargeLevelCode;
    }

    public void setChargeLevelCode(String chargeLevelCode) {
        this.chargeLevelCode = chargeLevelCode;
    }

    public String getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public String getChgTurnOver() {
        return chgTurnOver;
    }

    public void setChgTurnOver(String chgTurnOver) {
        this.chgTurnOver = chgTurnOver;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCombinedStmtFlag() {
        return combinedStmtFlag;
    }

    public void setCombinedStmtFlag(String combinedStmtFlag) {
        this.combinedStmtFlag = combinedStmtFlag;
    }

    public String getCommunicationPreference() {
        return communicationPreference;
    }

    public void setCommunicationPreference(String communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public Date getCreditRatingAsOn() {
        return creditRatingAsOn;
    }

    public void setCreditRatingAsOn(Date creditRatingAsOn) {
        this.creditRatingAsOn = creditRatingAsOn;
    }

    public String getCreditRiskRatingInternal() {
        return creditRiskRatingInternal;
    }

    public void setCreditRiskRatingInternal(String creditRiskRatingInternal) {
        this.creditRiskRatingInternal = creditRiskRatingInternal;
    }

    public String getCurrencyCodeType() {
        return currencyCodeType;
    }

    public void setCurrencyCodeType(String currencyCodeType) {
        this.currencyCodeType = currencyCodeType;
    }

    public Date getCustAcquistionDate() {
        return custAcquistionDate;
    }

    public void setCustAcquistionDate(Date custAcquistionDate) {
        this.custAcquistionDate = custAcquistionDate;
    }

    public Date getCustFinancialDate() {
        return custFinancialDate;
    }

    public void setCustFinancialDate(Date custFinancialDate) {
        this.custFinancialDate = custFinancialDate;
    }

    public String getCustFinancialDetails() {
        return custFinancialDetails;
    }

    public void setCustFinancialDetails(String custFinancialDetails) {
        this.custFinancialDetails = custFinancialDetails;
    }

    public String getCustLevelCharges() {
        return custLevelCharges;
    }

    public void setCustLevelCharges(String custLevelCharges) {
        this.custLevelCharges = custLevelCharges;
    }

    public Double getCustSalary() {
        return custSalary;
    }

    public void setCustSalary(Double custSalary) {
        this.custSalary = custSalary;
    }

    public String getCustStatus() {
        return custStatus;
    }

    public void setCustStatus(String custStatus) {
        this.custStatus = custStatus;
    }

    public String getCustTitle() {
        return custTitle;
    }

    public void setCustTitle(String custTitle) {
        this.custTitle = custTitle;
    }

    public Date getCustUpdationDate() {
        return custUpdationDate;
    }

    public void setCustUpdationDate(Date custUpdationDate) {
        this.custUpdationDate = custUpdationDate;
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

    public String getDeliveryChannelChargeCode() {
        return deliveryChannelChargeCode;
    }

    public void setDeliveryChannelChargeCode(String deliveryChannelChargeCode) {
        this.deliveryChannelChargeCode = deliveryChannelChargeCode;
    }

    public Double getDeposits() {
        return deposits;
    }

    public void setDeposits(Double deposits) {
        this.deposits = deposits;
    }

    public String getDrivingLicenseNo() {
        return drivingLicenseNo;
    }

    public void setDrivingLicenseNo(String drivingLicenseNo) {
        this.drivingLicenseNo = drivingLicenseNo;
    }

    public String getePSChargeCode() {
        return ePSChargeCode;
    }

    public void setePSChargeCode(String ePSChargeCode) {
        this.ePSChargeCode = ePSChargeCode;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getEmpAddressLine1() {
        return empAddressLine1;
    }

    public void setEmpAddressLine1(String empAddressLine1) {
        this.empAddressLine1 = empAddressLine1;
    }

    public String getEmpAddressLine2() {
        return empAddressLine2;
    }

    public void setEmpAddressLine2(String empAddressLine2) {
        this.empAddressLine2 = empAddressLine2;
    }

    public String getEmpBlock() {
        return empBlock;
    }

    public void setEmpBlock(String empBlock) {
        this.empBlock = empBlock;
    }

    public String getEmpCityCode() {
        return empCityCode;
    }

    public void setEmpCityCode(String empCityCode) {
        this.empCityCode = empCityCode;
    }

    public String getEmpCountryCode() {
        return empCountryCode;
    }

    public void setEmpCountryCode(String empCountryCode) {
        this.empCountryCode = empCountryCode;
    }

    public String getEmpFaxNumber() {
        return empFaxNumber;
    }

    public void setEmpFaxNumber(String empFaxNumber) {
        this.empFaxNumber = empFaxNumber;
    }

    public String getEmpPhoneNumber() {
        return empPhoneNumber;
    }

    public void setEmpPhoneNumber(String empPhoneNumber) {
        this.empPhoneNumber = empPhoneNumber;
    }

    public String getEmpPostalCode() {
        return empPostalCode;
    }

    public void setEmpPostalCode(String empPostalCode) {
        this.empPostalCode = empPostalCode;
    }

    public String getEmpStateCode() {
        return empStateCode;
    }

    public void setEmpStateCode(String empStateCode) {
        this.empStateCode = empStateCode;
    }

    public String getEmpTelexNumber() {
        return empTelexNumber;
    }

    public void setEmpTelexNumber(String empTelexNumber) {
        this.empTelexNumber = empTelexNumber;
    }

    public String getEmpVillage() {
        return empVillage;
    }

    public void setEmpVillage(String empVillage) {
        this.empVillage = empVillage;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmployerid() {
        return employerid;
    }

    public void setEmployerid(String employerid) {
        this.employerid = employerid;
    }

    public String getExciseNo() {
        return exciseNo;
    }

    public void setExciseNo(String exciseNo) {
        this.exciseNo = exciseNo;
    }

    public String getExemptionRemarks() {
        return exemptionRemarks;
    }

    public void setExemptionRemarks(String exemptionRemarks) {
        this.exemptionRemarks = exemptionRemarks;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    public Date getExternLongRatingAsOn() {
        return externLongRatingAsOn;
    }

    public void setExternLongRatingAsOn(Date externLongRatingAsOn) {
        this.externLongRatingAsOn = externLongRatingAsOn;
    }

    public Date getExternShortRatingAsOn() {
        return externShortRatingAsOn;
    }

    public void setExternShortRatingAsOn(Date externShortRatingAsOn) {
        this.externShortRatingAsOn = externShortRatingAsOn;
    }

    public String getExternalRatingLongTerm() {
        return externalRatingLongTerm;
    }

    public void setExternalRatingLongTerm(String externalRatingLongTerm) {
        this.externalRatingLongTerm = externalRatingLongTerm;
    }

    public String getExternalRatingShortTerm() {
        return externalRatingShortTerm;
    }

    public void setExternalRatingShortTerm(String externalRatingShortTerm) {
        this.externalRatingShortTerm = externalRatingShortTerm;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getFinancialYearAndMonth() {
        return financialYearAndMonth;
    }

    public void setFinancialYearAndMonth(String financialYearAndMonth) {
        this.financialYearAndMonth = financialYearAndMonth;
    }

    public String getFirstAccountDate() {
        return firstAccountDate;
    }

    public void setFirstAccountDate(String firstAccountDate) {
        this.firstAccountDate = firstAccountDate;
    }

    public Double getFundBasedLimit() {
        return fundBasedLimit;
    }

    public void setFundBasedLimit(Double fundBasedLimit) {
        this.fundBasedLimit = fundBasedLimit;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getiTFileNo() {
        return iTFileNo;
    }

    public void setiTFileNo(String iTFileNo) {
        this.iTFileNo = iTFileNo;
    }

    public String getIntroCustomerId() {
        return introCustomerId;
    }

    public void setIntroCustomerId(String introCustomerId) {
        this.introCustomerId = introCustomerId;
    }

    public Double getInvestments() {
        return investments;
    }

    public void setInvestments(Double investments) {
        this.investments = investments;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
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

    public String getLastUpdatedBr() {
        return lastUpdatedBr;
    }

    public void setLastUpdatedBr(String lastUpdatedBr) {
        this.lastUpdatedBr = lastUpdatedBr;
    }

    public Double getLimitsWithOtherBank() {
        return limitsWithOtherBank;
    }

    public void setLimitsWithOtherBank(Double limitsWithOtherBank) {
        this.limitsWithOtherBank = limitsWithOtherBank;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameInPreferredLanguage() {
        return nameInPreferredLanguage;
    }

    public void setNameInPreferredLanguage(String nameInPreferredLanguage) {
        this.nameInPreferredLanguage = nameInPreferredLanguage;
    }

    public Double getNetworth() {
        return networth;
    }

    public void setNetworth(Double networth) {
        this.networth = networth;
    }

    public Double getNonFundBasedLimit() {
        return nonFundBasedLimit;
    }

    public void setNonFundBasedLimit(Double nonFundBasedLimit) {
        this.nonFundBasedLimit = nonFundBasedLimit;
    }

    public String getNriflag() {
        return nriflag;
    }

    public void setNriflag(String nriflag) {
        this.nriflag = nriflag;
    }

    public Double getOffLineCustDebitLimit() {
        return offLineCustDebitLimit;
    }

    public void setOffLineCustDebitLimit(Double offLineCustDebitLimit) {
        this.offLineCustDebitLimit = offLineCustDebitLimit;
    }

    public String getOperationalRiskRating() {
        return operationalRiskRating;
    }

    public void setOperationalRiskRating(String operationalRiskRating) {
        this.operationalRiskRating = operationalRiskRating;
    }

    public String getOtherBankCode() {
        return otherBankCode;
    }

    public void setOtherBankCode(String otherBankCode) {
        this.otherBankCode = otherBankCode;
    }

    public String getpANGIRNumber() {
        return pANGIRNumber;
    }

    public void setpANGIRNumber(String pANGIRNumber) {
        this.pANGIRNumber = pANGIRNumber;
    }

    public String getPaperRemittance() {
        return paperRemittance;
    }

    public void setPaperRemittance(String paperRemittance) {
        this.paperRemittance = paperRemittance;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getPrimaryBrCode() {
        return primaryBrCode;
    }

    public void setPrimaryBrCode(String primaryBrCode) {
        this.primaryBrCode = primaryBrCode;
    }

    public Double getPropertyAssets() {
        return propertyAssets;
    }

    public void setPropertyAssets(Double propertyAssets) {
        this.propertyAssets = propertyAssets;
    }

    public String getPurgeAllowed() {
        return purgeAllowed;
    }

    public void setPurgeAllowed(String purgeAllowed) {
        this.purgeAllowed = purgeAllowed;
    }

    public Date getRatingAsOn() {
        return ratingAsOn;
    }

    public void setRatingAsOn(Date ratingAsOn) {
        this.ratingAsOn = ratingAsOn;
    }

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getSalesTaxNo() {
        return salesTaxNo;
    }

    public void setSalesTaxNo(String salesTaxNo) {
        this.salesTaxNo = salesTaxNo;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getStaffflag() {
        return staffflag;
    }

    public void setStaffflag(String staffflag) {
        this.staffflag = staffflag;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStmtFreqNP() {
        return stmtFreqNP;
    }

    public void setStmtFreqNP(String stmtFreqNP) {
        this.stmtFreqNP = stmtFreqNP;
    }

    public String getStmtFreqStartDate() {
        return stmtFreqStartDate;
    }

    public void setStmtFreqStartDate(String stmtFreqStartDate) {
        this.stmtFreqStartDate = stmtFreqStartDate;
    }

    public String getStmtFreqType() {
        return stmtFreqType;
    }

    public void setStmtFreqType(String stmtFreqType) {
        this.stmtFreqType = stmtFreqType;
    }

    public String getStmtFreqWeekDay() {
        return stmtFreqWeekDay;
    }

    public void setStmtFreqWeekDay(String stmtFreqWeekDay) {
        this.stmtFreqWeekDay = stmtFreqWeekDay;
    }

    public String getStmtFreqWeekNo() {
        return stmtFreqWeekNo;
    }

    public void setStmtFreqWeekNo(String stmtFreqWeekNo) {
        this.stmtFreqWeekNo = stmtFreqWeekNo;
    }

    public String getSuspensionFlg() {
        return suspensionFlg;
    }

    public void setSuspensionFlg(String suspensionFlg) {
        this.suspensionFlg = suspensionFlg;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getSwiftCodeStatus() {
        return swiftCodeStatus;
    }

    public void setSwiftCodeStatus(String swiftCodeStatus) {
        this.swiftCodeStatus = swiftCodeStatus;
    }

    public String gettDSCode() {
        return tDSCode;
    }

    public void settDSCode(String tDSCode) {
        this.tDSCode = tDSCode;
    }

    public String gettDSCustomerId() {
        return tDSCustomerId;
    }

    public void settDSCustomerId(String tDSCustomerId) {
        this.tDSCustomerId = tDSCustomerId;
    }

    public Date gettDSExemptionEndDate() {
        return tDSExemptionEndDate;
    }

    public void settDSExemptionEndDate(Date tDSExemptionEndDate) {
        this.tDSExemptionEndDate = tDSExemptionEndDate;
    }

    public String gettDSExemptionReferenceNo() {
        return tDSExemptionReferenceNo;
    }

    public void settDSExemptionReferenceNo(String tDSExemptionReferenceNo) {
        this.tDSExemptionReferenceNo = tDSExemptionReferenceNo;
    }

    public Double gettDSFloorLimit() {
        return tDSFloorLimit;
    }

    public void settDSFloorLimit(Double tDSFloorLimit) {
        this.tDSFloorLimit = tDSFloorLimit;
    }

    public Date gettDSFormReceiveDate() {
        return tDSFormReceiveDate;
    }

    public void settDSFormReceiveDate(Date tDSFormReceiveDate) {
        this.tDSFormReceiveDate = tDSFormReceiveDate;
    }

    public String gettINNumber() {
        return tINNumber;
    }

    public void settINNumber(String tINNumber) {
        this.tINNumber = tINNumber;
    }

    public String getTaxSlab() {
        return taxSlab;
    }

    public void setTaxSlab(String taxSlab) {
        this.taxSlab = taxSlab;
    }

    public String getTelexNumber() {
        return telexNumber;
    }

    public void setTelexNumber(String telexNumber) {
        this.telexNumber = telexNumber;
    }

    public Date getThresoldLimitUpdationDate() {
        return thresoldLimitUpdationDate;
    }

    public void setThresoldLimitUpdationDate(Date thresoldLimitUpdationDate) {
        this.thresoldLimitUpdationDate = thresoldLimitUpdationDate;
    }

    public Double getThresoldTransactionLimit() {
        return thresoldTransactionLimit;
    }

    public void setThresoldTransactionLimit(Double thresoldTransactionLimit) {
        this.thresoldTransactionLimit = thresoldTransactionLimit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTradeFinanceFlag() {
        return tradeFinanceFlag;
    }

    public void setTradeFinanceFlag(String tradeFinanceFlag) {
        this.tradeFinanceFlag = tradeFinanceFlag;
    }

    public String getTsCnt() {
        return tsCnt;
    }

    public void setTsCnt(String tsCnt) {
        this.tsCnt = tsCnt;
    }

    public String getuINCardNo() {
        return uINCardNo;
    }

    public void setuINCardNo(String uINCardNo) {
        this.uINCardNo = uINCardNo;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getVoterIDNo() {
        return voterIDNo;
    }

    public void setVoterIDNo(String voterIDNo) {
        this.voterIDNo = voterIDNo;
    }

    public String getAdhaarNo() {
        return adhaarNo;
    }

    public void setAdhaarNo(String adhaarNo) {
        this.adhaarNo = adhaarNo;
    }

    public String getLpgId() {
        return lpgId;
    }

    public void setLpgId(String lpgId) {
        this.lpgId = lpgId;
    }

    public String getAdhaarLpgAcno() {
        return adhaarLpgAcno;
    }

    public void setAdhaarLpgAcno(String adhaarLpgAcno) {
        this.adhaarLpgAcno = adhaarLpgAcno;
    }

    public String getMandateFlag() {
        return mandateFlag;
    }

    public void setMandateFlag(String mandateFlag) {
        this.mandateFlag = mandateFlag;
    }

    public String getMandateDt() {
        return mandateDt;
    }

    public void setMandateDt(String mandateDt) {
        this.mandateDt = mandateDt;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getDlExpiry() {
        return dlExpiry;
    }

    public void setDlExpiry(String dlExpiry) {
        this.dlExpiry = dlExpiry;
    }

    public String getNetworthAsOn() {
        return networthAsOn;
    }

    public void setNetworthAsOn(String networthAsOn) {
        this.networthAsOn = networthAsOn;
    }

    public String getOtherIdentity() {
        return otherIdentity;
    }

    public void setOtherIdentity(String otherIdentity) {
        this.otherIdentity = otherIdentity;
    }

    public String getPoa() {
        return poa;
    }

    public void setPoa(String poa) {
        this.poa = poa;
    }

    public String getStrFlag() {
        return strFlag;
    }

    public void setStrFlag(String strFlag) {
        this.strFlag = strFlag;
    }

    public String getPerAddType() {
        return perAddType;
    }

    public void setPerAddType(String perAddType) {
        this.perAddType = perAddType;
    }

    public String getMailAddType() {
        return mailAddType;
    }

    public void setMailAddType(String mailAddType) {
        this.mailAddType = mailAddType;
    }

    public String getMailPoa() {
        return mailPoa;
    }

    public void setMailPoa(String mailPoa) {
        this.mailPoa = mailPoa;
    }

    public String getJuriAddBasedOnFlag() {
        return juriAddBasedOnFlag;
    }

    public void setJuriAddBasedOnFlag(String juriAddBasedOnFlag) {
        this.juriAddBasedOnFlag = juriAddBasedOnFlag;
    }

    public String getJuriAddType() {
        return juriAddType;
    }

    public void setJuriAddType(String juriAddType) {
        this.juriAddType = juriAddType;
    }

    public String getJuriPoa() {
        return juriPoa;
    }

    public void setJuriPoa(String juriPoa) {
        this.juriPoa = juriPoa;
    }

    public String getCustEntityType() {
        return custEntityType;
    }

    public void setCustEntityType(String custEntityType) {
        this.custEntityType = custEntityType;
    }

    public String getAcHolderTypeFlag() {
        return acHolderTypeFlag;
    }

    public void setAcHolderTypeFlag(String acHolderTypeFlag) {
        this.acHolderTypeFlag = acHolderTypeFlag;
    }

    public String getAcHolderType() {
        return acHolderType;
    }

    public void setAcHolderType(String acHolderType) {
        this.acHolderType = acHolderType;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getcKYCNo() {
        return cKYCNo;
    }

    public void setcKYCNo(String cKYCNo) {
        this.cKYCNo = cKYCNo;
    }

    public String getFatherMiddleName() {
        return fatherMiddleName;
    }

    public void setFatherMiddleName(String fatherMiddleName) {
        this.fatherMiddleName = fatherMiddleName;
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getSpouseMiddleName() {
        return spouseMiddleName;
    }

    public void setSpouseMiddleName(String spouseMiddleName) {
        this.spouseMiddleName = spouseMiddleName;
    }

    public String getSpouseLastName() {
        return spouseLastName;
    }

    public void setSpouseLastName(String spouseLastName) {
        this.spouseLastName = spouseLastName;
    }

    public String getMotherMiddleName() {
        return motherMiddleName;
    }

    public void setMotherMiddleName(String motherMiddleName) {
        this.motherMiddleName = motherMiddleName;
    }

    public String getMotherLastName() {
        return motherLastName;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public String getTinIssuingCountry() {
        return tinIssuingCountry;
    }

    public void setTinIssuingCountry(String tinIssuingCountry) {
        this.tinIssuingCountry = tinIssuingCountry;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getIdExpiryDate() {
        return idExpiryDate;
    }

    public void setIdExpiryDate(String idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    public String getPerMailAddSameFlagIndicate() {
        return perMailAddSameFlagIndicate;
    }

    public void setPerMailAddSameFlagIndicate(String perMailAddSameFlagIndicate) {
        this.perMailAddSameFlagIndicate = perMailAddSameFlagIndicate;
    }

    public String getPerDistrict() {
        return perDistrict;
    }

    public void setPerDistrict(String perDistrict) {
        this.perDistrict = perDistrict;
    }

    public String getMailDistrict() {
        return mailDistrict;
    }

    public void setMailDistrict(String mailDistrict) {
        this.mailDistrict = mailDistrict;
    }

    public String getEmpDistrict() {
        return empDistrict;
    }

    public void setEmpDistrict(String empDistrict) {
        this.empDistrict = empDistrict;
    }

    public String getPerOtherPOA() {
        return perOtherPOA;
    }

    public void setPerOtherPOA(String perOtherPOA) {
        this.perOtherPOA = perOtherPOA;
    }

    public String getMailOtherPOA() {
        return mailOtherPOA;
    }

    public void setMailOtherPOA(String mailOtherPOA) {
        this.mailOtherPOA = mailOtherPOA;
    }

    public String getJuriOtherPOA() {
        return juriOtherPOA;
    }

    public void setJuriOtherPOA(String juriOtherPOA) {
        this.juriOtherPOA = juriOtherPOA;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getJuriDistrict() {
        return juriDistrict;
    }

    public void setJuriDistrict(String juriDistrict) {
        this.juriDistrict = juriDistrict;
    }

    public String getFatherSpouseFlag() {
        return fatherSpouseFlag;
    }

    public void setFatherSpouseFlag(String fatherSpouseFlag) {
        this.fatherSpouseFlag = fatherSpouseFlag;
    }

    public String getCustImage() {
        return custImage;
    }

    public void setCustImage(String custImage) {
        this.custImage = custImage;
    }

    public String getCustFullName() {
        return custFullName;
    }

    public void setCustFullName(String custFullName) {
        this.custFullName = custFullName;
    }

    public String getGstIdentificationNumber() {
        return gstIdentificationNumber;
    }

    public void setGstIdentificationNumber(String gstIdentificationNumber) {
        this.gstIdentificationNumber = gstIdentificationNumber;
    }

    public String getAadhaarMode() {
        return aadhaarMode;
    }

    public void setAadhaarMode(String aadhaarMode) {
        this.aadhaarMode = aadhaarMode;
    }

    public String getAadhaarBankIin() {
        return aadhaarBankIin;
    }

    public void setAadhaarBankIin(String aadhaarBankIin) {
        this.aadhaarBankIin = aadhaarBankIin;
    }

    public String getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
    }

    

    
}
