package com.hrms.web.controller.personnel;

import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.delegate.DefinitionsDelegate;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.EmployeeGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.delegate.RecruitmentDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

public class EmployeeDetails extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(EmployeeDetails.class);
    protected int compCode, defaultComp;
    private long empCode;
    protected PersonnelDelegate personnelDelegate;
    protected DefinitionsDelegate definitionsDelegate;
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private Integer currentIEmpRow;
    private Calendar cal;
    private String authBy, enteredBy, message, statFlag, statUpFlag, mode, calFromDate, calToDate, formName, workStatusCode, workStatusValue,
            bankCode, bankValue, blockCode, blockValue, categoryCode, categoryValue, departmentCode, departmentValue, designationCode, designationValue,
            designationCode2, designationValue2, employeeTypeCode, employeeTypeValue, gradeCode, gradeValue, locationCode, locationValue, organisationCode,
            organisationValue, qualificationCode, qualificationValue, skillCode, skillValue, specializationCode, specializationValue, subDepartmentCode,
            subDepartmentValue, unitCode, unitValue, zoneCode, zoneValue, empId, lastEmpId, currentEmpId, empName, empFName, empMName, empLName, empCardNo,
            conAddress, perAddress, dateOfBirth, joiningDate, bankAccountNo, financeLoanAccountCode, paymentMode, authorizedUser, authorizedPassword,
            conAddressLine, conCity,
            conState, conPin, conPhone, perAddressLine, perCity, perState, perPin, perPhone,
            empSearchCriteria, empSearchValue, function;
    private List<SelectItem> workStatusList, bankList, blockList, categoryList, departmentList, designationList, designationList2, employeeTypeList,
            gradeList, locationList, qualificationList, organisationList, skillList, specializationList, subDepartmentList, unitList, zoneList;
    private boolean disableSaveButton, disableDeleteButton, disableCancelButton;
    private Character sex, ESIMembership, PFMembership, VPFMembership, TRUSTMembership;
    private char empStatus;
    private List<String> membershipGroup;
    private List<EmployeeGrid> empSearchTable;
    private EmployeeGrid currentEmpItem = new EmployeeGrid();
    private PersonnelUtil personnelUtil;
    private Validator validator;
    private boolean disableTabs;
    EmployeeOtherDetail employeeOtherDetail;

    public EmployeeDetails() {
        try {
            personnelDelegate = new PersonnelDelegate();
            definitionsDelegate = new DefinitionsDelegate();
            Date date = new Date();
            cal = new GregorianCalendar();
            cal.setTime(date);
            disableTabs = true;
            compCode = Integer.parseInt(getOrgnBrCode());
            companyMasterTOs = webUtil.getCompanyMasterTO(compCode);
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            getHttpSession().setAttribute("USER_NAME", getUserName());
            getHttpSession().setAttribute("EMP_CODE", empCode);
            getHttpSession().setAttribute("COMP_CODE", compCode);
            getHttpSession().setAttribute("DEFAULT_COMP", defaultComp);
            statFlag = "Y";
            statUpFlag = "N";
            mode = "save";
            authBy = getUserName();
            enteredBy = getUserName();
            getInitialData();
            formName = this.getClass().getSimpleName();
//            getEmployeeOtherDetail().setDisableWeddingDate(true);
            setDisableSaveButton(true);
            setDisableDeleteButton(true);
            personnelUtil = new PersonnelUtil();
            membershipGroup = new ArrayList<String>();
            validator = new Validator();
            calFromDate = (WebUtil.getFinancialYear(compCode).get(0));
            calToDate = (WebUtil.getFinancialYear(compCode).get(1));
            getDesignationListValues();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method BusMaster()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method BusMaster()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void onLoadMode() {
        setMode("save");
        setMessage("");
        setDisableSaveButton(true);
        setDisableDeleteButton(true);
    }

    public boolean validateData() {
        if (empId == null || empId.equalsIgnoreCase("") || !empId.substring(0, 3).equalsIgnoreCase("EMP")) {
            setMessage("Employee ID must follow EMP****** format");
            return false;
        }
        if (designationCode.equalsIgnoreCase("0")) {
            setMessage("Please Select Designation !!");
            return false;
        }
        if (zoneCode.equalsIgnoreCase("0")) {
            setMessage("Please Select Zone !!");
            return false;
        }
        if (locationCode.equalsIgnoreCase("0")) {
            setMessage("Please select location!");
            return false;
        }
        if (subDepartmentCode.equalsIgnoreCase("0")) {
            setMessage("Please Select Department !!");
            return false;
        }
        if (empFName.equalsIgnoreCase("") || dateOfBirth.equalsIgnoreCase("") || joiningDate.equalsIgnoreCase("") || sex == '0'
                || workStatusCode.equalsIgnoreCase("0") || blockCode.equalsIgnoreCase("0") || unitCode.equalsIgnoreCase("0")
                || gradeCode.equalsIgnoreCase("0") || employeeTypeCode.equalsIgnoreCase("0") || categoryCode.equalsIgnoreCase("0")
                || zoneCode.equalsIgnoreCase("0") || departmentCode.equalsIgnoreCase("0") || bankCode.equalsIgnoreCase("0")
                || paymentMode.equalsIgnoreCase("0")) {
            setMessage("Please Fill Mandatory Fields !!");
            return false;
        }

        if (paymentMode.equalsIgnoreCase("BK")) {
            if (bankAccountNo.equalsIgnoreCase("")) {
                setMessage("Please enter Bank Account no.");
                return false;
            }
        }
        if (!validator.validatePhone(conPhone)) {
            setMessage("Contact Phone Can Have Numeric Value Only !!");
            return false;
        }
        if (!validator.validateInteger(conPin)) {
            setMessage("Contact Pin Can Have Numeric Value Only !!");
            return false;
        }
        if (!validator.validatePhone(perPhone)) {
            setMessage("Permanent Phone Can Have Numeric Value Only !!");
            return false;
        }
        if (!validator.validateInteger(perPin)) {
            setMessage("Permanent Pin Can Have Numeric Value Only!!");
            return false;
        }

        if (!validator.validateDate_dd_mm_yyyy(dateOfBirth)) {
            setMessage("Date Of Birth Is In Incorrect Format. It Should Be In dd/mm/yyyy Format!!");
            return false;
        }
        return true;
    }

    public void saveGeneralDetails() {
        try {
            if (workStatusCode.equalsIgnoreCase("STA000")) {
                setEmpStatus('N');
            } else if (workStatusCode.equalsIgnoreCase("STA001")) {
                setEmpStatus('Y');
            }
            if (workStatusCode.equalsIgnoreCase("STA002")) {
                setEmpStatus('H');
            }
            if (dateOfBirth == null || dateOfBirth.equalsIgnoreCase("")) {
                dateOfBirth = "01/01/1900";
            }
            if (joiningDate == null || joiningDate.equalsIgnoreCase("")) {
                joiningDate = "01/01/1900";
            }
            if (conPin == null || conPin.equalsIgnoreCase("")) {
                setConPin("0");
            }
            if (conPhone == null || conPhone.equalsIgnoreCase("")) {
                setConPhone("0");
            }
            if (perPin == null || perPin.equalsIgnoreCase("")) {
                setPerPin("0");
            }
            if (perPhone == null || perPhone.equalsIgnoreCase("")) {
                setPerPhone("0");
            }

            if (validateData()) {
                mode = function;
                empName = (empFName + " " + empMName + " " + empLName);
                if (empName.length() > 100) {
                    empName = empName.substring(0, 100);
                }
                conAddress = conAddressLine;
                if (conAddress.length() > 150) {
                    conAddress = conAddress.substring(0, 150);

                }
                perAddress = perAddressLine;
                if (perAddress.length() > 150) {
                    perAddress = perAddress.substring(0, 150);
                }
                if (mode.equalsIgnoreCase("save")) {
                    empCode = personnelDelegate.getMaxEmpCode() + 1;

                } else if (mode.equalsIgnoreCase("update")) {
                    empCode = currentEmpItem.getEmpCode();
                }

                if (!membershipGroup.isEmpty()) {
                    for (String value : membershipGroup) {
                        if (value.equalsIgnoreCase("ESI")) {
                            ESIMembership = 'T';
                            break;
                        } else {
                            ESIMembership = 'F';
                        }
                    }
                    for (String value : membershipGroup) {
                        if (value.equalsIgnoreCase("PF")) {
                            PFMembership = 'T';
                            break;
                        } else {
                            PFMembership = 'F';
                        }
                    }
                    for (String value : membershipGroup) {
                        if (value.equalsIgnoreCase("VPF")) {
                            VPFMembership = 'T';
                            break;
                        } else {
                            VPFMembership = 'F';
                        }
                    }
                    for (String value : membershipGroup) {
                        if (value.equalsIgnoreCase("TRUST")) {
                            TRUSTMembership = 'T';
                            break;
                        } else {
                            TRUSTMembership = 'F';
                        }
                    }
                }

                String result = personnelDelegate.saveHrPersonnelEmployeeDetails(getPersonnelDetailsTO(), function);
                cancelGeneralDetails();
                setMessage(result);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteGeneralDetails() {
        try {
            empCode = currentEmpItem.getEmpCode();
            HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
            HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
            hrPersonnelDetailsPKTO.setCompCode(compCode);
            hrPersonnelDetailsPKTO.setEmpCode(empCode);
            hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);
            String result = personnelDelegate.saveHrPersonnelEmployeeDetails(hrPersonnelDetailsTO, "delete");
            cancelGeneralDetails();
            setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }

    public void cancelGeneralDetails() {
        try {
            setMode("save");
            disableTabs = true;
            setEmpSearchCriteria("Name");
            setEmpSearchValue("");
            setEmpName("");
            setEmpId("");
            setLastEmpId("");
            setMessage("");
            setFunction("0");
            setWorkStatusCode("0");
            setSex('0');
            setBlockCode("0");
            setEmpCode(0);
            setEmpCardNo("");
            setEmpFName("");
            setEmpMName("");
            setEmpLName("");
            setDateOfBirth("");
            setJoiningDate("");
            setUnitCode("0");
            setGradeCode("0");
            setDesignationCode("0");
            setEmployeeTypeCode("0");
            setCategoryCode("0");
            setZoneCode("0");
            setLocationCode("0");
            setDepartmentCode("0");
            setSubDepartmentCode("0");
            setBankCode("0");
            setBankAccountNo("");
            setFinanceLoanAccountCode("");
            setPaymentMode("0");
            setConAddressLine("");
            setConCity("");
            setConState("");
            setConPhone("");
            setConPin("");
            setPerAddressLine("");
            setPerCity("");
            setPerState("");
            setPerPhone("");
            setPerPin("");
            getEmployeeOtherDetail().setFatherName_husbandDesg("");
            getEmployeeOtherDetail().setFatherName_husbandName("");
            getEmployeeOtherDetail().setFatherName_husbandOcc("");
            getEmployeeOtherDetail().setFatherName_husbandOrg("");
            getEmployeeOtherDetail().setFatherName_husbandPhone("");
            getEmployeeOtherDetail().setBirthCity("");
            getEmployeeOtherDetail().setBirthState("");
            getEmployeeOtherDetail().setNation("");
            getEmployeeOtherDetail().setHeight("");
            getEmployeeOtherDetail().setWeight("");
            getEmployeeOtherDetail().setHealth("");
            getEmployeeOtherDetail().setChest("");
            getEmployeeOtherDetail().setBloodGroup("0");
            getEmployeeOtherDetail().setChildren("");
            getEmployeeOtherDetail().setMaritalStatus('0');
            getEmployeeOtherDetail().setWeddingDate("");
            getEmployeeOtherDetail().setReligion("");
            getEmployeeOtherDetail().setEmailId("");
            getEmployeeOtherDetail().setVisaDetail("");
            getEmployeeOtherDetail().setAdolescentDate("");
            getEmployeeOtherDetail().setAdolescentNo("");
            getEmployeeOtherDetail().setAdolescentReferenceNo("");
            getEmployeeOtherDetail().setAdolescentRelay("");
            getEmployeeOtherDetail().setAdolescentRelayDate("");
            getEmployeeOtherDetail().setAdolescentTokenNo("");
            locationCode = "0";
//            if (locationList != null) {
//                locationList.clear();
//                locationList.add(new SelectItem("0", "--Select--"));
//            }
            if (subDepartmentList != null) {
                subDepartmentList.clear();
                subDepartmentList.add(new SelectItem("0", "--Select--"));
            }
            if (empSearchTable != null) {
                empSearchTable.clear();
            }
            if (membershipGroup != null) {
                membershipGroup.clear();
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String exitGeneralDetails() {
        try {
            cancelGeneralDetails();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitGeneralDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public void getDesignationListValues() {
        try {
//            designationList.clear();
//            String gradeCodeS = "", gradeValueS = "", designationCodeS = "", designationValueS = "";
//            designationList = new ArrayList<SelectItem>();
//            List list = personnelDelegate.getGradeAnddesignationList(compCode);
//            if (!list.isEmpty()) {
//                for (int i = 0; i < list.size(); i++) {
//                    Object[] objects = (Object[]) list.get(i);
//                    gradeCodeS = objects[0].toString();
//                    gradeValueS = objects[1].toString();
//                    if (objects[2] != null) {
//                        designationCodeS = objects[2].toString();
//                    }
//                    if (objects[3] != null) {
//                        designationValueS = objects[3].toString();
//                    }
//                    if (gradeCode.equalsIgnoreCase(gradeCodeS)) {
//                        designationList.add(new SelectItem(designationCodeS, designationValueS));
//                    }
//                }
//            } else {
//                designationList.add(new SelectItem("N", "--No Designation--"));
//            }
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            designationList = new ArrayList<SelectItem>();
            designationList.add(new SelectItem("0", "--Select--"));
            List<HrMstStructTO> structMasterTOs = recruitmentDelegate.getPositionList(compCode, "des%");
            if (!structMasterTOs.isEmpty()) {
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    designationList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(), structMasterTO.getDescription()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getLocations() {
        try {
            locationList = new ArrayList<SelectItem>();
            List data = definitionsDelegate.getAssignedLocations(compCode, zoneCode);
            locationList.add(new SelectItem("0", "--Select--"));
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) {
                    Object[] ob = (Object[]) data.get(i);
                    locationList.add(new SelectItem(ob[0].toString(), ob[1].toString()));
                }
            } else {
                locationCode = "0";
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getSubDepartments() {
        try {
            subDepartmentList = new ArrayList<SelectItem>();
            List data = definitionsDelegate.getSubDepartmentsAssigned(compCode, departmentCode);
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) {
                    Object[] ob = (Object[]) data.get(i);
                    subDepartmentList.add(new SelectItem(ob[0].toString(), ob[1].toString()));
                }
            } else {
                subDepartmentList.add(new SelectItem("--Select--"));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getEmployeeTableData() {
        try {
            setLastEmpId("");
            setEmpId("");
            empSearchTable = personnelUtil.getEmployeeTableData(compCode, empSearchCriteria, empSearchValue);
            setMessage(empSearchTable.size() + " rows found");
        } catch (WebException e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String getNewEmpId() {
        try {
            lastEmpId = personnelDelegate.getLastEmployeeId(compCode);
            int iEmpId = Integer.parseInt(lastEmpId.substring(3)) + 1;
            currentEmpId = "EMP" + iEmpId;
            int lastEmpIdLen = lastEmpId.length();
            String str = "";
            int currentEmpIdLen = currentEmpId.length();
            if (currentEmpIdLen < lastEmpIdLen) {
                int diff = lastEmpIdLen - currentEmpIdLen;
                for (int i = 0; i < diff; i++) {
                    str += "0";
                }
                currentEmpId = "EMP" + str + iEmpId;
            }
            empId = currentEmpId;
            return empId;
        } catch (WebException e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "Error";
    }

    public void onChangeFunction() {
        try {
            if (function.equalsIgnoreCase("save")) {
                setDisableTabs(true);
                setDisableSaveButton(false);
                setDisableDeleteButton(true);
                getNewEmpId();
                setMessage("Please Fill Employee Details");
            } else if (function.equalsIgnoreCase("update")) {
                setDisableSaveButton(false);
                setDisableDeleteButton(false);
                setMessage("Please Select A Row To Edit");

            } else {
                empId = "";
                lastEmpId = "";
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setEmpRowValues() {
        try {
            empCode = currentEmpItem.getEmpCode();
            compCode = currentEmpItem.getCompCode();
            getHttpSession().setAttribute("EMP_CODE", empCode);
            getHttpSession().setAttribute("COMP_CODE", compCode);
            getHttpSession().setAttribute("DEFAULT_COMP", defaultComp);
            getHttpSession().setAttribute("EMP_ID", empId);
            empName = currentEmpItem.getEmpName();
            if (currentEmpItem.getEmpId() != null) {
                setEmpId(currentEmpItem.getEmpId());
            } else {
                setEmpId("");
            }
            if (currentEmpItem.getEmpFName() != null) {
                setEmpFName(currentEmpItem.getEmpFName());
            } else {
                setEmpFName("");
            }
            if (currentEmpItem.getEmpMName() != null) {
                setEmpMName(currentEmpItem.getEmpMName());
            } else {
                setEmpMName("");
            }
            if (currentEmpItem.getEmpLName() != null) {
                setEmpLName(currentEmpItem.getEmpLName());
            } else {
                setEmpLName("");
            }
            if (currentEmpItem.getEmpCardNo() != null) {
                setEmpCardNo(currentEmpItem.getEmpCardNo());
            } else {
                setEmpCardNo("");
            }
            if (currentEmpItem.getDateOfBirth() != null) {
                setDateOfBirth(currentEmpItem.getDateOfBirth());
            } else {
                setDateOfBirth("");
            }
            if (currentEmpItem.getJoiningDate() != null) {
                setJoiningDate(currentEmpItem.getJoiningDate());
            } else {
                setJoiningDate("");
            }
            if (currentEmpItem.getSex() != '0') {
                setSex(currentEmpItem.getSex());
            } else {
                setSex('0');
            }
            if (currentEmpItem.getWorkStatusCode() != null) {
                setWorkStatusCode(currentEmpItem.getWorkStatusCode());
            } else {
                setWorkStatusCode("");
            }
            if (currentEmpItem.getBlockCode() != null) {
                setBlockCode(currentEmpItem.getBlockCode());
            } else {
                setBlockCode("");
            }
            if (currentEmpItem.getUnitCode() != null) {
                setUnitCode(currentEmpItem.getUnitCode());
            } else {
                setUnitCode("");
            }
            if (currentEmpItem.getGradeCode() != null) {
                setGradeCode(currentEmpItem.getGradeCode());
            } else {
                setEmpFName("");
            }
//            if (currentEmpItem.getDesgCode() != null) {
//                List<HrMstStructTO> hrMstStructTO = personnelDelegate.getInitialData(compCode, currentEmpItem.getDesgCode());
//                designationList = new ArrayList<SelectItem>();
//                designationList.add(new SelectItem(hrMstStructTO.get(0).getHrMstStructPKTO().getStructCode(), hrMstStructTO.get(0).getDescription()));
//            } else {
//                designationList = new ArrayList<SelectItem>();
//                designationList.add(new SelectItem("0", "--Select--"));
//            }
            if (currentEmpItem.getDesgCode() != null) {
                setDesignationCode(currentEmpItem.getDesgCode());
            } else {
                setDesignationCode("0");
            }

            if (currentEmpItem.getEmployeeTypeCode() != null) {
                setEmployeeTypeCode(currentEmpItem.getEmployeeTypeCode());
            } else {
                setEmployeeTypeCode("");
            }
            if (currentEmpItem.getCategoryCode() != null) {
                setCategoryCode(currentEmpItem.getCategoryCode());
            } else {
                setCategoryCode("");
            }
            if (currentEmpItem.getZoneCode() != null) {
                setZoneCode(currentEmpItem.getZoneCode());
            } else {
                setZoneCode("");
            }
            if (currentEmpItem.getLocationCode() != null) {
                setLocationCode(currentEmpItem.getLocationCode());
            } else {
                setLocationCode("");
            }
            if (currentEmpItem.getDepartmentCode() != null) {
                setDepartmentCode(currentEmpItem.getDepartmentCode());
            } else {
                setDepartmentCode("");
            }
            if (currentEmpItem.getSubDepartmentCode() != null) {
                setSubDepartmentCode(currentEmpItem.getSubDepartmentCode());
            } else {
                setSubDepartmentCode("");
            }
            if (currentEmpItem.getBankCode() != null) {
                setBankCode(currentEmpItem.getBankCode());
            } else {
                setBankCode("");
            }
            if (currentEmpItem.getBankAccountNo() != null) {
                setBankAccountNo(currentEmpItem.getBankAccountNo());
            } else {
                setBankAccountNo("");
            }
            if (currentEmpItem.getFinanceLoanAccountCode() != null) {
                setFinanceLoanAccountCode(currentEmpItem.getFinanceLoanAccountCode());
            } else {
                setFinanceLoanAccountCode("");
            }
            if (currentEmpItem.getPaymentMode() != null) {
                setPaymentMode(currentEmpItem.getPaymentMode());
            } else {
                setPaymentMode("");
            }
            membershipGroup = new ArrayList<String>();
            membershipGroup.add(String.valueOf(currentEmpItem.getESIMembership()));
            membershipGroup.add(String.valueOf(currentEmpItem.getPFMembership()));
            membershipGroup.add(String.valueOf(currentEmpItem.getVPFMembership()));
            membershipGroup.add(String.valueOf(currentEmpItem.getTRUSTMembership()));
            if (currentEmpItem.getESIMembership() != null && currentEmpItem.getESIMembership() == 'T') {
                membershipGroup.add("ESI");
            }
            if (currentEmpItem.getPFMembership() != null && currentEmpItem.getPFMembership() == 'T') {
                membershipGroup.add("PF");
            }
            if (currentEmpItem.getVPFMembership() != null && currentEmpItem.getVPFMembership() == 'T') {
                membershipGroup.add("VPF");
            }
            if (currentEmpItem.getTRUSTMembership() != null && currentEmpItem.getTRUSTMembership() == 'T') {
                membershipGroup.add("TRUST");
            }
            if (currentEmpItem.getEmpAddress() != null) {
                setConAddressLine(currentEmpItem.getEmpAddress());
            } else {
                setConAddressLine("");
            }
            if (currentEmpItem.getEmpConCity() != null) {
                setConCity(currentEmpItem.getEmpConCity());
            } else {
                setConCity("");
            }
            if (currentEmpItem.getEmpConState() != null) {
                setConState(currentEmpItem.getEmpConState());
            } else {
                setConState("");
            }
            if (currentEmpItem.getEmpConPin() != null) {
                setConPin(currentEmpItem.getEmpConPin());
            } else {
                setConPin("");
            }
            if (currentEmpItem.getEmpPhone() != null) {
                setConPhone(currentEmpItem.getEmpPhone());
            } else {
                setConPhone("");
            }
            if (currentEmpItem.getEmpPerAddress() != null) {
                setPerAddressLine(currentEmpItem.getEmpPerAddress());
            } else {
                setPerAddressLine("");
            }
            if (currentEmpItem.getEmpPerCity() != null) {
                setPerCity(currentEmpItem.getEmpPerCity());
            } else {
                setPerCity("");
            }
            if (currentEmpItem.getEmpPerState() != null) {
                setPerState(currentEmpItem.getEmpPerState());
            } else {
                setPerState("");
            }
            if (currentEmpItem.getEmpPerPin() != null) {
                setPerPin(currentEmpItem.getEmpPerPin());
            } else {
                setPerPin("");
            }
            if (currentEmpItem.getEmpPerPhone() != null) {
                setPerPhone(currentEmpItem.getEmpPerPhone());
            } else {
                setPerPhone("");
            }
            if (currentEmpItem.getFatherName_husbandName() != null) {
                getEmployeeOtherDetail().setFatherName_husbandName(currentEmpItem.getFatherName_husbandName());
            } else {
                getEmployeeOtherDetail().setFatherName_husbandName("");
            }
            if (currentEmpItem.getFatherName_husbandOcc() != null) {
                getEmployeeOtherDetail().setFatherName_husbandOcc(currentEmpItem.getFatherName_husbandOcc());
            } else {
                getEmployeeOtherDetail().setFatherName_husbandOcc("");
            }
            if (currentEmpItem.getFatherName_husbandDesg() != null) {
                getEmployeeOtherDetail().setFatherName_husbandDesg(currentEmpItem.getFatherName_husbandDesg());
            } else {
                getEmployeeOtherDetail().setFatherName_husbandDesg("");
            }
            if (currentEmpItem.getFatherName_husbandOrg() != null) {
                getEmployeeOtherDetail().setFatherName_husbandOrg(currentEmpItem.getFatherName_husbandOrg());
            } else {
                getEmployeeOtherDetail().setFatherName_husbandOrg("");
            }
            if (currentEmpItem.getFatherName_husbandPhone() != null) {
                getEmployeeOtherDetail().setFatherName_husbandPhone(currentEmpItem.getFatherName_husbandPhone());
            } else {
                getEmployeeOtherDetail().setFatherName_husbandPhone("");
            }
            if (currentEmpItem.getBirthCity() != null) {
                getEmployeeOtherDetail().setBirthCity(currentEmpItem.getBirthCity());
            } else {
                getEmployeeOtherDetail().setBirthCity("");
            }
            if (currentEmpItem.getBirthState() != null) {
                getEmployeeOtherDetail().setBirthState(currentEmpItem.getBirthState());
            } else {
                getEmployeeOtherDetail().setBirthState("");
            }
            if (currentEmpItem.getNation() != null) {
                getEmployeeOtherDetail().setNation(currentEmpItem.getNation());
            } else {
                getEmployeeOtherDetail().setNation("");
            }
            if (currentEmpItem.getHeight() != null) {
                getEmployeeOtherDetail().setHeight(currentEmpItem.getHeight());
            } else {
                getEmployeeOtherDetail().setHeight("");
            }
            if (currentEmpItem.getWeight() != null) {
                getEmployeeOtherDetail().setWeight(currentEmpItem.getWeight());
            } else {
                getEmployeeOtherDetail().setWeight("");
            }
            if (currentEmpItem.getHealth() != null) {
                getEmployeeOtherDetail().setHealth(currentEmpItem.getHealth());
            } else {
                getEmployeeOtherDetail().setHealth("");
            }
            if (currentEmpItem.getChest() != null) {
                getEmployeeOtherDetail().setChest(currentEmpItem.getChest());
            } else {
                getEmployeeOtherDetail().setChest("");
            }
            if (currentEmpItem.getBloodGroup() != null) {
                getEmployeeOtherDetail().setBloodGroup(currentEmpItem.getBloodGroup());
            } else {
                getEmployeeOtherDetail().setBloodGroup("0");
            }
            if (currentEmpItem.getChildren() != null) {
                getEmployeeOtherDetail().setChildren(String.valueOf(currentEmpItem.getChildren()));
            } else {
                getEmployeeOtherDetail().setChildren("0");
            }
            if (currentEmpItem.getMaritalStatus() != null) {
                getEmployeeOtherDetail().setMaritalStatus(currentEmpItem.getMaritalStatus());
            } else {
                getEmployeeOtherDetail().setMaritalStatus('0');
            }
            if (currentEmpItem.getWeddingDate() != null) {
                if (!currentEmpItem.getWeddingDate().equalsIgnoreCase("01/01/1900")) {
                    getEmployeeOtherDetail().setWeddingDate(currentEmpItem.getWeddingDate());
                } else {
                    getEmployeeOtherDetail().setWeddingDate("");
                }
            }
            if (currentEmpItem.getReligion() != null) {
                getEmployeeOtherDetail().setReligion(currentEmpItem.getReligion());
            } else {
                getEmployeeOtherDetail().setReligion("");
            }
            if (currentEmpItem.getEmailId() != null) {
                getEmployeeOtherDetail().setEmailId(currentEmpItem.getEmailId());
            } else {
                getEmployeeOtherDetail().setEmailId("");
            }
            if (currentEmpItem.getVisaDetail() != null) {
                getEmployeeOtherDetail().setVisaDetail(currentEmpItem.getVisaDetail());
            } else {
                getEmployeeOtherDetail().setVisaDetail("");
            }
            if (currentEmpItem.getAdolescentNo() != null) {
                getEmployeeOtherDetail().setAdolescentNo(currentEmpItem.getAdolescentNo());
            } else {
                getEmployeeOtherDetail().setAdolescentNo("");
            }
            if (currentEmpItem.getAdolescentDate() != null) {
                if (!currentEmpItem.getAdolescentDate().equalsIgnoreCase("01/01/1900")) {
                    getEmployeeOtherDetail().setAdolescentDate(currentEmpItem.getAdolescentDate());
                } else {
                    getEmployeeOtherDetail().setAdolescentDate("");
                }
            }
            if (currentEmpItem.getAdolescentTokenNo() != null) {
                getEmployeeOtherDetail().setAdolescentTokenNo(currentEmpItem.getAdolescentTokenNo());
            } else {
                getEmployeeOtherDetail().setAdolescentTokenNo("");
            }
            if (currentEmpItem.getAdolescentReferenceNo() != null) {
                getEmployeeOtherDetail().setAdolescentReferenceNo(currentEmpItem.getAdolescentReferenceNo());
            } else {
                getEmployeeOtherDetail().setAdolescentReferenceNo("");
            }
            if (currentEmpItem.getAdolescentRelay() != null) {
                getEmployeeOtherDetail().setAdolescentRelay(currentEmpItem.getAdolescentRelay());
            } else {
                getEmployeeOtherDetail().setAdolescentRelay("");
            }
            if (currentEmpItem.getAdolescentRelayDate() != null) {
                if (!currentEmpItem.getAdolescentRelayDate().equalsIgnoreCase("01/01/1900")) {
                    getEmployeeOtherDetail().setAdolescentRelayDate(currentEmpItem.getAdolescentRelayDate());
                } else {
                    getEmployeeOtherDetail().setAdolescentRelayDate("");
                }
            }
            setMessage("");
            setDisableTabs(false);
        } catch (Exception e) {
            e.printStackTrace();
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setDisableAllTabs() {
        setDisableTabs(true);
    }

    public void getInitialData() {
        try {
            String structCode = "%";
            workStatusList = new ArrayList<SelectItem>();
            workStatusList.add(new SelectItem("0", "--Select--"));
            blockList = new ArrayList<SelectItem>();
            blockList.add(new SelectItem("0", "--Select--"));
            unitList = new ArrayList<SelectItem>();
            unitList.add(new SelectItem("0", "--Select--"));
            gradeList = new ArrayList<SelectItem>();
            gradeList.add(new SelectItem("0", "--Select--"));
            designationList = new ArrayList<SelectItem>();
            designationList.add(new SelectItem("0", "--Select--"));
            designationList2 = new ArrayList<SelectItem>();
            designationList2.add(new SelectItem("0", "--Select--"));
            departmentList = new ArrayList<SelectItem>();
            departmentList.add(new SelectItem("0", "--Select--"));
            subDepartmentList = new ArrayList<SelectItem>();
            subDepartmentList.add(new SelectItem("0", "--Select--"));
            categoryList = new ArrayList<SelectItem>();
            categoryList.add(new SelectItem("0", "--Select--"));
            zoneList = new ArrayList<SelectItem>();
            zoneList.add(new SelectItem("0", "--Select--"));
            locationList = new ArrayList<SelectItem>();
            locationList.add(new SelectItem("0", "--Select--"));
            bankList = new ArrayList<SelectItem>();
            bankList.add(new SelectItem("0", "--Select--"));
            employeeTypeList = new ArrayList<SelectItem>();
            employeeTypeList.add(new SelectItem("0", "--Select--"));
            skillList = new ArrayList<SelectItem>();
            skillList.add(new SelectItem("0", "--Select--"));
            specializationList = new ArrayList<SelectItem>();
            specializationList.add(new SelectItem("0", "--Select--"));
            qualificationList = new ArrayList<SelectItem>();
            qualificationList.add(new SelectItem("0", "--Select--"));
            List<HrMstStructTO> hrMstStructTOs = personnelDelegate.getInitialData(compCode, structCode);
            if (!hrMstStructTOs.isEmpty()) {
                for (HrMstStructTO hrMstStructTO : hrMstStructTOs) {
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("STA")) {
                        workStatusList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("BLO")) {
                        blockList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("UNI")) {
                        unitList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("EMP")) {
                        employeeTypeList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("CAT")) {
                        categoryList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("ZON")) {
                        zoneList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("LOC")) {
                        locationList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("BAN")) {
                        bankList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("DEP")) {
                        departmentList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("SUB")) {
                        subDepartmentList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("GRA")) {
                        gradeList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("SKI")) {
                        skillList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("SPE")) {
                        specializationList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("QUA")) {
                        qualificationList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                    if (hrMstStructTO.getHrMstStructPKTO().getStructCode().substring(0, 3).equalsIgnoreCase("DES")) {
                        designationList2.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                    }
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public HrPersonnelDetailsTO getPersonnelDetailsTO() {
        HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
        HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
        try {
            hrPersonnelDetailsPKTO.setCompCode(compCode);
            hrPersonnelDetailsPKTO.setEmpCode(empCode);
            hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);
            hrPersonnelDetailsTO.setEmpId(empId);
            hrPersonnelDetailsTO.setEmpName(empName);
            hrPersonnelDetailsTO.setBlock(blockCode);
            hrPersonnelDetailsTO.setUnitName(unitCode);
            hrPersonnelDetailsTO.setEmpFirName(empFName);
            hrPersonnelDetailsTO.setEmpMidName(empMName);
            hrPersonnelDetailsTO.setEmpLastName(empLName);
            hrPersonnelDetailsTO.setSex(sex);
            hrPersonnelDetailsTO.setEmpType(employeeTypeCode);
            hrPersonnelDetailsTO.setZones(zoneCode);
            hrPersonnelDetailsTO.setLocatCode(locationCode);
            hrPersonnelDetailsTO.setDeptCode(departmentCode);
            hrPersonnelDetailsTO.setGradeCode(gradeCode);
            hrPersonnelDetailsTO.setDesgCode(designationCode);
            hrPersonnelDetailsTO.setCatgCode(categoryCode);
            hrPersonnelDetailsTO.setSubdeptCode(subDepartmentCode);
            hrPersonnelDetailsTO.setJoinDate(dmyFormat.parse(joiningDate));
            hrPersonnelDetailsTO.setPayMode(paymentMode);
            hrPersonnelDetailsTO.setBankCode(bankCode);
            hrPersonnelDetailsTO.setBankAccountCode(bankAccountNo);
            hrPersonnelDetailsTO.setDefaultComp(defaultComp);
            hrPersonnelDetailsTO.setStatFlag(statFlag);
            hrPersonnelDetailsTO.setStatUpFlag(statUpFlag);
            hrPersonnelDetailsTO.setModDate(Date.class.newInstance());
            hrPersonnelDetailsTO.setAuthBy(authBy);
            hrPersonnelDetailsTO.setFinAccountCode(financeLoanAccountCode);
            hrPersonnelDetailsTO.setWorkStatus(workStatusCode);
            hrPersonnelDetailsTO.setPassword(empFName);
            hrPersonnelDetailsTO.setEnteredBy(enteredBy);
            hrPersonnelDetailsTO.setBirthDate(dmyFormat.parse(dateOfBirth));
            hrPersonnelDetailsTO.setEmpCardNo(empCardNo);
            hrPersonnelDetailsTO.setEsiMember(ESIMembership);
            hrPersonnelDetailsTO.setPfMember(PFMembership);
            hrPersonnelDetailsTO.setVpfMember(VPFMembership);
            hrPersonnelDetailsTO.setTrustMember(TRUSTMembership);
            hrPersonnelDetailsTO.setFathHusName(getEmployeeOtherDetail().getFatherName_husbandName());
            hrPersonnelDetailsTO.setFatherHusOcc(getEmployeeOtherDetail().getFatherName_husbandOcc());
            hrPersonnelDetailsTO.setFatherHusDesig(getEmployeeOtherDetail().getFatherName_husbandDesg());
            hrPersonnelDetailsTO.setFatherHusOrg(getEmployeeOtherDetail().getFatherName_husbandOrg());
            hrPersonnelDetailsTO.setFatherHusPhone(getEmployeeOtherDetail().getFatherName_husbandPhone());
            hrPersonnelDetailsTO.setEmpContAdd(conAddress);
            hrPersonnelDetailsTO.setEmpContPin(conPin);
            hrPersonnelDetailsTO.setEmpContCity(conCity);
            hrPersonnelDetailsTO.setEmpContState(conState);
            hrPersonnelDetailsTO.setEmpContTel(conPhone);
            hrPersonnelDetailsTO.setEmpPermAdd(perAddress);
            hrPersonnelDetailsTO.setEmpPermPin(perPin);
            hrPersonnelDetailsTO.setEmpPermCity(perCity);
            hrPersonnelDetailsTO.setEmpPermState(perState);
            hrPersonnelDetailsTO.setEmpPermTel(perPhone);
            hrPersonnelDetailsTO.setHeight(getEmployeeOtherDetail().getHeight());
            hrPersonnelDetailsTO.setWeight(getEmployeeOtherDetail().getWeight());
            hrPersonnelDetailsTO.setHealth(getEmployeeOtherDetail().getHealth());
            hrPersonnelDetailsTO.setBloodGrp(getEmployeeOtherDetail().getBloodGroup());
            hrPersonnelDetailsTO.setBirthCity(getEmployeeOtherDetail().getBirthCity());
            hrPersonnelDetailsTO.setBirthState(getEmployeeOtherDetail().getBirthState());
            if (!getEmployeeOtherDetail().getWeddingDate().equalsIgnoreCase("")) {
                hrPersonnelDetailsTO.setWeddingDate(dmyFormat.parse(getEmployeeOtherDetail().getWeddingDate()));
            } else {
                hrPersonnelDetailsTO.setWeddingDate(dmyFormat.parse("01/01/1900"));
            }
            hrPersonnelDetailsTO.setEmailId(getEmployeeOtherDetail().getEmailId());
            hrPersonnelDetailsTO.setCertAdosNo(getEmployeeOtherDetail().getAdolescentNo());
            if (!getEmployeeOtherDetail().getAdolescentDate().equalsIgnoreCase("")) {
                hrPersonnelDetailsTO.setCertAdosDate(dmyFormat.parse(getEmployeeOtherDetail().getAdolescentDate()));
            } else {
                hrPersonnelDetailsTO.setCertAdosDate(dmyFormat.parse("01/01/1900"));
            }
            hrPersonnelDetailsTO.setCertTokNo(getEmployeeOtherDetail().getAdolescentTokenNo());
            hrPersonnelDetailsTO.setCertRef(getEmployeeOtherDetail().getAdolescentReferenceNo());
            hrPersonnelDetailsTO.setRelay(getEmployeeOtherDetail().getAdolescentRelay());
            if (!getEmployeeOtherDetail().getAdolescentRelayDate().equalsIgnoreCase("")) {
                hrPersonnelDetailsTO.setRelayDate(dmyFormat.parse(getEmployeeOtherDetail().getAdolescentRelayDate()));
            } else {
                hrPersonnelDetailsTO.setRelayDate(dmyFormat.parse("01/01/1900"));
            }

            hrPersonnelDetailsTO.setNation(getEmployeeOtherDetail().getNation());
            hrPersonnelDetailsTO.setChest(getEmployeeOtherDetail().getChest());
            hrPersonnelDetailsTO.setReligion(getEmployeeOtherDetail().getReligion());
            hrPersonnelDetailsTO.setMaritalStatus(getEmployeeOtherDetail().getMaritalStatus());
            hrPersonnelDetailsTO.setVisaDet(getEmployeeOtherDetail().getVisaDetail());
            hrPersonnelDetailsTO.setEmpStatus(empStatus);
            hrPersonnelDetailsTO.setChildren(Integer.parseInt(getEmployeeOtherDetail().getChildren()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrPersonnelDetailsTO;
    }

    public String getAuthorizedPassword() {
        return authorizedPassword;
    }

    public void setAuthorizedPassword(String authorizedPassword) {
        this.authorizedPassword = authorizedPassword;
    }

    public String getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(String authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public List<SelectItem> getBankList() {
        return bankList;
    }

    public void setBankList(List<SelectItem> bankList) {
        this.bankList = bankList;
    }

    public String getBankValue() {
        return bankValue;
    }

    public void setBankValue(String bankValue) {
        this.bankValue = bankValue;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public List<SelectItem> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<SelectItem> blockList) {
        this.blockList = blockList;
    }

    public String getBlockValue() {
        return blockValue;
    }

    public void setBlockValue(String blockValue) {
        this.blockValue = blockValue;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getCalToDate() {
        return calToDate;
    }

    public void setCalToDate(String calToDate) {
        this.calToDate = calToDate;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public List<SelectItem> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<SelectItem> categoryList) {
        this.categoryList = categoryList;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String getConAddress() {
        return conAddress;
    }

    public void setConAddress(String conAddress) {
        this.conAddress = conAddress;
    }

    public String getConAddressLine() {
        return conAddressLine;
    }

    public void setConAddressLine(String conAddressLine) {
        this.conAddressLine = conAddressLine;
    }

    public String getConCity() {
        return conCity;
    }

    public void setConCity(String conCity) {
        this.conCity = conCity;
    }

    public String getConPhone() {
        return conPhone;
    }

    public void setConPhone(String conPhone) {
        this.conPhone = conPhone;
    }

    public String getConPin() {
        return conPin;
    }

    public void setConPin(String conPin) {
        this.conPin = conPin;
    }

    public String getConState() {
        return conState;
    }

    public void setConState(String conState) {
        this.conState = conState;
    }

    public String getCurrentEmpId() {
        return currentEmpId;
    }

    public void setCurrentEmpId(String currentEmpId) {
        this.currentEmpId = currentEmpId;
    }

    public EmployeeGrid getCurrentEmpItem() {
        return currentEmpItem;
    }

    public void setCurrentEmpItem(EmployeeGrid currentEmpItem) {
        this.currentEmpItem = currentEmpItem;
    }

    public Integer getCurrentIEmpRow() {
        return currentIEmpRow;
    }

    public void setCurrentIEmpRow(Integer currentIEmpRow) {
        this.currentIEmpRow = currentIEmpRow;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public DefinitionsDelegate getDefinitionsDelegate() {
        return definitionsDelegate;
    }

    public void setDefinitionsDelegate(DefinitionsDelegate definitionsDelegate) {
        this.definitionsDelegate = definitionsDelegate;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public List<SelectItem> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<SelectItem> departmentList) {
        this.departmentList = departmentList;
    }

    public String getDepartmentValue() {
        return departmentValue;
    }

    public void setDepartmentValue(String departmentValue) {
        this.departmentValue = departmentValue;
    }

    public String getDesignationCode() {
        return designationCode;
    }

    public void setDesignationCode(String designationCode) {
        this.designationCode = designationCode;
    }

    public String getDesignationCode2() {
        return designationCode2;
    }

    public void setDesignationCode2(String designationCode2) {
        this.designationCode2 = designationCode2;
    }

    public List<SelectItem> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<SelectItem> designationList) {
        this.designationList = designationList;
    }

    public List<SelectItem> getDesignationList2() {
        return designationList2;
    }

    public void setDesignationList2(List<SelectItem> designationList2) {
        this.designationList2 = designationList2;
    }

    public String getDesignationValue() {
        return designationValue;
    }

    public void setDesignationValue(String designationValue) {
        this.designationValue = designationValue;
    }

    public String getDesignationValue2() {
        return designationValue2;
    }

    public void setDesignationValue2(String designationValue2) {
        this.designationValue2 = designationValue2;
    }

    public boolean isDisableCancelButton() {
        return disableCancelButton;
    }

    public void setDisableCancelButton(boolean disableCancelButton) {
        this.disableCancelButton = disableCancelButton;
    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public SimpleDateFormat getDmyFormat() {
        return dmyFormat;
    }

    public void setDmyFormat(SimpleDateFormat dmyFormat) {
        this.dmyFormat = dmyFormat;
    }

    public String getEmpCardNo() {
        return empCardNo;
    }

    public void setEmpCardNo(String empCardNo) {
        this.empCardNo = empCardNo;
    }

    public String getEmpFName() {
        return empFName;
    }

    public void setEmpFName(String empFName) {
        this.empFName = empFName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpLName() {
        return empLName;
    }

    public void setEmpLName(String empLName) {
        this.empLName = empLName;
    }

    public String getEmpMName() {
        return empMName;
    }

    public void setEmpMName(String empMName) {
        this.empMName = empMName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpSearchCriteria() {
        return empSearchCriteria;
    }

    public void setEmpSearchCriteria(String empSearchCriteria) {
        this.empSearchCriteria = empSearchCriteria;
    }

    public List<EmployeeGrid> getEmpSearchTable() {
        return empSearchTable;
    }

    public void setEmpSearchTable(List<EmployeeGrid> empSearchTable) {
        this.empSearchTable = empSearchTable;
    }

    public String getEmpSearchValue() {
        return empSearchValue;
    }

    public void setEmpSearchValue(String empSearchValue) {
        this.empSearchValue = empSearchValue;
    }

    public String getEmployeeTypeCode() {
        return employeeTypeCode;
    }

    public void setEmployeeTypeCode(String employeeTypeCode) {
        this.employeeTypeCode = employeeTypeCode;
    }

    public List<SelectItem> getEmployeeTypeList() {
        return employeeTypeList;
    }

    public void setEmployeeTypeList(List<SelectItem> employeeTypeList) {
        this.employeeTypeList = employeeTypeList;
    }

    public String getEmployeeTypeValue() {
        return employeeTypeValue;
    }

    public void setEmployeeTypeValue(String employeeTypeValue) {
        this.employeeTypeValue = employeeTypeValue;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getFinanceLoanAccountCode() {
        return financeLoanAccountCode;
    }

    public void setFinanceLoanAccountCode(String financeLoanAccountCode) {
        this.financeLoanAccountCode = financeLoanAccountCode;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public List<SelectItem> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<SelectItem> gradeList) {
        this.gradeList = gradeList;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getLastEmpId() {
        return lastEmpId;
    }

    public void setLastEmpId(String lastEmpId) {
        this.lastEmpId = lastEmpId;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public List<SelectItem> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<SelectItem> locationList) {
        this.locationList = locationList;
    }

    public String getLocationValue() {
        return locationValue;
    }

    public void setLocationValue(String locationValue) {
        this.locationValue = locationValue;
    }

    public List<String> getMembershipGroup() {
        return membershipGroup;
    }

    public void setMembershipGroup(List<String> membershipGroup) {
        this.membershipGroup = membershipGroup;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getOrganisationCode() {
        return organisationCode;
    }

    public void setOrganisationCode(String organisationCode) {
        this.organisationCode = organisationCode;
    }

    public List<SelectItem> getOrganisationList() {
        return organisationList;
    }

    public void setOrganisationList(List<SelectItem> organisationList) {
        this.organisationList = organisationList;
    }

    public String getOrganisationValue() {
        return organisationValue;
    }

    public void setOrganisationValue(String organisationValue) {
        this.organisationValue = organisationValue;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPerAddress() {
        return perAddress;
    }

    public void setPerAddress(String perAddress) {
        this.perAddress = perAddress;
    }

    public String getPerAddressLine() {
        return perAddressLine;
    }

    public void setPerAddressLine(String perAddressLine) {
        this.perAddressLine = perAddressLine;
    }

    public String getPerCity() {
        return perCity;
    }

    public void setPerCity(String perCity) {
        this.perCity = perCity;
    }

    public String getPerPhone() {
        return perPhone;
    }

    public void setPerPhone(String perPhone) {
        this.perPhone = perPhone;
    }

    public String getPerPin() {
        return perPin;
    }

    public void setPerPin(String perPin) {
        this.perPin = perPin;
    }

    public String getPerState() {
        return perState;
    }

    public void setPerState(String perState) {
        this.perState = perState;
    }

    public PersonnelDelegate getPersonnelDelegate() {
        return personnelDelegate;
    }

    public void setPersonnelDelegate(PersonnelDelegate personnelDelegate) {
        this.personnelDelegate = personnelDelegate;
    }

    public String getQualificationCode() {
        return qualificationCode;
    }

    public void setQualificationCode(String qualificationCode) {
        this.qualificationCode = qualificationCode;
    }

    public List<SelectItem> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<SelectItem> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public String getQualificationValue() {
        return qualificationValue;
    }

    public void setQualificationValue(String qualificationValue) {
        this.qualificationValue = qualificationValue;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public PersonnelUtil getPersonnelUtil() {
        return personnelUtil;
    }

    public void setPersonnelUtil(PersonnelUtil personnelUtil) {
        this.personnelUtil = personnelUtil;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public String getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
    }

    public List<SelectItem> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<SelectItem> skillList) {
        this.skillList = skillList;
    }

    public String getSkillValue() {
        return skillValue;
    }

    public void setSkillValue(String skillValue) {
        this.skillValue = skillValue;
    }

    public String getSpecializationCode() {
        return specializationCode;
    }

    public void setSpecializationCode(String specializationCode) {
        this.specializationCode = specializationCode;
    }

    public List<SelectItem> getSpecializationList() {
        return specializationList;
    }

    public void setSpecializationList(List<SelectItem> specializationList) {
        this.specializationList = specializationList;
    }

    public String getSpecializationValue() {
        return specializationValue;
    }

    public void setSpecializationValue(String specializationValue) {
        this.specializationValue = specializationValue;
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

    public String getSubDepartmentCode() {
        return subDepartmentCode;
    }

    public void setSubDepartmentCode(String subDepartmentCode) {
        this.subDepartmentCode = subDepartmentCode;
    }

    public List<SelectItem> getSubDepartmentList() {
        return subDepartmentList;
    }

    public void setSubDepartmentList(List<SelectItem> subDepartmentList) {
        this.subDepartmentList = subDepartmentList;
    }

    public String getSubDepartmentValue() {
        return subDepartmentValue;
    }

    public void setSubDepartmentValue(String subDepartmentValue) {
        this.subDepartmentValue = subDepartmentValue;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public List<SelectItem> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<SelectItem> unitList) {
        this.unitList = unitList;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public String getWorkStatusCode() {
        return workStatusCode;
    }

    public void setWorkStatusCode(String workStatusCode) {
        this.workStatusCode = workStatusCode;
    }

    public List<SelectItem> getWorkStatusList() {
        return workStatusList;
    }

    public void setWorkStatusList(List<SelectItem> workStatusList) {
        this.workStatusList = workStatusList;
    }

    public String getWorkStatusValue() {
        return workStatusValue;
    }

    public void setWorkStatusValue(String workStatusValue) {
        this.workStatusValue = workStatusValue;
    }

    public SimpleDateFormat getYmdFormat() {
        return ymdFormat;
    }

    public void setYmdFormat(SimpleDateFormat ymdFormat) {
        this.ymdFormat = ymdFormat;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public List<SelectItem> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<SelectItem> zoneList) {
        this.zoneList = zoneList;
    }

    public String getZoneValue() {
        return zoneValue;
    }

    public void setZoneValue(String zoneValue) {
        this.zoneValue = zoneValue;
    }

    public char getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(char empStatus) {
        this.empStatus = empStatus;
    }

    public Character getESIMembership() {
        return ESIMembership;
    }

    public void setESIMembership(Character ESIMembership) {
        this.ESIMembership = ESIMembership;
    }

    public Character getPFMembership() {
        return PFMembership;
    }

    public void setPFMembership(Character PFMembership) {
        this.PFMembership = PFMembership;
    }

    public Character getTRUSTMembership() {
        return TRUSTMembership;
    }

    public void setTRUSTMembership(Character TRUSTMembership) {
        this.TRUSTMembership = TRUSTMembership;
    }

    public Character getVPFMembership() {
        return VPFMembership;
    }

    public void setVPFMembership(Character VPFMembership) {
        this.VPFMembership = VPFMembership;
    }

    public boolean isDisableTabs() {
        return disableTabs;
    }

    public void setDisableTabs(boolean disableTabs) {
        this.disableTabs = disableTabs;
    }

    public EmployeeOtherDetail getEmployeeOtherDetail() {
        return employeeOtherDetail;
    }

    public void setEmployeeOtherDetail(EmployeeOtherDetail employeeOtherDetail) {
        this.employeeOtherDetail = employeeOtherDetail;
    }
}
