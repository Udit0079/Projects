/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.hr;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrSalaryProcessingPKTO;
import com.hrms.common.to.HrSalaryProcessingTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.delegate.PayrollTransactionsManagementDelegate;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.EmployeeDetailGrid;
import com.hrms.web.pojo.SalaryProcessingGrid;
import com.hrms.web.utils.LocalizationUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;

/**
 * @author Admin
 */
public class SalaryProjection extends BaseBean {

    private static final Logger logger = Logger.getLogger(SalaryProjection.class);
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private String message;
    private String projectionCateory;
    private List<SelectItem> projectionCateoryList;
    private String categorizationDetails;
    private boolean disableCategorizeDetails;
    private List<SelectItem> categorizationDetailsList;
    private String year;
    private List<SelectItem> yearList;
    private String monthCategory;
    private List<SelectItem> monthCategoryList;
    private String yearFromDate;
    private String yearToDate;
    private int compCode;
    private String employeeWise = "false";
    private String empSearchCriteria;
    private String empSearchValue;
    private int totalEmpRecords;
    private EmployeeDetailGrid currentItem;
    private List<EmployeeDetailGrid> empSearchTable;
    private List<HrSalaryProcessingTO> salaryProcessingList;
    private PayrollTransactionsManagementDelegate payRollDelegate;
    private PayrollTransactionsFacadeRemote payrollRemote;
    private CommonReportMethodsRemote commomRemote;
    private List<SalaryProcessingGrid> salaryProcessingGrid;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private boolean disableMonth;
   

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProjectionCateory() {
        return projectionCateory;
    }

    public void setProjectionCateory(String projectionCateory) {
        this.projectionCateory = projectionCateory;
    }

    public List<SelectItem> getProjectionCateoryList() {
        return projectionCateoryList;
    }

    public void setProjectionCateoryList(List<SelectItem> projectionCateoryList) {
        this.projectionCateoryList = projectionCateoryList;
    }

    public String getCategorizationDetails() {
        return categorizationDetails;
    }

    public void setCategorizationDetails(String categorizationDetails) {
        this.categorizationDetails = categorizationDetails;
    }

    public boolean isDisableCategorizeDetails() {
        return disableCategorizeDetails;
    }

    public void setDisableCategorizeDetails(boolean disableCategorizeDetails) {
        this.disableCategorizeDetails = disableCategorizeDetails;
    }

    public List<SelectItem> getCategorizationDetailsList() {
        return categorizationDetailsList;
    }

