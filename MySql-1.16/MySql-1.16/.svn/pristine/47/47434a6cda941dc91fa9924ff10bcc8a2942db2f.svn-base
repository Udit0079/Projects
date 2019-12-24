package com.hrms.web.controller.personnel;

import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.EmployeeGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class JobDetails {

    private static final Logger logger = Logger.getLogger(JobDetails.class);
    List<EmployeeGrid> empSearchTable;
    EmployeeGrid currentEmpRow;
    private EmployeeGrid currentEmpItem = new EmployeeGrid();
    private Character sex, empStatus, overtime;
    private long empCode;
    private int defaultComp, currentIEmpRow, compCode, totalEmpRecords;
    private String mode, statFlag, statUpFlag, authBy, enteredBy, message, totExp, autoExp, empId, empSearchCriteria, empSearchValue, empName, noticePeriod, probationPeriod, confirmationDate, reportingToEmpName, skillCode, specialisationCode, achievement, jobdesc, blockCode, unitCode, empFName, employeeTypeCode, zoneCode, locationCode, deptcode, gradeCode, desgCode, categoryCode;
    private Date joiningDate;
    private boolean disableAddButton, disableSaveButton, disableEditButton, disableDeleteButton, disableTotExp, disableAutoExp, disableNoticePeriod, disableProbationPeriod, disableConfirmationDate, disableOvertime, disableSkill, disableSppecialisation, disableAchievement, disableJobdesc, disableButton1;
    protected PersonnelDelegate personnelDelegate;
    private PersonnelUtil personnelUtil;
    protected HttpServletRequest request;
    private Validator validator;
  
    public JobDetails() {
        try {
            mode = "update";
            statFlag = "Y";
            statUpFlag = "Y";
            personnelDelegate = new PersonnelDelegate();
            personnelUtil = new PersonnelUtil();
            request = getRequest();
            String user = (String) getRequest().getSession().getAttribute("USER_NAME");
            authBy = user;
            enteredBy = user;
            validator = new Validator();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method JobDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method JobDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

   

    private boolean validate() {
        if (!validator.validateDoublePositive(totExp)) {
            setMessage("Total Experience Can Have Numeric Value Only !!");
            return false;
        }
        if (!validator.validateDoublePositive(autoExp)) {
            setMessage("Related Industry Experience Can Have Numeric Value Only !!");
            return false;
        }
        if (!validator.validateDoublePositive(noticePeriod)) {
            setMessage("Notice Period Can Have Numeric Value Only !!");
            return false;
        }
        if (!validator.validateDoublePositive(probationPeriod)) {
            setMessage("Probation Period Can Have Numeric Value Only !!");
            return false;
        }
        return true;
    }

    public void saveJobDetails() {
        try {
            if (empCode != 0) {
                if (totExp == null || totExp.equalsIgnoreCase("")) {
                    totExp = "0";
                }
                if (autoExp == null || autoExp.equalsIgnoreCase("")) {
                    autoExp = "0";
                }
                if (noticePeriod == null || noticePeriod.equalsIgnoreCase("")) {
                    noticePeriod = "0";
                }
                if (probationPeriod == null || probationPeriod.equalsIgnoreCase("")) {
                    probationPeriod = "0";
                }
                if (confirmationDate == null || confirmationDate.equalsIgnoreCase("")) {
                    confirmationDate = "01/01/1900";
                }
                if (validate()) {
                    String result = personnelDelegate.saveHrPersonnelJobDetails(getPersonnelDetailsTO(compCode, empCode), "update");
                    cancelJobDetails();
                    setMessage(result);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveJobDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveJobDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void editJobDetails() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
            defaultComp = (Integer) getRequest().getSession().getAttribute("DEFAULT_COMP");
            if (empCode != 0) {
                setMessage("");
                HrPersonnelDetailsTO hrPersonnelDetailsTO = personnelDelegate.getAllByCompCodeAndEmpCodeOrEMPID(compCode, empCode, "");
                if (hrPersonnelDetailsTO != null) {
                    empCode = hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getEmpCode();
                    empId = hrPersonnelDetailsTO.getEmpId();
                    blockCode = hrPersonnelDetailsTO.getBlock();
                    unitCode = hrPersonnelDetailsTO.getUnitName();
                    empFName = hrPersonnelDetailsTO.getEmpFirName();
                    sex = hrPersonnelDetailsTO.getSex();
                    employeeTypeCode = hrPersonnelDetailsTO.getEmpType();
                    zoneCode = hrPersonnelDetailsTO.getZones();
                    locationCode = hrPersonnelDetailsTO.getLocatCode();
                    deptcode = hrPersonnelDetailsTO.getDeptCode();
                    gradeCode = hrPersonnelDetailsTO.getGradeCode();
                    desgCode = hrPersonnelDetailsTO.getDesgCode();
                    categoryCode = hrPersonnelDetailsTO.getCatgCode();
                    joiningDate = hrPersonnelDetailsTO.getJoinDate();
                    empStatus = 'Y';
                    if (hrPersonnelDetailsTO.getTotExpr() != null) {
                        setTotExp(hrPersonnelDetailsTO.getTotExpr() + "");
                    } else {
                        setTotExp("0");
                    }
                    if (hrPersonnelDetailsTO.getAutoExpr() != null) {
                        setAutoExp(hrPersonnelDetailsTO.getAutoExpr() + "");
                    } else {
                        setAutoExp("0");
                    }
                    if (hrPersonnelDetailsTO.getNoticePeriod() != null) {
                        setNoticePeriod(hrPersonnelDetailsTO.getNoticePeriod() + "");
                    } else {
                        setNoticePeriod("0");
                    }
                    if (hrPersonnelDetailsTO.getProbPeriod() != null) {
                        setProbationPeriod(hrPersonnelDetailsTO.getProbPeriod() + "");
                    } else {
                        setProbationPeriod("0");
                    }
                    if (hrPersonnelDetailsTO.getConfirmationDate() != null) {
                        setConfirmationDate(personnelUtil.dmyFormat.format(hrPersonnelDetailsTO.getConfirmationDate()));
                    } else {
                        setConfirmationDate("");
                    }
                    if (hrPersonnelDetailsTO.getOtEligibility() != null) {
                        setOvertime(hrPersonnelDetailsTO.getOtEligibility());
                    } else {
                        setOvertime('0');
                    }
                    if (hrPersonnelDetailsTO.getRepTo() != null) {
                        setReportingToEmpName(hrPersonnelDetailsTO.getRepTo());
                    } else {
                        setReportingToEmpName("");
                    }
                    if (hrPersonnelDetailsTO.getSkillCode() != null) {
                        setSkillCode(hrPersonnelDetailsTO.getSkillCode());
                    } else {
                        setSkillCode("0");
                    }
                    if (hrPersonnelDetailsTO.getSpecialCode() != null) {
                        setSpecialisationCode(hrPersonnelDetailsTO.getSpecialCode());
                    } else {
                        setSpecialisationCode("0");
                    }
                    if (hrPersonnelDetailsTO.getCareer() != null) {
                        setAchievement(hrPersonnelDetailsTO.getCareer());
                    } else {
                        setAchievement("");
                    }
                    if (hrPersonnelDetailsTO.getJobdesc() != null) {
                        setJobdesc(hrPersonnelDetailsTO.getJobdesc());
                    } else {
                        setJobdesc("");
                    }
                } else {
                    setMessage("No data Exists: Please Fill Details for employee code "+empCode);
                }
            } else {
                this.setMessage("Please Select An Employee From General Details Page");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editJobDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteJobDetails() {
        try {
            HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
            HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
            hrPersonnelDetailsPKTO.setCompCode(compCode);
            hrPersonnelDetailsPKTO.setEmpCode(empCode);
            hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);
            hrPersonnelDetailsTO.setTotExpr(null);
            hrPersonnelDetailsTO.setAutoExpr(null);
            hrPersonnelDetailsTO.setNoticePeriod(null);
            hrPersonnelDetailsTO.setProbPeriod(null);
            hrPersonnelDetailsTO.setConfirmationDate(null);
            hrPersonnelDetailsTO.setOtEligibility(null);
            hrPersonnelDetailsTO.setRepTo("");
            hrPersonnelDetailsTO.setSkillCode("");
            hrPersonnelDetailsTO.setSpecialCode("");
            hrPersonnelDetailsTO.setCareer("");
            hrPersonnelDetailsTO.setJobdesc("");
            personnelDelegate.saveHrPersonnelJobDetails(hrPersonnelDetailsTO, "update");
            cancelJobDetails();
            setMessage("Data Has Been Successfully Deleted !!");
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteJobDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteJobDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelJobDetails() {
        try {
            setMessage("");
            setTotExp("");
            setAutoExp("");
            setNoticePeriod("");
            setProbationPeriod("");
            setConfirmationDate("");
            setOvertime('0');
            setReportingToEmpName("");
            setSkillCode("0");
            setSpecialisationCode("0");
            setAchievement("");
            setJobdesc("");
            empSearchTable = new ArrayList<EmployeeGrid>();
            empSearchCriteria = "Name";
            empSearchValue = "";
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelJobDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String exitJobDetails() {
        try {
            cancelJobDetails();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitJobDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public HrPersonnelDetailsTO getPersonnelDetailsTO(int compCode, long empCode) {
        HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
        HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
        try {
            hrPersonnelDetailsPKTO.setCompCode(compCode);
            hrPersonnelDetailsPKTO.setEmpCode(empCode);
            hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);

            hrPersonnelDetailsTO.setTotExpr(Double.parseDouble(totExp));
            hrPersonnelDetailsTO.setAutoExpr(Double.parseDouble(autoExp));
            hrPersonnelDetailsTO.setNoticePeriod(Double.parseDouble(noticePeriod));
            hrPersonnelDetailsTO.setProbPeriod(Double.parseDouble(probationPeriod));
            hrPersonnelDetailsTO.setConfirmationDate(personnelUtil.dmyFormat.parse(confirmationDate));
            hrPersonnelDetailsTO.setOtEligibility(overtime);
            hrPersonnelDetailsTO.setJobdesc(jobdesc);
            hrPersonnelDetailsTO.setRepTo(reportingToEmpName);
            hrPersonnelDetailsTO.setSkillCode(skillCode);
            hrPersonnelDetailsTO.setSpecialCode(specialisationCode);
            hrPersonnelDetailsTO.setCareer(achievement);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrPersonnelDetailsTO;
    }

 

 
    public void getEmployeeTableData() {
        try {
            compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
            empSearchTable = personnelUtil.getEmployeeTableData(compCode, empSearchCriteria, empSearchValue);
            totalEmpRecords = empSearchTable.size();
        } catch (WebException e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setEmpRowValues() {
        try {
            setReportingToEmpName(currentEmpItem.getEmpId());
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

        
    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getAutoExp() {
        return autoExp;
    }

    public void setAutoExp(String autoExp) {
        this.autoExp = autoExp;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public int getTotalEmpRecords() {
        return totalEmpRecords;
    }

    public void setTotalEmpRecords(int totalEmpRecords) {
        this.totalEmpRecords = totalEmpRecords;
    }

    public String getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(String confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public EmployeeGrid getCurrentEmpItem() {
        return currentEmpItem;
    }

    public void setCurrentEmpItem(EmployeeGrid currentEmpItem) {
        this.currentEmpItem = currentEmpItem;
    }

    public EmployeeGrid getCurrentEmpRow() {
        return currentEmpRow;
    }

    public void setCurrentEmpRow(EmployeeGrid currentEmpRow) {
        this.currentEmpRow = currentEmpRow;
    }

    public int getCurrentIEmpRow() {
        return currentIEmpRow;
    }

    public void setCurrentIEmpRow(int currentIEmpRow) {
        this.currentIEmpRow = currentIEmpRow;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public boolean isDisableAchievement() {
        return disableAchievement;
    }

    public void setDisableAchievement(boolean disableAchievement) {
        this.disableAchievement = disableAchievement;
    }

    public boolean isDisableAutoExp() {
        return disableAutoExp;
    }

    public void setDisableAutoExp(boolean disableAutoExp) {
        this.disableAutoExp = disableAutoExp;
    }

    public boolean isDisableButton1() {
        return disableButton1;
    }

    public void setDisableButton1(boolean disableButton1) {
        this.disableButton1 = disableButton1;
    }

    public boolean isDisableConfirmationDate() {
        return disableConfirmationDate;
    }

    public void setDisableConfirmationDate(boolean disableConfirmationDate) {
        this.disableConfirmationDate = disableConfirmationDate;
    }

    public boolean isDisableJobdesc() {
        return disableJobdesc;
    }

    public void setDisableJobdesc(boolean disableJobdesc) {
        this.disableJobdesc = disableJobdesc;
    }

    public boolean isDisableNoticePeriod() {
        return disableNoticePeriod;
    }

    public void setDisableNoticePeriod(boolean disableNoticePeriod) {
        this.disableNoticePeriod = disableNoticePeriod;
    }

    public boolean isDisableOvertime() {
        return disableOvertime;
    }

    public void setDisableOvertime(boolean disableOvertime) {
        this.disableOvertime = disableOvertime;
    }

    public boolean isDisableProbationPeriod() {
        return disableProbationPeriod;
    }

    public void setDisableProbationPeriod(boolean disableProbationPeriod) {
        this.disableProbationPeriod = disableProbationPeriod;
    }

    public boolean isDisableSkill() {
        return disableSkill;
    }

    public void setDisableSkill(boolean disableSkill) {
        this.disableSkill = disableSkill;
    }

    public boolean isDisableSppecialisation() {
        return disableSppecialisation;
    }

    public void setDisableSppecialisation(boolean disableSppecialisation) {
        this.disableSppecialisation = disableSppecialisation;
    }

    public boolean isDisableTotExp() {
        return disableTotExp;
    }

    public void setDisableTotExp(boolean disableTotExp) {
        this.disableTotExp = disableTotExp;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
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

    public Character getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(Character empStatus) {
        this.empStatus = empStatus;
    }

    public String getEmployeeTypeCode() {
        return employeeTypeCode;
    }

    public void setEmployeeTypeCode(String employeeTypeCode) {
        this.employeeTypeCode = employeeTypeCode;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getJobdesc() {
        return jobdesc;
    }

    public void setJobdesc(String jobdesc) {
        this.jobdesc = jobdesc;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(String noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public Character getOvertime() {
        return overtime;
    }

    public void setOvertime(Character overtime) {
        this.overtime = overtime;
    }

    public PersonnelDelegate getPersonnelDelegate() {
        return personnelDelegate;
    }

    public void setPersonnelDelegate(PersonnelDelegate personnelDelegate) {
        this.personnelDelegate = personnelDelegate;
    }

    public String getProbationPeriod() {
        return probationPeriod;
    }

    public void setProbationPeriod(String probationPeriod) {
        this.probationPeriod = probationPeriod;
    }

    public String getReportingToEmpName() {
        return reportingToEmpName;
    }

    public void setReportingToEmpName(String reportingToEmpName) {
        this.reportingToEmpName = reportingToEmpName;
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

    public String getSpecialisationCode() {
        return specialisationCode;
    }

    public void setSpecialisationCode(String specialisationCode) {
        this.specialisationCode = specialisationCode;
    }

    public String getTotExp() {
        return totExp;
    }

    public void setTotExp(String totExp) {
        this.totExp = totExp;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public boolean isDisableEditButton() {
        return disableEditButton;
    }

    public void setDisableEditButton(boolean disableEditButton) {
        this.disableEditButton = disableEditButton;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public PersonnelUtil getPersonnelUtil() {
        return personnelUtil;
    }

    public void setPersonnelUtil(PersonnelUtil personnelUtil) {
        this.personnelUtil = personnelUtil;
    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }

    public boolean isDisableAddButton() {
        return disableAddButton;
    }

    public void setDisableAddButton(boolean disableAddButton) {
        this.disableAddButton = disableAddButton;
    }
}
