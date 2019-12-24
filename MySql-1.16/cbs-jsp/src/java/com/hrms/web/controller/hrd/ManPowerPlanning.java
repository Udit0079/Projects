/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.ManPowerPlanningSearch;
import com.cbs.web.controller.BaseBean;
import java.util.Iterator;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrManpowerPKTO;
import com.hrms.common.to.HrManpowerTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.RecruitmentDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zeeshan Waris
 */
public class ManPowerPlanning extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(ManPowerPlanning.class);
    private String message;
    private String zone;
    private String year;
    private boolean yearDisable;
    private String department;
    private String period;
    private String designation;
    private String Skill;
    private String grade;
    private String specialisation;
    private String qualification1;
    private String qualification2;
    private String qualification3;
    private String jobSpecialization;
    private String budeget;
    private String present;
    private String vacancies;
    private String minimum;
    private String required;
    private String preffered;
    private String compCode="0";
    private List<SelectItem> zoneList;
    private List<SelectItem> departmentList;
    private List<SelectItem> periodOfRequirementList;
    private List<SelectItem> designationList;
    private List<SelectItem> skillList;
    private List<SelectItem> specializationList;
    private List<SelectItem> qualificationList;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String monthHide;
    private List<ManPowerPlanningSearch> dataObject;
    private int currentRow;
    private ManPowerPlanningSearch currentItem = new ManPowerPlanningSearch();
    String gradeCode;
    private boolean flag;
    private Integer defaultComp;
    private boolean updateDisable; 
    private boolean saveDisable;
    private String operation;
    private List<SelectItem> operationList;
    
    public boolean isUpdateDisable() {
        return updateDisable;
    }

    public void setUpdateDisable(boolean updateDisable) {
        this.updateDisable = updateDisable;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public ManPowerPlanningSearch getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ManPowerPlanningSearch currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<ManPowerPlanningSearch> getDataObject() {
        return dataObject;
    }

    public void setDataObject(List<ManPowerPlanningSearch> dataObject) {
        this.dataObject = dataObject;
    }

    public String getMonthHide() {
        return monthHide;
    }

    public void setMonthHide(String monthHide) {
        this.monthHide = monthHide;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<SelectItem> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<SelectItem> departmentList) {
        this.departmentList = departmentList;
    }

    public List<SelectItem> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<SelectItem> designationList) {
        this.designationList = designationList;
    }

    public String getJobSpecialization() {
        return jobSpecialization;
    }

    public void setJobSpecialization(String jobSpecialization) {
        this.jobSpecialization = jobSpecialization;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public List<SelectItem> getPeriodOfRequirementList() {
        return periodOfRequirementList;
    }

    public void setPeriodOfRequirementList(List<SelectItem> periodOfRequirementList) {
        this.periodOfRequirementList = periodOfRequirementList;
    }

    public String getPreffered() {
        return preffered;
    }

    public void setPreffered(String preffered) {
        this.preffered = preffered;
    }

    public String getQualification1() {
        return qualification1;
    }

    public void setQualification1(String qualification1) {
        this.qualification1 = qualification1;
    }

    public String getQualification2() {
        return qualification2;
    }

    public void setQualification2(String qualification2) {
        this.qualification2 = qualification2;
    }

    public String getQualification3() {
        return qualification3;
    }

    public void setQualification3(String qualification3) {
        this.qualification3 = qualification3;
    }

    public List<SelectItem> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<SelectItem> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public List<SelectItem> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<SelectItem> skillList) {
        this.skillList = skillList;
    }

    public List<SelectItem> getSpecializationList() {
        return specializationList;
    }

    public void setSpecializationList(List<SelectItem> specializationList) {
        this.specializationList = specializationList;
    }

    public String getVacancies() {
        return vacancies;
    }

    public void setVacancies(String vacancies) {
        this.vacancies = vacancies;
    }

    public List<SelectItem> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<SelectItem> zoneList) {
        this.zoneList = zoneList;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSkill() {
        return Skill;
    }

    public void setSkill(String Skill) {
        this.Skill = Skill;
    }

    public String getBudeget() {
        return budeget;
    }

    public void setBudeget(String budeget) {
        this.budeget = budeget;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

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

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public boolean isYearDisable() {
        return yearDisable;
    }

    public void setYearDisable(boolean yearDisable) {
        this.yearDisable = yearDisable;
    }
     
    
    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public ManPowerPlanning() {
        try {
          
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","-Select-"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","Edit"));
            setTodayDate(sdf.format(date));
            this.setMessage("");
            dropdown(Integer.parseInt(compCode), "ZON%");
            dropdown(Integer.parseInt(compCode), "DEP%");
            dropdown(Integer.parseInt(compCode), "REQ%");
            dropdown(Integer.parseInt(compCode), "DES%");
            dropdown(Integer.parseInt(compCode), "SKI%");
            dropdown(Integer.parseInt(compCode), "SPE%");
            dropdown(Integer.parseInt(compCode), "QUA%");
            //getTableDetails();
            flag=true;
            yearDisable=true;
            setMinimum("0");
            setRequired("0");
            setPreffered("0");
            updateDisable = true;
            setOperation("0");
            setPresent("0");
            saveDisable=true;
          } catch (Exception e) {
            logger.error("Exception occured while executing method ManPowerPlanning()", e);
            logger.error("ManPowerPlanning()", e);
        }
    }

    public void dropdown(int compCode, String description) {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            List<HrMstStructTO> structMasterTOs = recruitmentDelegate.getManPowerPlanningList(compCode, description);
            if (description.equalsIgnoreCase("ZON%")) {
                zoneList = new ArrayList<SelectItem>();
                zoneList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    zoneList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
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
            if (description.equalsIgnoreCase("REQ%")) {
                periodOfRequirementList = new ArrayList<SelectItem>();
                periodOfRequirementList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    periodOfRequirementList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
            if (description.equalsIgnoreCase("DES%")) {
                designationList = new ArrayList<SelectItem>();
                designationList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    designationList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
            if (description.equalsIgnoreCase("SKI%")) {
                skillList = new ArrayList<SelectItem>();
                skillList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    skillList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
            if (description.equalsIgnoreCase("SPE%")) {
                specializationList = new ArrayList<SelectItem>();
                specializationList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    specializationList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
            if (description.equalsIgnoreCase("QUA%")) {
                qualificationList = new ArrayList<SelectItem>();
                qualificationList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    qualificationList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method dropdown()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method dropdown()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method dropdown()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }
      public void setOperation()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        }
        else if(operation.equalsIgnoreCase("1"))
        { 
            flag=false;
            yearDisable=false;
            saveDisable=false;
            updateDisable=true;
        }  
        else if(operation.equalsIgnoreCase("2"))
        {
            flag=false;
            saveDisable=true;
            updateDisable=false;
            yearDisable=false;
            getTableDetails();
        }
    }
    
    public void getTableDetails() {
          dataObject = new ArrayList<ManPowerPlanningSearch>();
        try {
            List resultLt = new ArrayList();
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            resultLt = recruitmentDelegate.manpowerDetailList(Integer.parseInt(compCode));
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    ManPowerPlanningSearch rd = new ManPowerPlanningSearch();
                    rd.setYear(result[0].toString());
                    rd.setMonth(result[1].toString());
                    rd.setDescription(result[2].toString());
                    rd.setZone(result[3].toString());
                    rd.setDescription1(result[4].toString());
                    rd.setDeptCode(result[5].toString());
                    rd.setDescription2(result[6].toString());
                    rd.setQualCode(result[7].toString());
                    rd.setQualCode2(result[8].toString());
                    rd.setQualcode3(result[9].toString());
                    rd.setSpecialcode(result[10].toString());
                    rd.setDesgCode(result[11].toString());
                    rd.setDescription3(result[12].toString());
                    rd.setMinExperience(result[13].toString());
                    rd.setPrefExperience(result[14].toString());
                    rd.setReqExperience(result[15].toString());
                    rd.setAutoExperience(result[16].toString());
                    rd.setPosFill(result[17].toString());
                    rd.setPosReq(result[18].toString());
                    rd.setPosSanc(result[19].toString());
                    rd.setSkillCode(result[20].toString());
                    dataObject.add(rd);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getTableDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /************************** END Table Data functionality************************/
    /************************** Select Button functionality************************/
    public void select() {
        flag = false;
        yearDisable=true;
        this.setMessage("");
        setZone(currentItem.getZone());
        setYear(currentItem.getYear());
        setDepartment(currentItem.getDeptCode());
        setPeriod(currentItem.getMonth());
        setDesignation(currentItem.getDesgCode());
        setSkill(currentItem.getSkillCode());
        setGrade(currentItem.getDescription3());
        setSpecialisation(currentItem.getSpecialcode());
        setQualification1(currentItem.getQualCode());
        setQualification2(currentItem.getQualCode2());
        setQualification3(currentItem.getQualcode3());
        setJobSpecialization(currentItem.getDescription2());
        setBudeget(currentItem.getPosSanc());
        setPresent(currentItem.getPosFill());
        setVacancies(currentItem.getPosReq());
        setMinimum(currentItem.getMinExperience());
        setRequired(currentItem.getReqExperience());
        setPreffered(currentItem.getPrefExperience());
    }

    public void manpowerPlanGradeCode() {
        if (zone == null) {
            setMessage("Please select zone");
            return;
        } else if (zone.equalsIgnoreCase("0")) {
            setMessage("Please select zone");
            return;
        }
        if (department == null) {
            setMessage("Please select Department");
            return;
        } else if (department.equalsIgnoreCase("0")) {
            setMessage("Please select Department");
            return;
        }

        if (designation.equalsIgnoreCase("0")) {
            setMessage("Please select Designation");
            return;
        } else if (designation.equalsIgnoreCase("0")) {
            setMessage("Please select Designation");
            return;
        }
        setMessage("");
        setGradeCode("");
        setGrade("");
        setJobSpecialization("");
        try {
            List resultLt = new ArrayList();
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            resultLt = recruitmentDelegate.manpowerGradeCodeList(Integer.parseInt(compCode), designation);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    setGradeCode(result[0].toString());
                    setGrade(result[1].toString());
                    setJobSpecialization(result[2].toString());
                }
            }
            manpowerPlanEmployeeNo();
        } catch (WebException e) {
            logger.error("Exception occured while executing method manpowerPlanGradeCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method manpowerPlanGradeCode()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method manpowerPlanGradeCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void manpowerPlanEmployeeNo() {
        try {
            List resultLt = new ArrayList();
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            resultLt = recruitmentDelegate.manpowerEmployeeNoList(Integer.parseInt(compCode), zone, department, designation, gradeCode);
            String size = Integer.toString(resultLt.size());
            setPresent(size);
        } catch (WebException e) {
            logger.error("Exception occured while executing method manpowerPlanEmployeeNo()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method manpowerPlanEmployeeNo()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method manpowerPlanEmployeeNo()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void manpowerPlanDeleteAction() {
        saveDisable=true;
        updateDisable=true;
        try {

            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            String rsmsg = recruitmentDelegate.manpowerPlanningDelete(Integer.parseInt(compCode), Integer.parseInt(currentItem.getYear()), currentItem.getMonth(), currentItem.getZone(), currentItem.getDeptCode());
            if (rsmsg.equalsIgnoreCase("true")) {
                setMessage("Data deleted successfully!");
            } else {
                setMessage(rsmsg);
            }
            clear();
            getTableDetails();
        } catch (WebException e) {
            logger.error("Exception occured while executing method manpowerPlanDeleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method manpowerPlanDeleteAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method manpowerPlanDeleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void manpowerSaveAction() {
        setMessage("");
        saveDisable=false;
        updateDisable=true;
        flag=false;
        yearDisable=false;
       try {
            if (compCode.equalsIgnoreCase("")) {
                setMessage("Please fill the Comany code!");
                return;
            }
            if (compCode == null) {
                setMessage("Please fill the Comany code!");
                return;
            }
            if (period.equalsIgnoreCase("")) {
                setMessage("Please fill the month!");
                return;
            }
            if (period == null) {
                setMessage("Please fill the month!");
                return;
            }
            if (year.equalsIgnoreCase("")) {
                setMessage("Please fill year!");
                return;
            }
            if (year == null) {
                setMessage("Please fill year!");
                return;
            }

            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            String stateFlag = "Y";
            String stateUpflag = "Y";
            HrManpowerTO manpower = new HrManpowerTO();
            HrManpowerPKTO manpowerPk = new HrManpowerPKTO();
            manpowerPk.setCompCode(Integer.parseInt(compCode));
            manpowerPk.setYear(Integer.parseInt(year));
            manpowerPk.setMonth(period);
            manpowerPk.setZone(zone);
            manpowerPk.setDeptCode(department);
            manpower.setHrManpowerPKTO(manpowerPk);
            manpower.setQualcode1(qualification1);
            manpower.setQualCode2(qualification2);
            manpower.setQualCode3(qualification3);
            manpower.setSpecialCode(specialisation);
            manpower.setDesgCode(designation);
            manpower.setMinmExp(Double.parseDouble(minimum));
            manpower.setPrefExp(Double.parseDouble(preffered));
            manpower.setRequExp(Double.parseDouble(required));
            String auto = "0";
            manpower.setAutoExp(Double.parseDouble(auto));
            manpower.setPosFill(Integer.parseInt(present));
            manpower.setPosRequ(Integer.parseInt(vacancies));
            manpower.setPosSanc(Integer.parseInt(budeget));
            manpower.setSkillCode(Skill);
            manpower.setDefaultComp(defaultComp);
            manpower.setStatFlag(stateFlag);
            manpower.setStatUpFlag(stateUpflag);
            manpower.setModDate(getDate());
            manpower.setAuthBy(getUserName());
            manpower.setEnteredBy(getUserName());
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            String msg = recruitmentDelegate.manpowerPlanSave(manpower);
            clear();
            setMessage(msg);
            getTableDetails();
        } catch (WebException e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setMessage("Duplicate entry Exist,please fill another detail");
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method initData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setMessage("Duplicate entry Exist,please fill another detail");
        }
      

    }

    public void manpowerUpdateAction() {
        setMessage("");
        setSaveDisable(true);
        setUpdateDisable(false);
        flag=false;
        yearDisable=false;
        try {
            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            String stateFlag = "Y";
            String stateUpflag = "Y";
            HrManpowerTO manpower = new HrManpowerTO();
            HrManpowerPKTO manpowerPk = new HrManpowerPKTO();
            manpowerPk.setCompCode(Integer.parseInt(compCode));
            manpowerPk.setYear(Integer.parseInt(year));
            manpowerPk.setMonth(period);
            manpowerPk.setZone(zone);
            manpowerPk.setDeptCode(department);
            manpower.setHrManpowerPKTO(manpowerPk);
            manpower.setQualcode1(qualification1);
            manpower.setQualCode2(qualification2);
            manpower.setQualCode3(qualification3);
            manpower.setSpecialCode(specialisation);
            manpower.setDesgCode(designation);
            manpower.setMinmExp(Double.parseDouble(minimum));
            manpower.setPrefExp(Double.parseDouble(preffered));
            manpower.setRequExp(Double.parseDouble(required));
            String auto = "0";
            manpower.setAutoExp(Double.parseDouble(auto));
            manpower.setPosFill(Integer.parseInt(present));
            manpower.setPosRequ(Integer.parseInt(vacancies));
            manpower.setPosSanc(Integer.parseInt(budeget));
            manpower.setSkillCode(Skill);
            manpower.setDefaultComp(defaultComp);
            manpower.setStatFlag(stateFlag);
            manpower.setStatUpFlag(stateUpflag);
            manpower.setModDate(getDate());
            manpower.setAuthBy(getUserName());
            manpower.setEnteredBy(getUserName());
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            String msg = recruitmentDelegate.manpowerPlanupdate(manpower);
            setMessage(msg);
            clear();
            getTableDetails();
        } catch (WebException e) {
            logger.error("Exception occured while executing method manpowerUpdateAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method manpowerUpdateAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method manpowerUpdateAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void clear() {
        setZone("0");
        setYear("");
        setDepartment("0");
        setPeriod("0");
        setDesignation("0");
        setSkill("0");
        setGrade("");
        setSpecialisation("0");
        setQualification1("0");
        setQualification2("0");
        setQualification3("0");
        setJobSpecialization("");
        setBudeget("");
        setPresent("");
        setVacancies("");
        setMinimum("");
        setRequired("");
        setPreffered("");
        setOperation("0");
        updateDisable = true;
        saveDisable=true;
    }

    public void refreshbuttonAction() {
        updateDisable = true;
        saveDisable=true;
        flag =true;
        yearDisable=true;
        setMessage("");
        setZone("0");
        setYear("");
        setDepartment("0");
        setPeriod("0");
        setDesignation("0");
        setSkill("0");
        setGrade("");
        setSpecialisation("0");
        setQualification1("0");
        setQualification2("0");
        setQualification3("0");
        setJobSpecialization("");
        setBudeget("");
        setPresent("");
        setVacancies("");
        setMinimum("0");
        setRequired("0");
        setPreffered("0");
        setOperation("0");
    }

    public String validations() {
        try {
            if (zone == null) {
                this.setMessage("Please select zone");
                return "false";
            }
            if (zone.equalsIgnoreCase("")) {
                this.setMessage("Please select zone");
                return "false";
            }
            if (year == null) {
                this.setMessage("Please fill the Year");
                return "false";
            }
            if (year.equalsIgnoreCase("")) {
                this.setMessage("Please fill the Year");
                return "false";
            }
            if (!year.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Year");
                return "false";
            } else {
                if (year.length() < 4) {
                    this.setMessage("Year can't be less than 4 char");
                    return "false";
                }
            }
            if (department == null) {
                this.setMessage("Please select department");
                return "false";
            }
            if (department.equalsIgnoreCase("")) {
                this.setMessage("Please select department");
                return "false";
            }

            if (period == null) {
                this.setMessage("Please select Period Of Requirement");
                return "false";
            }
            if (period.equalsIgnoreCase("")) {
                this.setMessage("Please select Period Of Requirement");
                return "false";
            }

            if (designation == null) {
                this.setMessage("Please select Designation");
                return "false";
            }
            if (designation.equalsIgnoreCase("")) {
                this.setMessage("Please select Designation");
                return "false";
            }

            if (!budeget.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Budeget");
                return "false";
            }
            if (!present.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Present");
                return "false";
            }
            if (!vacancies.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Vacancies");
                return "false";
            }
            if (!minimum.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Minimum");
                return "false";
            }
            if (!required.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Required");
                return "false";
            }
            if (!preffered.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Preferred");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }

    public String qualificationValidationFirst() {
        setMessage("");
        try {
            if (qualification1.equals(qualification2)) {
                setMessage("Duplicate Qualification, please select another Qualification");
                return "false";
            }
            if (qualification1.equals(qualification3)) {
                setMessage("Duplicate Qualification, please select another Qualification");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }

    public String qualificationValidationSecond() {
        setMessage("");
        try {
            if (qualification1.equals(qualification2)) {
                setMessage("Duplicate Qualification, please select another Qualification");
                return "false";
            }
            if (qualification2.equals(qualification3)) {
                setMessage("Duplicate Qualification, please select another Qualification");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }

    public String qualificationValidationThird() {
        setMessage("");
        try {
            if (qualification1.equals(qualification3)) {
                setMessage("Duplicate Qualification, please select another Qualification");
                return "false";
            }
            if (qualification2.equals(qualification3)) {
                setMessage("Duplicate Qualification, please select another Qualification");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }

    public void vacanciesAction() {
        this.setMessage("");
         try {
            if (budeget.equalsIgnoreCase("")) {
                this.setMessage("Enter Numeric Value in Budget");
                return;
            } else {
                if (!budeget.matches("[0-9]*")) {
                    this.setMessage("Enter Numeric Value in Budget");
                    return;
                }
            }
            if (budeget == null) {
                this.setMessage("Enter Numeric Value in Budget");
                return;
            } else {
                if (!budeget.matches("[0-9]*")) {
                    this.setMessage("Enter Numeric Value in Budget");
                    return;
                }
            }
            setVacancies(budeget);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String btnExit() {
        try {
            refreshbuttonAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "case1";
    }
}
