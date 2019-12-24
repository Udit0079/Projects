/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.DirectEmployeeSearch;
import com.hrms.web.pojo.DirectRecruitmentViewTable;
import com.hrms.web.pojo.DirectRecruitmentSearch;
import com.cbs.web.controller.BaseBean;
import java.util.Iterator;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrDirectRecPKTO;
import com.hrms.common.to.HrDirectRecTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.RecruitmentDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import java.math.BigInteger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Zeeshan Waris
 */
public class DirectRecruitment extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(DirectRecruitment.class);
    private String message;
    private String arNumber;
    private String designation;
    private String zone;
    private String location;
    private Date arDate;
    private Date confirmDt;
    private Date appointmentDt;
    private String candidateName;
    private String fatherName;
    private String contactNo;
    private String qualification;
    private String jobStatus;
    private String justification;
    private String initiatedBy;
    private String supervisorName;
    private String deparmentHead;
    private String approvedByhrd;
    private String approvedByMd;
    private String basicSalary;
    private String hra;
    private String ta;
    private String otherAllowance;
    private String total;
    private String address;
    private String city;
    private String state;
    private String pin;
    private String emailId;
    private String compCode = "0";
    private List<SelectItem> designationList;
    private List<SelectItem> zoneList;
    private List<SelectItem> qualificationList;
    private List<SelectItem> jobStatusList;
    private List<SelectItem> approvedByhrdList;
    private List<SelectItem> approvedbyMdList;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<DirectRecruitmentSearch> searchRecruitment;
    private int currentRow1, defaultComp;
    private DirectRecruitmentSearch currentItem1 = new DirectRecruitmentSearch();
    private String message1;
    private String searchBy;
    private String nameSearch;
    private List<SelectItem> searchList;
    private List<DirectEmployeeSearch> directEmpSearch;
    private int currentRow2;
    private DirectEmployeeSearch currentItem2 = new DirectEmployeeSearch();
    private String flag;
    private String flag1;
    private String flag2;
    private List<DirectRecruitmentViewTable> directView;
    private int currentRow3;
    private boolean saveDisable;
    private boolean disableallflag;
    private String mode;
    private String candName;
    private boolean deleteDisable;
    //private List<DirectRecruitmentDto> dtoDirect;
    private String locationHide;
    private String inititatorIdHide;
    private String superIdHide;
    private String headIdHide;
    private String inititatorIdSave;
    private String superIdSave;
    private String headIdSave;
    private String locationUpdate;
    private boolean arNoDisable;
    private Validator valid = new Validator();
    private String operation;
    private List<SelectItem> operationList;

    public boolean isArNoDisable() {
        return arNoDisable;
    }

    public void setArNoDisable(boolean arNoDisable) {
        this.arNoDisable = arNoDisable;
    }

    public String getLocationUpdate() {
        return locationUpdate;
    }

    public void setLocationUpdate(String locationUpdate) {
        this.locationUpdate = locationUpdate;
    }

    public String getHeadIdSave() {
        return headIdSave;
    }

    public void setHeadIdSave(String headIdSave) {
        this.headIdSave = headIdSave;
    }

    public String getInititatorIdSave() {
        return inititatorIdSave;
    }

    public void setInititatorIdSave(String inititatorIdSave) {
        this.inititatorIdSave = inititatorIdSave;
    }

    public String getSuperIdSave() {
        return superIdSave;
    }

    public void setSuperIdSave(String superIdSave) {
        this.superIdSave = superIdSave;
    }

    public String getHeadIdHide() {
        return headIdHide;
    }

    public void setHeadIdHide(String headIdHide) {
        this.headIdHide = headIdHide;
    }

    public String getInititatorIdHide() {
        return inititatorIdHide;
    }

    public void setInititatorIdHide(String inititatorIdHide) {
        this.inititatorIdHide = inititatorIdHide;
    }

    public String getSuperIdHide() {
        return superIdHide;
    }

    public void setSuperIdHide(String superIdHide) {
        this.superIdHide = superIdHide;
    }

    public String getLocationHide() {
        return locationHide;
    }

    public void setLocationHide(String locationHide) {
        this.locationHide = locationHide;
    }

    public boolean isDeleteDisable() {
        return deleteDisable;
    }

    public void setDeleteDisable(boolean deleteDisable) {
        this.deleteDisable = deleteDisable;
    }

    public String getCandName() {
        return candName;
    }

    public void setCandName(String candName) {
        this.candName = candName;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }
    private DirectRecruitmentViewTable currentItem3 = new DirectRecruitmentViewTable();

    public DirectRecruitmentViewTable getCurrentItem3() {
        return currentItem3;
    }

    public void setCurrentItem3(DirectRecruitmentViewTable currentItem3) {
        this.currentItem3 = currentItem3;
    }

    public List<DirectRecruitmentViewTable> getDirectView() {
        return directView;
    }

    public void setDirectView(List<DirectRecruitmentViewTable> directView) {
        this.directView = directView;
    }

    public DirectEmployeeSearch getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(DirectEmployeeSearch currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public int getCurrentRow2() {
        return currentRow2;
    }

    public void setCurrentRow2(int currentRow2) {
        this.currentRow2 = currentRow2;
    }

    public List<DirectEmployeeSearch> getDirectEmpSearch() {
        return directEmpSearch;
    }

    public void setDirectEmpSearch(List<DirectEmployeeSearch> directEmpSearch) {
        this.directEmpSearch = directEmpSearch;
    }

    public List<SelectItem> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<SelectItem> searchList) {
        this.searchList = searchList;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public DirectRecruitmentSearch getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(DirectRecruitmentSearch currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public List<DirectRecruitmentSearch> getSearchRecruitment() {
        return searchRecruitment;
    }

    public void setSearchRecruitment(List<DirectRecruitmentSearch> searchRecruitment) {
        this.searchRecruitment = searchRecruitment;
    }

    public Date getAppointmentDt() {
        return appointmentDt;
    }

    public void setAppointmentDt(Date appointmentDt) {
        this.appointmentDt = appointmentDt;
    }

    public Date getArDate() {
        return arDate;
    }

    public void setArDate(Date arDate) {
        this.arDate = arDate;
    }

    public Date getConfirmDt() {
        return confirmDt;
    }

    public void setConfirmDt(Date confirmDt) {
        this.confirmDt = confirmDt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApprovedByMd() {
        return approvedByMd;
    }

    public void setApprovedByMd(String approvedByMd) {
        this.approvedByMd = approvedByMd;
    }

    public String getApprovedByhrd() {
        return approvedByhrd;
    }

    public void setApprovedByhrd(String approvedByhrd) {
        this.approvedByhrd = approvedByhrd;
    }

    public List<SelectItem> getApprovedByhrdList() {
        return approvedByhrdList;
    }

    public void setApprovedByhrdList(List<SelectItem> approvedByhrdList) {
        this.approvedByhrdList = approvedByhrdList;
    }

    public List<SelectItem> getApprovedbyMdList() {
        return approvedbyMdList;
    }

    public void setApprovedbyMdList(List<SelectItem> approvedbyMdList) {
        this.approvedbyMdList = approvedbyMdList;
    }

    public String getArNumber() {
        return arNumber;
    }

    public void setArNumber(String arNumber) {
        this.arNumber = arNumber;
    }

    public String getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(String basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDeparmentHead() {
        return deparmentHead;
    }

    public void setDeparmentHead(String deparmentHead) {
        this.deparmentHead = deparmentHead;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<SelectItem> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<SelectItem> designationList) {
        this.designationList = designationList;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getHra() {
        return hra;
    }

    public void setHra(String hra) {
        this.hra = hra;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public List<SelectItem> getJobStatusList() {
        return jobStatusList;
    }

    public void setJobStatusList(List<SelectItem> jobStatusList) {
        this.jobStatusList = jobStatusList;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
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

    public String getOtherAllowance() {
        return otherAllowance;
    }

    public void setOtherAllowance(String otherAllowance) {
        this.otherAllowance = otherAllowance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public List<SelectItem> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<SelectItem> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getTa() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public List<SelectItem> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<SelectItem> zoneList) {
        this.zoneList = zoneList;
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

    public boolean isDisableallflag() {
        return disableallflag;
    }

    public void setDisableallflag(boolean disableallflag) {
        this.disableallflag = disableallflag;
    }

    public DirectRecruitment() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            setTodayDate(sdf.format(date));
            this.setMessage("");

            operationList = new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0", "-Select-"));
            operationList.add(new SelectItem("1", "Add"));
            operationList.add(new SelectItem("2", "Edit"));

            dropdownList(Integer.parseInt(compCode), "ZON%");
            dropdownList(Integer.parseInt(compCode), "DES%");
            dropdownList(Integer.parseInt(compCode), "QUA%");

            jobStatusList = new ArrayList<SelectItem>();
            jobStatusList.add(new SelectItem("0", "--Select--"));
            jobStatusList.add(new SelectItem("R", "REPLACEMENT"));
            jobStatusList.add(new SelectItem("A", "ADDITIONAL"));
            jobStatusList.add(new SelectItem("P", "PERMANENT"));
            jobStatusList.add(new SelectItem("T", "TEMPORARY"));
            jobStatusList.add(new SelectItem("B", "BUDGETED"));
            jobStatusList.add(new SelectItem("U", "UNBUDGETED"));

            approvedByhrdList = new ArrayList<SelectItem>();
            approvedByhrdList.add(new SelectItem("0", "--Select--"));
            approvedByhrdList.add(new SelectItem("Y", "Yes"));
            approvedByhrdList.add(new SelectItem("N", "No"));

            approvedbyMdList = new ArrayList<SelectItem>();
            approvedbyMdList.add(new SelectItem("0", "--Select--"));
            approvedbyMdList.add(new SelectItem("Y", "Yes"));
            approvedbyMdList.add(new SelectItem("N", "No"));

            searchList = new ArrayList<SelectItem>();
            searchList.add(new SelectItem("0", "By Id"));
            searchList.add(new SelectItem("1", "By Name"));
            mode = "save";
            setDeleteDisable(true);
            setBasicSalary("0");
            setHra("0");
            setTa("0");
            setOtherAllowance("0");
            setTotal("0");
            setArNoDisable(true);
            setSaveDisable(true);
            disableallflag = true;
        } catch (Exception e) {
            logger.error("Exception occured while executing method DirectRecruitment()", e);
            logger.error("DirectRecruitment()", e);
        }
    }

    public void dropdownList(int compCode, String description) {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            List<HrMstStructTO> structMasterTOs = recruitmentDelegate.getPositionList(compCode, description);
            if (description.equalsIgnoreCase("ZON%")) {
                zoneList = new ArrayList<SelectItem>();
                zoneList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    zoneList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
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
            if (description.equalsIgnoreCase("QUA%")) {
                qualificationList = new ArrayList<SelectItem>();
                qualificationList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    qualificationList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method dropDownPositionList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method dropDownPositionList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method dropDownPositionList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    //Set operation for operationList
    public void setOperationOnBlur() {
        setMessage("");
        if (operation.equalsIgnoreCase("0")) {
            setMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {

            mode = "save";
            disableallflag = false;
            setSaveDisable(false);
            setDeleteDisable(true);
        } else if (operation.equalsIgnoreCase("2")) {
            disableallflag = false;
            closePanelView();
            setSaveDisable(false);
            setDeleteDisable(false);
        }
    }

    public void interviewerDetails() {
        setMessage1("");
        if (zone.equalsIgnoreCase("0")) {
            setMessage1("please select Zone");
            searchRecruitment = new ArrayList<DirectRecruitmentSearch>();
            return;
        }
        searchRecruitment = new ArrayList<DirectRecruitmentSearch>();
        try {
            List resultLt = new ArrayList();
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            resultLt = recruitmentDelegate.directRecruitmentZoneList(Integer.parseInt(compCode), zone);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    DirectRecruitmentSearch rd = new DirectRecruitmentSearch();
                    rd.setLocationcode(result[0].toString());
                    rd.setDescription(result[1].toString());
                    searchRecruitment.add(rd);
                }
            } else {
                setMessage1("No Data Found");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method interviewerDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method interviewerDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method interviewerDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow1(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Location"));
        currentRow1 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (DirectRecruitmentSearch item : searchRecruitment) {
            if (item.getLocationcode().equals(accNo)) {
                currentItem1 = item;
                break;
            }
        }
    }

    public void select() {
        setLocation(currentItem1.getDescription());
        setLocationHide(currentItem1.getLocationcode());
    }

    public void closePanel2() {
        setMessage1("");
        setZone("");
    }

    public void employeeSearch() {
        setMessage1("");
        directEmpSearch = new ArrayList<DirectEmployeeSearch>();
        try {
            if (this.searchBy.equalsIgnoreCase("0")) {
                List resultLt = new ArrayList();
                RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
                resultLt = recruitmentDelegate.directRecruitmentInterviewersDetailsById(Integer.parseInt(compCode), nameSearch);
                if (resultLt.size() != 0) {
                    Iterator i1 = resultLt.iterator();
                    while (i1.hasNext()) {
                        Object[] result = (Object[]) i1.next();
                        DirectEmployeeSearch rd = new DirectEmployeeSearch();
                        if (result[0] == null) {
                            rd.setEmpId("");
                        } else {
                            rd.setEmpId(result[0].toString());
                        }
                        if (result[1] == null) {
                            rd.setEmpName("");
                        } else {
                            rd.setEmpName(result[1].toString());
                        }
                        if (result[2] == null) {
                            rd.setEmpAdd("");
                        } else {
                            rd.setEmpAdd(result[2].toString());
                        }
                        if (result[3] == null) {
                            rd.setEmpTel("");
                        } else {
                            rd.setEmpTel(result[3].toString());
                        }
                        directEmpSearch.add(rd);
                    }
                } else {
                    setMessage1("No Data Found");
                }
            } else if (searchBy.equalsIgnoreCase("1")) {
                List resultLt = new ArrayList();
                RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
                resultLt = recruitmentDelegate.directRecruitmentInterviewersDetailsByName(Integer.parseInt(compCode), nameSearch);
                if (resultLt.size() != 0) {
                    Iterator i1 = resultLt.iterator();
                    while (i1.hasNext()) {
                        Object[] result = (Object[]) i1.next();
                        DirectEmployeeSearch rd = new DirectEmployeeSearch();
                        if (result[0] == null) {
                            rd.setEmpId("");
                        } else {
                            rd.setEmpId(result[0].toString());
                        }
                        if (result[1] == null) {
                            rd.setEmpName("");
                        } else {
                            rd.setEmpName(result[1].toString());
                        }
                        if (result[2] == null) {
                            rd.setEmpAdd("");
                        } else {
                            rd.setEmpAdd(result[2].toString());
                        }
                        if (result[3] == null) {
                            rd.setEmpTel("");
                        } else {
                            rd.setEmpTel(result[3].toString());
                        }
                        directEmpSearch.add(rd);
                    }
                } else {
                    setMessage1("No Data Found");
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method employeeSearch()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method employeeSearch()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method employeeSearch()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow2(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Employee Id"));
        currentRow2 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (DirectEmployeeSearch item : directEmpSearch) {
            if (item.getEmpId().equals(accNo)) {
                currentItem2 = item;
                break;
            }
        }
    }

    public void selectEmployeeName() {
        if (flag.equalsIgnoreCase("true")) {
            selectFrst();
        } else if (flag1.equalsIgnoreCase("true")) {
            selectSecond();
        } else if (flag2.equalsIgnoreCase("true")) {
            selectThird();
        }
    }

    public void selectFrst() {
        setInitiatedBy(currentItem2.getEmpName());
        setInititatorIdSave(currentItem2.getEmpId());
    }

    public void selectSecond() {
        setSupervisorName(currentItem2.getEmpName());
        setSuperIdSave(currentItem2.getEmpId());
    }

    public void selectThird() {
        setDeparmentHead(currentItem2.getEmpName());
        setHeadIdSave(currentItem2.getEmpId());
    }

    public void closePanelFirst() {
        flag = "true";
        flag1 = "false";
        flag2 = "false";
        setMessage1("");
        directEmpSearch = new ArrayList<DirectEmployeeSearch>();
    }

    public void closePanelSecond() {
        flag1 = "true";
        flag = "false";
        flag2 = "false";
        setMessage1("");
        directEmpSearch = new ArrayList<DirectEmployeeSearch>();
    }

    public void closePanelThird() {
        flag2 = "true";
        flag = "false";
        flag1 = "false";
        setMessage1("");
        directEmpSearch = new ArrayList<DirectEmployeeSearch>();
    }

    public void totalFunction() {
        try {
            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            int totalBalance = Integer.parseInt(basicSalary) + Integer.parseInt(hra) + Integer.parseInt(ta) + Integer.parseInt(otherAllowance);
            setTotal(Integer.toString(totalBalance));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fetchCurrentRow3(ActionEvent event) {
        setMessage("");
        setMessage1("");
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("First Name"));
        currentRow3 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (DirectRecruitmentViewTable item : directView) {
            if (item.getCandidateName().equals(accNo)) {
                currentItem3 = item;
                break;
            }
        }
    }

    public void closePanelView() {
        setMessage1("");
        setCandName("");
        directView = new ArrayList<DirectRecruitmentViewTable>();
        mode = "update";
    }

    public void candidateSearch() {
        setMessage1("");
        directView = new ArrayList<DirectRecruitmentViewTable>();
        try {
            List resultLt = new ArrayList();
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            resultLt = recruitmentDelegate.directRecruitmentViewDetails(Integer.parseInt(compCode), candName);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    DirectRecruitmentViewTable rd = new DirectRecruitmentViewTable();
                    if (result[0] == null) {
                        rd.setCompanyCode("");
                    } else {
                        rd.setCompanyCode(result[0].toString());
                    }
                    if (result[1] == null) {
                        rd.setArNo("");
                    } else {
                        rd.setArNo(result[1].toString());
                    }
                    if (result[2] == null) {
                        rd.setCandidateName("");
                    } else {
                        rd.setCandidateName(result[2].toString());
                    }
                    if (result[3] == null) {
                        rd.setFatherName("");
                    } else {
                        rd.setFatherName(result[3].toString());
                    }
                    if (result[4] != null) {
                        Date test = (Date) result[4];
                        rd.setArDate(sdf.format(test));
                    }
                    directView.add(rd);
                }
            } else {
                setMessage1("No Data Found");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method candidateSearch()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method candidateSearch()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method candidateSearch()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectSearch() {
        saveDisable = false;
        deleteDisable = false;
        candidateDeatails();
    }

    public void candidateDeatails() {
        setMessage1("");
        try {
            List resultLt = new ArrayList();
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            resultLt = recruitmentDelegate.directRecruitmentUpdateDetails(Integer.parseInt(compCode), currentItem3.getArNo());
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    setArNumber(result[1].toString());
                    Date test = (Date) result[2];
                    String dt = sdf.format(test);
                    setArDate(sdf.parse(dt));
                    setCandidateName(result[3].toString());
                    setFatherName(result[4].toString());
                    setContactNo(result[5].toString());
                    setQualification(result[6].toString());
                    setJobStatus(result[7].toString());
                    setZone(result[8].toString());
                    setDesignation(result[9].toString());
                    setLocationUpdate(result[10].toString());
                    Date test1 = (Date) result[11];
                    String dt1 = sdf.format(test1);
                    setConfirmDt(sdf.parse(dt1));
                    Date test2 = (Date) result[12];
                    String dt2 = sdf.format(test2);
                    setAppointmentDt(sdf.parse(dt2));
                    setJustification(result[13].toString());
                    setApprovedByhrd(result[14].toString());
                    setApprovedByMd(result[15].toString());
                    setBasicSalary(result[16].toString());
                    setHra(result[17].toString());
                    setTa(result[18].toString());
                    setOtherAllowance(result[19].toString());
                    setTotal(result[20].toString());
                    setAddress(result[21].toString());
                    setCity(result[22].toString());
                    setState(result[23].toString());
                    setPin(result[24].toString());
                    setEmailId(result[25].toString());
                    setSuperIdHide(result[26].toString());
                    setSupervisorName(result[27].toString());
                    setInititatorIdHide(result[28].toString());
                    setInitiatedBy(result[29].toString());
                    setHeadIdHide(result[30].toString());
                    setDeparmentHead(result[31].toString());
                    setLocation(result[32].toString());
                }
            } else {
                setMessage1("No Data Found");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method candidateDeatails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method candidateDeatails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method candidateDeatails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void refreshAction() {
        setMessage1("");
        setMessage("");
        setOperation("0");
        directView = new ArrayList<DirectRecruitmentViewTable>();
        directEmpSearch = new ArrayList<DirectEmployeeSearch>();
        searchRecruitment = new ArrayList<DirectRecruitmentSearch>();
        saveDisable = true;
        deleteDisable = true;
        setArNumber("");
        setArDate(null);
        setCandidateName("");
        setFatherName("");
        setContactNo("");
        setQualification("");
        setJobStatus("");
        setZone("");
        setDesignation("");
        setConfirmDt(null);
        setAppointmentDt(null);
        setJustification("");
        setApprovedByhrd("");
        setApprovedByMd("");
        setBasicSalary("");
        setHra("");
        setTa("");
        setOtherAllowance("");
        setTotal("");
        setAddress("");
        setCity("");
        setState("");
        setPin("");
        setEmailId("");
        setSupervisorName("");
        setInitiatedBy("");
        setDeparmentHead("");
        setLocation("");
        disableallflag = true;
    }

    public void clear() {
        setMessage1("");
        disableallflag = true;
        directView = new ArrayList<DirectRecruitmentViewTable>();
        directEmpSearch = new ArrayList<DirectEmployeeSearch>();
        searchRecruitment = new ArrayList<DirectRecruitmentSearch>();
        saveDisable = true;
        deleteDisable = true;
        setArNumber("");
        setArDate(null);
        setCandidateName("");
        setFatherName("");
        setContactNo("");
        setQualification("");
        setJobStatus("");
        setZone("");
        setDesignation("");
        setConfirmDt(null);
        setAppointmentDt(null);
        setJustification("");
        setApprovedByhrd("");
        setApprovedByMd("");
        setBasicSalary("");
        setHra("");
        setTa("");
        setOtherAllowance("");
        setTotal("");
        setAddress("");
        setCity("");
        setState("");
        setPin("");
        setEmailId("");
        setSupervisorName("");
        setInitiatedBy("");
        setDeparmentHead("");
        setLocation("");
    }

    public void directRecruitmrntSaveAction() {

        String msg = "";
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            if (validations().equalsIgnoreCase("true") && validationsOnSave().equalsIgnoreCase("true")) {
                if (mode.equalsIgnoreCase("save")) {
                    String stateFlag = "Y";
                    String stateUpflag = "Y";
                    HrDirectRecTO dr = new HrDirectRecTO();
                    HrDirectRecPKTO drPK = new HrDirectRecPKTO();
                    drPK.setCompCode(Integer.parseInt(compCode));
                    String value;
                    List dropDownList = recruitmentDelegate.directRecruitmentSaveCheck(Integer.parseInt(compCode));
                    if (dropDownList.isEmpty() || dropDownList == null || dropDownList.get(0) == null) {
                        value = "0";
                    } else {
                        value = dropDownList.get(0).toString();
                        value = value.substring(3, 10);
                    }
                    int value1 = Integer.parseInt(value) + 1;
                    value = Integer.toString(value1);
                    int length = value.length();
                    int addedZero = 7 - length;
                    for (int i = 1; i <= addedZero; i++) {
                        value = "0" + value;
                    }
                    value = "DIR" + value;
                    drPK.setArno(value);
                    dr.setHrDirectRecPKTO(drPK);
                    dr.setArdate(arDate);
                    dr.setZoneCode(zone);
                    dr.setDesigCode(designation);
                    dr.setLocationCode(locationHide);
                    List dropDownList1 = recruitmentDelegate.directRecruitmentValidCheck();
                    dr.setCandCode(dropDownList1.size() + 1);
                    dr.setCandName(candidateName);
                    dr.setFatherName(fatherName);
                    dr.setAppointmentDate(appointmentDt);
                    dr.setContactNo(contactNo);
                    dr.setBasicSalary(Integer.parseInt(basicSalary));
                    dr.setHra(Integer.parseInt(hra));
                    dr.setTa(Integer.parseInt(ta));
                    dr.setMedicalAllw(Integer.parseInt(otherAllowance));
                    dr.setTotal(Integer.parseInt(total));
                    dr.setAddress(address);
                    dr.setCity(city);
                    dr.setState(state);
                    dr.setPin(pin);
                    dr.setEmailId(emailId);
                    char status = 'A';
                    if (jobStatus.equalsIgnoreCase("R")) {
                        status = 'R';
                    } else if (jobStatus.equalsIgnoreCase("A")) {
                        status = 'A';
                    } else if (jobStatus.equalsIgnoreCase("P")) {
                        status = 'P';
                    } else if (jobStatus.equalsIgnoreCase("T")) {
                        status = 'T';
                    } else if (jobStatus.equalsIgnoreCase("B")) {
                        status = 'B';
                    } else if (jobStatus.equalsIgnoreCase("U")) {
                        status = 'U';
                    }
                    dr.setJobStatus(status);
                    dr.setRemarks(justification);
                    dr.setEffectiveDate(confirmDt);
                    dr.setQualCode(qualification);
                    String superCode;
                    String initiatorCode;
                    String deptHead;
                    List superList = recruitmentDelegate.directRecruitmentSuperCode(Integer.parseInt(compCode), superIdSave);
                    superCode = superList.get(0).toString();
                    List initiatorList = recruitmentDelegate.directRecruitmentSuperCode(Integer.parseInt(compCode), inititatorIdSave);
                    initiatorCode = initiatorList.get(0).toString();
                    List deptHeadList = recruitmentDelegate.directRecruitmentSuperCode(Integer.parseInt(compCode), headIdSave);
                    deptHead = deptHeadList.get(0).toString();
                    BigInteger superInt = new BigInteger(superCode);
                    dr.setSuperId(superInt);
                    BigInteger initiatedInt = new BigInteger(initiatorCode);
                    dr.setInitiatorId(initiatedInt);
                    BigInteger deptInt = new BigInteger(deptHead);
                    dr.setDeptHeadId(deptInt);
                    char approvHrd = 'N';
                    if (approvedByhrd.equalsIgnoreCase("N")) {
                        approvHrd = 'N';
                    } else if (approvedByhrd.equalsIgnoreCase("Y")) {
                        approvHrd = 'Y';
                    }
                    dr.setHrdApproval(approvHrd);
                    char approv = 'N';
                    if (approvedByMd.equalsIgnoreCase("N")) {
                        approv = 'N';
                    } else if (approvedByMd.equalsIgnoreCase("Y")) {
                        approv = 'Y';
                    }
                    dr.setMdApproval(approv);
                    dr.setStatFlag(stateFlag);
                    dr.setStatUpFlag(stateUpflag);
                    dr.setModDate(getDate());
                    dr.setDefaultComp(defaultComp);
                    dr.setAuthBy(getUserName());
                    dr.setEnteredBy(getUserName());
                    msg = recruitmentDelegate.directRecruitmentSave(dr);
                    setMessage(msg);
                } else if (mode.equalsIgnoreCase("update")) {
                    String stateFlag = "Y";
                    String stateUpflag = "Y";
                    HrDirectRecTO dr = new HrDirectRecTO();
                    HrDirectRecPKTO drPK = new HrDirectRecPKTO();
                    drPK.setCompCode(Integer.parseInt(compCode));
                    drPK.setArno(arNumber);
                    dr.setHrDirectRecPKTO(drPK);
                    dr.setArdate(arDate);
                    dr.setZoneCode(zone);
                    dr.setDesigCode(designation);
                    dr.setLocationCode(locationUpdate);
                    dr.setCandName(candidateName);
                    dr.setFatherName(fatherName);
                    dr.setAppointmentDate(appointmentDt);
                    dr.setContactNo(contactNo);
                    dr.setBasicSalary(Integer.parseInt(basicSalary));
                    dr.setHra(Integer.parseInt(hra));
                    dr.setTa(Integer.parseInt(ta));
                    dr.setMedicalAllw(Integer.parseInt(otherAllowance));
                    dr.setTotal(Integer.parseInt(total));
                    dr.setAddress(address);
                    dr.setCity(city);
                    dr.setState(state);
                    dr.setPin(pin);
                    dr.setEmailId(emailId);
                    char status = 'A';
                    if (jobStatus.equalsIgnoreCase("R")) {
                        status = 'R';
                    } else if (jobStatus.equalsIgnoreCase("A")) {
                        status = 'A';
                    } else if (jobStatus.equalsIgnoreCase("P")) {
                        status = 'P';
                    } else if (jobStatus.equalsIgnoreCase("T")) {
                        status = 'T';
                    } else if (jobStatus.equalsIgnoreCase("B")) {
                        status = 'B';
                    } else if (jobStatus.equalsIgnoreCase("U")) {
                        status = 'U';
                    }
                    dr.setJobStatus(status);
                    dr.setRemarks(justification);
                    dr.setEffectiveDate(confirmDt);
                    dr.setQualCode(qualification);
                    String superCode;
                    String initiatorCode;
                    String deptHead;
                    List superList = recruitmentDelegate.directRecruitmentSuperCode(Integer.parseInt(compCode), superIdHide);
                    superCode = superList.get(0).toString();
                    List initiatorList = recruitmentDelegate.directRecruitmentSuperCode(Integer.parseInt(compCode), inititatorIdHide);
                    initiatorCode = initiatorList.get(0).toString();
                    List deptHeadList = recruitmentDelegate.directRecruitmentSuperCode(Integer.parseInt(compCode), headIdHide);
                    deptHead = deptHeadList.get(0).toString();
                    BigInteger superInt = new BigInteger(superCode);
                    dr.setSuperId(superInt);
                    BigInteger initiatedInt = new BigInteger(initiatorCode);
                    dr.setInitiatorId(initiatedInt);
                    BigInteger deptInt = new BigInteger(deptHead);
                    dr.setDeptHeadId(deptInt);
                    char approvHrd = 'N';
                    if (approvedByhrd.equalsIgnoreCase("N")) {
                        approvHrd = 'N';
                    } else if (approvedByhrd.equalsIgnoreCase("Y")) {
                        approvHrd = 'Y';
                    }
                    dr.setHrdApproval(approvHrd);
                    char approv = 'N';
                    if (approvedByMd.equalsIgnoreCase("N")) {
                        approv = 'N';
                    } else if (approvedByMd.equalsIgnoreCase("Y")) {
                        approv = 'Y';
                    }
                    dr.setMdApproval(approv);
                    dr.setStatFlag(stateFlag);
                    dr.setStatUpFlag(stateUpflag);
                    dr.setModDate(getDate());
                    dr.setDefaultComp(defaultComp);
                    dr.setAuthBy(getUserName());
                    dr.setEnteredBy(getUserName());
                    msg = recruitmentDelegate.directRecruitmentUpdate(dr);
                    setMessage(msg);
                }
                clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void directRecruitmentDeleteAction() {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            String rsmsg = recruitmentDelegate.directRecruitmentDelete(Integer.parseInt(compCode), arNumber);
            if (rsmsg.equalsIgnoreCase("true")) {
                setMessage("Data deleted successfuly");
            } else {
                setMessage(rsmsg);
            }
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method directRecruitmentDeleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method directRecruitmentDeleteAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentDeleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String validations() {
        try {
               if (!contactNo.matches("[0-9]*")) {
                this.setMessage("Please Enter valid contact no.");
                return "false";
            }
            if (!basicSalary.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Basic Salary");
                return "false";
            }
            if (!hra.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in H.R.A");
                return "false";
            }
            if (!ta.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in T.A");
                return "false";
            }
            if (!otherAllowance.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Other Allowance");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }

    public String validationsOnSave() {
        try {

            if (designation.equalsIgnoreCase("0")) {
                this.setMessage("Please selcet designation");
                return "false";
            }
            if (zone.equalsIgnoreCase("0")) {
                this.setMessage("Please select the zone");
                return "false";
            }
            if (location.equalsIgnoreCase("")) {
                this.setMessage("Please fill the location");
                return "false";
            }
            if (location == null) {
                this.setMessage("Please fill the location");
                return "false";
            }
            if (arDate == null) {
                this.setMessage("Please fill the A/R Date");
                return "false";
            }

            if (confirmDt == null) {
                this.setMessage("Please fill the Confirmation Date");
                return "false";
            }
            if (appointmentDt == null) {
                this.setMessage("Please fill the Appointment Date");
                return "false";
            }

            if (initiatedBy == null) {
                this.setMessage("Please fill the Initiated By");
                return "false";
            }
            if (initiatedBy.equalsIgnoreCase("")) {
                this.setMessage("Please fill the Initiated By");
                return "false";
            }

            if (supervisorName == null) {
                this.setMessage("Please fill the Supervisor Name");
                return "false";
            }
            if (initiatedBy.equalsIgnoreCase("")) {
                this.setMessage("Please fill the Supervisor Name");
                return "false";
            }

            if (deparmentHead == null) {
                this.setMessage("Please fill the Deparment Head");
                return "false";
            }
            if (deparmentHead.equalsIgnoreCase("")) {
                this.setMessage("Please fill the Deparment Head");
                return "false";
            }
            if (!valid.validateEmail(emailId)) {
                this.setMessage("Please Enter Valid Email Id");
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
            refreshAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "case1";
    }
}
