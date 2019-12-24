/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.DataBankSearch;
import com.cbs.web.controller.BaseBean;
import java.text.ParseException;
import java.util.Iterator;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrDataPrevCompPKTO;
import com.hrms.common.to.HrDataPrevCompTO;
import com.hrms.common.to.HrDataQualPKTO;
import com.hrms.common.to.HrDataQualTO;
import com.hrms.common.to.HrDataReferencePKTO;
import com.hrms.common.to.HrDataReferenceTO;
import com.hrms.common.to.HrDatabankPKTO;
import com.hrms.common.to.HrDatabankTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.DatabankDelegate;
import com.hrms.web.delegate.RecruitmentDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author Zeeshan Waris
 */
public class CreationOfDatabank extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(CreationOfDatabank.class);
    private String message;
    private String message1;
    private String advertisementNo;
    private String candidateId;
    private String candidateFirstName;
    private String candidateMidName;
    private String candidateLastName;
    private String jobSpecification;
    private String postAppliedFirst;
    private String postAppliedSecond;
    private String location;
    private String zone;
    private Date dateOfBirth;
    private String sex;
    private String contactNoFirst;
    private String contactNoSecond;
    private String faxFirst;
    private String faxSecond;
    private String emialfirst;
    private String emailSecond;
    private String advertisementNoPop;
    private String compCode = "0";
    private String candId;
    private String contantAddressMore;
    private String contantCityMore;
    private String contantStateMore;
    private String contantPinMore;
    private String permanentAddressMore;
    private String permanentcityMore;
    private String permanentStateMore;
    private String permanetPinMore;
    private String specializationfirstMore;
    private String specializationSecondMore;
    private String consultantNameMore;
    private String skillTypeMore;
    private String organisationTypeMore;
    private String nameMore;
    private String occupationMore;
    private String designationMore;
    private String organisationMore;
    private String phoneMore;
    private String companyNameJobDetails;
    private String designationJobDetails;
    private String totalExperienceJobDetails;
    private String relatedExperienceJobDetails;
    private String currentSalaryJobDetails;
    private String expectedSalaryJobDetails;
    private String slaaryOnjoingJobDetails;
    private String lenthOfServiceJobDetails;
    private String designationOnJoiningJobDetails;
    private String companyAddressJobDetails;
    private String companyAddressJobDetails1;
    private String cityJobDetails;
    private String stateJobDetails;
    private String pinJobDetails;
    private String examinationQualification;
    private String instituteQualification;
    private String subjectsQualification;
    private String specializationQualification;
    private String marksQualification;
    private String yearOfPassingQualification;
    private String divisionQualification;
    private String companyNameExperience;
    private String companyAddressExperience;
    private Date durationFromExperience;
    private Date durationToExperience;
    private String designationOfJoiningExperience;
    private String designationOfLeavingExperience;
    private String employeeUnderExperience;
    private String totalEmployeeExperience;
    private String salaryOnJoiningExperience;
    private String salaryOnLeavingExperience;
    private String annualTurnoverExperience;
    private Date dateOfLeavingExperience;
    private String reasonOfLeavingExperience;
    private String freeDayMoreDetails;
    private String joiningPeriodMoreDetails;
    private String nationalityMoreDetails;
    private String relagionMoreDetails;
    private String bloodGroupMoreDetails;
    private String membershipMoreDetails;
    private String individualMoreDetails;
    private String professionalMoreDetails;
    private String passportNoMoreDetails;
    private Date expiryDateMoreDetails;
    private String visadetailsMoreDetails;
    private String selectionTypeMoreDetails;
    private String maritalStatusMoreDetails;
    private String earlierCompanyMoreDetails;
    private String previousEmployeeNoMoreDetails;
    private Date durationFromMoreDetails;
    private Date durationToMoreDetails;
    private String nameOfUnitMoreDetails;
    private String departmentMoreDetails;
    private String positionHeldMoreDetails;
    private String salaryDrawnMoreDetails;
    private String reasonForLeavingMoreDetails;
    private String referanceNameReferanceDetails;
    private String occupationReferanceDetails;
    private String addressReferanceDetails;
    private String cityReferanceDetails;
    private String stateReferanceDetails;
    private String pinReferanceDetails;
    private String phoneReferanceDetails;
    private List<SelectItem> postAppliedFirstList;
    private List<SelectItem> postAppliedSecondList;
    private List<SelectItem> zoneList;
    private List<SelectItem> locationList;
    private List<SelectItem> sexList;
    private List<SelectItem> specializationFirstList;
    private List<SelectItem> specializationSecondList;
    private List<SelectItem> consultantNameList;
    private List<SelectItem> skillTypeList;
    private List<SelectItem> organizationTypeList;
    private List<SelectItem> nameList;
    private List<SelectItem> occuationList;
    private List<SelectItem> organisationList;
    private List<SelectItem> phoneList;
    private List<SelectItem> examinationList;
    private List<SelectItem> specializationList;
    private List<SelectItem> freeDayList;
    private List<SelectItem> relegionList;
    private List<SelectItem> bloodgroupList;
    private List<SelectItem> selectionTypeList;
    private List<SelectItem> maritalStatusList;
    private List<SelectItem> earliercompanyEmployeetList;
    private List<SelectItem> departmentList;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#0.00");
    private List<DataBankAdvertisement> advo;
    private DataBankAdvertisement currentItem1 = new DataBankAdvertisement();
    private boolean disableAdvertisement;
    private List<DataBankSearch> search;
    private DataBankSearch currentItem2 = new DataBankSearch();
    private boolean flag;
    private String mode;
    private boolean candidateIdDisable;
    private boolean disableUpdate;
    Validator valid = new Validator();
    private Integer defaultComp;
    private String operation;
    private List<SelectItem> operationList;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<SelectItem> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<SelectItem> operationList) {
        this.operationList = operationList;
    }

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
    }

    public boolean isCandidateIdDisable() {
        return candidateIdDisable;
    }

    public void setCandidateIdDisable(boolean candidateIdDisable) {
        this.candidateIdDisable = candidateIdDisable;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCompanyAddressJobDetails1() {
        return companyAddressJobDetails1;
    }

    public void setCompanyAddressJobDetails1(String companyAddressJobDetails1) {
        this.companyAddressJobDetails1 = companyAddressJobDetails1;
    }

    public List<SelectItem> getEarliercompanyEmployeetList() {
        return earliercompanyEmployeetList;
    }

    public void setEarliercompanyEmployeetList(List<SelectItem> earliercompanyEmployeetList) {
        this.earliercompanyEmployeetList = earliercompanyEmployeetList;
    }

    public String getAddressReferanceDetails() {
        return addressReferanceDetails;
    }

    public void setAddressReferanceDetails(String addressReferanceDetails) {
        this.addressReferanceDetails = addressReferanceDetails;
    }

    public String getAnnualTurnoverExperience() {
        return annualTurnoverExperience;
    }

    public void setAnnualTurnoverExperience(String annualTurnoverExperience) {
        this.annualTurnoverExperience = annualTurnoverExperience;
    }

    public String getBloodGroupMoreDetails() {
        return bloodGroupMoreDetails;
    }

    public void setBloodGroupMoreDetails(String bloodGroupMoreDetails) {
        this.bloodGroupMoreDetails = bloodGroupMoreDetails;
    }

    public List<SelectItem> getBloodgroupList() {
        return bloodgroupList;
    }

    public void setBloodgroupList(List<SelectItem> bloodgroupList) {
        this.bloodgroupList = bloodgroupList;
    }

    public String getCityJobDetails() {
        return cityJobDetails;
    }

    public void setCityJobDetails(String cityJobDetails) {
        this.cityJobDetails = cityJobDetails;
    }

    public String getCityReferanceDetails() {
        return cityReferanceDetails;
    }

    public void setCityReferanceDetails(String cityReferanceDetails) {
        this.cityReferanceDetails = cityReferanceDetails;
    }

    public String getCompanyAddressExperience() {
        return companyAddressExperience;
    }

    public void setCompanyAddressExperience(String companyAddressExperience) {
        this.companyAddressExperience = companyAddressExperience;
    }

    public String getCompanyAddressJobDetails() {
        return companyAddressJobDetails;
    }

    public void setCompanyAddressJobDetails(String companyAddressJobDetails) {
        this.companyAddressJobDetails = companyAddressJobDetails;
    }

    public String getCompanyNameExperience() {
        return companyNameExperience;
    }

    public void setCompanyNameExperience(String companyNameExperience) {
        this.companyNameExperience = companyNameExperience;
    }

    public String getCompanyNameJobDetails() {
        return companyNameJobDetails;
    }

    public void setCompanyNameJobDetails(String companyNameJobDetails) {
        this.companyNameJobDetails = companyNameJobDetails;
    }

    public List<SelectItem> getConsultantNameList() {
        return consultantNameList;
    }

    public void setConsultantNameList(List<SelectItem> consultantNameList) {
        this.consultantNameList = consultantNameList;
    }

    public String getConsultantNameMore() {
        return consultantNameMore;
    }

    public void setConsultantNameMore(String consultantNameMore) {
        this.consultantNameMore = consultantNameMore;
    }

    public String getContantAddressMore() {
        return contantAddressMore;
    }

    public void setContantAddressMore(String contantAddressMore) {
        this.contantAddressMore = contantAddressMore;
    }

    public String getContantCityMore() {
        return contantCityMore;
    }

    public void setContantCityMore(String contantCityMore) {
        this.contantCityMore = contantCityMore;
    }

    public String getContantPinMore() {
        return contantPinMore;
    }

    public void setContantPinMore(String contantPinMore) {
        this.contantPinMore = contantPinMore;
    }

    public String getContantStateMore() {
        return contantStateMore;
    }

    public void setContantStateMore(String contantStateMore) {
        this.contantStateMore = contantStateMore;
    }

    public String getCurrentSalaryJobDetails() {
        return currentSalaryJobDetails;
    }

    public void setCurrentSalaryJobDetails(String currentSalaryJobDetails) {
        this.currentSalaryJobDetails = currentSalaryJobDetails;
    }

    public List<SelectItem> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<SelectItem> departmentList) {
        this.departmentList = departmentList;
    }

    public String getDepartmentMoreDetails() {
        return departmentMoreDetails;
    }

    public void setDepartmentMoreDetails(String departmentMoreDetails) {
        this.departmentMoreDetails = departmentMoreDetails;
    }

    public String getDesignationJobDetails() {
        return designationJobDetails;
    }

    public void setDesignationJobDetails(String designationJobDetails) {
        this.designationJobDetails = designationJobDetails;
    }

    public String getDesignationMore() {
        return designationMore;
    }

    public void setDesignationMore(String designationMore) {
        this.designationMore = designationMore;
    }

    public String getDesignationOfJoiningExperience() {
        return designationOfJoiningExperience;
    }

    public void setDesignationOfJoiningExperience(String designationOfJoiningExperience) {
        this.designationOfJoiningExperience = designationOfJoiningExperience;
    }

    public String getDesignationOfLeavingExperience() {
        return designationOfLeavingExperience;
    }

    public void setDesignationOfLeavingExperience(String designationOfLeavingExperience) {
        this.designationOfLeavingExperience = designationOfLeavingExperience;
    }

    public String getDesignationOnJoiningJobDetails() {
        return designationOnJoiningJobDetails;
    }

    public void setDesignationOnJoiningJobDetails(String designationOnJoiningJobDetails) {
        this.designationOnJoiningJobDetails = designationOnJoiningJobDetails;
    }

    public String getDivisionQualification() {
        return divisionQualification;
    }

    public void setDivisionQualification(String divisionQualification) {
        this.divisionQualification = divisionQualification;
    }

    public Date getDurationFromMoreDetails() {
        return durationFromMoreDetails;
    }

    public void setDurationFromMoreDetails(Date durationFromMoreDetails) {
        this.durationFromMoreDetails = durationFromMoreDetails;
    }

    public Date getDateOfLeavingExperience() {
        return dateOfLeavingExperience;
    }

    public void setDateOfLeavingExperience(Date dateOfLeavingExperience) {
        this.dateOfLeavingExperience = dateOfLeavingExperience;
    }

    public Date getDurationFromExperience() {
        return durationFromExperience;
    }

    public void setDurationFromExperience(Date durationFromExperience) {
        this.durationFromExperience = durationFromExperience;
    }

    public Date getDurationToExperience() {
        return durationToExperience;
    }

    public void setDurationToExperience(Date durationToExperience) {
        this.durationToExperience = durationToExperience;
    }

    public Date getDurationToMoreDetails() {
        return durationToMoreDetails;
    }

    public void setDurationToMoreDetails(Date durationToMoreDetails) {
        this.durationToMoreDetails = durationToMoreDetails;
    }

    public String getEarlierCompanyMoreDetails() {
        return earlierCompanyMoreDetails;
    }

    public void setEarlierCompanyMoreDetails(String earlierCompanyMoreDetails) {
        this.earlierCompanyMoreDetails = earlierCompanyMoreDetails;
    }

    public String getEmployeeUnderExperience() {
        return employeeUnderExperience;
    }

    public void setEmployeeUnderExperience(String employeeUnderExperience) {
        this.employeeUnderExperience = employeeUnderExperience;
    }

    public List<SelectItem> getExaminationList() {
        return examinationList;
    }

    public void setExaminationList(List<SelectItem> examinationList) {
        this.examinationList = examinationList;
    }

    public String getExaminationQualification() {
        return examinationQualification;
    }

    public void setExaminationQualification(String examinationQualification) {
        this.examinationQualification = examinationQualification;
    }

    public String getExpectedSalaryJobDetails() {
        return expectedSalaryJobDetails;
    }

    public void setExpectedSalaryJobDetails(String expectedSalaryJobDetails) {
        this.expectedSalaryJobDetails = expectedSalaryJobDetails;
    }

    public Date getExpiryDateMoreDetails() {
        return expiryDateMoreDetails;
    }

    public void setExpiryDateMoreDetails(Date expiryDateMoreDetails) {
        this.expiryDateMoreDetails = expiryDateMoreDetails;
    }

    public List<SelectItem> getFreeDayList() {
        return freeDayList;
    }

    public void setFreeDayList(List<SelectItem> freeDayList) {
        this.freeDayList = freeDayList;
    }

    public String getFreeDayMoreDetails() {
        return freeDayMoreDetails;
    }

    public void setFreeDayMoreDetails(String freeDayMoreDetails) {
        this.freeDayMoreDetails = freeDayMoreDetails;
    }

    public String getIndividualMoreDetails() {
        return individualMoreDetails;
    }

    public void setIndividualMoreDetails(String individualMoreDetails) {
        this.individualMoreDetails = individualMoreDetails;
    }

    public String getInstituteQualification() {
        return instituteQualification;
    }

    public void setInstituteQualification(String instituteQualification) {
        this.instituteQualification = instituteQualification;
    }

    public String getJoiningPeriodMoreDetails() {
        return joiningPeriodMoreDetails;
    }

    public void setJoiningPeriodMoreDetails(String joiningPeriodMoreDetails) {
        this.joiningPeriodMoreDetails = joiningPeriodMoreDetails;
    }

    public String getLenthOfServiceJobDetails() {
        return lenthOfServiceJobDetails;
    }

    public void setLenthOfServiceJobDetails(String lenthOfServiceJobDetails) {
        this.lenthOfServiceJobDetails = lenthOfServiceJobDetails;
    }

    public List<SelectItem> getMaritalStatusList() {
        return maritalStatusList;
    }

    public void setMaritalStatusList(List<SelectItem> maritalStatusList) {
        this.maritalStatusList = maritalStatusList;
    }

    public String getMaritalStatusMoreDetails() {
        return maritalStatusMoreDetails;
    }

    public void setMaritalStatusMoreDetails(String maritalStatusMoreDetails) {
        this.maritalStatusMoreDetails = maritalStatusMoreDetails;
    }

    public String getMarksQualification() {
        return marksQualification;
    }

    public void setMarksQualification(String marksQualification) {
        this.marksQualification = marksQualification;
    }

    public String getMembershipMoreDetails() {
        return membershipMoreDetails;
    }

    public void setMembershipMoreDetails(String membershipMoreDetails) {
        this.membershipMoreDetails = membershipMoreDetails;
    }

    public List<SelectItem> getNameList() {
        return nameList;
    }

    public void setNameList(List<SelectItem> nameList) {
        this.nameList = nameList;
    }

    public String getNameMore() {
        return nameMore;
    }

    public void setNameMore(String nameMore) {
        this.nameMore = nameMore;
    }

    public String getNameOfUnitMoreDetails() {
        return nameOfUnitMoreDetails;
    }

    public void setNameOfUnitMoreDetails(String nameOfUnitMoreDetails) {
        this.nameOfUnitMoreDetails = nameOfUnitMoreDetails;
    }

    public String getNationalityMoreDetails() {
        return nationalityMoreDetails;
    }

    public void setNationalityMoreDetails(String nationalityMoreDetails) {
        this.nationalityMoreDetails = nationalityMoreDetails;
    }

    public List<SelectItem> getOccuationList() {
        return occuationList;
    }

    public void setOccuationList(List<SelectItem> occuationList) {
        this.occuationList = occuationList;
    }

    public String getOccupationMore() {
        return occupationMore;
    }

    public void setOccupationMore(String occupationMore) {
        this.occupationMore = occupationMore;
    }

    public String getOccupationReferanceDetails() {
        return occupationReferanceDetails;
    }

    public void setOccupationReferanceDetails(String occupationReferanceDetails) {
        this.occupationReferanceDetails = occupationReferanceDetails;
    }

    public List<SelectItem> getOrganisationList() {
        return organisationList;
    }

    public void setOrganisationList(List<SelectItem> organisationList) {
        this.organisationList = organisationList;
    }

    public String getOrganisationMore() {
        return organisationMore;
    }

    public void setOrganisationMore(String organisationMore) {
        this.organisationMore = organisationMore;
    }

    public String getOrganisationTypeMore() {
        return organisationTypeMore;
    }

    public void setOrganisationTypeMore(String organisationTypeMore) {
        this.organisationTypeMore = organisationTypeMore;
    }

    public List<SelectItem> getOrganizationTypeList() {
        return organizationTypeList;
    }

    public void setOrganizationTypeList(List<SelectItem> organizationTypeList) {
        this.organizationTypeList = organizationTypeList;
    }

    public String getPassportNoMoreDetails() {
        return passportNoMoreDetails;
    }

    public void setPassportNoMoreDetails(String passportNoMoreDetails) {
        this.passportNoMoreDetails = passportNoMoreDetails;
    }

    public String getPermanentAddressMore() {
        return permanentAddressMore;
    }

    public void setPermanentAddressMore(String permanentAddressMore) {
        this.permanentAddressMore = permanentAddressMore;
    }

    public String getPermanentStateMore() {
        return permanentStateMore;
    }

    public void setPermanentStateMore(String permanentStateMore) {
        this.permanentStateMore = permanentStateMore;
    }

    public String getPermanentcityMore() {
        return permanentcityMore;
    }

    public void setPermanentcityMore(String permanentcityMore) {
        this.permanentcityMore = permanentcityMore;
    }

    public String getPermanetPinMore() {
        return permanetPinMore;
    }

    public void setPermanetPinMore(String permanetPinMore) {
        this.permanetPinMore = permanetPinMore;
    }

    public List<SelectItem> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<SelectItem> phoneList) {
        this.phoneList = phoneList;
    }

    public String getPhoneMore() {
        return phoneMore;
    }

    public void setPhoneMore(String phoneMore) {
        this.phoneMore = phoneMore;
    }

    public String getPhoneReferanceDetails() {
        return phoneReferanceDetails;
    }

    public void setPhoneReferanceDetails(String phoneReferanceDetails) {
        this.phoneReferanceDetails = phoneReferanceDetails;
    }

    public String getPinJobDetails() {
        return pinJobDetails;
    }

    public void setPinJobDetails(String pinJobDetails) {
        this.pinJobDetails = pinJobDetails;
    }

    public String getPinReferanceDetails() {
        return pinReferanceDetails;
    }

    public void setPinReferanceDetails(String pinReferanceDetails) {
        this.pinReferanceDetails = pinReferanceDetails;
    }

    public String getPositionHeldMoreDetails() {
        return positionHeldMoreDetails;
    }

    public void setPositionHeldMoreDetails(String positionHeldMoreDetails) {
        this.positionHeldMoreDetails = positionHeldMoreDetails;
    }

    public String getPreviousEmployeeNoMoreDetails() {
        return previousEmployeeNoMoreDetails;
    }

    public void setPreviousEmployeeNoMoreDetails(String previousEmployeeNoMoreDetails) {
        this.previousEmployeeNoMoreDetails = previousEmployeeNoMoreDetails;
    }

    public String getProfessionalMoreDetails() {
        return professionalMoreDetails;
    }

    public void setProfessionalMoreDetails(String professionalMoreDetails) {
        this.professionalMoreDetails = professionalMoreDetails;
    }

    public String getReasonForLeavingMoreDetails() {
        return reasonForLeavingMoreDetails;
    }

    public void setReasonForLeavingMoreDetails(String reasonForLeavingMoreDetails) {
        this.reasonForLeavingMoreDetails = reasonForLeavingMoreDetails;
    }

    public String getReasonOfLeavingExperience() {
        return reasonOfLeavingExperience;
    }

    public void setReasonOfLeavingExperience(String reasonOfLeavingExperience) {
        this.reasonOfLeavingExperience = reasonOfLeavingExperience;
    }

    public String getReferanceNameReferanceDetails() {
        return referanceNameReferanceDetails;
    }

    public void setReferanceNameReferanceDetails(String referanceNameReferanceDetails) {
        this.referanceNameReferanceDetails = referanceNameReferanceDetails;
    }

    public String getRelagionMoreDetails() {
        return relagionMoreDetails;
    }

    public void setRelagionMoreDetails(String relagionMoreDetails) {
        this.relagionMoreDetails = relagionMoreDetails;
    }

    public String getRelatedExperienceJobDetails() {
        return relatedExperienceJobDetails;
    }

    public void setRelatedExperienceJobDetails(String relatedExperienceJobDetails) {
        this.relatedExperienceJobDetails = relatedExperienceJobDetails;
    }

    public List<SelectItem> getRelegionList() {
        return relegionList;
    }

    public void setRelegionList(List<SelectItem> relegionList) {
        this.relegionList = relegionList;
    }

    public String getSalaryDrawnMoreDetails() {
        return salaryDrawnMoreDetails;
    }

    public void setSalaryDrawnMoreDetails(String salaryDrawnMoreDetails) {
        this.salaryDrawnMoreDetails = salaryDrawnMoreDetails;
    }

    public String getSalaryOnJoiningExperience() {
        return salaryOnJoiningExperience;
    }

    public void setSalaryOnJoiningExperience(String salaryOnJoiningExperience) {
        this.salaryOnJoiningExperience = salaryOnJoiningExperience;
    }

    public String getSalaryOnLeavingExperience() {
        return salaryOnLeavingExperience;
    }

    public void setSalaryOnLeavingExperience(String salaryOnLeavingExperience) {
        this.salaryOnLeavingExperience = salaryOnLeavingExperience;
    }

    public List<SelectItem> getSelectionTypeList() {
        return selectionTypeList;
    }

    public void setSelectionTypeList(List<SelectItem> selectionTypeList) {
        this.selectionTypeList = selectionTypeList;
    }

    public String getSelectionTypeMoreDetails() {
        return selectionTypeMoreDetails;
    }

    public void setSelectionTypeMoreDetails(String selectionTypeMoreDetails) {
        this.selectionTypeMoreDetails = selectionTypeMoreDetails;
    }

    public List<SelectItem> getSkillTypeList() {
        return skillTypeList;
    }

    public void setSkillTypeList(List<SelectItem> skillTypeList) {
        this.skillTypeList = skillTypeList;
    }

    public String getSkillTypeMore() {
        return skillTypeMore;
    }

    public void setSkillTypeMore(String skillTypeMore) {
        this.skillTypeMore = skillTypeMore;
    }

    public String getSlaaryOnjoingJobDetails() {
        return slaaryOnjoingJobDetails;
    }

    public void setSlaaryOnjoingJobDetails(String slaaryOnjoingJobDetails) {
        this.slaaryOnjoingJobDetails = slaaryOnjoingJobDetails;
    }

    public List<SelectItem> getSpecializationFirstList() {
        return specializationFirstList;
    }

    public void setSpecializationFirstList(List<SelectItem> specializationFirstList) {
        this.specializationFirstList = specializationFirstList;
    }

    public List<SelectItem> getSpecializationList() {
        return specializationList;
    }

    public void setSpecializationList(List<SelectItem> specializationList) {
        this.specializationList = specializationList;
    }

    public String getSpecializationQualification() {
        return specializationQualification;
    }

    public void setSpecializationQualification(String specializationQualification) {
        this.specializationQualification = specializationQualification;
    }

    public List<SelectItem> getSpecializationSecondList() {
        return specializationSecondList;
    }

    public void setSpecializationSecondList(List<SelectItem> specializationSecondList) {
        this.specializationSecondList = specializationSecondList;
    }

    public String getSpecializationSecondMore() {
        return specializationSecondMore;
    }

    public void setSpecializationSecondMore(String specializationSecondMore) {
        this.specializationSecondMore = specializationSecondMore;
    }

    public String getSpecializationfirstMore() {
        return specializationfirstMore;
    }

    public void setSpecializationfirstMore(String specializationfirstMore) {
        this.specializationfirstMore = specializationfirstMore;
    }

    public String getStateJobDetails() {
        return stateJobDetails;
    }

    public void setStateJobDetails(String stateJobDetails) {
        this.stateJobDetails = stateJobDetails;
    }

    public String getStateReferanceDetails() {
        return stateReferanceDetails;
    }

    public void setStateReferanceDetails(String stateReferanceDetails) {
        this.stateReferanceDetails = stateReferanceDetails;
    }

    public String getSubjectsQualification() {
        return subjectsQualification;
    }

    public void setSubjectsQualification(String subjectsQualification) {
        this.subjectsQualification = subjectsQualification;
    }

    public String getTotalEmployeeExperience() {
        return totalEmployeeExperience;
    }

    public void setTotalEmployeeExperience(String totalEmployeeExperience) {
        this.totalEmployeeExperience = totalEmployeeExperience;
    }

    public String getTotalExperienceJobDetails() {
        return totalExperienceJobDetails;
    }

    public void setTotalExperienceJobDetails(String totalExperienceJobDetails) {
        this.totalExperienceJobDetails = totalExperienceJobDetails;
    }

    public String getVisadetailsMoreDetails() {
        return visadetailsMoreDetails;
    }

    public void setVisadetailsMoreDetails(String visadetailsMoreDetails) {
        this.visadetailsMoreDetails = visadetailsMoreDetails;
    }

    public String getYearOfPassingQualification() {
        return yearOfPassingQualification;
    }

    public void setYearOfPassingQualification(String yearOfPassingQualification) {
        this.yearOfPassingQualification = yearOfPassingQualification;
    }

    public boolean isDisableAdvertisement() {
        return disableAdvertisement;
    }

    public void setDisableAdvertisement(boolean disableAdvertisement) {
        this.disableAdvertisement = disableAdvertisement;
    }

    public DataBankSearch getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(DataBankSearch currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public List<DataBankSearch> getSearch() {
        return search;
    }

    public void setSearch(List<DataBankSearch> search) {
        this.search = search;
    }

    public String getCandId() {
        return candId;
    }

    public void setCandId(String candId) {
        this.candId = candId;
    }

    public List<SelectItem> getSexList() {
        return sexList;
    }

    public void setSexList(List<SelectItem> sexList) {
        this.sexList = sexList;
    }

    public List<DataBankAdvertisement> getAdvo() {
        return advo;
    }

    public void setAdvo(List<DataBankAdvertisement> advo) {
        this.advo = advo;
    }

    public DataBankAdvertisement getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(DataBankAdvertisement currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<SelectItem> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<SelectItem> locationList) {
        this.locationList = locationList;
    }

    public List<SelectItem> getPostAppliedFirstList() {
        return postAppliedFirstList;
    }

    public void setPostAppliedFirstList(List<SelectItem> postAppliedFirstList) {
        this.postAppliedFirstList = postAppliedFirstList;
    }

    public List<SelectItem> getPostAppliedSecondList() {
        return postAppliedSecondList;
    }

    public void setPostAppliedSecondList(List<SelectItem> postAppliedSecondList) {
        this.postAppliedSecondList = postAppliedSecondList;
    }

    public List<SelectItem> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<SelectItem> zoneList) {
        this.zoneList = zoneList;
    }

    public String getAdvertisementNo() {
        return advertisementNo;
    }

    public void setAdvertisementNo(String advertisementNo) {
        this.advertisementNo = advertisementNo;
    }

    public String getAdvertisementNoPop() {
        return advertisementNoPop;
    }

    public void setAdvertisementNoPop(String advertisementNoPop) {
        this.advertisementNoPop = advertisementNoPop;
    }

    public String getCandidateFirstName() {
        return candidateFirstName;
    }

    public void setCandidateFirstName(String candidateFirstName) {
        this.candidateFirstName = candidateFirstName;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateLastName() {
        return candidateLastName;
    }

    public void setCandidateLastName(String candidateLastName) {
        this.candidateLastName = candidateLastName;
    }

    public String getCandidateMidName() {
        return candidateMidName;
    }

    public void setCandidateMidName(String candidateMidName) {
        this.candidateMidName = candidateMidName;
    }

    public String getContactNoFirst() {
        return contactNoFirst;
    }

    public void setContactNoFirst(String contactNoFirst) {
        this.contactNoFirst = contactNoFirst;
    }

    public String getContactNoSecond() {
        return contactNoSecond;
    }

    public void setContactNoSecond(String contactNoSecond) {
        this.contactNoSecond = contactNoSecond;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmailSecond() {
        return emailSecond;
    }

    public void setEmailSecond(String emailSecond) {
        this.emailSecond = emailSecond;
    }

    public String getEmialfirst() {
        return emialfirst;
    }

    public void setEmialfirst(String emialfirst) {
        this.emialfirst = emialfirst;
    }

    public String getFaxFirst() {
        return faxFirst;
    }

    public void setFaxFirst(String faxFirst) {
        this.faxFirst = faxFirst;
    }

    public String getFaxSecond() {
        return faxSecond;
    }

    public void setFaxSecond(String faxSecond) {
        this.faxSecond = faxSecond;
    }

    public String getJobSpecification() {
        return jobSpecification;
    }

    public void setJobSpecification(String jobSpecification) {
        this.jobSpecification = jobSpecification;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getPostAppliedFirst() {
        return postAppliedFirst;
    }

    public void setPostAppliedFirst(String postAppliedFirst) {
        this.postAppliedFirst = postAppliedFirst;
    }

    public String getPostAppliedSecond() {
        return postAppliedSecond;
    }

    public void setPostAppliedSecond(String postAppliedSecond) {
        this.postAppliedSecond = postAppliedSecond;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public CreationOfDatabank() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            operationList = new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0", "---Select---"));
            operationList.add(new SelectItem("1", "Add"));
            operationList.add(new SelectItem("2", "Edit"));
            setTodayDate(sdf.format(date));
            this.setMessage("");
            dropdownList(Integer.parseInt(compCode), "DES%");
            dropdownList(Integer.parseInt(compCode), "ZON%");
            dropdownList(Integer.parseInt(compCode), "LOC%");
            dropdownList(Integer.parseInt(compCode), "SPE%");
            dropdownList(Integer.parseInt(compCode), "SKI%");
            dropdownList(Integer.parseInt(compCode), "ORG%");
            dropdownList(Integer.parseInt(compCode), "QUA%");
            dropdownList(Integer.parseInt(compCode), "DEP%");

            sexList = new ArrayList<SelectItem>();
            sexList.add(new SelectItem("0", "--Select--"));
            sexList.add(new SelectItem("M", "Male"));
            sexList.add(new SelectItem("F", "female"));

            freeDayList = new ArrayList<SelectItem>();
            freeDayList.add(new SelectItem("0", "--Select--"));
            freeDayList.add(new SelectItem("1", "Sunday"));
            freeDayList.add(new SelectItem("2", "Monday"));
            freeDayList.add(new SelectItem("3", "Tuesday"));
            freeDayList.add(new SelectItem("4", "Wednesday"));
            freeDayList.add(new SelectItem("5", "Thuresday"));
            freeDayList.add(new SelectItem("6", "Friday"));
            freeDayList.add(new SelectItem("7", "Saturday"));

            relegionList = new ArrayList<SelectItem>();
            relegionList.add(new SelectItem("0", "--Select--"));
            relegionList.add(new SelectItem("H", "Hindu"));
            relegionList.add(new SelectItem("M", "Muslim"));
            relegionList.add(new SelectItem("S", "Sikh"));
            relegionList.add(new SelectItem("I", "Cristian"));
            relegionList.add(new SelectItem("O", "Others"));

            bloodgroupList = new ArrayList<SelectItem>();
            bloodgroupList.add(new SelectItem("0", "--Select--"));
            bloodgroupList.add(new SelectItem("A+", "A+"));
            bloodgroupList.add(new SelectItem("B+", "B+"));
            bloodgroupList.add(new SelectItem("O+", "O+"));
            bloodgroupList.add(new SelectItem("AB+", "AB+"));
            bloodgroupList.add(new SelectItem("A-", "A-"));
            bloodgroupList.add(new SelectItem("B-", "B-"));
            bloodgroupList.add(new SelectItem("AB-", "AB-"));

            selectionTypeList = new ArrayList<SelectItem>();
            selectionTypeList.add(new SelectItem("0", "--Select--"));
            selectionTypeList.add(new SelectItem("C", "Called"));
            selectionTypeList.add(new SelectItem("I", "Interviewed"));

            maritalStatusList = new ArrayList<SelectItem>();
            maritalStatusList.add(new SelectItem("0", "--Select--"));
            maritalStatusList.add(new SelectItem("M", "Married"));
            maritalStatusList.add(new SelectItem("U", "Unmarried"));
            maritalStatusList.add(new SelectItem("D", "Divorced"));
            maritalStatusList.add(new SelectItem("W", "Widow"));

            earliercompanyEmployeetList = new ArrayList<SelectItem>();
            earliercompanyEmployeetList.add(new SelectItem("0", "--Select--"));
            earliercompanyEmployeetList.add(new SelectItem("Y", "Yes"));
            earliercompanyEmployeetList.add(new SelectItem("N", "No"));

            setTotalExperienceJobDetails("0.0");
            setRelatedExperienceJobDetails("0.0");
            setCurrentSalaryJobDetails("0.0");
            setExpectedSalaryJobDetails("0.0");
            setSlaaryOnjoingJobDetails("0.0");
            setLenthOfServiceJobDetails("0.0");
            setFreeDayMoreDetails("0");
            setJoiningPeriodMoreDetails("0");
            setSalaryDrawnMoreDetails("0.0");
            setYearOfPassingQualification("0");
            setMarksQualification("0.0");
            setAnnualTurnoverExperience("0.0");
            setTotalEmployeeExperience("0");
            setEmployeeUnderExperience("0");
            setSalaryOnJoiningExperience("0.0");
            setSalaryOnLeavingExperience("0.0");


            editdataList();
            consultantNameList();
            setDisableAdvertisement(true);
            flag = false;
            mode = "Save";
        } catch (Exception e) {
            logger.error("Exception occured while executing method DirectRecruitment()", e);
            logger.error("DirectRecruitment()", e);
        }
    }

    public void dropdownList(int compCode, String description) {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            List<HrMstStructTO> structMasterTOs = recruitmentDelegate.getPositionList(compCode, description);

            if (description.equalsIgnoreCase("DES%")) {
                postAppliedFirstList = new ArrayList<SelectItem>();
                postAppliedFirstList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    postAppliedFirstList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
            if (description.equalsIgnoreCase("ZON%")) {
                zoneList = new ArrayList<SelectItem>();
                zoneList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    zoneList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
            if (description.equalsIgnoreCase("LOC%")) {
                locationList = new ArrayList<SelectItem>();
                locationList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    locationList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
            if (description.equalsIgnoreCase("SPE%")) {
                specializationFirstList = new ArrayList<SelectItem>();
                specializationFirstList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    specializationFirstList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
            if (description.equalsIgnoreCase("SKI%")) {
                skillTypeList = new ArrayList<SelectItem>();
                skillTypeList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    skillTypeList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }

            if (description.equalsIgnoreCase("ORG%")) {
                organizationTypeList = new ArrayList<SelectItem>();
                organizationTypeList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    organizationTypeList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }

            if (description.equalsIgnoreCase("QUA%")) {
                examinationList = new ArrayList<SelectItem>();
                examinationList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    examinationList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }

            if (description.equalsIgnoreCase("DEP%")) {
                departmentList = new ArrayList<SelectItem>();
                departmentList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    departmentList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method dropdownList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method dropdownList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method dropdownList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void consultantNameList() {
        try {
            List resultLt = new ArrayList();
            DatabankDelegate databankDelegate = new DatabankDelegate();
            resultLt = databankDelegate.creationOfDatabankCustomerNameList(Integer.parseInt(compCode));
            consultantNameList = new ArrayList<SelectItem>();
            consultantNameList.add(new SelectItem("0", "--Select--"));
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    consultantNameList.add(new SelectItem(result[0].toString(),
                            result[1].toString()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method consultantNameList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method consultantNameList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method consultantNameList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void advertisementDetails() {
        setMessage1("");
        advo = new ArrayList<DataBankAdvertisement>();
        try {
            List resultLt = new ArrayList();
            DatabankDelegate databankDelegate = new DatabankDelegate();
            resultLt = databankDelegate.creationOfDatabankSearchList(Integer.parseInt(compCode), advertisementNoPop);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    DataBankAdvertisement rd = new DataBankAdvertisement();
                    rd.setAdvetisementcode(result[0].toString());
                    rd.setJobCode(result[1].toString());
                    advo.add(rd);
                }
            } else {
                setMessage1("No Data Found");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method advertisementDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method advertisementDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method advertisementDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void select() {
        setAdvertisementNo(currentItem1.getAdvetisementcode());
        setJobSpecification(currentItem1.getJobCode());
    }

    public void closePanelView() {
        setMessage1("");
        setAdvertisementNoPop("");
        disableUpdate = true;
        advo = new ArrayList<DataBankAdvertisement>();
    }

    public void editdataList() {
        search = new ArrayList<DataBankSearch>();
        try {
            List resultList = new ArrayList();
            DatabankDelegate databankDelegate = new DatabankDelegate();
            resultList = databankDelegate.creationOfDatabankViewDetails(Integer.parseInt(compCode), candId);
            if (resultList.size() != 0) {
                Iterator itr = resultList.iterator();
                while (itr.hasNext()) {
                    HrDatabankTO emp = (HrDatabankTO) itr.next();
                    HrDatabankPKTO pk = emp.getHrDatabankPKTO();
                    DataBankSearch db = new DataBankSearch();
                    double cancode = emp.getCandCode();
                    db.setCandCode(Double.toString(cancode));
                    db.setCandId(pk.getCandId());
                    db.setAdvtCode(pk.getAdvtCode());
                    db.setJoCode(pk.getJobCode());
                    db.setFirstName(emp.getCandFirName());
                    db.setMidName(emp.getCandMidName());
                    db.setLastname(emp.getCandLastName());
                    db.setContAddress(emp.getCandContAdd());
                    db.setContPin(emp.getCandContPin());
                    db.setContCity(emp.getCandContCity());
                    db.setContState(emp.getCandContState());
                    db.setPerAddress(emp.getCandPermAdd());
                    db.setPerPin(emp.getCandPermPin());
                    db.setPerCity(emp.getCandPermCity());
                    db.setPermState(emp.getCandPermState());
                    db.setOffTel(emp.getCandOffTel());
                    db.setOffFax(emp.getCandOffFax());
                    db.setOffMail(emp.getCandOffEmail());
                    db.setRestel(emp.getCandResTel());
                    db.setResFax(emp.getCandResFax());
                    db.setResMail(emp.getCandResEmail());
                    db.setDateOfBirth(emp.getDtBirth());
                    char sexual = emp.getSex();
                    db.setSex(Character.toString(sexual));
                    db.setSpecialCode1(emp.getSpecialCode1());
                    db.setSpecialCode2(emp.getSpecialCode2());
                    double totExpr = emp.getTotExpr();
                    db.setTotExpr(Double.toString(totExpr));
                    double currExpr = emp.getCurrExpr();
                    db.setCurrExpr(Double.toString(currExpr));
                    double autoExpr = emp.getAutoExpr();
                    db.setAutoExpr(Double.toString(autoExpr));
                    db.setPostApplied1(emp.getPostApplied1());
                    db.setPostApplied2(emp.getPostApplied2());
                    db.setZone(emp.getZone());
                    db.setLocataCode(emp.getLocatCode());
                    db.setDesgCode(emp.getDesgCode());
                    db.setConscode(emp.getConsCode());
                    double curSalry = emp.getCurrSal();
                    db.setCurrSalary(Double.toString(curSalry));
                    db.setCurrDesg(emp.getCurrDesg());
                    db.setCurrCName(emp.getCurrCname());
                    db.setCompAdd1(emp.getCompAdd1());
                    db.setCompAdd2(emp.getCompAdd2());
                    db.setCompcity(emp.getCompCity());
                    db.setCompState(emp.getCompState());
                    db.setCompPin(emp.getCompPin());
                    double expSal = emp.getExpSal();
                    db.setExpSalary(Double.toString(expSal));
                    double joinSal = emp.getJoinSal();
                    db.setJoinSalary(Double.toString(joinSal));
                    db.setJoinDesg(emp.getJoinDesg());
                    double serviceLen = emp.getServiceLen();
                    db.setServiceLen(Double.toString(serviceLen));
                    if (emp.getStatus() != null) {
                        char status = emp.getStatus();
                        db.setStatus(Character.toString(status));
                    }
                    db.setOrgType(emp.getOrgType());
                    db.setSkillCode(emp.getSkillCode());
                    int freeDay = emp.getFreeDay();
                    db.setFreeDay(Integer.toString(freeDay));
                    db.setNation(emp.getNation());
                    db.setRelagion(emp.getReligion());
                    if (emp.getMarital() != null) {
                        char marital = emp.getMarital();
                        db.setMarital(Character.toString(marital));
                    }
                    db.setBloodGroup(emp.getBloodGrp());
                    int joinPeriod = emp.getJoiningPeriod();
                    db.setJoiningPeriod(Integer.toString(joinPeriod));
                    db.setPassportNo(emp.getPassportNo());
                    db.setPassportDt(emp.getPassportDate());
                    db.setVisaDet(emp.getVisaDet());
                    db.setFatherName(emp.getFathName());
                    db.setFatherOccupation(emp.getFatherOcc());
                    db.setFatherDesg(emp.getFatherDesig());
                    db.setFatherOrg(emp.getFatherOrg());
                    db.setFatherPhone(emp.getFatherPhone());
                    db.setProfMem(emp.getProfMember());
                    db.setIndivMember(emp.getIndivMember());
                    if (emp.getCallInt() != null) {
                        char callint = emp.getCallInt();
                        db.setCallInt(Character.toString(callint));
                    }
                    if (emp.getPrevEmp() != null) {
                        char prevEmp = emp.getPrevEmp();
                        db.setPrevEmp(Character.toString(prevEmp));
                    }
                    db.setPrevEmpcode(emp.getPrevEmpCode());
                    db.setDurfrom(emp.getDurFrom());
                    db.setDurTo(emp.getDurTo());
                    db.setUnitName(emp.getUnitName());
                    db.setPrevDeptCode(emp.getPrevDeptCode());
                    db.setPrevDesgcode(emp.getPrevDesgCode());
                    double slary = emp.getSalaryDrawn();
                    db.setSalaryDrawn(Double.toString(slary));
                    db.setReasonLeave(emp.getReasonLeave());
                    if (emp.getEvalFlag() != null) {
                        char evalFalg = emp.getEvalFlag();
                        db.setEvalflag(Character.toString(evalFalg));
                    }
                    db.setDataTransfer(emp.getDataTransfer());
                    search.add(db);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method editdataList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method editdataList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method editdataList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectDataForupdate() throws ParseException {
        flag = true;
        mode = "Update";
        this.setMessage("");
        candidateIdDisable = true;
        setJobSpecification(currentItem2.getJoCode());
        setCandidateId(currentItem2.getCandId());
        setAdvertisementNo(currentItem2.getAdvtCode());
        setCandidateFirstName(currentItem2.getFirstName());
        setCandidateMidName(currentItem2.getMidName());
        setCandidateLastName(currentItem2.getLastname());
        setContantAddressMore(currentItem2.getContAddress());
        setContantPinMore(currentItem2.getContPin());
        setContantCityMore(currentItem2.getContCity());
        setContantStateMore(currentItem2.getContState());
        setPermanentAddressMore(currentItem2.getPerAddress());
        setPermanetPinMore(currentItem2.getPerPin());
        setPermanentcityMore(currentItem2.getPerCity());
        setPermanentStateMore(currentItem2.getPermState());
        setContactNoFirst(currentItem2.getOffTel());
        setFaxFirst(currentItem2.getOffFax());
        setEmialfirst(currentItem2.getOffMail());
        setContactNoSecond(currentItem2.getRestel());
        setFaxSecond(currentItem2.getResFax());
        setEmailSecond(currentItem2.getResMail());
        if (currentItem2.getDateOfBirth() != null) {
            setDateOfBirth(sdf.parse(sdf.format(currentItem2.getDateOfBirth())));
        }
        setSex(currentItem2.getSex());
        setSpecializationfirstMore(currentItem2.getSpecialCode1());
        setSpecializationSecondMore(currentItem2.getSpecialCode2());
        setTotalExperienceJobDetails(formatter.format(Double.parseDouble(currentItem2.getTotExpr())));
        setRelatedExperienceJobDetails(formatter.format(Double.parseDouble(currentItem2.getCurrExpr())));
        ////////////  auto Experience
        setPostAppliedFirst(currentItem2.getPostApplied1());
        setPostAppliedSecond(currentItem2.getPostApplied2());
        setZone(currentItem2.getZone());
        setLocation(currentItem2.getLocataCode());
        setConsultantNameMore(currentItem2.getConscode());
        ///////////////// desgcode
        setCurrentSalaryJobDetails(formatter.format(Double.parseDouble(currentItem2.getCurrSalary())));
        setDesignationJobDetails(currentItem2.getCurrDesg());
        setCompanyNameJobDetails(currentItem2.getCurrCName());
        setCompanyAddressJobDetails(currentItem2.getCompAdd1());
        setCompanyAddressJobDetails1(currentItem2.getCompAdd2());
        setCityJobDetails(currentItem2.getCompcity());
        setStateJobDetails(currentItem2.getCompState());
        setPinJobDetails(currentItem2.getCompPin());
        setExpectedSalaryJobDetails(formatter.format(Double.parseDouble(currentItem2.getExpSalary())));
        setSlaaryOnjoingJobDetails(formatter.format(Double.parseDouble(currentItem2.getJoinSalary())));
        setDesignationOnJoiningJobDetails(currentItem2.getJoinDesg());
        setLenthOfServiceJobDetails(formatter.format(Double.parseDouble(currentItem2.getServiceLen())));
        ////////status
        setOrganisationTypeMore(currentItem2.getOrgType());
        setSkillTypeMore(currentItem2.getSkillCode());
        setFreeDayMoreDetails(currentItem2.getFreeDay());
        setNationalityMoreDetails(currentItem2.getNation());
        setRelagionMoreDetails(currentItem2.getRelagion());
        setMaritalStatusMoreDetails(currentItem2.getMarital());
        setBloodGroupMoreDetails(currentItem2.getBloodGroup());
        setJoiningPeriodMoreDetails(currentItem2.getJoiningPeriod());
        setPassportNoMoreDetails(currentItem2.getPassportNo());
        if (currentItem2.getPassportDt() != null) {
            setExpiryDateMoreDetails(sdf.parse(sdf.format(currentItem2.getPassportDt())));
        }
        setVisadetailsMoreDetails(currentItem2.getVisaDet());
        setNameMore(currentItem2.getFatherName());
        setOccupationMore(currentItem2.getFatherOccupation());
        setDesignationMore(currentItem2.getFatherDesg());
        setOrganisationMore(currentItem2.getFatherOrg());
        setPhoneMore(currentItem2.getFatherPhone());
        setProfessionalMoreDetails(currentItem2.getProfMem());
        setIndividualMoreDetails(currentItem2.getIndivMember());
        setSelectionTypeMoreDetails(currentItem2.getCallInt());
        setEarlierCompanyMoreDetails(currentItem2.getPrevEmp());
        setPreviousEmployeeNoMoreDetails(currentItem2.getPrevEmpcode());
        if (currentItem2.getDurfrom() != null) {
            setDurationFromMoreDetails(sdf.parse(sdf.format(currentItem2.getDurfrom())));
        }
        if (currentItem2.getDurTo() != null) {
            setDurationToMoreDetails(sdf.parse(sdf.format(currentItem2.getDurTo())));
        }
        setNameOfUnitMoreDetails(currentItem2.getUnitName());
        setDepartmentMoreDetails(currentItem2.getPrevDeptCode());
        setPositionHeldMoreDetails(currentItem2.getPrevDesgcode());
        setSalaryDrawnMoreDetails(formatter.format(Double.parseDouble(currentItem2.getSalaryDrawn())));
        setReasonForLeavingMoreDetails(currentItem2.getReasonLeave());
        qualificationDetails();
        previousEmploymentDetails();
        referanceDetails();
    }

    public void referanceDetails() {
        try {
            List resultLt = new ArrayList();
            DatabankDelegate databankDelegate = new DatabankDelegate();
            resultLt = databankDelegate.creationOfDatabankReferanceDetails(Integer.parseInt(compCode), currentItem2.getCandId(), currentItem2.getAdvtCode(), currentItem2.getJoCode());
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    setReferanceNameReferanceDetails(result[0].toString());
                    setAddressReferanceDetails(result[1].toString());
                    setPinReferanceDetails(result[2].toString());
                    setStateReferanceDetails(result[3].toString());
                    setCityReferanceDetails(result[4].toString());
                    setPhoneReferanceDetails(result[5].toString());
                    setOccupationReferanceDetails(result[6].toString());
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method referanceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method referanceDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method referanceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void qualificationDetails() {
        try {
            List resultLt = new ArrayList();
            DatabankDelegate databankDelegate = new DatabankDelegate();
            resultLt = databankDelegate.creationOfDatabankQualificationDetails(Integer.parseInt(compCode), currentItem2.getCandId(), currentItem2.getAdvtCode(), currentItem2.getJoCode());
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    setExaminationQualification(result[0].toString());
                    setInstituteQualification(result[2].toString());
                    setYearOfPassingQualification(result[3].toString());
                    setSubjectsQualification(result[4].toString());
                    setSpecializationQualification(result[5].toString());
                    setMarksQualification(result[7].toString());
                    setDivisionQualification(result[8].toString());
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method qualificationDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method qualificationDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method qualificationDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void previousEmploymentDetails() {
        try {
            List resultLt = new ArrayList();
            DatabankDelegate databankDelegate = new DatabankDelegate();
            resultLt = databankDelegate.creationOfDatabankPreEmpDetails(Integer.parseInt(compCode), currentItem2.getCandId(), currentItem2.getAdvtCode(), currentItem2.getJoCode());
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    setCompanyNameExperience(result[0].toString());
                    setAnnualTurnoverExperience(formatter.format(Double.parseDouble(result[1].toString())));
                    if (result[2] != null) {
                        Date test = (Date) result[2];
                        String dateLeave = sdf.format(test);
                        setDateOfLeavingExperience(sdf.parse(dateLeave));
                    }
                    setCompanyAddressExperience(result[3].toString());
                    if (result[4] != null) {
                        Date test = (Date) result[4];
                        String dateFrom = sdf.format(test);
                        setDurationFromExperience(sdf.parse(dateFrom));
                    }
                    if (result[5] != null) {
                        Date test = (Date) result[5];
                        String dateTo = sdf.format(test);
                        setDurationToExperience(sdf.parse(dateTo));
                    }
                    setDesignationOfJoiningExperience(result[6].toString());
                    setDesignationOfLeavingExperience(result[8].toString());
                    setTotalEmployeeExperience(result[10].toString());
                    setEmployeeUnderExperience(result[11].toString());
                    setSalaryOnJoiningExperience(formatter.format(Double.parseDouble(result[12].toString())));
                    setSalaryOnLeavingExperience(formatter.format(Double.parseDouble(result[13].toString())));
                    setReasonOfLeavingExperience(result[14].toString());
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method previousEmploymentDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method previousEmploymentDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method previousEmploymentDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void refreshButtonAction() {
        flag = false;
        disableUpdate = false;
        this.setMessage("");
        setAdvertisementNo("");
        setCandidateId("");
        setCandidateFirstName("");
        setCandidateMidName("");
        setCandidateLastName("");
        setJobSpecification("");
        setPostAppliedFirst("0");
        setPostAppliedSecond("0");
        setLocation("0");
        setZone("0");
        setDateOfBirth(null);
        setSex("0");
        setContactNoFirst("");
        setContactNoSecond("");
        setFaxFirst("");
        setFaxSecond("");
        setEmialfirst("");
        setEmailSecond("");
        setContantAddressMore("");
        setContantCityMore("");
        setContantStateMore("");
        setContantPinMore("");
        setPermanentAddressMore("");
        setPermanentcityMore("");
        setPermanentStateMore("");
        setPermanetPinMore("");
        setSpecializationfirstMore("0");
        setSpecializationSecondMore("0");
        setConsultantNameMore("0");
        setSkillTypeMore("0");
        setOrganisationTypeMore("0");
        setNameMore("");
        setOccupationMore("");
        setDesignationMore("0");
        setOrganisationMore("0");
        setPhoneMore("");
        setCompanyNameJobDetails("");
        setDesignationJobDetails("0");
        setTotalExperienceJobDetails("");
        setRelatedExperienceJobDetails("");
        setCurrentSalaryJobDetails("");
        setExpectedSalaryJobDetails("");
        setSlaaryOnjoingJobDetails("");
        setLenthOfServiceJobDetails("");
        setDesignationOnJoiningJobDetails("0");
        setCompanyAddressJobDetails("");
        setCompanyAddressJobDetails1("");
        setCityJobDetails("");
        setStateJobDetails("");
        setPinJobDetails("");
        setFreeDayMoreDetails("0");
        setJoiningPeriodMoreDetails("");
        setNationalityMoreDetails("");
        setRelagionMoreDetails("0");
        setBloodGroupMoreDetails("0");
        setIndividualMoreDetails("");
        setProfessionalMoreDetails("");
        setPassportNoMoreDetails("");
        setExpiryDateMoreDetails(null);
        setVisadetailsMoreDetails("");
        setSelectionTypeMoreDetails("0");
        setMaritalStatusMoreDetails("0");
        setEarlierCompanyMoreDetails("0");
        setPreviousEmployeeNoMoreDetails("");
        setDurationFromMoreDetails(null);
        setDurationToMoreDetails(null);
        setNameOfUnitMoreDetails("");
        setDepartmentMoreDetails("0");
        setPositionHeldMoreDetails("0");
        setSalaryDrawnMoreDetails("");
        setReasonForLeavingMoreDetails("");
        setReferanceNameReferanceDetails("");
        setAddressReferanceDetails("");
        setPinReferanceDetails("");
        setStateReferanceDetails("");
        setCityReferanceDetails("");
        setPhoneReferanceDetails("");
        setOccupationReferanceDetails("");
        setExaminationQualification("0");
        setInstituteQualification("");
        setYearOfPassingQualification("");
        setSubjectsQualification("");
        setSpecializationQualification("0");
        setMarksQualification("");
        setDivisionQualification("");
        setCompanyNameExperience("");
        setAnnualTurnoverExperience("");
        setDateOfLeavingExperience(null);
        setCompanyAddressExperience("");
        setDurationFromExperience(null);
        setDurationToExperience(null);
        setDesignationOfJoiningExperience("0");
        setDesignationOfLeavingExperience("0");
        setTotalEmployeeExperience("");
        setEmployeeUnderExperience("");
        setSalaryOnJoiningExperience("");
        setSalaryOnLeavingExperience("");
        setReasonOfLeavingExperience("");
        setOperation("0");
    }

    public void clear() {
        flag = false;
        disableUpdate = false;
        setAdvertisementNo("");
        setCandidateId("");
        setCandidateFirstName("");
        setCandidateMidName("");
        setCandidateLastName("");
        setJobSpecification("");
        setPostAppliedFirst("0");
        setPostAppliedSecond("0");
        setLocation("0");
        setZone("0");
        setDateOfBirth(null);
        setSex("0");
        setContactNoFirst("");
        setContactNoSecond("");
        setFaxFirst("");
        setFaxSecond("");
        setEmialfirst("");
        setEmailSecond("");
        setContantAddressMore("");
        setContantCityMore("");
        setContantStateMore("");
        setContantPinMore("");
        setPermanentAddressMore("");
        setPermanentcityMore("");
        setPermanentStateMore("");
        setPermanetPinMore("");
        setSpecializationfirstMore("0");
        setSpecializationSecondMore("0");
        setConsultantNameMore("0");
        setSkillTypeMore("0");
        setOrganisationTypeMore("0");
        setNameMore("");
        setOccupationMore("");
        setDesignationMore("0");
        setOrganisationMore("0");
        setPhoneMore("");
        setCompanyNameJobDetails("");
        setDesignationJobDetails("0");
        setTotalExperienceJobDetails("");
        setRelatedExperienceJobDetails("");
        setCurrentSalaryJobDetails("");
        setExpectedSalaryJobDetails("");
        setSlaaryOnjoingJobDetails("");
        setLenthOfServiceJobDetails("");
        setDesignationOnJoiningJobDetails("0");
        setCompanyAddressJobDetails("");
        setCompanyAddressJobDetails1("");
        setCityJobDetails("");
        setStateJobDetails("");
        setPinJobDetails("");
        setFreeDayMoreDetails("0");
        setJoiningPeriodMoreDetails("");
        setNationalityMoreDetails("");
        setRelagionMoreDetails("0");
        setBloodGroupMoreDetails("0");
        setIndividualMoreDetails("");
        setProfessionalMoreDetails("");
        setPassportNoMoreDetails("");
        setExpiryDateMoreDetails(null);
        setVisadetailsMoreDetails("");
        setSelectionTypeMoreDetails("0");
        setMaritalStatusMoreDetails("0");
        setEarlierCompanyMoreDetails("0");
        setPreviousEmployeeNoMoreDetails("");
        setDurationFromMoreDetails(null);
        setDurationToMoreDetails(null);
        setNameOfUnitMoreDetails("");
        setDepartmentMoreDetails("0");
        setPositionHeldMoreDetails("0");
        setSalaryDrawnMoreDetails("");
        setReasonForLeavingMoreDetails("");
        setReferanceNameReferanceDetails("");
        setAddressReferanceDetails("");
        setPinReferanceDetails("");
        setStateReferanceDetails("");
        setCityReferanceDetails("");
        setPhoneReferanceDetails("");
        setOccupationReferanceDetails("");
        setExaminationQualification("0");
        setInstituteQualification("");
        setYearOfPassingQualification("");
        setSubjectsQualification("");
        setSpecializationQualification("0");
        setMarksQualification("");
        setDivisionQualification("");
        setCompanyNameExperience("");
        setAnnualTurnoverExperience("");
        setDateOfLeavingExperience(null);
        setCompanyAddressExperience("");
        setDurationFromExperience(null);
        setDurationToExperience(null);
        setDesignationOfJoiningExperience("0");
        setDesignationOfLeavingExperience("0");
        setTotalEmployeeExperience("");
        setEmployeeUnderExperience("");
        setSalaryOnJoiningExperience("");
        setSalaryOnLeavingExperience("");
        setReasonOfLeavingExperience("");
        setOperation("0");
    }

    public void saveUpdateCreationOfDatabank() {
        try {
            if (operation.equalsIgnoreCase("0")) {
                setMessage("Please select an operation!");
                return;
            }
            if (compCode.equalsIgnoreCase("")) {
                setMessage("Please fill the Company code");
                return;
            }
            if (compCode == null) {
                setMessage("Please fill the Company code");
                return;
            }
            if (candidateId == null) {
                setMessage("Please fill the Candidate Id in Candidate Details Tab");
                return;
            }
            if (candidateId.equalsIgnoreCase("")) {
                setMessage("Please fill the Candidate Id in Candidate Details Tab");
                return;
            }
            if (advertisementNo == null) {
                setMessage("Please fill the Advertisement No in Candidate Details Tab");
                return;
            }
            if (advertisementNo.equalsIgnoreCase("")) {
                setMessage("Please fill the Advertisement No in Candidate Details Tab");
                return;
            }

            if (jobSpecification == null) {
                setMessage("Please fill the Job Specification in Candidate Details Tab");
                return;
            }
            if (jobSpecification.equals("")) {
                setMessage("Please fill the Job Specification in Candidate Details Tab");
                return;
            }
            if (candidateFirstName == null) {
                setMessage("Please fill the Candidate First Name in Candidate Details Tab");
                return;
            }
            if (candidateFirstName.equalsIgnoreCase("")) {
                setMessage("Please fill the Candidate First Name in Candidate Details Tab");
                return;
            }
            if (candidateLastName == null) {
                setMessage("Please fill the Candidate Last Name in Candidate Details Tab");
                return;
            }
            if (candidateLastName.equalsIgnoreCase("")) {
                setMessage("Please fill the Candidate Last Name in Candidate Details Tab");
                return;
            }
            if (sex == null) {
                setMessage("Please select the Sex in Candidate Details Tab");
                return;
            }
            if (sex.equalsIgnoreCase("0")) {
                setMessage("Please select the Sex in Candidate Details Tab");
                return;
            }
            if (postAppliedFirst == null) {
                setMessage("Please fill the Post Applied For(1) in Candidate Details Tab");
                return;
            }
            if (postAppliedFirst.equalsIgnoreCase("0")) {
                setMessage("Please Select the Post Applied For(1) in Candidate Details Tab");
                return;
            }
            if (location == null) {
                setMessage("Please Select the Location in Candidate Details Tab");
                return;
            }

            if (location.equalsIgnoreCase("0")) {
                setMessage("Please Select the Location in Candidate Details Tab");
                return;
            }

            if (zone == null) {
                setMessage("Please Select the Zone in Candidate Details Tab");
                return;
            }
            if (zone.equalsIgnoreCase("0")) {
                setMessage("Please Select the Zone in Candidate Details Tab");
                return;
            }
            if (referanceNameReferanceDetails == null) {
                setMessage("Please fill the Reference Name in Reference Details Tab");
                return;
            }
            if (referanceNameReferanceDetails.equalsIgnoreCase("")) {
                setMessage("Please fill the Reference Name in Reference Details Tab");
                return;
            }
            if (addressReferanceDetails.equalsIgnoreCase("")) {
                setMessage("Please fill the Address in Reference Details Tab");
                return;
            }
            if (pinReferanceDetails.equalsIgnoreCase("")) {
                setMessage("Please fill the Pin in Reference Details Tab");
                return;
            }
            if (stateReferanceDetails.equalsIgnoreCase("")) {
                setMessage("Please fill the State in Reference Details Tab");
                return;
            }
            if (cityReferanceDetails.equalsIgnoreCase("")) {
                setMessage("Please fill the City in Reference Details Tab");
                return;
            }
            if (phoneReferanceDetails.equalsIgnoreCase("")) {
                setMessage("Please fill the Phone in Reference Details Tab");
                return;
            }
            if (occupationReferanceDetails.equalsIgnoreCase("")) {
                setMessage("Please fill the occupation in Reference Details Tab");
                return;
            }

            if (examinationQualification == null) {
                setMessage("Please select the Examination/Course in Qualification Tab");
                return;
            }
            if (examinationQualification.equalsIgnoreCase("")) {
                setMessage("Please select the Examination/Course in Qualification Tab");
                return;
            }
            if (instituteQualification == null) {
                setMessage("Please fill the Institute/Board in Qualification Tab");
                return;
            }
            if (instituteQualification.equalsIgnoreCase("")) {
                setMessage("Please fill the Institute/Board in Qualification Tab");
                return;
            }
            if (yearOfPassingQualification == null) {
                setMessage("Please fill the Year Of Passing in Qualification Tab");
                return;
            }
            if (yearOfPassingQualification.equalsIgnoreCase("")) {
                setMessage("Please fill the Year Of Passing in Qualification Tab");
                return;
            }
            if (specializationQualification.equalsIgnoreCase("0")) {
                setMessage("Please select specialization in Qualification Tab.");
                return;
            }
            if (companyNameExperience == null) {
                setMessage("Please fill the Company Name in Previous Employment Tab");
                return;
            }
            if (companyNameExperience.equalsIgnoreCase("")) {
                setMessage("Please fill the Company Name in Previous Employment Tab");
                return;
            }

            if (validations().equalsIgnoreCase("false")) {
                return;
            }

            DatabankDelegate databankDelegate = new DatabankDelegate();
            if (mode.equalsIgnoreCase("save")) {
                String stateFlag = "Y";
                String stateUpflag = "Y";
                HrDatabankTO hrDatabank = new HrDatabankTO();
                HrDatabankPKTO hrDatabankPK = new HrDatabankPKTO();
                hrDatabankPK.setCompCode(Integer.parseInt(compCode));
                hrDatabankPK.setCandId(candidateId);
                hrDatabankPK.setAdvtCode(advertisementNo);
                hrDatabankPK.setJobCode(jobSpecification);
                hrDatabank.setHrDatabankPKTO(hrDatabankPK);
                List dropDownList1 = databankDelegate.creationOfDatabankValidCheck();
                hrDatabank.setCandCode(dropDownList1.size() + 1);
                hrDatabank.setCandFirName(candidateFirstName);
                hrDatabank.setCandMidName(candidateMidName);
                hrDatabank.setCandLastName(candidateLastName);
                hrDatabank.setCandContAdd(contantAddressMore);
                hrDatabank.setCandContPin(contantPinMore);
                hrDatabank.setCandContCity(contantCityMore);
                hrDatabank.setCandContState(contantStateMore);
                hrDatabank.setCandPermAdd(permanentAddressMore);
                hrDatabank.setCandPermPin(permanetPinMore);
                hrDatabank.setCandPermCity(permanentcityMore);
                hrDatabank.setCandPermState(permanentStateMore);
                hrDatabank.setCandOffTel(contactNoFirst);
                hrDatabank.setCandOffFax(faxFirst);
                hrDatabank.setCandOffEmail(emialfirst);
                hrDatabank.setCandResTel(contactNoSecond);
                hrDatabank.setCandResFax(faxSecond);
                hrDatabank.setCandResEmail(emailSecond);
                hrDatabank.setDtBirth(dateOfBirth);
                char sexChar = 'M';
                if (sex.equalsIgnoreCase("M")) {
                    sexChar = 'M';
                } else if (sex.equalsIgnoreCase("F")) {
                    sexChar = 'F';
                }
                hrDatabank.setSex(sexChar);
                hrDatabank.setSpecialCode1(specializationfirstMore);
                hrDatabank.setSpecialCode2(specializationSecondMore);
                hrDatabank.setTotExpr(Double.parseDouble(totalExperienceJobDetails));
                hrDatabank.setCurrExpr(Double.parseDouble(relatedExperienceJobDetails));
                Double autoExpr = 0.0d;
                hrDatabank.setAutoExpr(autoExpr);////////
                hrDatabank.setPostApplied1(postAppliedFirst);
                hrDatabank.setPostApplied2(postAppliedSecond);
                hrDatabank.setZone(zone);
                hrDatabank.setLocatCode(location);
                hrDatabank.setDesgCode("");//////
                hrDatabank.setConsCode(consultantNameMore);
                hrDatabank.setCurrSal(Double.parseDouble(currentSalaryJobDetails));
                hrDatabank.setCurrDesg(designationJobDetails);
                hrDatabank.setCurrCname(companyNameJobDetails);
                hrDatabank.setCompAdd1(companyAddressJobDetails);
                hrDatabank.setCompAdd2(companyAddressJobDetails1);
                hrDatabank.setCompCity(cityJobDetails);
                hrDatabank.setCompState(stateJobDetails);
                hrDatabank.setCompPin(pinJobDetails);
                hrDatabank.setExpSal(Double.parseDouble(expectedSalaryJobDetails));
                hrDatabank.setJoinSal(Double.parseDouble(slaaryOnjoingJobDetails));
                hrDatabank.setJoinDesg(designationOnJoiningJobDetails);
                hrDatabank.setServiceLen(Double.parseDouble(lenthOfServiceJobDetails));
                hrDatabank.setStatus('Y');
                hrDatabank.setOrgType(organisationTypeMore);
                hrDatabank.setSkillCode(skillTypeMore);
                hrDatabank.setFreeDay(Integer.parseInt(freeDayMoreDetails));
                hrDatabank.setNation(nationalityMoreDetails);
                hrDatabank.setReligion(relagionMoreDetails);
                char maritalStatus = 'M';
                if (maritalStatusMoreDetails.equalsIgnoreCase("M")) {
                    maritalStatus = 'M';
                } else if (maritalStatusMoreDetails.equalsIgnoreCase("U")) {
                    maritalStatus = 'U';
                } else if (maritalStatusMoreDetails.equalsIgnoreCase("D")) {
                    maritalStatus = 'D';
                } else if (maritalStatusMoreDetails.equalsIgnoreCase("W")) {
                    maritalStatus = 'W';
                }
                hrDatabank.setMarital(maritalStatus);
                hrDatabank.setBloodGrp(bloodGroupMoreDetails);
                hrDatabank.setJoiningPeriod(Integer.parseInt(joiningPeriodMoreDetails));
                hrDatabank.setPassportNo(passportNoMoreDetails);
                hrDatabank.setPassportDate(expiryDateMoreDetails);
                hrDatabank.setVisaDet(visadetailsMoreDetails);
                hrDatabank.setFathName(nameMore);
                hrDatabank.setFatherOcc(occupationMore);
                hrDatabank.setFatherDesig(designationMore);
                hrDatabank.setFatherOrg(organisationMore);
                hrDatabank.setFatherPhone(phoneMore);
                hrDatabank.setProfMember(professionalMoreDetails);
                hrDatabank.setIndivMember(individualMoreDetails);
                hrDatabank.setCallInt('C');
                char earDetails = 'Y';
                if (earlierCompanyMoreDetails.equalsIgnoreCase("Y")) {
                    earDetails = 'Y';
                }
                if (earlierCompanyMoreDetails.equalsIgnoreCase("N")) {
                    earDetails = 'N';
                }
                hrDatabank.setPrevEmp(earDetails);
                hrDatabank.setPrevEmpCode(previousEmployeeNoMoreDetails);
                hrDatabank.setDurFrom(durationFromMoreDetails);
                hrDatabank.setDurTo(durationToMoreDetails);
                hrDatabank.setUnitName(nameOfUnitMoreDetails);
                hrDatabank.setPrevDeptCode(departmentMoreDetails);
                hrDatabank.setPrevDesgCode(positionHeldMoreDetails);
                hrDatabank.setSalaryDrawn(Double.parseDouble(salaryDrawnMoreDetails));
                hrDatabank.setReasonLeave(reasonForLeavingMoreDetails);
                hrDatabank.setEvalFlag('N');///
                hrDatabank.setDataTransfer("Y");/////
                hrDatabank.setDefaultComp(defaultComp);
                hrDatabank.setStatFlag(stateFlag);
                hrDatabank.setStatUpFlag(stateUpflag);
                hrDatabank.setModDate(getDate());
                hrDatabank.setAuthBy(getUserName());
                hrDatabank.setEnteredBy(getUserName());

                HrDataQualTO hrDataQual = new HrDataQualTO();
                HrDataQualPKTO hrDataQualPK = new HrDataQualPKTO();
                hrDataQualPK.setCompCode(Integer.parseInt(compCode));
                hrDataQualPK.setAdvtCode(advertisementNo);
                hrDataQualPK.setJobCode(jobSpecification);
                hrDataQualPK.setCandSrno(candidateId);
                hrDataQualPK.setQualCode(examinationQualification);
                hrDataQual.setHrDataQualPKTO(hrDataQualPK);
                hrDataQual.setInstName(instituteQualification);
                hrDataQual.setYear(Integer.parseInt(yearOfPassingQualification));
                hrDataQual.setSubName(subjectsQualification);
                hrDataQual.setSpecial(specializationQualification);
                hrDataQual.setPerMarks(Double.parseDouble(marksQualification));
                hrDataQual.setDiv(divisionQualification);
                hrDataQual.setDefaultCompCode(defaultComp);
                hrDataQual.setStatFlag(stateFlag);
                hrDataQual.setStatUpFlag(stateUpflag);
                hrDataQual.setModDate(getDate());
                hrDataQual.setAuthBy(getUserName());
                hrDataQual.setEnteredBy(getUserName());

                HrDataPrevCompTO hrDatabankPrev = new HrDataPrevCompTO();
                HrDataPrevCompPKTO hrDatabankPrevPK = new HrDataPrevCompPKTO();
                hrDatabankPrevPK.setCompCode(Integer.parseInt(compCode));
                hrDatabankPrevPK.setAdvtCode(advertisementNo);
                hrDatabankPrevPK.setJobCode(jobSpecification);
                hrDatabankPrevPK.setCandSrno(candidateId);
                hrDatabankPrevPK.setCompName(companyNameExperience);
                hrDatabankPrev.setHrDataPrevCompPKTO(hrDatabankPrevPK);
                hrDatabankPrev.setAnnualTurn(Double.parseDouble(annualTurnoverExperience));
                hrDatabankPrev.setLeaveDate(dateOfLeavingExperience);
                hrDatabankPrev.setCompAdd(companyAddressExperience);
                hrDatabankPrev.setDurFrom(durationFromExperience);
                hrDatabankPrev.setDurTo(durationToExperience);
                hrDatabankPrev.setDesgJoin(designationOfJoiningExperience);
                hrDatabankPrev.setDesgLeave(designationOfLeavingExperience);
                hrDatabankPrev.setTotEmp(Integer.parseInt(totalEmployeeExperience));
                hrDatabankPrev.setEmpUnder(Integer.parseInt(employeeUnderExperience));
                hrDatabankPrev.setSalJoin(Double.parseDouble(salaryOnJoiningExperience));
                hrDatabankPrev.setSalLeave(Double.parseDouble(salaryOnLeavingExperience));
                hrDatabankPrev.setReason(reasonOfLeavingExperience);
                hrDatabankPrev.setDefaultCompCode(defaultComp);
                hrDatabankPrev.setStatFlag(stateFlag);
                hrDatabankPrev.setStatUpFlag(stateUpflag);
                hrDatabankPrev.setModDate(getDate());
                hrDatabankPrev.setAuthBy(getUserName());
                hrDatabankPrev.setEnteredBy(getUserName());

                HrDataReferenceTO hrDataRef = new HrDataReferenceTO();
                HrDataReferencePKTO hrDataRefPK = new HrDataReferencePKTO();
                hrDataRefPK.setCompCode(Integer.parseInt(compCode));
                hrDataRefPK.setAdvtCode(advertisementNo);
                hrDataRefPK.setJobCode(jobSpecification);
                hrDataRefPK.setCandSrno(candidateId);
                hrDataRefPK.setReferName(referanceNameReferanceDetails);
                hrDataRef.setHrDataReferencePKTO(hrDataRefPK);
                hrDataRef.setReferAdd(addressReferanceDetails);
                hrDataRef.setReferPin(pinReferanceDetails);
                hrDataRef.setReferState(stateReferanceDetails);
                hrDataRef.setReferCity(cityReferanceDetails);
                hrDataRef.setReferPhone(phoneReferanceDetails);
                hrDataRef.setReferOcc(occupationReferanceDetails);
                hrDataRef.setDefaultCompCode(defaultComp);
                hrDataRef.setStatFlag(stateFlag);
                hrDataRef.setStatUpFlag(stateUpflag);
                hrDataRef.setModDate(getDate());
                hrDataRef.setAuthBy(getUserName());
                hrDataRef.setEnteredBy(getUserName());
                String result = databankDelegate.saveCreationOfDatabank(Integer.parseInt(compCode), advertisementNo, jobSpecification, candidateId, examinationQualification, companyNameExperience, referanceNameReferanceDetails, hrDatabank, hrDataQual, hrDatabankPrev, hrDataRef);
                setMessage(result);
            } /******************* update *******************/
            else if (mode.equalsIgnoreCase("Update")) {
                if (compCode.equalsIgnoreCase("")) {
                    setMessage("Please fill the Comany code");
                    return;
                }
                if (compCode == null) {
                    setMessage("Please fill the Comany code");
                    return;
                }
                if (candidateId.equalsIgnoreCase("")) {
                    setMessage("Please fill the Candidate Id in Candidate Details Tab");
                    return;
                }
                if (candidateId == null) {
                    setMessage("Please fill the Candidate Id in Candidate Details Tab");
                    return;
                }
                if (advertisementNo.equalsIgnoreCase("")) {
                    setMessage("Please fill the Advertisement No in Candidate Details Tab");
                    return;
                }
                if (advertisementNo == null) {
                    setMessage("Please fill the Advertisement No in Candidate Details Tab");
                    return;
                }
                if (jobSpecification == null) {
                    setMessage("Please fill the Job Specification in Candidate Details Tab");
                    return;
                }
                if (jobSpecification.equals("")) {
                    setMessage("Please fill the Job Specification in Candidate Details Tab");
                    return;
                }
                if (candidateFirstName.equalsIgnoreCase("")) {
                    setMessage("Please fill the Candidate First Name in Candidate Details Tab");
                    return;
                }
                if (candidateFirstName == null) {
                    setMessage("Please fill the Candidate First Name in Candidate Details Tab");
                    return;
                }
                if (sex.equalsIgnoreCase("0")) {
                    setMessage("Please select the Sex in Candidate Details Tab");
                    return;
                }
                if (sex == null) {
                    setMessage("Please select the Sex in Candidate Details Tab");
                    return;
                }
                if (postAppliedFirst.equalsIgnoreCase("0")) {
                    setMessage("Please Select the Post Applied For(1) in Candidate Details Tab");
                    return;
                }
                if (postAppliedFirst == null) {
                    setMessage("Please fill the Post Applied For(1) in Candidate Details Tab");
                    return;
                }

                if (location.equalsIgnoreCase("0")) {
                    setMessage("Please Select the Location in Candidate Details Tab");
                    return;
                }
                if (location == null) {
                    setMessage("Please Select the Location in Candidate Details Tab");
                    return;
                }

                if (zone.equalsIgnoreCase("0")) {
                    setMessage("Please Select the Zone in Candidate Details Tab");
                    return;
                }
                if (zone == null) {
                    setMessage("Please Select the Zone in Candidate Details Tab");
                    return;
                }


                String stateFlag = "Y";
                String stateUpflag = "Y";
                HrDatabankTO hrDatabank = new HrDatabankTO();
                HrDatabankPKTO hrDatabankPK = new HrDatabankPKTO();
                hrDatabankPK.setCompCode(Integer.parseInt(compCode));
                hrDatabankPK.setCandId(candidateId);
                hrDatabankPK.setAdvtCode(advertisementNo);
                hrDatabankPK.setJobCode(jobSpecification);
                hrDatabank.setHrDatabankPKTO(hrDatabankPK);
                List dropDownList1 = databankDelegate.creationOfDatabankValidCheck();
                hrDatabank.setCandCode(dropDownList1.size() + 1);
                hrDatabank.setCandFirName(candidateFirstName);
                hrDatabank.setCandMidName(candidateMidName);
                hrDatabank.setCandLastName(candidateLastName);
                hrDatabank.setCandContAdd(contantAddressMore);
                hrDatabank.setCandContPin(contantPinMore);
                hrDatabank.setCandContCity(contantCityMore);
                hrDatabank.setCandContState(contantStateMore);
                hrDatabank.setCandPermAdd(permanentAddressMore);
                hrDatabank.setCandPermPin(permanetPinMore);
                hrDatabank.setCandPermCity(permanentcityMore);
                hrDatabank.setCandPermState(permanentStateMore);
                hrDatabank.setCandOffTel(contactNoFirst);
                hrDatabank.setCandOffFax(faxFirst);
                hrDatabank.setCandOffEmail(emialfirst);
                hrDatabank.setCandResTel(contactNoSecond);
                hrDatabank.setCandResFax(faxSecond);
                hrDatabank.setCandResEmail(emailSecond);
                hrDatabank.setDtBirth(dateOfBirth);
                char sexChar = 'M';
                if (sex.equalsIgnoreCase("M")) {
                    sexChar = 'M';
                } else if (sex.equalsIgnoreCase("F")) {
                    sexChar = 'F';
                }
                hrDatabank.setSex(sexChar);
                hrDatabank.setSpecialCode1(specializationfirstMore);
                hrDatabank.setSpecialCode2(specializationSecondMore);
                hrDatabank.setTotExpr(Double.parseDouble(totalExperienceJobDetails));
                hrDatabank.setCurrExpr(Double.parseDouble(relatedExperienceJobDetails));
                Double autoExpr = 0.0d;
                hrDatabank.setAutoExpr(autoExpr);
                hrDatabank.setPostApplied1(postAppliedFirst);
                hrDatabank.setPostApplied2(postAppliedSecond);
                hrDatabank.setZone(zone);
                hrDatabank.setLocatCode(location);
                hrDatabank.setDesgCode("");
                hrDatabank.setConsCode(consultantNameMore);
                hrDatabank.setCurrSal(Double.parseDouble(currentSalaryJobDetails));
                hrDatabank.setCurrDesg(designationJobDetails);
                hrDatabank.setCurrCname(companyNameJobDetails);
                hrDatabank.setCompAdd1(companyAddressJobDetails);
                hrDatabank.setCompAdd2(companyAddressJobDetails1);
                hrDatabank.setCompCity(cityJobDetails);
                hrDatabank.setCompState(stateJobDetails);
                hrDatabank.setCompPin(pinJobDetails);
                hrDatabank.setExpSal(Double.parseDouble(expectedSalaryJobDetails));
                hrDatabank.setJoinSal(Double.parseDouble(slaaryOnjoingJobDetails));
                hrDatabank.setJoinDesg(designationOnJoiningJobDetails);
                hrDatabank.setServiceLen(Double.parseDouble(lenthOfServiceJobDetails));
                hrDatabank.setStatus('Y');
                hrDatabank.setOrgType(organisationTypeMore);
                hrDatabank.setSkillCode(skillTypeMore);
                hrDatabank.setFreeDay(Integer.parseInt(freeDayMoreDetails));
                hrDatabank.setNation(nationalityMoreDetails);
                hrDatabank.setReligion(relagionMoreDetails);
                char maritalStatus = 'M';
                if (maritalStatusMoreDetails.equalsIgnoreCase("M")) {
                    maritalStatus = 'M';
                } else if (maritalStatusMoreDetails.equalsIgnoreCase("U")) {
                    maritalStatus = 'U';
                } else if (maritalStatusMoreDetails.equalsIgnoreCase("D")) {
                    maritalStatus = 'D';
                } else if (maritalStatusMoreDetails.equalsIgnoreCase("W")) {
                    maritalStatus = 'W';
                }
                hrDatabank.setMarital(maritalStatus);
                hrDatabank.setBloodGrp(bloodGroupMoreDetails);
                hrDatabank.setJoiningPeriod(Integer.parseInt(joiningPeriodMoreDetails));
                hrDatabank.setPassportNo(passportNoMoreDetails);
                hrDatabank.setPassportDate(expiryDateMoreDetails);
                hrDatabank.setVisaDet(visadetailsMoreDetails);
                hrDatabank.setFathName(nameMore);
                hrDatabank.setFatherOcc(occupationMore);
                hrDatabank.setFatherDesig(designationMore);
                hrDatabank.setFatherOrg(organisationMore);
                hrDatabank.setFatherPhone(phoneMore);
                hrDatabank.setProfMember(professionalMoreDetails);
                hrDatabank.setIndivMember(individualMoreDetails);
                hrDatabank.setCallInt('C');
                char earDetails = 'Y';
                if (earlierCompanyMoreDetails.equalsIgnoreCase("Y")) {
                    earDetails = 'Y';
                }
                if (earlierCompanyMoreDetails.equalsIgnoreCase("N")) {
                    earDetails = 'N';
                }
                hrDatabank.setPrevEmp(earDetails);
                hrDatabank.setPrevEmpCode(previousEmployeeNoMoreDetails);
                hrDatabank.setDurFrom(durationFromMoreDetails);
                hrDatabank.setDurTo(durationToMoreDetails);
                hrDatabank.setUnitName(nameOfUnitMoreDetails);
                hrDatabank.setPrevDeptCode(departmentMoreDetails);
                hrDatabank.setPrevDesgCode(positionHeldMoreDetails);
                hrDatabank.setSalaryDrawn(Double.parseDouble(salaryDrawnMoreDetails));
                hrDatabank.setReasonLeave(reasonForLeavingMoreDetails);
                hrDatabank.setEvalFlag('N');
                hrDatabank.setDataTransfer("Y");
                hrDatabank.setDefaultComp(defaultComp);
                hrDatabank.setStatFlag(stateFlag);
                hrDatabank.setStatUpFlag(stateUpflag);
                hrDatabank.setModDate(getDate());
                hrDatabank.setAuthBy(getUserName());
                hrDatabank.setEnteredBy(getUserName());
                String result = databankDelegate.updateCreationOfDatabank(hrDatabank);
                setMessage(result);
            }
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveUpdateCreationOfDatabank()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveUpdateCreationOfDatabank()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveUpdateCreationOfDatabank()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String validations() {
        try {
            if (!contactNoFirst.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Contact No Field in Candidate Details Tab");
                return "false";
            }
            if (!contactNoSecond.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Contact No Field in Candidate Details Tab");
                return "false";
            }
            if (!faxFirst.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Fax Field in Candidate Details Tab");
                return "false";
            }
            if (!faxSecond.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Fax Field in Candidate Details Tab");
                return "false";
            }

            if (!emialfirst.equalsIgnoreCase("") && !valid.validateEmail(emialfirst)) {
                this.setMessage("Please Enter Valid E-Mail Id in Candidate Details Tab");
                return "false";
            }
            if (!emailSecond.equalsIgnoreCase("") && !valid.validateEmail(emailSecond)) {
                this.setMessage("Please Enter Valid E-Mail Id in Candidate Details Tab");
                return "false";
            }

            if (!contantPinMore.equalsIgnoreCase("")) {
                if (!contantPinMore.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Pin Field in More About Candidates Tab");
                    return "false";
                }
                if (contantPinMore.length() < 6) {
                    this.setMessage("Pin code can't be less than 6 char in More About Candidates Tab");
                    return "false";
                }
            }
            if (!permanetPinMore.equalsIgnoreCase("")) {
                if (!permanetPinMore.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Pin  Field in More About Candidates Tab");
                    return "false";
                }
                if (permanetPinMore.length() < 6) {
                    this.setMessage("Pin code can't be less than 6 char in More About Candidates Tab");
                    return "false";
                }
            }
            if (!phoneMore.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Phone Field  in More About Candidates Tab");
                return "false";
            }
            if (!pinJobDetails.equalsIgnoreCase("")) {
                if (!pinJobDetails.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in 	Pin Field in Present Employement Details Tab");
                    return "false";
                }
                if (pinJobDetails.length() < 6) {
                    this.setMessage("Pin code can't be less than 6 char in in Present Employement Details Tab");
                    return "false";
                }
            }
            if (!yearOfPassingQualification.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in 	Year Of Passing Field in Qualification Tab");
                return "false";
            }

            if (!totalExperienceJobDetails.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Total Experience Field in Present Employement Details Tab");
                return "false";
            }

            if (!relatedExperienceJobDetails.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Related Industry Experience Field in Present Employement Details Tab");
                return "false";
            }
            if (!currentSalaryJobDetails.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Current Salary(Annual) Field in Present Employement Details Tab");
                return "false";
            }

            if (!expectedSalaryJobDetails.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Expected Salary(Annual) Field in Present Employement Details Tab");
                return "false";
            }
            if (!slaaryOnjoingJobDetails.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Salary On Joining(Annual) Field in Present Employement Details Tab");
                return "false";
            }
            if (!lenthOfServiceJobDetails.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Length Of Service Field in Present Employement Details Tab");
                return "false";
            }
            if (!freeDayMoreDetails.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Free Days Field in More Details Tab");
                return "false";
            }
            if (!joiningPeriodMoreDetails.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Joining Period Field in More Details Tab");
                return "false";
            }
            if (!salaryDrawnMoreDetails.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Salary Drawn Field in More Details Details Tab");
                return "false";
            }

            if (!yearOfPassingQualification.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Year Of Passing Field in Qualification Tab");
                return "false";
            }
            if (!marksQualification.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Marks Field in Qualification Tab");
                return "false";
            }
            if (!annualTurnoverExperience.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Annual Turn Over Field in Previous Employment Tab");
                return "false";
            }
            if (!totalEmployeeExperience.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Total Employee Field in Previous Employment Tab");
                return "false";
            }
            if (!employeeUnderExperience.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Employee Under Field in Previous Employment Tab");
                return "false";
            }
            if (!salaryOnJoiningExperience.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Salary On Joining Field in Previous Employment Tab");
                return "false";
            }
            if (!salaryOnLeavingExperience.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Salary On Leaving Field in Previous Employment Tab");
                return "false";
            }
            if (!pinReferanceDetails.equalsIgnoreCase("")) {
                if (!pinReferanceDetails.matches("[0-9.]*")) {
                    this.setMessage("Please Enter Numeric Value in Pin Field in Reference Details Tab");
                    return "false";
                }
                if (pinReferanceDetails.length() < 6) {
                    this.setMessage("Pin code can't be less than 6 char in Reference Details Tab");
                    return "false";
                }
            }
            if (!phoneReferanceDetails.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Phone Field in Reference Details Tab");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }

    public String btnExit() {
        try {
            refreshButtonAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "case1";
    }

    public void setOperationOnBlur() {
        if (operation.equalsIgnoreCase("0")) {
            setMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            mode = "save";
            closePanelView();
        } else if (operation.equalsIgnoreCase("2")) {
            mode = "Update";
        }
    }
}
