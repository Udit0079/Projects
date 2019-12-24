/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.personnel;

import com.hrms.web.pojo.TemporaryStaffTable;
import com.cbs.web.controller.BaseBean;
import java.util.Iterator;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrTempStaffPKTO;
import com.hrms.common.to.HrTempStaffTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.RecruitmentDelegate;
import com.hrms.web.delegate.TransferDetailsDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import java.text.ParseException;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.utils.WebUtil;

/**
 *
 * @author Zeeshan Waris
 */
public class TemporaryStaff extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(TemporaryStaff.class);
    private String arNo;
    private String message;
    private String message1;
    private String designation;
    private String location;
    private String zone;
    private String employeeName;
    private String contractorName;
    private String dailyWages;
    private String status;
    private Date periodFrom;
    private Date periodTo;
    private String compCode="0";
    private String nameSearch;
    private char statusTemp;
    private List<SelectItem> designationList;
    private List<SelectItem> zoneFromlist;
    private List<SelectItem> locationFromList;
    private List<SelectItem> contractorNameList;
    Date date = new Date();
    private List<TemporaryStaffTable> arnoSearch;
    private TemporaryStaffTable currentItem = new TemporaryStaffTable();
    private List<TransferEmpDetails> searchEmp;
    private TransferEmpDetails currentItem2 = new TransferEmpDetails();
    private String searchBy;
    private List<SelectItem> searchList;
    private List<SelectItem> statusList;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private boolean disableUpdate;
    private boolean disableSave;
    private int defaultComp;
    private String operation;
    private boolean flag;
    private List<SelectItem> operationList;
    
    
    
    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
    }

    public List<TemporaryStaffTable> getArnoSearch() {
        return arnoSearch;
    }

    public void setArnoSearch(List<TemporaryStaffTable> arnoSearch) {
        this.arnoSearch = arnoSearch;
    }

    public TemporaryStaffTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TemporaryStaffTable currentItem) {
        this.currentItem = currentItem;
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

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getArNo() {
        return arNo;
    }

    public void setArNo(String arNo) {
        this.arNo = arNo;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public List<SelectItem> getContractorNameList() {
        return contractorNameList;
    }

    public void setContractorNameList(List<SelectItem> contractorNameList) {
        this.contractorNameList = contractorNameList;
    }

    public String getDailyWages() {
        return dailyWages;
    }

    public void setDailyWages(String dailyWages) {
        this.dailyWages = dailyWages;
    }

    public boolean isFlag() {
        return flag; 
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public List<SelectItem> getZoneFromlist() {
        return zoneFromlist;
    }

    public void setZoneFromlist(List<SelectItem> zoneFromlist) {
        this.zoneFromlist = zoneFromlist;
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
    
    public TemporaryStaff() {
        try {
           
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
             operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","-Select-"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","Edit"));
            compCode = getOrgnBrCode();
            setOperation("0");
            this.setMessage("");
            dropdownList(Integer.parseInt(compCode), "DEP%");
            dropdownList(Integer.parseInt(compCode), "ZON%");
            dropdownList(Integer.parseInt(compCode), "LOC%");

            searchList = new ArrayList<SelectItem>();
            searchList.add(new SelectItem("0", "By Id"));
            searchList.add(new SelectItem("1", "By Name"));

            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("0", "--Select--"));
            statusList.add(new SelectItem("Y", "ACTIVE"));
            statusList.add(new SelectItem("N", "INACTIVE"));

            contractorNameList();
            temporaryEditDetails();
            flag=true;
            disableUpdate = true;
            disableSave=true;
        } catch (Exception e) {
            logger.error("Exception occured while executing method TemporaryStaff()", e);
            logger.error("TemporaryStaff()", e);
        }
    }
    
    public void dropdownList(int compCode, String description) {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            List<HrMstStructTO> structMasterTOs = recruitmentDelegate.getPositionList(compCode, description);
            if (description.equalsIgnoreCase("DEP%")) {
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

    public void contractorNameList() {
        try {
            List resultLt = new ArrayList();
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            resultLt = transferDetailsDelegate.temporaryStaffContractorDetails(Integer.parseInt(compCode));
            contractorNameList = new ArrayList<SelectItem>();
            contractorNameList.add(new SelectItem("0", "--Select--"));
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    contractorNameList.add(new SelectItem(result[0].toString(),
                            result[1].toString()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method contractorNameList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method contractorNameList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method contractorNameList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
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

    public void closePanel() {
        setMessage1("");
        searchEmp = new ArrayList<TransferEmpDetails>();
    }

    public void SelectEmployeeName() {
        setMessage("");
        setEmployeeName(currentItem2.getEmpName());
    }

    public void temporaryEditDetails() {
        setMessage1("");
        arnoSearch = new ArrayList<TemporaryStaffTable>();
        try {
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            List<HrTempStaffTO> hrtempStaff = transferDetailsDelegate.getEditList(Integer.parseInt(compCode));
            for (HrTempStaffTO structMasterTO : hrtempStaff) {
                TemporaryStaffTable rd = new TemporaryStaffTable();
                rd.setCompcode(structMasterTO.getHrTempStaffPKTO().getCompCode() + "");
                rd.setArNo(structMasterTO.getHrTempStaffPKTO().getArNo());
                if (structMasterTO.getContCode() == null) {
                    rd.setContCode("");
                } else {
                    rd.setContCode(structMasterTO.getContCode());
                }
                if (structMasterTO.getEmpName() == null) {
                    rd.setEmpName("");
                } else {
                    rd.setEmpName(structMasterTO.getEmpName());
                }
                if (structMasterTO.getDesgCode() == null) {
                    rd.setDesdCode("");
                } else {
                    rd.setDesdCode(structMasterTO.getDesgCode());
                }
                if (structMasterTO.getZone() == null) {
                    rd.setZone("");
                } else {
                    rd.setZone(structMasterTO.getZone());
                }
                if (structMasterTO.getLocatCode() == null) {
                    rd.setLocateCode("");
                } else {
                    rd.setLocateCode(structMasterTO.getLocatCode());
                }
                if (structMasterTO.getFromDate() != null) {
                    rd.setFromDt(structMasterTO.getFromDate());
                }
                if (structMasterTO.getToDate() != null) {
                    rd.setToDt(structMasterTO.getToDate());
                }
                if (structMasterTO.getWages() == null) {
                    rd.setWages("");
                } else {
                    rd.setWages(structMasterTO.getWages() + "");
                }
                if (structMasterTO.getActive() == null) {
                    rd.setActive("0");
                } else {
                    if (structMasterTO.getActive().equals('Y')) {
                        rd.setActive("Y");
                    } else if (structMasterTO.getActive().equals('N')) {
                        rd.setActive("N");
                    }
                }
                arnoSearch.add(rd);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method temporaryEditDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method temporaryEditDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method temporaryEditDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void SelectCurrentroewforEdit() throws ParseException {
        setMessage("");
        disableUpdate = false;
        flag=false; 
        disableSave = true;
        setArNo(currentItem.getArNo());
        setDesignation(currentItem.getDesdCode());
        setLocation(currentItem.getLocateCode());
        setZone(currentItem.getZone());
        setEmployeeName(currentItem.getEmpName());
        setContractorName(currentItem.getContCode());
        setDailyWages(currentItem.getWages());
        setStatus(currentItem.getActive());
        if (currentItem.getFromDt() != null) {
            setPeriodFrom(sdf.parse(currentItem.getFromDt()));
        }
        if (currentItem.getToDt() != null) {
            setPeriodTo(sdf.parse(currentItem.getToDt()));
        }
    }
     public void setOperationOnBlur()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
           setMessage("");
           flag=false;
           disableSave=false;
           disableUpdate=true;
           
           
         } else if (operation.equalsIgnoreCase("2")) {
            setMessage("");
            flag=false;
            disableSave=true;
            disableUpdate=false;
        
        }
    }
     
    public void deleteTemporaryStaffAction() {
        setMessage("");
        try {
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            String rsmsg = transferDetailsDelegate.temporaryStaffDelete(Integer.parseInt(currentItem.getCompcode()), currentItem.getArNo());
            if (rsmsg.equalsIgnoreCase("true")) {
                setMessage("Data deleted successfuly");
            } else {
                setMessage(rsmsg);
            }
            temporaryEditDetails();
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteTemporaryStaffAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteTemporaryStaffAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteTemporaryStaffAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveTemporaryStaffAction() {
        setMessage("");
        try {
            if (arNo.equalsIgnoreCase("")) {
                setMessage("Please fill the A /R No.");
                return;
            }
            if (arNo == null) {
                setMessage("Please fill the A /R No.");
                return;
            }
            if (compCode == null) {
                setMessage("Please fill the company code");
                return;
            }
            if (compCode.equalsIgnoreCase("")) {
                setMessage("Please fill the company code");
                return;
            }
            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            String stateFlag = "N";
            String stateUpflag = "Y";
            HrTempStaffTO struct = new HrTempStaffTO();
            struct.setContCode(contractorName);
            struct.setEmpName(employeeName);
            struct.setDesgCode(designation);
            struct.setZone(zone);
            struct.setLocatCode(location);
            struct.setFromDate(sdf.format(periodFrom));
            struct.setToDate(sdf.format(periodTo));
            struct.setWages(Double.parseDouble(dailyWages));
            if (status.equalsIgnoreCase("Y")) {
                statusTemp = 'Y';
            } else if (status.equalsIgnoreCase("N")) {
                statusTemp = 'N';
            }
            struct.setActive(statusTemp);
            struct.setDefaultComp(defaultComp); 
            struct.setStatFlag(stateFlag);
            struct.setStatUpFlag(stateUpflag);
            struct.setModDate(getDate());
            struct.setAuthBy(getUserName());
            struct.setEnteredBy(getUserName());
            HrTempStaffPKTO structPK = new HrTempStaffPKTO();
            structPK.setCompCode(Integer.parseInt(compCode));
            structPK.setArNo(arNo);
            struct.setHrTempStaffPKTO(structPK);
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            String result = transferDetailsDelegate.temporaryStaffSave(struct);
            setMessage(result);
            temporaryEditDetails();
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveTemporaryStaffAction()", e);
            this.setMessage("You may be trying to save duplicate entry!!");
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveTemporaryStaffAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveTemporaryStaffAction()", e);
            this.setMessage("You may be trying to save duplicate entry!!");
        }
    }

    public void updateTemporaryStaffAction() {
        setMessage("");
        flag=false;
        try {
            if (currentItem.getArNo().equalsIgnoreCase("")) {
                setMessage("Please fill the A /R No.");
                return;
            }
            if (currentItem.getArNo() == null) {
                setMessage("Please fill the A /R No.");
                return;
            }

            if (currentItem.getCompcode() == null) {
                setMessage("Please fill the company code");
                return;
            }
            if (currentItem.getCompcode().equalsIgnoreCase("")) {
                setMessage("Please fill the company code");
                return;
            }
            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            String stateFlag = "N";
            String stateUpflag = "Y";
            HrTempStaffTO struct = new HrTempStaffTO();
            struct.setContCode(contractorName);
            struct.setEmpName(employeeName);
            struct.setDesgCode(designation);
            struct.setZone(zone);
            struct.setLocatCode(location);
            struct.setFromDate(sdf.format(periodFrom));
            struct.setToDate(sdf.format(periodTo));
            struct.setWages(Double.parseDouble(dailyWages));
            if (status.equalsIgnoreCase("Y")) {
                statusTemp = 'Y';
            } else if (status.equalsIgnoreCase("N")) {
                statusTemp = 'N';
            }
            struct.setActive(statusTemp);
            struct.setDefaultComp(defaultComp);
            struct.setStatFlag(stateFlag);
            struct.setStatUpFlag(stateUpflag);
            struct.setModDate(getDate());
            struct.setAuthBy(getUserName());
            struct.setEnteredBy(getUserName());
            HrTempStaffPKTO structPK = new HrTempStaffPKTO();
            structPK.setCompCode(Integer.parseInt(compCode));
            structPK.setArNo(currentItem.getArNo());
            struct.setHrTempStaffPKTO(structPK);
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            String result = transferDetailsDelegate.temporaryStaffUpdate(struct);
            setMessage(result);
            temporaryEditDetails();
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method updateTemporaryStaffAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method updateTemporaryStaffAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method updateTemporaryStaffAction()", e);
            this.setMessage("");
        }
    }

    public void clear() {
        setArNo("");
        setDesignation("0");
        setLocation("0");
        setZone("0");
        setEmployeeName("");
        setDailyWages("");
        setStatus("0");
        setPeriodFrom(null);
        setPeriodTo(null);
        setContractorName("0");
        setOperation("0");
        flag=true;
        
        
    }

    public void refreshButtonAction() {
        flag=true;
        disableUpdate = true; 
        disableSave = true;
        setMessage("");
        setMessage1("");
        setArNo("");
        setDesignation("0");
        setLocation("0");
        setZone("0");
        setEmployeeName("");
        setDailyWages("");
        setStatus("0");
        setPeriodFrom(null);
        setPeriodTo(null);
        setContractorName("0");
        setOperation("0");
    }

    public String validations() {
        try {
//              if (contractorName.equalsIgnoreCase("0")) {
//                setMessage("Please select the Contractor Name");
//                return "false";
//            }
            if (designation.equalsIgnoreCase("0")) {
                setMessage("Please select the Designation");
                return "false";
            }
            if (location.equalsIgnoreCase("0")) {
                setMessage("Please select the Location");
                return "false";
            }
            if (zone.equalsIgnoreCase("0")) {
                setMessage("Please select the Zone");
                return "false";
            }

            if (employeeName.equalsIgnoreCase("")) {
                setMessage("Please fill the Employee Name");
                return "false";
            }
            if (employeeName == null) {
                setMessage("Please fill the Employee Name");
                return "false";
            }
            if (!dailyWages.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in Daily Wages");
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
}