    public void setCategorizationDetailsList(List<SelectItem> categorizationDetailsList) {
        this.categorizationDetailsList = categorizationDetailsList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<SelectItem> getYearList() {
        return yearList;
    }

    public void setYearList(List<SelectItem> yearList) {
        this.yearList = yearList;
    }

    public String getMonthCategory() {
        return monthCategory;
    }

    public void setMonthCategory(String monthCategory) {
        this.monthCategory = monthCategory;
    }

    public List<SelectItem> getMonthCategoryList() {
        return monthCategoryList;
    }

    public void setMonthCategoryList(List<SelectItem> monthCategoryList) {
        this.monthCategoryList = monthCategoryList;
    }

    public String getYearFromDate() {
        return yearFromDate;
    }

    public void setYearFromDate(String yearFromDate) {
        this.yearFromDate = yearFromDate;
    }

    public String getYearToDate() {
        return yearToDate;
    }

    public void setYearToDate(String yearToDate) {
        this.yearToDate = yearToDate;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getEmployeeWise() {
        return employeeWise;
    }

    public void setEmployeeWise(String employeeWise) {
        this.employeeWise = employeeWise;
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

    public int getTotalEmpRecords() {
        return totalEmpRecords;
    }

    public void setTotalEmpRecords(int totalEmpRecords) {
        this.totalEmpRecords = totalEmpRecords;
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

    public List<HrSalaryProcessingTO> getSalaryProcessingList() {
        return salaryProcessingList;
    }

    public void setSalaryProcessingList(List<HrSalaryProcessingTO> salaryProcessingList) {
        this.salaryProcessingList = salaryProcessingList;
    }

    public List<SalaryProcessingGrid> getSalaryProcessingGrid() {
        return salaryProcessingGrid;
    }

    public void setSalaryProcessingGrid(List<SalaryProcessingGrid> salaryProcessingGrid) {
        this.salaryProcessingGrid = salaryProcessingGrid;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public boolean isDisableMonth() {
        return disableMonth;
    }

    public void setDisableMonth(boolean disableMonth) {
        this.disableMonth = disableMonth;
    }

    /**
     * Creates a new instance of ArrearProcessing
     */
    public SalaryProjection() {
        try {
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
            commomRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            compCode = Integer.parseInt(getOrgnBrCode());
            getFinancialYear();
            getInitalData();
            payRollDelegate = new PayrollTransactionsManagementDelegate();
            categorizationDetailsList = new ArrayList<SelectItem>();
            categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("S", "--Select--"));
            reportTypeList.add(new SelectItem("M", "Month"));
            reportTypeList.add(new SelectItem("Y", "Year"));
        } catch (Exception e) {
            logger.error("Exception occured while executing method SalaryProcessing()", e);
            logger.error("SalaryProcessing()", e);
        }
    }

    /**
     * Get the financial year calendar
     */
    public void getFinancialYear() {
        try {
            List list = payrollRemote.getFinYears(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Object[] ele = (Object[]) list.get(0);
                yearFromDate = ele[0].toString().trim();
                yearToDate = ele[1].toString().trim();
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getFinancialYear()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getFinancialYear()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /**
     * Get the processing categories data list
     */
    public void getInitalData() {
        try {
            projectionCateoryList = new ArrayList<SelectItem>();
            projectionCateoryList.add(new SelectItem("0", "--SELECT--"));
            projectionCateoryList.add(new SelectItem("EWE", "EMPLOYEE WISE"));
            projectionCateoryList.add(new SelectItem("BRN", "BRANCH WISE"));
            monthCategoryList = new ArrayList<SelectItem>();
            monthCategoryList.add(new SelectItem("0", "--SELECT--"));
            monthCategoryList.add(new SelectItem("JANUARY", "JANUARY"));
            monthCategoryList.add(new SelectItem("FEBRUARY", "FEBRUARY"));
            monthCategoryList.add(new SelectItem("MARCH", "MARCH"));
            monthCategoryList.add(new SelectItem("APRIL", "APRIL"));
            monthCategoryList.add(new SelectItem("MAY", "MAY"));
            monthCategoryList.add(new SelectItem("JUNE", "JUNE"));
            monthCategoryList.add(new SelectItem("JULY", "JULY"));
            monthCategoryList.add(new SelectItem("AUGUST", "AUGUST"));
            monthCategoryList.add(new SelectItem("SEPTEMBER", "SEPTEMBER"));
            monthCategoryList.add(new SelectItem("OCTOBER", "OCTOBER"));
            monthCategoryList.add(new SelectItem("NOVEMBER", "NOVEMBER"));
            monthCategoryList.add(new SelectItem("DECEMBER", "DECEMBER"));

            yearList = new ArrayList<SelectItem>();
            yearList.add(new SelectItem("0", "--Select--"));
            List list = payrollRemote.getYears(getOrgnBrCode());
            if (list.isEmpty()) {
                this.setMessage("Please define payroll calender");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Object[] ele = (Object[]) list.get(0);
                String firstYear = ele[0].toString().trim();
                String secondYear = ele[1].toString().trim();
                if (firstYear.equalsIgnoreCase(secondYear)) {
                    yearList.add(new SelectItem(firstYear));
                } else {
                    yearList.add(new SelectItem(firstYear));
                    yearList.add(new SelectItem(secondYear));
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    /**
     * Function on change of the Category data
     */
    public void onChangeCategory() {
        try {
            categorizationDetailsList = new ArrayList<SelectItem>();
            categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));
            if (projectionCateory.equalsIgnoreCase("EWE")) {
                employeeWise = "true";
                disableCategorizeDetails = true;
                this.setMessage("");
            } else if (projectionCateory.equalsIgnoreCase("0")) {
                employeeWise = "false";
                disableCategorizeDetails = false;
                this.setMessage("Please Select Categorization !!");
            } else if (projectionCateory.equalsIgnoreCase("BRN")) {
                employeeWise = "false";
                disableCategorizeDetails = false;

                List list = commomRemote.getAlphacodeAllAndBranch(getOrgnBrCode());
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    categorizationDetailsList.add(new SelectItem(ele.get(1).toString().length() < 2
                            ? "0" + ele.get(1).toString() : ele.get(1).toString(), ele.get(0).toString()));
                }
            } else {
                disableCategorizeDetails = false;
                employeeWise = "false";
                PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
                String structCode = this.projectionCateory + "%";
                List<HrMstStructTO> hrMstStructTOs = masterManagementDelegate.getInitialData(compCode, structCode);
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

    public void onRportType() {
        setMessage("");
        if (reportType.equalsIgnoreCase("M")) {
            this.disableMonth = false;
        } else {
            this.disableMonth = true;
        }
    }

    /**
     * function to set the employee details on selecting the employee details
     * and print the grid of tax processing for the financial year
     */
    public void setEmpRowValues() {
        categorizationDetailsList.clear();
        categorizationDetailsList.add(new SelectItem(currentItem.getEmpId(), currentItem.getEmpName()));
    }

    /**
     * set the employee wise data on click of find button in page
     */
    public void getEmployeeDetails() {
        try {
            PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();
            empSearchTable = new ArrayList<>();
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = payrollDelegate.getHrPersonnelData(compCode, empSearchCriteria, empSearchValue);
            int i = 0;
            EmployeeDetailGrid currentEmpRow;
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
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmployeeTableDataEmloyeeWise()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    /**
     * Function handles the salary projection either by employee wise or
     * category wise.
     */
    public void salaryProjectionRep() {
        try {
            String errorResult = validations();
            if (!errorResult.equalsIgnoreCase("true")) {
                this.setMessage(errorResult);
                return;
            }
            HrSalaryProcessingTO hrSalaryProcessingTO = new HrSalaryProcessingTO();
            HrSalaryProcessingPKTO hrsalaryProcessingPKTO = new HrSalaryProcessingPKTO();
            hrsalaryProcessingPKTO.setCalDateFrom(formatter.parse(yearFromDate));
            hrsalaryProcessingPKTO.setCalDateTo(formatter.parse(yearToDate));
            hrsalaryProcessingPKTO.setCompCode(compCode);
            hrsalaryProcessingPKTO.setMonths(monthCategory);
            hrSalaryProcessingTO.setHrSalaryProcessingPK(hrsalaryProcessingPKTO);
            hrSalaryProcessingTO.setAuthBy(getUserName());
            hrSalaryProcessingTO.setDefaultComp(compCode);
            hrSalaryProcessingTO.setEnteredBy(getUserName());
            hrSalaryProcessingTO.setStatFlag("Y");
            hrSalaryProcessingTO.setStatUpFlag("Y");
            hrSalaryProcessingTO.setModDate(new Date());
            hrSalaryProcessingTO.setYear(this.year);
            salaryProcessingList = new ArrayList<>();
            if (disableCategorizeDetails == true) {
                salaryProcessingList = payRollDelegate.salaryCalculationProjection(hrSalaryProcessingTO, projectionCateory, currentItem.getEmpId(), this.getMonthCategory());
            } else {
                salaryProcessingList = payRollDelegate.salaryCalculationProjection(hrSalaryProcessingTO, projectionCateory, categorizationDetails, this.getMonthCategory());
            }
            if (salaryProcessingList.isEmpty()) {                   
                setMessage("Problem in salary calculation. Please check salary allocation.");
            } else {
                salaryProcessingGrid = new ArrayList<>();
                SalaryProcessingGrid salGrid;
                String prvEmpId = "";
                for (HrSalaryProcessingTO salaryProcessingTO : salaryProcessingList) {
                    for (int i = 0; i < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); i++) {
                        salGrid = new SalaryProcessingGrid();
                        String empId = salaryProcessingTO.getHrPersonnelDetailsTO().getEmpId();
                        if (prvEmpId.equalsIgnoreCase(empId)) {
                            prvEmpId = empId;
                            salGrid.setsNo(i + 1);
                        } else {
                            prvEmpId = empId;
                            i = 0;
                            salGrid.setsNo(i + 1);
                        }
                        salGrid.setEmployeeId(salaryProcessingTO.getHrPersonnelDetailsTO().getEmpId());
                        salGrid.setEmployeeName(salaryProcessingTO.getHrPersonnelDetailsTO().getEmpName());
                        salGrid.setCompType(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getComponentType());
                        if (reportType.equalsIgnoreCase("M")) {
                            salGrid.setCompAmt(new BigDecimal(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary()));
                        } else {
                            salGrid.setCompAmt(new BigDecimal(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary()).multiply(new BigDecimal(12)));
                        }
                        salGrid.setMonth(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                        String salAccount = "";
                        if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR002")) {
                            if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode() == 1) {
                                salAccount = salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount();
                            } else {
                                salAccount = salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getGlCode();
                            }
                        }

                        if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR001")) {
                            if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode() == 1) {
                                salAccount = salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode();
                                
                            }
                        }

                        salGrid.setVarDesc(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode());
                        salGrid.setSalAcc(salAccount);
                        salGrid.setGrossSalary(0.0);
                        salGrid.setIncomeTax(0.0);
                        salGrid.setNetSalary(0.0);
                        salaryProcessingGrid.add(salGrid);
                    }
                }

                List brNameAndAddList;
                Map fillParams = new HashMap();
                if (projectionCateory.equalsIgnoreCase("EWE")) {
                    brNameAndAddList = commomRemote.getBranchNameandAddress(commomRemote.getBranchCode(prvEmpId));                    
                }else{
                    brNameAndAddList = commomRemote.getBranchNameandAddress(categorizationDetails);
                }
                
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAdd", brNameAndAddList.get(1).toString());
                if (reportType.equalsIgnoreCase("M")) {
                    fillParams.put("pMonthYear", monthCategory + " " + year);
                } else {
                    fillParams.put("pMonthYear", year);
                }

                String repType = "BRANCH WISE";
                String repHdVal = "Branch Name";
                String repHdDtl = "";
                if (projectionCateory.equalsIgnoreCase("EWE")) {
                    repType = "EMPLOYEE WISE";
                    repHdVal = "Employee Id";
                } else {
                    repHdDtl = commomRemote.getBranchNameByBrncode(categorizationDetails);
                }
                fillParams.put("pReportName", "Salary Projection");
                fillParams.put("PReportType", repType);
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pHdVal", repHdVal);
                fillParams.put("pDtlVal", repHdDtl);
                fillParams.put("pProjCateg", projectionCateory);
              
           
                 if(commomRemote.getCodeByReportName("BASIC-INCREMENT-DT")==1){
                fillParams.put("pIncDateLbl","Basic Increament Date");
                fillParams.put("pIncreamentDate",salaryProcessingList.get(0).getBasicSalIncDate());
                 }else{
                fillParams.put("pIncDateLbl","");
                fillParams.put("pIncreamentDate","");    
                 }

                new ReportBean().openPdf("SalaryProjection_" + monthCategory + " " + year, "Salary_Projection", new JRBeanCollectionDataSource(salaryProcessingGrid), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", "Salary Projection");

            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveSalaryProjection()", e);
            this.setMessage(e.getLocalizedMessage());
        }
    }

    /**
     * validations check
     *
     * @return
     */
    public String validations() {
        setMessage("");
        if (reportType.equalsIgnoreCase("S")) {
            return "Please select report type ";
        }
        if (reportType.equalsIgnoreCase("M")) {
            if (monthCategory.equalsIgnoreCase("0")) {
                return "Please select the month category!!";
            }
        }
        if (projectionCateory.equalsIgnoreCase("0")) {
            return "Please select the processing category!!";
        }
        if (disableCategorizeDetails == false) {
            if (categorizationDetails.equalsIgnoreCase("0")) {
                return "Please select the categorization details!!";
            }
        }
        if (this.year == null || this.year.equals("0")) {
            return "Please select the year!!";
        }
        return "true";
    }

    /**
     * Function to clear the forms values
     */
    public void refresh() {
        this.setMessage("");
        this.setProjectionCateory("0");
        this.setCategorizationDetails("0");
        this.setMonthCategory("0");
        disableCategorizeDetails = true;
        categorizationDetailsList.clear();
        categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));
    }

    public String btnExitAction() {
        refresh();
        return "case1";
    }
}