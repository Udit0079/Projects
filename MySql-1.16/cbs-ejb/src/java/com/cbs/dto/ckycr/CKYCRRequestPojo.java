/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.ckycr;

public class CKYCRRequestPojo {

    //Added By Manish
    private int sno;
    private String customerIdOrCKYCRNo;
    private String custName;
    private String dateOfBirth;
    private String fatherName;
    private String gender;
    private String maritalStatus;
    private String primaryBrCode;
    private String reason;
    private String reasonCode;
    private boolean checkBox;
    private String primaryBrName;
    private String uploadedFileName;
    private String uploadedGenDate;
    //Added By Dayanand Sagar
    private String type;
    private String status;
    //Added by Dinesh Pratap Singh
    private String mode;
    private String customerId;
    private String requestBy;
    private String requestDate;
    private String fetchMode;
    private String custEntityType;
    private String acHolderTypeFlag;
    private String acHolderType;
    private String acType;
    private String ckycNo;
    private String title;
    private String middleName;
    private String lastName;
    private String fatherSpouseFlag;
    private String fatherMiddleName;
    private String fatherLastName;
    private String spouseName;
    private String spouseMiddleName;
    private String spouseLastName;
    private String motherName;
    private String motherMiddleName;
    private String motherLastName;
    private String nationality;
    private String legalDocument;
    private String identityNo;
    private String idExpiryDate;
    private String tinIssuingCountry;
    private String nriFlag;
    private String perAddType;
    private String perAddressLineOne;
    private String perVillage;
    private String perDistrict;
    private String perStateCode;
    private String perCountryCode;
    private String perPostalCode;
    private String poa;
    private String perOtherPoa;
    private String perMailAddSameFlagIndicate;
    private String mailAddType;
    private String mailAddressLineOne;
    private String mailVillage;
    private String mailDistrict;
    private String mailStateCode;
    private String mailCountryCode;
    private String mailPostalCode;
    private String mailPoa;
    private String mailOtherPoa;
    private String juriAddBasedOnFlag;
    private String juriAddType;
    private String juriAddOne;
    private String juriCity;
    private String juriState;
    private String juriCountry;
    private String juriPostal;
    private String juriPoa;
    private String juriOtherPoa;
    private String isdCode;
    private String mobileNo;
    private String creationTime;
    private String constitutionCode;
    private String occupationCode;
    private String incorporationPlace;
    private String commencementDate;
    private String misJuriResidence;
    private String misTin;
    private String misBirthCountry;
    private String misCity;
    private String typeOfDocSubmitted;
    private String kycVerifiedUserName;
    private String brnCode;
    private String kycVerifiedBy;
    private String panGirNumber;
    private String countryIncorporation;
    private String countryResidenceTaxLaws;
    private String customerUpdationDate;
    private String kycVerifiedDate;
    private String custImage;
    //Update Flags Added By Dinesh
    private String applicantNameUpdateFlag;
    private String personalEntityUpdateFlag;
    private String addressUpdateFlag;
    private String contactUpdateFlag;
    private String remarksUpdateFlag;
    private String kycVerificationUpdateFlag;
    private String identityUpdateFlag;
    private String relatedPersonUpdateFlag;
    private String controllingPersonUpdateFlag;
    private String imageUpdateFlag;
    private boolean selected;
    //Related Info Detail
    private String minorFlag;
    private String relatedCkycNo;
    private String relationType;
    private String relatedAdditionFlag;
    private String relatedNamePrefix;
    private String relatedFirstName;
    private String relatedMiddleName;
    private String relatedLastName;

    public String getMinorFlag() {
        return minorFlag;
    }

    public void setMinorFlag(String minorFlag) {
        this.minorFlag = minorFlag;
    }

    public String getRelatedCkycNo() {
        return relatedCkycNo;
    }

