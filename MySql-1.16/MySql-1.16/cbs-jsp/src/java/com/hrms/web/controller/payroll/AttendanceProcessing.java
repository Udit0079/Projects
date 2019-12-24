/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;


import com.hrms.web.pojo.EmpAttenDetailsGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrAttendanceDetailsPKTO;
import com.hrms.common.to.HrAttendanceDetailsTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.payroll.PayrollOtherMgmFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.EmployeeDetailGrid;
import com.hrms.web.pojo.FinYearDetail;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.delegate.PayrollTransactionsManagementDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr Bisht //modified by Ankit Verma
 */
public class AttendanceProcessing extends BaseBean {
    
    private static final Logger logger = Logger.getLogger(AttendanceProcessing.class);
    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private String message;
//    private String yearFromDate;
//    private String yearToDate;
    private int compCode;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private String attendanceCategory;
    private List<SelectItem> attendanceCategoryList;
    private String categorizationDetails;
    private List<SelectItem> categorizationDetailsList;
    private boolean disableCategorizeDetails;
    private String employeeWise = "false";
    private Date pageFromDate;
    private Date pageToDate;
    private String minimumWorkingUnit;
    private List<SelectItem> minWorkingUnitList;
    private String empSearchCriteria;
    private String empSearchValue;
    private List<EmployeeDetailGrid> empSearchTable;
    private EmployeeDetailGrid currentEmpRow;
    private int totalEmpRecords;
    private EmployeeDetailGrid currentItem;
    private String presentDays;
    private String absentDays;
    private String paidLeave;
    private String workingDays;
    private String overTime;
    private List<EmpAttenDetailsGrid> empAttenDetailsGrid = new ArrayList<EmpAttenDetailsGrid>();
    private EmpAttenDetailsGrid currentGridItem;
    private String labelInfo;
    private boolean disable;
    private String mode = "ADD";
    private boolean disableSave;
    private boolean disableCalendars;
    private String operation;
    private List<SelectItem> operationList;
    private boolean disableWorkingDays;
    private Double deductDays;
    private  PayrollOtherMgmFacadeRemote othrMgmRemotefacade;

    
    public boolean isDisableWorkingDays() {
        return disableWorkingDays;
    }
    
    public void setDisableWorkingDays(boolean disableWorkingDays) {
        this.disableWorkingDays = disableWorkingDays;
    }
    
    public boolean isDisableCalendars() {
        return disableCalendars;
    }
    
    public void setDisableCalendars(boolean disableCalendars) {
        this.disableCalendars = disableCalendars;
    }
    
