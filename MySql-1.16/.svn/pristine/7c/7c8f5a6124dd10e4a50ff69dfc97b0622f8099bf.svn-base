package com.hrms.web.controller.payroll;

import com.hrms.web.pojo.EmployeeShiftTable;
import com.hrms.web.pojo.EmployeeDetailGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrMstShiftPKTO;
import com.hrms.common.to.HrMstShiftTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrShiftMapPKTO;
import com.hrms.common.to.HrShiftMapTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class ShiftMapping extends BaseBean {

    private static final Logger logger = Logger.getLogger(ShiftMapping.class);
    private String finYearFrom,
            finYearTo,
            statFlag, statUpFlag,
            old_shiftValue,
            structCode,
            mode = "",
            enteredBy,
            authBy,
            catDetValue,
            catValue,
            shiftValue,
            empSearchCriteria,
            empName,
            empId,
            employeeWise = "false",
            empSearchValue,
            message;
    private int defaultComp,
            totalEmpRecords,
            totalShiftTableRecords,
            currentRow,
            compCode;
    private List<SelectItem> catList, catDetList, shiftList;
    private List<EmployeeDetailGrid> empSearchTable;
    private EmployeeShiftTable currentShiftItem = new EmployeeShiftTable(),
            currentEmpShiftRow;
    private List<EmployeeShiftTable> empShiftTable;
    private EmployeeDetailGrid currentItem = new EmployeeDetailGrid(),
            currentEmpRow;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy"),
            mdyFormat = new SimpleDateFormat("MM'/'dd'/'yy");
    PayrollMasterManagementDelegate payrollRollDelegate;
    private long empCode;
    private boolean disableCategorizeDetails,
            showShiftTable,
            disableSaveButton,
            disableEditButton,
            disableDeleteButton;
    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
     private String operation;
    private List<SelectItem> operationList;

    public void onLoadMode() {
        setMessage("");
        setMode("save");
        setDisableSaveButton(true);
        setDisableEditButton(false);
        setDisableDeleteButton(true);
    }

    public ShiftMapping() {
        try {
            payrollRollDelegate = new PayrollMasterManagementDelegate();
            compCode = Integer.parseInt(getOrgnBrCode());
            companyMasterTOs = webUtil.getCompanyMasterTO(compCode);
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","---Select---"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","Edit"));
            authBy = getUserName();
            enteredBy = getUserName();
            statFlag = "Y";
            statUpFlag = "Y";
            catDetList = new ArrayList<SelectItem>();
            catDetList.add(new SelectItem("0", "--Select--"));
            disableCategorizeDetails = true;
            getCategorizationList();
            getShiftListDetail();
            finYearFrom = (WebUtil.getFinancialYear(compCode)).get(0);
            finYearTo = (WebUtil.getFinancialYear(compCode)).get(1);
            onLoadMode();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method AdvanceActivity()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method AdvanceActivity()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveAction() {
        try {
            if(operation.equalsIgnoreCase("0"))
            {
                setMessage("Please select an operation");
                return;
            }
            if (validate()) {
                int j = 0;
                if (mode.equalsIgnoreCase("save")) {
                    boolean flag = false;
                    List shiftTableDataList = payrollRollDelegate.getShiftTableData(compCode);
                    if (this.getCatValue().equalsIgnoreCase("EWE")) {
                        List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = payrollRollDelegate.getHrPersonnelData(compCode, "ID", empId);
                        empCode = hrPersonnelDetailsTOs.get(0).getHrPersonnelDetailsPKTO().getEmpCode();
                        for (int i = 0; i < shiftTableDataList.size(); i++) {
                            Object[] ob = (Object[]) shiftTableDataList.get(i);
                            long empCode_1 = Long.parseLong(ob[0].toString());
                            String shiftCode = ob[3].toString();
                            if (empCode == empCode_1 && shiftValue.equalsIgnoreCase(shiftCode)) {
                                flag = true;
                                break;
                            } else {
                                flag = false;
                            }
                        }
                        if (flag == false) {
                            String result = performAction(shiftValue);
                            cancelAction();
                            this.setMessage(result);
                        } else {
                            cancelAction();
                            this.setMessage("Already exists !!");
                        }
                    } else {
                        List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = payrollRollDelegate.getCategorizationBasedEmployees(compCode, this.getCatDetValue());
                        for (HrPersonnelDetailsTO hrPersonnelDetailsTO : hrPersonnelDetailsTOs) {
                            for (int i = 0; i < shiftTableDataList.size(); i++) {
                                Object[] ob = (Object[]) shiftTableDataList.get(i);
                                long empCode_1 = Long.parseLong(ob[0].toString());
                                String shiftCode = ob[3].toString();
                                if (hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getEmpCode() == empCode_1 && shiftValue.equalsIgnoreCase(shiftCode)) {
                                    flag = true;
                                    break;
                                } else if (hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getEmpCode() == empCode_1 && !shiftValue.equalsIgnoreCase(shiftCode)) {
                                    flag = false;
                                }
                            }
                            if (flag == false) {
                                empCode = hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getEmpCode();
                                authBy = getUserName();
                                enteredBy = getUserName();
                                ++j;
                                performAction(shiftValue);
                            }
                        }
                        cancelAction();
                        if(j==0)
                        {
                            setMessage("No record is saved !");
                        }
                        else
                        {
                            this.setMessage(j + " rows saved successfully");
                        }
                    }
                } else {
                    editAction();
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String performAction(String shiftValue) {
        String result = "There is An Error !!";
        try {

            if (shiftValue.equalsIgnoreCase("0")) {
                return "Please select shift !";
            }
            result = payrollRollDelegate.saveShiftMapDetail(getHrShiftMapTO(compCode, empCode, shiftValue), mode);
            this.setMode("save");
            //       cancelAction();
            onLoadMode();
            return result;

        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return result;
    }

    public void viewAction() {
        try {
            int i = 0;
            empShiftTable = new ArrayList<EmployeeShiftTable>();
            List shiftTableDataList = payrollRollDelegate.getShiftTableData(compCode);
            if (!shiftTableDataList.isEmpty()) {
                for (i = 0; i < shiftTableDataList.size(); i++) {
                    currentEmpShiftRow = new EmployeeShiftTable();
                    Object[] ob = (Object[]) shiftTableDataList.get(i);
                    currentEmpShiftRow.setSno(i + 1);
                    if (ob[0] != null) {
                        currentEmpShiftRow.setEmpCode(Long.parseLong(ob[0].toString()));
                    }
                    if (ob[1] != null) {
                        currentEmpShiftRow.setEmpId(ob[1].toString());
                    }
                    if (ob[2] != null) {
                        currentEmpShiftRow.setEmpName(ob[2].toString());
                    }
                    if (ob[3] != null) {
                        currentEmpShiftRow.setShiftCode(ob[3].toString());
                    }
                    if (ob[4] != null) {
                        currentEmpShiftRow.setShiftDescription(ob[4].toString());
                    }
                    empShiftTable.add(currentEmpShiftRow);
                }
                totalShiftTableRecords = i;
                showShiftTable = true;
            } else {
                showShiftTable = false;
                setMessage("No Record(s) Found !!");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void editAction() {
        try {
            if (validate()) {
                old_shiftValue = currentShiftItem.getShiftCode();
                payrollRollDelegate.saveShiftMapDetail(getHrShiftMapTO(compCode, empCode, old_shiftValue), "delete");
                setMode("save");
                saveAction();
                setMessage("Shift Has Been Updated Successfully !!");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteAction() {
        try {
            authBy = getUserName();
            enteredBy = getUserName();
            if (validate()) {
                String result = payrollRollDelegate.saveShiftMapDetail(getHrShiftMapTO(compCode, empCode, shiftValue), "delete");
                cancelAction();
                onLoadMode();
                cancelAction();
                setMessage(result);
                showShiftTable = false;
            } else {
                viewAction();
                setMessage("Please Select A Row");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    HrShiftMapTO getHrShiftMapTO(int compCode, long empCode, String shiftCode) {
        HrShiftMapTO hrShiftMapTO = new HrShiftMapTO();
        HrShiftMapPKTO hrShiftMapPKTO = new HrShiftMapPKTO();
        try {
            hrShiftMapPKTO.setCompCode(compCode);
            hrShiftMapPKTO.setEmpCode(empCode);
            hrShiftMapPKTO.setShiftCode(shiftCode);
            hrShiftMapTO.setAuthBy(authBy);
            hrShiftMapTO.setDefaultComp(defaultComp);
            hrShiftMapTO.setEnteredBy(enteredBy);
            hrShiftMapTO.setStatFlag(statFlag);
            hrShiftMapTO.setStatUpFlag(statUpFlag);
            hrShiftMapTO.setModDate(Date.class.newInstance());
            hrShiftMapTO.setHrShiftMapPKTO(hrShiftMapPKTO);
            HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
            HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
            hrPersonnelDetailsPKTO.setCompCode(compCode);
            hrPersonnelDetailsPKTO.setEmpCode(empCode);
            hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);
            hrShiftMapTO.setHrPersonnelDetailsTO(hrPersonnelDetailsTO);
            HrMstShiftTO hrMstShiftTO = new HrMstShiftTO();
            HrMstShiftPKTO hrMstShiftPKTO = new HrMstShiftPKTO();
            hrMstShiftPKTO.setCompCode(compCode);
            hrMstShiftPKTO.setShiftCode(shiftCode);
            hrMstShiftTO.setHrMstShiftPKTO(hrMstShiftPKTO);
            hrShiftMapTO.setHrMstShiftTO(hrMstShiftTO);
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrShiftMapTO;
    }

    public void cancelAction() {
        onLoadMode();
        setMode("save");
        setCatDetValue("--Select--");
        setMessage("");
        catDetList = new ArrayList<SelectItem>();
        catDetList.add(new SelectItem("0", "--Select--"));
        setShiftValue("--Select--");
        setCatValue("--Select--");
        structCode = "";
        employeeWise = "false";
        setOperation("0");
    }

    public String exitAction() {
        try {
            cancelAction();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    boolean validate() {
        if (catValue == null || catValue.equalsIgnoreCase("0")) {
            setMessage("Please Select Categorization !!");
            return false;
        }
        if (catDetValue == null || catDetValue.equalsIgnoreCase("0")) {
            if (catValue.equalsIgnoreCase("EWE")) {
                setMessage("Please Select Employee!!");
                return false;
            } else {
                setMessage("Please Select Categorization Details!!");
                return false;
            }
        }
        if (shiftValue == null || shiftValue.equalsIgnoreCase("0")) {
            setMessage("Please Select Shift Code !!");
            return false;
        }
        return true;
    }

    public void onChangeShift() {
        if (shiftValue != null && !shiftValue.equals("0")) {
            setDisableSaveButton(false);
        } else {
            setDisableSaveButton(true);
        }
    }

    public void getShiftListDetail() {
        try {
            shiftList = new ArrayList<SelectItem>();
            shiftList.add(new SelectItem("0", "--Select--"));
            List<HrMstShiftTO> hrMstShiftTOs = payrollRollDelegate.getShiftList(compCode);
            if (!hrMstShiftTOs.isEmpty()) {
                for (HrMstShiftTO hrMstShiftTO : hrMstShiftTOs) {
                    shiftList.add(new SelectItem(hrMstShiftTO.getHrMstShiftPKTO().getShiftCode(), hrMstShiftTO.getShiftDesc()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getCategorizationList() {
        try {
            catList = new ArrayList<SelectItem>();
            catList.add(new SelectItem("0", "--Select--"));
            catList.add(new SelectItem("EWE", "EMPLOYEE WISE"));
            List<HrMstStructTO> HrMstStructTOs = payrollRollDelegate.getInitialData(compCode, "CHO%");
            if (!HrMstStructTOs.isEmpty()) {
                for (HrMstStructTO hrMstStructTO : HrMstStructTOs) {
                    catList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getCategorizationDetailList() {
        try {
            if (catValue.equalsIgnoreCase("EWE")) {
                catDetList = new ArrayList<SelectItem>();
                catDetList.add(new SelectItem("0", "--Select--"));
                employeeWise = "true";
                disableCategorizeDetails = true;
                this.setMessage("");
            } else if (catValue.equalsIgnoreCase("--Select--")) {
                employeeWise = "false";
                disableCategorizeDetails = false;
                this.setMessage("Please Select Categorization !!");
            } else {
                this.setMessage("");
                employeeWise = "false";
                disableCategorizeDetails = false;
                for (Iterator<SelectItem> it = catList.iterator(); it.hasNext();) {
                    SelectItem selectItem = it.next();
                    if (selectItem.getValue().toString().equalsIgnoreCase(catValue)) {
                        structCode = selectItem.getLabel().substring(0, 3);
                    }
                }
                onChangeCategorization();
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void onChangeCategorization() {
        try {
            catDetList = new ArrayList<SelectItem>();
            List<HrMstStructTO> HrMstStructTOs = payrollRollDelegate.getInitialData(compCode, structCode + "%");
            for (HrMstStructTO hrMstStructTO : HrMstStructTOs) {
                catDetList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getEmployeeTableData() {
        try {
            empSearchTable = new ArrayList<EmployeeDetailGrid>();
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = payrollRollDelegate.getHrPersonnelData(compCode, empSearchCriteria, empSearchValue);
            int i = 0;
            for (HrPersonnelDetailsTO hrPersonnelDetailsTO : hrPersonnelDetailsTOs) {
                currentEmpRow = new EmployeeDetailGrid();
                currentEmpRow.setSno(++i);
                currentEmpRow.setEmpId(hrPersonnelDetailsTO.getEmpId());
                currentEmpRow.setEmpName(hrPersonnelDetailsTO.getEmpName());
                currentEmpRow.setEmpAddress(hrPersonnelDetailsTO.getEmpContAdd());
                currentEmpRow.setEmpPhone(hrPersonnelDetailsTO.getEmpContTel());
                empSearchTable.add(currentEmpRow);
            }
            totalEmpRecords = i;
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setEmpRowValues() {
        try {
            catDetList = new ArrayList<SelectItem>();
            empName = currentItem.getEmpName();
            this.setCatDetValue(empName);
            empId = currentItem.getEmpId();
            catDetList.add(new SelectItem(empName));
            empSearchTable = null;
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setShiftTableRowValues() {
        try {
            setMode("update");
            catDetList = new ArrayList<SelectItem>();
            empId = currentShiftItem.getEmpId();
            shiftValue = currentShiftItem.getShiftCode();
            empName = currentShiftItem.getEmpName();
            empCode = currentShiftItem.getEmpCode();
            setCatDetValue(empName);
            catDetList.add(new SelectItem(empName));
            setCatValue("EWE");
            setDisableSaveButton(false);
            setDisableEditButton(true);
            setDisableDeleteButton(false);
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
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

    public List<SelectItem> getCatDetList() {
        return catDetList;
    }

    public void setCatDetList(List<SelectItem> catDetList) {
        this.catDetList = catDetList;
    }

    public String getCatDetValue() {
        return catDetValue;
    }

    public void setCatDetValue(String catDetValue) {
        this.catDetValue = catDetValue;
    }

    public List<SelectItem> getCatList() {
        return catList;
    }

    public void setCatList(List<SelectItem> catList) {
        this.catList = catList;
    }

    public String getFinYearFrom() {
        return finYearFrom;
    }

    public void setFinYearFrom(String finYearFrom) {
        this.finYearFrom = finYearFrom;
    }

    public String getFinYearTo() {
        return finYearTo;
    }

    public void setFinYearTo(String finYearTo) {
        this.finYearTo = finYearTo;
    }

    public SimpleDateFormat getMdyFormat() {
        return mdyFormat;
    }

    public void setMdyFormat(SimpleDateFormat mdyFormat) {
        this.mdyFormat = mdyFormat;
    }

    public PayrollMasterManagementDelegate getPayrollRollDelegate() {
        return payrollRollDelegate;
    }

    public void setPayrollRollDelegate(PayrollMasterManagementDelegate payrollRollDelegate) {
        this.payrollRollDelegate = payrollRollDelegate;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public SimpleDateFormat getDmyFormat() {
        return dmyFormat;
    }

    public void setDmyFormat(SimpleDateFormat dmyFormat) {
        this.dmyFormat = dmyFormat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EmployeeDetailGrid getCurrentEmpRow() {
        return currentEmpRow;
    }

    public void setCurrentEmpRow(EmployeeDetailGrid currentEmpRow) {
        this.currentEmpRow = currentEmpRow;
    }

    public EmployeeDetailGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(EmployeeDetailGrid currentItem) {
        this.currentItem = currentItem;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public List<EmployeeDetailGrid> getEmpSearchTable() {
        return empSearchTable;
    }

    public void setEmpSearchTable(List<EmployeeDetailGrid> empSearchTable) {
        this.empSearchTable = empSearchTable;
    }

    public int getTotalEmpRecords() {
        return totalEmpRecords;
    }

    public void setTotalEmpRecords(int totalEmpRecords) {
        this.totalEmpRecords = totalEmpRecords;
    }

    public String getEmpSearchCriteria() {
        return empSearchCriteria;
    }

    public void setEmpSearchCriteria(String empSearchCriteria) {
        this.empSearchCriteria = empSearchCriteria;
    }

    public String getEmpSearchValue() {
        return empSearchValue;
    }

    public void setEmpSearchValue(String empSearchValue) {
        this.empSearchValue = empSearchValue;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getStructCode() {
        return structCode;
    }

    public void setStructCode(String structCode) {
        this.structCode = structCode;
    }

    public String getCatValue() {
        return catValue;
    }

    public void setCatValue(String catValue) {
        this.catValue = catValue;
    }

    public String getShiftValue() {
        return shiftValue;
    }

    public void setShiftValue(String shiftValue) {
        this.shiftValue = shiftValue;
    }

    public String getEmployeeWise() {
        return employeeWise;
    }

    public void setEmployeeWise(String employeeWise) {
        this.employeeWise = employeeWise;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public List<SelectItem> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<SelectItem> shiftList) {
        this.shiftList = shiftList;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public EmployeeShiftTable getCurrentEmpShiftRow() {
        return currentEmpShiftRow;
    }

    public void setCurrentEmpShiftRow(EmployeeShiftTable currentEmpShiftRow) {
        this.currentEmpShiftRow = currentEmpShiftRow;
    }

    public EmployeeShiftTable getCurrentShiftItem() {
        return currentShiftItem;
    }

    public void setCurrentShiftItem(EmployeeShiftTable currentShiftItem) {
        this.currentShiftItem = currentShiftItem;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public List<EmployeeShiftTable> getEmpShiftTable() {
        return empShiftTable;
    }

    public void setEmpShiftTable(List<EmployeeShiftTable> empShiftTable) {
        this.empShiftTable = empShiftTable;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public int getTotalShiftTableRecords() {
        return totalShiftTableRecords;
    }

    public void setTotalShiftTableRecords(int totalShiftTableRecords) {
        this.totalShiftTableRecords = totalShiftTableRecords;
    }

    public boolean isDisableCategorizeDetails() {
        return disableCategorizeDetails;
    }

    public void setDisableCategorizeDetails(boolean disableCategorizeDetails) {
        this.disableCategorizeDetails = disableCategorizeDetails;
    }

    public String getOld_shiftValue() {
        return old_shiftValue;
    }

    public void setOld_shiftValue(String old_shiftValue) {
        this.old_shiftValue = old_shiftValue;
    }

    public boolean isShowShiftTable() {
        return showShiftTable;
    }

    public void setShowShiftTable(boolean showShiftTable) {
        this.showShiftTable = showShiftTable;
    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
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
    
}