    public void setRelatedCkycNo(String relatedCkycNo) {
        this.relatedCkycNo = relatedCkycNo;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getRelatedAdditionFlag() {
        return relatedAdditionFlag;
    }

    public void setRelatedAdditionFlag(String relatedAdditionFlag) {
        this.relatedAdditionFlag = relatedAdditionFlag;
    }

    public String getRelatedNamePrefix() {
        return relatedNamePrefix;
    }

    public void setRelatedNamePrefix(String relatedNamePrefix) {
        this.relatedNamePrefix = relatedNamePrefix;
    }

    public String getRelatedFirstName() {
        return relatedFirstName;
    }

    public void setRelatedFirstName(String relatedFirstName) {
        this.relatedFirstName = relatedFirstName;
    }

    public String getRelatedMiddleName() {
        return relatedMiddleName;
    }

    public void setRelatedMiddleName(String relatedMiddleName) {
        this.relatedMiddleName = relatedMiddleName;
    }

    public String getRelatedLastName() {
        return relatedLastName;
    }

    public void setRelatedLastName(String relatedLastName) {
        this.relatedLastName = relatedLastName;
    }

    public String getCustomerIdOrCKYCRNo() {
        return customerIdOrCKYCRNo;
    }

    public void setCustomerIdOrCKYCRNo(String customerIdOrCKYCRNo) {
        this.customerIdOrCKYCRNo = customerIdOrCKYCRNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPrimaryBrCode() {
        return primaryBrCode;
    }

    public void setPrimaryBrCode(String primaryBrCode) {
        this.primaryBrCode = primaryBrCode;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getFetchMode() {
        return fetchMode;
    }

    public void setFetchMode(String fetchMode) {
        this.fetchMode = fetchMode;
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

    public String getCkycNo() {
        return ckycNo;
    }

    public void setCkycNo(String ckycNo) {
        this.ckycNo = ckycNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
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

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLegalDocument() {
        return legalDocument;
    }

    public void setLegalDocument(String legalDocument) {
        this.legalDocument = legalDocument;
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

    public String getTinIssuingCountry() {
        return tinIssuingCountry;
    }

    public void setTinIssuingCountry(String tinIssuingCountry) {
        this.tinIssuingCountry = tinIssuingCountry;
    }

    public String getNriFlag() {
        return nriFlag;
    }

    public void setNriFlag(String nriFlag) {
        this.nriFlag = nriFlag;
    }

    public String getPerAddType() {
        return perAddType;
    }

    public void setPerAddType(String perAddType) {
        this.perAddType = perAddType;
    }

    public String getPerAddressLineOne() {
        return perAddressLineOne;
    }

    public void setPerAddressLineOne(String perAddressLineOne) {
        this.perAddressLineOne = perAddressLineOne;
    }

    public String getPerVillage() {
        return perVillage;
    }

    public void setPerVillage(String perVillage) {
        this.perVillage = perVillage;
    }

    public String getPerDistrict() {
        return perDistrict;
    }

    public void setPerDistrict(String perDistrict) {
        this.perDistrict = perDistrict;
    }

    public String getPerStateCode() {
        return perStateCode;
    }

    public void setPerStateCode(String perStateCode) {
        this.perStateCode = perStateCode;
    }

    public String getPerCountryCode() {
        return perCountryCode;
    }

    public void setPerCountryCode(String perCountryCode) {
        this.perCountryCode = perCountryCode;
    }

    public String getPerPostalCode() {
        return perPostalCode;
    }

    public void setPerPostalCode(String perPostalCode) {
        this.perPostalCode = perPostalCode;
    }

    public String getPoa() {
        return poa;
    }

    public void setPoa(String poa) {
        this.poa = poa;
    }

    public String getPerOtherPoa() {
        return perOtherPoa;
    }

    public void setPerOtherPoa(String perOtherPoa) {
        this.perOtherPoa = perOtherPoa;
    }

    public String getPerMailAddSameFlagIndicate() {
        return perMailAddSameFlagIndicate;
    }

    public void setPerMailAddSameFlagIndicate(String perMailAddSameFlagIndicate) {
        this.perMailAddSameFlagIndicate = perMailAddSameFlagIndicate;
    }

    public String getMailAddType() {
        return mailAddType;
    }

    public void setMailAddType(String mailAddType) {
        this.mailAddType = mailAddType;
    }

    public String getMailAddressLineOne() {
        return mailAddressLineOne;
    }

    public void setMailAddressLineOne(String mailAddressLineOne) {
        this.mailAddressLineOne = mailAddressLineOne;
    }

    public String getMailVillage() {
        return mailVillage;
    }

    public void setMailVillage(String mailVillage) {
        this.mailVillage = mailVillage;
    }

    public String getMailDistrict() {
        return mailDistrict;
    }

    public void setMailDistrict(String mailDistrict) {
        this.mailDistrict = mailDistrict;
    }

    public String getMailStateCode() {
        return mailStateCode;
    }

    public void setMailStateCode(String mailStateCode) {
        this.mailStateCode = mailStateCode;
    }

    public String getMailCountryCode() {
        return mailCountryCode;
    }

    public void setMailCountryCode(String mailCountryCode) {
        this.mailCountryCode = mailCountryCode;
    }

    public String getMailPostalCode() {
        return mailPostalCode;
    }

    public void setMailPostalCode(String mailPostalCode) {
        this.mailPostalCode = mailPostalCode;
    }

    public String getMailPoa() {
        return mailPoa;
    }

    public void setMailPoa(String mailPoa) {
        this.mailPoa = mailPoa;
    }

    public String getMailOtherPoa() {
        return mailOtherPoa;
    }

    public void setMailOtherPoa(String mailOtherPoa) {
        this.mailOtherPoa = mailOtherPoa;
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

    public String getJuriAddOne() {
        return juriAddOne;
    }

    public void setJuriAddOne(String juriAddOne) {
        this.juriAddOne = juriAddOne;
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

    public String getJuriCountry() {
        return juriCountry;
    }

    public void setJuriCountry(String juriCountry) {
        this.juriCountry = juriCountry;
    }

    public String getJuriPostal() {
        return juriPostal;
    }

    public void setJuriPostal(String juriPostal) {
        this.juriPostal = juriPostal;
    }

    public String getJuriPoa() {
        return juriPoa;
    }

    public void setJuriPoa(String juriPoa) {
        this.juriPoa = juriPoa;
    }

    public String getJuriOtherPoa() {
        return juriOtherPoa;
    }

    public void setJuriOtherPoa(String juriOtherPoa) {
        this.juriOtherPoa = juriOtherPoa;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getConstitutionCode() {
        return constitutionCode;
    }

    public void setConstitutionCode(String constitutionCode) {
        this.constitutionCode = constitutionCode;
    }

    public String getOccupationCode() {
        return occupationCode;
    }

    public void setOccupationCode(String occupationCode) {
        this.occupationCode = occupationCode;
    }

    public String getIncorporationPlace() {
        return incorporationPlace;
    }

    public void setIncorporationPlace(String incorporationPlace) {
        this.incorporationPlace = incorporationPlace;
    }

    public String getCommencementDate() {
        return commencementDate;
    }

    public void setCommencementDate(String commencementDate) {
        this.commencementDate = commencementDate;
    }

    public String getMisJuriResidence() {
        return misJuriResidence;
    }

    public void setMisJuriResidence(String misJuriResidence) {
        this.misJuriResidence = misJuriResidence;
    }

    public String getMisTin() {
        return misTin;
    }

    public void setMisTin(String misTin) {
        this.misTin = misTin;
    }

    public String getMisBirthCountry() {
        return misBirthCountry;
    }

    public void setMisBirthCountry(String misBirthCountry) {
        this.misBirthCountry = misBirthCountry;
    }

    public String getMisCity() {
        return misCity;
    }

    public void setMisCity(String misCity) {
        this.misCity = misCity;
    }

    public String getTypeOfDocSubmitted() {
        return typeOfDocSubmitted;
    }

    public void setTypeOfDocSubmitted(String typeOfDocSubmitted) {
        this.typeOfDocSubmitted = typeOfDocSubmitted;
    }

    public String getKycVerifiedUserName() {
        return kycVerifiedUserName;
    }

    public void setKycVerifiedUserName(String kycVerifiedUserName) {
        this.kycVerifiedUserName = kycVerifiedUserName;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public String getKycVerifiedBy() {
        return kycVerifiedBy;
    }

    public void setKycVerifiedBy(String kycVerifiedBy) {
        this.kycVerifiedBy = kycVerifiedBy;
    }

    public String getApplicantNameUpdateFlag() {
        return applicantNameUpdateFlag;
    }

    public void setApplicantNameUpdateFlag(String applicantNameUpdateFlag) {
        this.applicantNameUpdateFlag = applicantNameUpdateFlag;
    }

    public String getPersonalEntityUpdateFlag() {
        return personalEntityUpdateFlag;
    }

    public void setPersonalEntityUpdateFlag(String personalEntityUpdateFlag) {
        this.personalEntityUpdateFlag = personalEntityUpdateFlag;
    }

    public String getAddressUpdateFlag() {
        return addressUpdateFlag;
    }

    public void setAddressUpdateFlag(String addressUpdateFlag) {
        this.addressUpdateFlag = addressUpdateFlag;
    }

    public String getContactUpdateFlag() {
        return contactUpdateFlag;
    }

    public void setContactUpdateFlag(String contactUpdateFlag) {
        this.contactUpdateFlag = contactUpdateFlag;
    }

    public String getRemarksUpdateFlag() {
        return remarksUpdateFlag;
    }

    public void setRemarksUpdateFlag(String remarksUpdateFlag) {
        this.remarksUpdateFlag = remarksUpdateFlag;
    }

    public String getKycVerificationUpdateFlag() {
        return kycVerificationUpdateFlag;
    }

    public void setKycVerificationUpdateFlag(String kycVerificationUpdateFlag) {
        this.kycVerificationUpdateFlag = kycVerificationUpdateFlag;
    }

    public String getIdentityUpdateFlag() {
        return identityUpdateFlag;
    }

    public void setIdentityUpdateFlag(String identityUpdateFlag) {
        this.identityUpdateFlag = identityUpdateFlag;
    }

    public String getRelatedPersonUpdateFlag() {
        return relatedPersonUpdateFlag;
    }

    public void setRelatedPersonUpdateFlag(String relatedPersonUpdateFlag) {
        this.relatedPersonUpdateFlag = relatedPersonUpdateFlag;
    }

    public String getControllingPersonUpdateFlag() {
        return controllingPersonUpdateFlag;
    }

    public void setControllingPersonUpdateFlag(String controllingPersonUpdateFlag) {
        this.controllingPersonUpdateFlag = controllingPersonUpdateFlag;
    }

    public String getImageUpdateFlag() {
        return imageUpdateFlag;
    }

    public void setImageUpdateFlag(String imageUpdateFlag) {
        this.imageUpdateFlag = imageUpdateFlag;
    }

    public String getFatherSpouseFlag() {
        return fatherSpouseFlag;
    }

    public void setFatherSpouseFlag(String fatherSpouseFlag) {
        this.fatherSpouseFlag = fatherSpouseFlag;
    }

    public String getPanGirNumber() {
        return panGirNumber;
    }

    public void setPanGirNumber(String panGirNumber) {
        this.panGirNumber = panGirNumber;
    }

    public String getCountryIncorporation() {
        return countryIncorporation;
    }

    public void setCountryIncorporation(String countryIncorporation) {
        this.countryIncorporation = countryIncorporation;
    }

    public String getCountryResidenceTaxLaws() {
        return countryResidenceTaxLaws;
    }

    public void setCountryResidenceTaxLaws(String countryResidenceTaxLaws) {
        this.countryResidenceTaxLaws = countryResidenceTaxLaws;
    }

    public String getCustomerUpdationDate() {
        return customerUpdationDate;
    }

    public void setCustomerUpdationDate(String customerUpdationDate) {
        this.customerUpdationDate = customerUpdationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getKycVerifiedDate() {
        return kycVerifiedDate;
    }

    public void setKycVerifiedDate(String kycVerifiedDate) {
        this.kycVerifiedDate = kycVerifiedDate;
    }

    public String getCustImage() {
        return custImage;
    }

    public void setCustImage(String custImage) {
        this.custImage = custImage;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String getPrimaryBrName() {
        return primaryBrName;
    }

    public void setPrimaryBrName(String primaryBrName) {
        this.primaryBrName = primaryBrName;
    }

    public String getUploadedFileName() {
        return uploadedFileName;
    }

    public void setUploadedFileName(String uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
    }

    public String getUploadedGenDate() {
        return uploadedGenDate;
    }

    public void setUploadedGenDate(String uploadedGenDate) {
        this.uploadedGenDate = uploadedGenDate;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