    public boolean isDisableSave() {
        return disableSave;
    }
    
    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }
    
    public boolean isDisable() {
        return disable;
    }
    
    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    
    public String getLabelInfo() {
        return labelInfo;
    }
    
    public void setLabelInfo(String labelInfo) {
        this.labelInfo = labelInfo;
    }
    
    public EmpAttenDetailsGrid getCurrentGridItem() {
        return currentGridItem;
    }
    
    public void setCurrentGridItem(EmpAttenDetailsGrid currentGridItem) {
        this.currentGridItem = currentGridItem;
    }
    
    public List<EmpAttenDetailsGrid> getEmpAttenDetailsGrid() {
        return empAttenDetailsGrid;
    }
    
    public void setEmpAttenDetailsGrid(List<EmpAttenDetailsGrid> empAttenDetailsGrid) {
        this.empAttenDetailsGrid = empAttenDetailsGrid;
    }
    
    public String getAbsentDays() {
        return absentDays;
    }
    
    public void setAbsentDays(String absentDays) {
        this.absentDays = absentDays;
    }
    
    public String getOverTime() {
        return overTime;
    }
    
    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }
    
    public String getPresentDays() {
        return presentDays;
    }
    
    public void setPresentDays(String presentDays) {
        this.presentDays = presentDays;
    }
    
    public String getWorkingDays() {
        return workingDays;
    }
    
    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }
    
    public Date getPageFromDate() {
        return pageFromDate;
    }
    
    public void setPageFromDate(Date pageFromDate) {
        this.pageFromDate = pageFromDate;
    }
    
    public Date getPageToDate() {
        return pageToDate;
    }
    
    public void setPageToDate(Date pageToDate) {
        this.pageToDate = pageToDate;
    }
    
    public int getTotalEmpRecords() {
        return totalEmpRecords;
    }
    
    public void setTotalEmpRecords(int totalEmpRecords) {
        this.totalEmpRecords = totalEmpRecords;
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
    
    public List<EmployeeDetailGrid> getEmpSearchTable() {
        return empSearchTable;
    }
    
    public void setEmpSearchTable(List<EmployeeDetailGrid> empSearchTable) {
        this.empSearchTable = empSearchTable;
    }
    
    public String getEmpSearchValue() {
        return empSearchValue;
    }
    
    public void setEmpSearchValue(String empSearchValue) {
        this.empSearchValue = empSearchValue;
    }
    
    public String getEmpSearchCriteria() {
        return empSearchCriteria;
    }
    
    public void setEmpSearchCriteria(String empSearchCriteria) {
        this.empSearchCriteria = empSearchCriteria;
    }
    
    public List<SelectItem> getMinWorkingUnitList() {
        return minWorkingUnitList;
    }
    
    public void setMinWorkingUnitList(List<SelectItem> minWorkingUnitList) {
        this.minWorkingUnitList = minWorkingUnitList;
    }
    
    public String getMinimumWorkingUnit() {
        return minimumWorkingUnit;
    }
    
    public void setMinimumWorkingUnit(String minimumWorkingUnit) {
        this.minimumWorkingUnit = minimumWorkingUnit;
    }
    
    public String getAttendanceCategory() {
        return attendanceCategory;
    }
    
    public void setAttendanceCategory(String attendanceCategory) {
        this.attendanceCategory = attendanceCategory;
    }
    
    public List<SelectItem> getAttendanceCategoryList() {
        return attendanceCategoryList;
    }
    
    public void setAttendanceCategoryList(List<SelectItem> attendanceCategoryList) {
        this.attendanceCategoryList = attendanceCategoryList;
    }
    
    public String getCategorizationDetails() {
        return categorizationDetails;
    }
    
    public void setCategorizationDetails(String categorizationDetails) {
        this.categorizationDetails = categorizationDetails;
    }
    
    public List<SelectItem> getCategorizationDetailsList() {
        return categorizationDetailsList;
    }
    
    public void setCategorizationDetailsList(List<SelectItem> categorizationDetailsList) {
        this.categorizationDetailsList = categorizationDetailsList;
    }
    
    public boolean isDisableCategorizeDetails() {
        return disableCategorizeDetails;
    }
    
    public void setDisableCategorizeDetails(boolean disableCategorizeDetails) {
        this.disableCategorizeDetails = disableCategorizeDetails;
    }
    
    public String getEmployeeWise() {
        return employeeWise;
    }
    
    public void setEmployeeWise(String employeeWise) {
        this.employeeWise = employeeWise;
    }
    
    public SimpleDateFormat getFormatter() {
        return formatter;
    }
    
    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
//    public String getYearFromDate() {
//        return yearFromDate;
//    }
//    
//    public void setYearFromDate(String yearFromDate) {
//        this.yearFromDate = yearFromDate;
//    }
//    
//    public String getYearToDate() {
//        return yearToDate;
//    }
//    
//    public void setYearToDate(String yearToDate) {
//        this.yearToDate = yearToDate;
//    }
    
    public List<CompanyMasterTO> getCompanyMasterTOs() {
        return companyMasterTOs;
    }
    
    public void setCompanyMasterTOs(List<CompanyMasterTO> companyMasterTOs) {
        this.companyMasterTOs = companyMasterTOs;
    }
    
    public WebUtil getWebUtil() {
        return webUtil;
    }
    
    public void setWebUtil(WebUtil webUtil) {
        this.webUtil = webUtil;
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
    
    public int getCompCode() {
        return compCode;
    }
    
    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }
    
    public String getMode() {
        return mode;
    }
    
    public void setMode(String mode) {
        this.mode = mode;
    }
    
    public String getPaidLeave() {
        return paidLeave;
    }
    
    public void setPaidLeave(String paidLeave) {
        this.paidLeave = paidLeave;
    }

    public Double getDeductDays() {
        return deductDays;
    }

    public void setDeductDays(Double deductDays) {
        this.deductDays = deductDays;
    }

   
    
    /**
     * Creates a new instance of AttendanceProcessing
     */
    public AttendanceProcessing() {
        try {
            compCode = Integer.parseInt(getOrgnBrCode());
            operationList = new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0", "---Select---"));
            operationList.add(new SelectItem("1", "Add"));
            operationList.add(new SelectItem("2", "Edit"));
//            getFinancialYear();
            getInitalData();
            disableWorkingDays = true;
            disable = true;
            categorizationDetailsList = new ArrayList<SelectItem>();
            categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));
            companyMasterTOs = webUtil.getCompanyMasterTO(compCode);
        } catch (Exception e) {
            logger.error("Exception occured while executing method AttendanceProcessing()", e);
            logger.error("AttendanceProcessing()", e);
        }
    }
    
    public HttpServletRequest getRquest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    /**
     * ********** Get the current financial year********
     */
