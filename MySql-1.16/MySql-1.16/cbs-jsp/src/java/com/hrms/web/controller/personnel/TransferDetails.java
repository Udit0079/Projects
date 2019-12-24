/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.personnel;

import com.hrms.web.pojo.TransferSearchTable;
import com.cbs.web.controller.BaseBean;
import java.util.Iterator;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrTransferDetailsPKTO;
import com.hrms.common.to.HrTransferDetailsTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.RecruitmentDelegate;
import com.hrms.web.delegate.TransferDetailsDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import java.text.ParseException;
import javax.faces.context.FacesContext;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.utils.WebUtil;

/**
 *
 * @author Zeeshan Waris
 */
public class TransferDetails extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(TransferDetails.class);
    private String employeeID;
    private String employeeName;
    private String department;
    private String message;
    private String designation;
    private String zoneFrom;
    private String locationFrom;
    private String reportingTo;
    private String blockFrom;
    private String block;
    private String zone;
    private String location;
    private String designationSecond;
    private String departmentSecond;
    private String arNo;
    private Date arDate;
    private Date effectiveFrom;
    private String catagory;
    private String deputationIn;
    private String esi;
    private String salaryProcessing;
    private String transferIn;
    private String pf;
    private String compCode="0";
    private String arNum;
    private String empCode;
    private List<SelectItem> departmentList;
    private List<SelectItem> designationList;
    private List<SelectItem> zoneFromlist;
    private List<SelectItem> locationFromList;
    private List<SelectItem> blockList;
    private List<SelectItem> catagoryList;
    private List<SelectItem> deputationInList;
    private String message1;
    private List<TransferEmpDetails> searchEmp;
    private TransferEmpDetails currentItem2 = new TransferEmpDetails();
    private List<TransferSearchTable> arnoSearch;
    private TransferSearchTable currentItem = new TransferSearchTable();
    private String searchBy;
    private List<SelectItem> searchList;
    private String nameSearch;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String interviwerName;
    private String mode;
    private boolean saveDisable;
    private boolean flag;
    private boolean disableUpdate;
    private boolean disableArNo;
    private boolean addDisable;
    private String empCodeUpdate;
    private int defaultComp;
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

    
    public boolean isAddDisable() {
        return addDisable;
    }

    public void setAddDisable(boolean addDisable) {
        this.addDisable = addDisable;
    }

    public boolean isDisableArNo() {
        return disableArNo;
    }

    public void setDisableArNo(boolean disableArNo) {
        this.disableArNo = disableArNo;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public TransferSearchTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TransferSearchTable currentItem) {
        this.currentItem = currentItem;
    }

    public List<TransferSearchTable> getArnoSearch() {
        return arnoSearch;
    }

    public void setArnoSearch(List<TransferSearchTable> arnoSearch) {
        this.arnoSearch = arnoSearch;
    }

    public String getArNum() {
        return arNum;
    }

    public void setArNum(String arNum) {
        this.arNum = arNum;
    }

    public TransferEmpDetails getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(TransferEmpDetails currentItem2) {
        this.currentItem2 = currentItem2;
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

    public List<TransferEmpDetails> getSearchEmp() {
        return searchEmp;
    }

    public void setSearchEmp(List<TransferEmpDetails> searchEmp) {
        this.searchEmp = searchEmp;
    }

    public List<SelectItem> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<SelectItem> searchList) {
        this.searchList = searchList;
    }

    public List<SelectItem> getDeputationInList() {
        return deputationInList;
    }

    public void setDeputationInList(List<SelectItem> deputationInList) {
        this.deputationInList = deputationInList;
    }

    public Date getArDate() {
        return arDate;
    }

    public void setArDate(Date arDate) {
        this.arDate = arDate;
    }

    public String getArNo() {
        return arNo;
    }

    public void setArNo(String arNo) {
        this.arNo = arNo;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBlockFrom() {
        return blockFrom;
    }

    public void setBlockFrom(String blockFrom) {
        this.blockFrom = blockFrom;
    }

    public List<SelectItem> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<SelectItem> blockList) {
        this.blockList = blockList;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public List<SelectItem> getCatagoryList() {
        return catagoryList;
    }

    public void setCatagoryList(List<SelectItem> catagoryList) {
        this.catagoryList = catagoryList;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<SelectItem> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<SelectItem> departmentList) {
        this.departmentList = departmentList;
    }

    public String getDepartmentSecond() {
        return departmentSecond;
    }

    public void setDepartmentSecond(String departmentSecond) {
        this.departmentSecond = departmentSecond;
    }

    public String getDeputationIn() {
        return deputationIn;
    }

    public void setDeputationIn(String deputationIn) {
        this.deputationIn = deputationIn;
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

    public String getDesignationSecond() {
        return designationSecond;
    }

    public void setDesignationSecond(String designationSecond) {
        this.designationSecond = designationSecond;
    }

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEsi() {
        return esi;
    }

    public void setEsi(String esi) {
        this.esi = esi;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getInterviwerName() {
        return interviwerName;
    }

    public void setInterviwerName(String interviwerName) {
        this.interviwerName = interviwerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public List<SelectItem> getLocationFromList() {
        return locationFromList;
    }

    public void setLocationFromList(List<SelectItem> locationFromList) {
        this.locationFromList = locationFromList;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getReportingTo() {
        return reportingTo;
    }

    public void setReportingTo(String reportingTo) {
        this.reportingTo = reportingTo;
    }

    public String getSalaryProcessing() {
        return salaryProcessing;
    }

    public void setSalaryProcessing(String salaryProcessing) {
        this.salaryProcessing = salaryProcessing;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public String getTransferIn() {
        return transferIn;
    }

    public void setTransferIn(String transferIn) {
        this.transferIn = transferIn;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getZoneFrom() {
        return zoneFrom;
    }

    public void setZoneFrom(String zoneFrom) {
        this.zoneFrom = zoneFrom;
    }

    public List<SelectItem> getZoneFromlist() {
        return zoneFromlist;
    }

    public void setZoneFromlist(List<SelectItem> zoneFromlist) {
        this.zoneFromlist = zoneFromlist;
    }

    public TransferDetails() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","---Select---"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","Edit"));
            this.setMessage("");
            dropdownList(Integer.parseInt(compCode), "DEP%");
            dropdownList(Integer.parseInt(compCode), "DES%");
            dropdownList(Integer.parseInt(compCode), "ZON%");
            dropdownList(Integer.parseInt(compCode), "BLO%");
            dropdownList(Integer.parseInt(compCode), "LOC%");

            catagoryList = new ArrayList<SelectItem>();
            catagoryList.add(new SelectItem("0", "--Select--"));
            catagoryList.add(new SelectItem("T", "TRANSFER"));
            catagoryList.add(new SelectItem("D", "DEPUTATION"));

            deputationInList = new ArrayList<SelectItem>();
            deputationInList.add(new SelectItem("0", "--Select--"));
            deputationInList.add(new SelectItem("Y", "Yes"));
            deputationInList.add(new SelectItem("N", "No"));

            searchList = new ArrayList<SelectItem>();
            searchList.add(new SelectItem("0", "By Id"));
            searchList.add(new SelectItem("1", "By Name"));
            flag = true;
            mode = "Save";
            saveDisable = true;
        } catch (Exception e) {
            logger.error("Exception occured while executing method TransferDetails()", e);
            logger.error("LeaveMaster()", e);
        }
    }

    public void dropdownList(int compCode, String description) {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            List<HrMstStructTO> structMasterTOs = recruitmentDelegate.getPositionList(compCode, description);

            if (description.equalsIgnoreCase("DEP%")) {
                departmentList = new ArrayList<SelectItem>();
                departmentList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    departmentList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
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
            if (description.equalsIgnoreCase("ZON%")) {
                zoneFromlist = new ArrayList<SelectItem>();
                zoneFromlist.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    zoneFromlist.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }

            if (description.equalsIgnoreCase("BLO%")) {
                blockList = new ArrayList<SelectItem>();
                blockList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    blockList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }

            if (description.equalsIgnoreCase("LOC%")) {
                locationFromList = new ArrayList<SelectItem>();
                locationFromList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    locationFromList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
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

    public void fetchCurrentRow2(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Employee Id"));
        for (TransferEmpDetails item : searchEmp) {
            if (item.getEmpId().equals(accNo)) {
                currentItem2 = item;
                break;
            }
        }
    }

    public void employeeSearch() {
        setMessage1("");
        searchEmp = new ArrayList<TransferEmpDetails>();
        try {
            if (this.searchBy.equalsIgnoreCase("0")) {
                List resultLt = new ArrayList();
                RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
                resultLt = recruitmentDelegate.directRecruitmentInterviewersDetailsById(Integer.parseInt(compCode), nameSearch);
                if (resultLt.size() != 0) {
                    Iterator i1 = resultLt.iterator();
                    while (i1.hasNext()) {
                        Object[] result = (Object[]) i1.next();
                        TransferEmpDetails rd = new TransferEmpDetails();
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
                        searchEmp.add(rd);
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
                        TransferEmpDetails rd = new TransferEmpDetails();
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
                        searchEmp.add(rd);
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

    public void candidateDeatails() {
        setMessage1("");
        try {
            List resultLt = new ArrayList();
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            resultLt = transferDetailsDelegate.transferEmpDetails(currentItem2.getEmpId(), Integer.parseInt(compCode));
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    if (result[0] != null) {
                        setEmpCode(result[0].toString());
                    }
                    if (result[1] != null) {
                        setEmployeeID(result[1].toString());
                    }
                    if (result[2] != null) {
                        setEmployeeName(result[2].toString());
                    }
                    if (result[3] != null) {
                        setBlockFrom(result[3].toString());
                    }
                    if (result[7] != null) {
                        setDepartment(result[7].toString());
                    }
                    if (result[9] != null) {
                        setDesignation(result[9].toString());
                    }
                    if (result[13] != null) {
                        setZoneFrom(result[13].toString());
                    }
                    if (result[15] != null) {
                        setLocationFrom(result[15].toString());
                    }
                    if (result[20] != null) {
                        setReportingTo(result[20].toString());
                    }

                }
            } else {
                setMessage1("No Data Found");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method candidateDeatails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method candidateDeatails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectEmpId() {
        mode = "Save";
        flag = true;
        saveDisable = false;
        candidateDeatails();
    }

    public void transferSearchbyArNo() {
        setMessage1("");
        arnoSearch = new ArrayList<TransferSearchTable>();
        try {
            List resultLt = new ArrayList();
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            resultLt = transferDetailsDelegate.transferArNum(Integer.parseInt(compCode), arNum + "%");
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    TransferSearchTable rd = new TransferSearchTable();
                    if (result[0] == null) {
                        rd.setCompcode("");
                    } else {
                        rd.setCompcode(result[0].toString());
                    }
                    if (result[1] == null) {
                        rd.setArNumber("");
                    } else {
                        rd.setArNumber(result[1].toString());
                    }
                    if (result[2] != null) {
                        rd.setArDate(result[2].toString());
                    }
                    if (result[3] != null) {
                        rd.setEffective(result[3].toString());
                    }
                    arnoSearch.add(rd);
                }
            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method transferSearchbyArNo()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method transferSearchbyArNo()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method transferSearchbyArNo()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectCurrentRowForupdateDelete() throws ParseException {
        setMessage1("");
        mode = "Update";
        saveDisable = false;
        disableArNo = true;
        disableUpdate = true;
        addDisable = true;
        if (currentItem.getArNumber() != null) {
            setArNo(currentItem.getArNumber());
        }
        if (currentItem.getArDate() != null) {
            setArDate(sdf.parse(currentItem.getArDate()));
        }
        if (currentItem.getEffective() != null) {
            setEffectiveFrom(sdf.parse(currentItem.getEffective()));
        }
        try {
            List resultLt = new ArrayList();
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            resultLt = transferDetailsDelegate.transferEditDetails(Integer.parseInt(currentItem.getCompcode()), currentItem.getArNumber());
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    if (result[0] != null) {
                        setEmployeeID(result[0].toString());
                    }
                    if (result[1] != null) {
                        setEmployeeName(result[1].toString());
                    }
                    if (result[2] != null) {
                        empCodeUpdate = result[2].toString();
                    }
                    if (result[3] != null) {
                        setDesignation(result[3].toString());
                    }
                    if (result[4] != null) {
                        setDesignationSecond(result[4].toString());
                    }
                    if (result[5] != null) {
                        setZoneFrom(result[5].toString());
                    }
                    if (result[6] != null) {
                        setZone(result[6].toString());
                    }
                    if (result[7] != null) {
                        setBlockFrom(result[7].toString());
                    }
                    if (result[8] != null) {
                        setBlock(result[8].toString());
                    }
                    if (result[9] != null) {
                        setDepartment(result[9].toString());
                    }
                    if (result[10] != null) {
                        setDepartmentSecond(result[10].toString());
                    }
                    if (result[11] != null) {
                        setLocationFrom(result[11].toString());
                    }
                    if (result[12] != null) {
                        setLocation(result[12].toString());
                    }

                    if (result[14] != null) {
                        setTransferIn(result[14].toString());
                    }
                    if (result[15] != null) {
                        setPf(result[15].toString());
                    }
                    if (result[16] != null) {
                        setEsi(result[16].toString());
                    }
                    if (result[17] != null) {
                        setSalaryProcessing(result[17].toString());
                    }
                    if (result[18] != null) {
                        setCatagory(result[18].toString());
                    }
                    if (result[19] != null) {
                        setDeputationIn(result[19].toString());
                    }
                }
            } else {
                setMessage1("No Data Found");
            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method selectCurrentRowForupdateDelete()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method selectCurrentRowForupdateDelete()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectCurrentRowForupdateDelete()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }

    public void fetchCurrentRow(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("A/R Code"));
        for (TransferSearchTable item : arnoSearch) {
            if (item.getArNumber().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void closePanel() {
        setMessage1("");
        setArNum("");
        arnoSearch = new ArrayList<TransferSearchTable>();
    }

    public void saveUpdateTransferDetails() {
        try {
            setMessage("");
            if (compCode.equalsIgnoreCase("")) {
                setMessage("please fill the Comany code");
                return;
            }
            if (compCode == null) {
                setMessage("please fill the Comany code");
                return;
            }

            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            if (mode.equalsIgnoreCase("Save")) {
                if (arNo == null) {
                    setMessage("Please fill the A / R No");
                    return;
                }
                if (arNo.equalsIgnoreCase("")) {
                    setMessage("Please fill the A / R No");
                    return;
                }
                String stateFlag = "Y";
                String stateUpflag = "Y";
                HrTransferDetailsTO hrTransfer = new HrTransferDetailsTO();
                HrTransferDetailsPKTO hrTransferPK = new HrTransferDetailsPKTO();
                hrTransferPK.setCompCode(Integer.parseInt(compCode));
                hrTransferPK.setArNo(arNo);
                hrTransfer.setHrTransferDetailsPKTO(hrTransferPK);
                hrTransfer.setArdate(sdf.format(arDate));
                hrTransfer.setEmpCode(Long.parseLong(empCode));
                hrTransfer.setPresentDesig(designation);
                hrTransfer.setProposedDesig(designationSecond);
                hrTransfer.setZoneFrom(zoneFrom);
                hrTransfer.setZoneTo(zone);
                hrTransfer.setBlockFrom(blockFrom);
                hrTransfer.setBlockTo(block);
                hrTransfer.setDeptFrom(department);
                hrTransfer.setDeptTo(departmentSecond);
                hrTransfer.setLocationFrom(locationFrom);
                hrTransfer.setLocationTo(location);
                hrTransfer.setEffective(sdf.format(effectiveFrom));
                hrTransfer.setTransfer(transferIn);
                hrTransfer.setPf(pf);
                hrTransfer.setEsi(esi);
                hrTransfer.setSalProcess(salaryProcessing);
                hrTransfer.setReason(catagory);
                hrTransfer.setDeput(deputationIn);
                hrTransfer.setDefaultComp(defaultComp);
                hrTransfer.setStatFlag(stateFlag);
                hrTransfer.setStatUpFlag(stateUpflag);
                hrTransfer.setModDate(getDate());
                hrTransfer.setAuthBy(getUserName());
                hrTransfer.setEnteredBy(getUserName());
                TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
                String result = transferDetailsDelegate.transferDetailsSave(hrTransfer);
                setMessage(result);
            } /******************* update *******************/
            else if (mode.equalsIgnoreCase("Update")) {
                if (currentItem.getArNumber() == null) {
                    setMessage("Please fill the A / R No");
                    return;
                }
                if (currentItem.getArNumber().equalsIgnoreCase("")) {
                    setMessage("Please fill the A / R No");
                    return;
                }
                if (validations().equalsIgnoreCase("false")) {
                    return;
                }
                String stateFlag = "Y";
                String stateUpflag = "Y";
                HrTransferDetailsTO hrTransfer = new HrTransferDetailsTO();
                HrTransferDetailsPKTO hrTransferPK = new HrTransferDetailsPKTO();
                hrTransferPK.setCompCode(Integer.parseInt(currentItem.getCompcode()));
                hrTransferPK.setArNo(currentItem.getArNumber());
                hrTransfer.setHrTransferDetailsPKTO(hrTransferPK);
                hrTransfer.setArdate(sdf.format(arDate));
                hrTransfer.setEmpCode(Long.parseLong(empCodeUpdate));
                hrTransfer.setPresentDesig(designation);
                hrTransfer.setProposedDesig(designationSecond);
                hrTransfer.setZoneFrom(zoneFrom);
                hrTransfer.setZoneTo(zone);
                hrTransfer.setBlockFrom(blockFrom);
                hrTransfer.setBlockTo(block);
                hrTransfer.setDeptFrom(department);
                hrTransfer.setDeptTo(departmentSecond);
                hrTransfer.setLocationFrom(locationFrom);
                hrTransfer.setLocationTo(location);
                hrTransfer.setEffective(sdf.format(effectiveFrom));
                hrTransfer.setTransfer(transferIn);
                hrTransfer.setPf(pf);
                hrTransfer.setEsi(esi);
                hrTransfer.setSalProcess(salaryProcessing);
                hrTransfer.setReason(catagory);
                hrTransfer.setDeput(deputationIn);
                hrTransfer.setDefaultComp(defaultComp);
                hrTransfer.setStatFlag(stateFlag);
                hrTransfer.setStatUpFlag(stateUpflag);
                hrTransfer.setModDate(getDate());
                hrTransfer.setAuthBy(getUserName());
                hrTransfer.setEnteredBy(getUserName());
                TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
                String result = transferDetailsDelegate.transferDetailsUpdate(hrTransfer);
                setMessage(result);

            }
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveUpdateTransferDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveUpdateTransferDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveUpdateTransferDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteTransferAction() {
        try {
            setMessage("");
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            String rsmsg = transferDetailsDelegate.transferDetailsDelete(Integer.parseInt(currentItem.getCompcode()), currentItem.getArNumber());
            if (rsmsg.equalsIgnoreCase("true")) {
                setMessage("Data deleted successfuly");
            } else {
                setMessage(rsmsg);
            }
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteTransferAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteTransferAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteTransferAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void clear() {
        saveDisable = true;
        setMessage1("");
        setArNo("");
        setEmpCode("");
        setReportingTo("");
        setArDate(null);
        setEffectiveFrom(null);
        setEmployeeID("");
        setEmployeeName("");
        setDesignation("0");
        setDesignationSecond("0");
        setZoneFrom("0");
        setZone("0");
        setBlockFrom("0");
        setBlock("0");
        setDepartment("0");
        setDepartmentSecond("0");
        setLocationFrom("0");
        setLocation("0");
        setTransferIn("0");
        setPf("0");
        setEsi("0");
        setSalaryProcessing("0");
        setCatagory("0");
        setDeputationIn("0");
        setOperation("0");

    }

    public void refreshAction() {
        saveDisable = true;
        disableArNo = false;
        disableUpdate = false;
        addDisable = false;
        setMessage1("");
        setMessage("");
        setArNo("");
        setEmpCode("");
        setReportingTo("");
        setArDate(null);
        setEffectiveFrom(null);
        setEmployeeID("");
        setEmployeeName("");
        setDesignation("0");
        setDesignationSecond("0");
        setZoneFrom("0");
        setZone("0");
        setBlockFrom("0");
        setBlock("0");
        setDepartment("0");
        setDepartmentSecond("0");
        setLocationFrom("0");
        setLocation("0");
        setTransferIn("0");
        setPf("0");
        setEsi("0");
        setSalaryProcessing("0");
        setCatagory("0");
        setDeputationIn("0");
        setOperation("0");

    }

    public String validations() {
        try {
            if (catagory.equalsIgnoreCase("0")) {
                setMessage("Please select category");
                return "false";
            }
            if (salaryProcessing.equalsIgnoreCase("0")) {
                setMessage("Please select Salary Processing");
                return "false";
            }
            if (transferIn.equalsIgnoreCase("0")) {
                setMessage("Please select Transfer In");
                return "false";
            }
            if (esi.equalsIgnoreCase("0")) {
                setMessage("Please select ESI");
                return "false";
            }
            if (pf.equalsIgnoreCase("0")) {
                setMessage("Please select PF");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }
     public void setOperationOnBlur()
    {
        setMessage("");
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        }
        if(operation.equalsIgnoreCase("2"))
        {
            closePanel();
        }
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