//    public void getFinancialYear() {
//        try {
//            PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
//            List<PayrollCalendarTO> payrollCalendarTOs = masterManagementDelegate.getFinancialYear(compCode);
//            if (!payrollCalendarTOs.isEmpty()) {
//                for (PayrollCalendarTO payrollCalendarTO : payrollCalendarTOs) {
//                    FinYearDetail finYearDetail = new FinYearDetail();
//                    this.setYearFromDate(formatter.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateFrom()));
//                    this.setYearToDate(formatter.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateTo()));
//                }
//            }
//        } catch (WebException e) {
//            logger.error("Exception occured while executing method getFinancialYear()", e);
//            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
//        } catch (ServiceLocatorException e1) {
//            logger.error("Exception occured while executing method getFinancialYear()", e1);
//            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        } catch (Exception e) {
//            logger.error("Exception occured while executing method getFinancialYear()", e);
//            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        }
//    }

    /**
     * Set the data on page load
     */
    public void getInitalData() {
        try {
            attendanceCategoryList = new ArrayList<SelectItem>();
            attendanceCategoryList.add(new SelectItem("0", "--SELECT--"));
            attendanceCategoryList.add(new SelectItem("EWE", "EMPLOYEE WISE"));
            attendanceCategoryList.add(new SelectItem("All","All"));
            othrMgmRemotefacade = (PayrollOtherMgmFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollOtherMgmFacade");
          
            PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
            List<HrMstStructTO> HrMstStructTOs = masterManagementDelegate.getInitialData(compCode, "CHO%");
            if (!HrMstStructTOs.isEmpty()) {
                for (HrMstStructTO HrMstStructTO : HrMstStructTOs) {
                    attendanceCategoryList.add(new SelectItem(HrMstStructTO.getHrMstStructPKTO().getStructCode(), HrMstStructTO.getDescription()));
                }
            }
            minWorkingUnitList = new ArrayList<SelectItem>();
            minWorkingUnitList.add(new SelectItem("0", "--SELECT--"));
            minWorkingUnitList.add(new SelectItem("HOUR", "HOUR"));
            minWorkingUnitList.add(new SelectItem("MINUTE", "MINUTE"));
        } catch (WebException e) {
            logger.error("Exception occured while executing method getInitalData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getInitalData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getInitalData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /**
     * Action on change the value of Attendance category list
     */
    public void onChangeCategory() {
        try {
            categorizationDetailsList = new ArrayList<SelectItem>();
            categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));
            if (attendanceCategory.equalsIgnoreCase("EWE")) {
                employeeWise = "true";
                disableCategorizeDetails = true;
                this.setMessage("");
            } else if (attendanceCategory.equalsIgnoreCase("0")) {
                employeeWise = "false";
                disableCategorizeDetails = false;
                this.setMessage("Please Select Categorization !!");
            } else if(attendanceCategory.equalsIgnoreCase("All")){
                employeeWise = "false";
                disableCategorizeDetails = true;
                if(operation.equalsIgnoreCase("2")){
                  disableCategorizeDetails = true;
                  disableCalendars = true;
                }
                  
            } else {
                disableCategorizeDetails = false;
                employeeWise = "false";
                PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
                String structCode = this.attendanceCategory + "%";
                String structCodeDecsription = "";
                List<HrMstStructTO> hrMstStructTOs = masterManagementDelegate.getInitialData(compCode, structCode);
                for (HrMstStructTO hrMststruct : hrMstStructTOs) {
                    structCodeDecsription = hrMststruct.getDescription();
                    structCodeDecsription = structCodeDecsription.substring(0, 3);
                }
                structCodeDecsription = structCodeDecsription + "%";
                hrMstStructTOs = masterManagementDelegate.getInitialData(compCode, structCodeDecsription);
                for (HrMstStructTO hrMststruct : hrMstStructTOs) {
                    categorizationDetailsList.add(new SelectItem(hrMststruct.getHrMstStructPKTO().getStructCode(), hrMststruct.getDescription()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method onChangeCategory()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method onChangeCategory()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method onChangeCategory()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /**
     * Get the employee data on selecting employee wise from Category list
     */
    public void getEmployeeTableDataEmloyeeWise() {
        try {
            PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();
            empSearchTable = new ArrayList<EmployeeDetailGrid>();
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = payrollDelegate.getHrPersonnelData(compCode, empSearchCriteria, empSearchValue);
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
            logger.error("Exception occured while executing method getEmployeeTableDataEmloyeeWise()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getEmployeeTableDataEmloyeeWise()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmployeeTableDataEmloyeeWise()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /**
     * Set the employee information on page on selecting secific employee at
     * employee wise category
     */
    public void setEmpRowValues() {
        categorizationDetailsList.clear();
        categorizationDetailsList.add(new SelectItem(currentItem.getEmpId(), currentItem.getEmpName()));
    }

    /**
     * Save Attendance processing using values set on page fields
     */
    public void saveAttendanceProcessing() {
        String valResult = validations();
        if (!valResult.equalsIgnoreCase("true")) {
            this.setMessage(valResult);
            return;
        }
        try {
            String result = "";
            if (mode.equalsIgnoreCase("ADD")) {
                HrAttendanceDetailsTO hrAttendanceDetailsTO = new HrAttendanceDetailsTO();
                hrAttendanceDetailsTO.setProcessDateFrom(getPageFromDate());
                hrAttendanceDetailsTO.setProcessDateTo(getPageToDate());
                hrAttendanceDetailsTO.setDefaultComp(compCode);
                hrAttendanceDetailsTO.setStatFlag("Y");
                hrAttendanceDetailsTO.setStatUpFlag("Y");
                hrAttendanceDetailsTO.setAuthBy(getUserName());
                hrAttendanceDetailsTO.setEnteredBy(getUserName());
                hrAttendanceDetailsTO.setOverTimeUnit(minimumWorkingUnit);
                PayrollTransactionsManagementDelegate payrollTransDelegate = new PayrollTransactionsManagementDelegate();
                if (attendanceCategory.equalsIgnoreCase("EWE")) {
                    result = payrollTransDelegate.saveAttendanceProcessing(hrAttendanceDetailsTO, attendanceCategory, currentItem.getEmpId(), mode);
                }else if(attendanceCategory.equalsIgnoreCase("All")){
                     result = payrollTransDelegate.saveAttendanceProcessing(hrAttendanceDetailsTO, attendanceCategory, categorizationDetails, mode);
             
                } else {
                    result = payrollTransDelegate.saveAttendanceProcessing(hrAttendanceDetailsTO, attendanceCategory, categorizationDetails, mode);
                }
            }else if (mode.equalsIgnoreCase("EDIT")) {
                HrAttendanceDetailsTO hrAttendanceDetailsTO = new HrAttendanceDetailsTO();
                HrAttendanceDetailsPKTO hrAttendanceDetailsPKTO = new HrAttendanceDetailsPKTO();
                hrAttendanceDetailsPKTO.setAttenMonth(currentGridItem.getMonth());
                hrAttendanceDetailsPKTO.setAttenYear(Integer.parseInt(currentGridItem.getYear()));
                hrAttendanceDetailsPKTO.setCompCode(compCode);
                hrAttendanceDetailsTO.setAbsentDays(Integer.parseInt(getAbsentDays()));
                hrAttendanceDetailsTO.setAuthBy(getUserName());
                hrAttendanceDetailsTO.setDefaultComp(compCode);
                hrAttendanceDetailsTO.setEnteredBy(getUserName());
                hrAttendanceDetailsTO.setHrAttendanceDetailsPKTO(hrAttendanceDetailsPKTO);
                hrAttendanceDetailsTO.setOverTimePeriod(Long.parseLong(getOverTime()));
                hrAttendanceDetailsTO.setPresentDays(Integer.parseInt(getPresentDays()));
                hrAttendanceDetailsTO.setWorkingDays(Integer.parseInt(getWorkingDays()));
                hrAttendanceDetailsTO.setDeductDays(getDeductDays());
                PayrollTransactionsManagementDelegate payrollTransMgmtDele = new PayrollTransactionsManagementDelegate();
                result = payrollTransMgmtDele.editAttendanceDetails(hrAttendanceDetailsTO, currentGridItem.getEmloyeeId());
                getAttendancedetailsforEmployee();
            }
            refresh();
            this.setMessage(result);
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAttendanceProcessing()", e);
            this.setMessage(e.getMessage());
//            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /**
     * Function to delete the attendance details of the employee which has not
     * been posted
     */
    public void deleteAttendanceDetails() {
        mode = "DELETE";
        postAttendance();
    }

    /**
     * Function to get the attendance details for the company of all employees
     */
    public void getAttendancedetailsforEmployee() {
        if (operation.equalsIgnoreCase("0")) {
            setMessage("Please select an operation!");
            return;
        }
        if (operation.equalsIgnoreCase("1")) {
            mode = "ADD";
             disable =true;
        } else if (operation.equalsIgnoreCase("2")) {
            mode = "EDIT";
            disable =false;
        
        }
        empAttenDetailsGrid.clear();
        this.setMessage("");
        Date currentDate = new Date();
        String formattedDate = formatter.format(currentDate);
        int year = Integer.parseInt(formattedDate.substring(6, 10));
        try {
            PayrollTransactionsManagementDelegate payrollTransDelegate = new PayrollTransactionsManagementDelegate();
            List attendanceDetailsList = payrollTransDelegate.getAttendanceDetails(compCode, year);
            Iterator listIterator = attendanceDetailsList.iterator();
            while (listIterator.hasNext()) {
                Object[] result = (Object[]) listIterator.next();
                EmpAttenDetailsGrid empGrid = new EmpAttenDetailsGrid();
                empGrid.setAbsentDays(result[7].toString());
                empGrid.setDateFrom(formatter.format(result[3]));
                empGrid.setDateTo(formatter.format(result[4]));
                empGrid.setEmloyeeId(result[11].toString());
                empGrid.setEmployeeName(result[12].toString());
                empGrid.setMonth(result[1].toString());
                empGrid.setOverTime(result[8].toString());
                empGrid.setOverTimeUnit(result[9].toString());
                empGrid.setPostFlag(result[10].toString());
                empGrid.setPresentDays(result[6].toString());
                empGrid.setWorkingDays(result[5].toString());
                empGrid.setYear(result[2].toString());    
                empGrid.setEmpCode(result[0].toString());
                empGrid.setPaidLeave(result[13].toString());
                empGrid.setDeductDays(result[14].toString());
                empAttenDetailsGrid.add(empGrid);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAttendancedetailsforEmployee()", e);
            this.setMessage(e.getMessage());
        }
    }

    /**
     * Set the attendance details row values on page fields
     */
    public void setAttendanceRowValues() {
        this.setMessage("");
        disable = false;
        disableSave = false;
        disableCalendars = true;
        disableCategorizeDetails = true;
        mode = "EDIT";
        this.operation = "2";
        try {
            setAbsentDays(currentGridItem.getAbsentDays());
            setPaidLeave(currentGridItem.getPaidLeave());
            setPageFromDate(formatter.parse(currentGridItem.getDateFrom()));
            setPageToDate(formatter.parse(currentGridItem.getDateTo()));
            categorizationDetailsList.clear();
            categorizationDetailsList.add(new SelectItem(currentGridItem.getEmloyeeId(), currentGridItem.getEmployeeName()));
            setAttendanceCategory("EWE");
            setOverTime(currentGridItem.getOverTime());
            this.setMinimumWorkingUnit(currentGridItem.getOverTimeUnit());
            setPresentDays(currentGridItem.getPresentDays());
            setWorkingDays(currentGridItem.getWorkingDays());
            setDeductDays(Double.valueOf(currentGridItem.getDeductDays()));
        } catch (Exception e) {
            
        }
    }

    /**
     * Post the attendance either employee wise or category wise between the
     * processing dates
     */
    public void postAttendance() {
        HrAttendanceDetailsTO hrAttendanceDetailsTO = new HrAttendanceDetailsTO();
        HrAttendanceDetailsPKTO hrAttendanceDetailsPKTO = new HrAttendanceDetailsPKTO();
        hrAttendanceDetailsPKTO.setCompCode(compCode);
        hrAttendanceDetailsTO.setProcessDateFrom(getPageFromDate());
        hrAttendanceDetailsTO.setProcessDateTo(getPageToDate());
        hrAttendanceDetailsTO.setAuthBy(getUserName());
        hrAttendanceDetailsTO.setEnteredBy(getUserName());
        hrAttendanceDetailsTO.setHrAttendanceDetailsPKTO(hrAttendanceDetailsPKTO);
        String result ="";
        try {
            PayrollTransactionsManagementDelegate payrolltransactionsMgmt = new PayrollTransactionsManagementDelegate();
             
            if(!attendanceCategory.equalsIgnoreCase("All")){
            if (disableCategorizeDetails == true) {
                result = payrolltransactionsMgmt.postAttendanceProcessing(hrAttendanceDetailsTO, attendanceCategory, currentGridItem.getEmloyeeId(), mode,empAttenDetailsGrid.get(0).getDateFrom(),empAttenDetailsGrid.get(0).getDateTo());
            } else {
                result = payrolltransactionsMgmt.postAttendanceProcessing(hrAttendanceDetailsTO, attendanceCategory, categorizationDetails,mode, empAttenDetailsGrid.get(0).getDateFrom(),empAttenDetailsGrid.get(0).getDateTo());
            }
            }else{
                
                for(int i=0;i<empAttenDetailsGrid.size();i++){
                result = payrolltransactionsMgmt.postAttendanceProcessing(hrAttendanceDetailsTO, attendanceCategory,empAttenDetailsGrid.get(i).getEmloyeeId(), mode,empAttenDetailsGrid.get(0).getDateFrom(),empAttenDetailsGrid.get(0).getDateTo()); 
                }
            }
            refresh();
            getAttendancedetailsforEmployee();
            disable = true;
            this.setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method postAttendance()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method postAttendance()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method postAttendance()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /**
     * Function to edit the attendane details of the employee for the month and
     * year which is selected from the grid
     */
//    public void editAttendance() {
//        String valResult = validations();
//        if (!valResult.equalsIgnoreCase("true")) {
//            this.setMessage(valResult);
//            return;
//        }
//        try {
//        } catch (WebException e) {
//            logger.error("Exception occured while executing method editAttendance()", e);
//            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
//        } catch (ServiceLocatorException e1) {
//            logger.error("Exception occured while executing method editAttendance()", e1);
//            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        } catch (Exception e) {
//            logger.error("Exception occured while executing method editAttendance()", e);
//            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        }
//    }
    public String validations() {
        try {
            if (operation.equalsIgnoreCase("0")) {
                return "Please select an operation!";
            }
            if (attendanceCategory.equalsIgnoreCase("0")) {
                return "Attendance Category cannot be blank!!!";
            }
            if (disableCategorizeDetails == false) {
                if (categorizationDetails.equalsIgnoreCase("0")) {
                    return "Category details list cannot be blank!!!";
                }
            }
            if (this.getPageFromDate() == null) {
                return "From date Calendar cannot be empty!!!";
            }
            if (this.getPageToDate() == null) {
                return "To date calendar cannot be empty!!!";
            }
            if (minimumWorkingUnit.equalsIgnoreCase("0")) {
                return "Minimum working unit list cannot be empty!!!";
            }
            if (pageFromDate.after(pageToDate)) {
                return "Date from can not be greater than Date to!";
            }
            if (disable == false) {
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                if (getWorkingDays().equalsIgnoreCase("")) {
                    return "Working days field cannot be empty!!!";
                }
                Matcher workingDaysCheck = p.matcher(this.getWorkingDays());
                if (!workingDaysCheck.matches()) {
                    return "Please Enter Valid numeric value for working days!!!";
                }
                if (Integer.parseInt(getWorkingDays()) < 0) {
                    return "Working days cannot be negative!!!";
                }
                if (getPresentDays().equalsIgnoreCase("")) {
                    return "Present days field cannot be empty!!!";
                }
                Matcher presentDaysCheck = p.matcher(this.getPresentDays());
                if (!presentDaysCheck.matches()) {
                    return "Please Enter Valid numeric value for present days!!!";
                }
                if (Integer.parseInt(getPresentDays()) < 0) {
                    return "Present days field cannot be negative";
                }
                if (getAbsentDays().equalsIgnoreCase("")) {
                    return "Absent days field cannot be empty!!!";
                }
                Matcher absentDaysCheck = p.matcher(this.getAbsentDays());
                if (!absentDaysCheck.matches()) {
                    return "Please Enter Valid numeric value for absent days!!!";
                }
                if (Integer.parseInt(getAbsentDays()) < 0) {
                    return "Absent days field cannot be negative!!!";
                }
                if (getOverTime().equalsIgnoreCase("")) {
                    return "Overtime field cannot be empty!!!";
                }
                Matcher overTimeCheck = p.matcher(this.getAbsentDays());
                if (!absentDaysCheck.matches()) {
                    return "Please Enter Valid numeric value for over time period!!!";
                }
                if (Integer.parseInt(getOverTime()) < 0) {
                    return "Overtime field cannot be empty!!!";
                }
                if (String.valueOf(getDeductDays()).equalsIgnoreCase("") && getDeductDays()!= null) {
                    return "Salary Deduction Days field cannot be empty!!!";
                }
//                Matcher deductDaysCheck = p.matcher(this.getDeductDays());
//                if (!deductDaysCheck.matches()) {
//                    return "Please Enter Valid numeric value for Salary Deduction Days!!!";
//                }
                
                Double inputNum = Double.valueOf(deductDays);
                if(inputNum >30.0){
                    return "Invalid Dedudt Days!!";
                }
                
                Double outcomeNum = (inputNum * 10)/5;
                if((outcomeNum % 1) != 0){
                    return "Invalid Entered Deduct Days !!";  
                }
                
                if (Double.valueOf(getDeductDays()) < 0) {
                    return "Salary Deduction Days field cannot be negative!!!";
                }
                
                
            }
        } catch (NumberFormatException e) {
            return "Entered numeric field should have integral numeric values (0-9)!!!";
        } catch (Exception e) {
            return "Error occurred , Unable to process!!!";
        }
        return "true";
    }

    /**
     * function to clean up the page
     */
    public void refresh() {
        this.setAttendanceCategory("0");
        this.setMessage("");
        this.setMinimumWorkingUnit("0");
        this.setPageFromDate(null);
        this.setPageToDate(null);
//        this.setYearFromDate("");
//        this.setYearToDate("");
        setOperation("0");
        setWorkingDays("");
        setPresentDays("");
        setAbsentDays("");
        setPaidLeave("");
        setOverTime("");
        setDeductDays(0.0);
        disableCalendars = false;
        disableSave = false;
        disable = true;
        mode = "ADD";
        categorizationDetailsList.clear();
        categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));
    }
    
    public String btnExitAction() {
        refresh();
        return "case1";
    }
}
