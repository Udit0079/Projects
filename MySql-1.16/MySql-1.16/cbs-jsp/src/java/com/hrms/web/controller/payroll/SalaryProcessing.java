/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.SalaryRegisterPojo;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.pojo.SalaryProcessingGrid;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.utils.Util;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrSalaryProcessingPKTO;
import com.hrms.common.to.HrSalaryProcessingTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.payroll.PayrollOtherMgmFacadeRemote;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.EmployeeDetailGrid;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.delegate.PayrollTransactionsManagementDelegate;
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
 *
 * @author Sudhir Kr Bisht
 */
public class SalaryProcessing extends BaseBean {

    private static final Logger logger = Logger.getLogger(SalaryProcessing.class);
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private String message;
    private String yearFromDate;
    private String yearToDate;
    private int compCode;
    private String processingCateory;
    private String categorizationDetails;
    private boolean disableCategorizeDetails;
    private String employeeWise = "false";
    private String empSearchCriteria;
    private String empSearchValue;
    private int totalEmpRecords;
    private String monthCategory;
    private String showGrid;
    private SalaryProcessingGrid currentGridItem;
    private EmployeeDetailGrid currentItem;
    private List<SelectItem> processingCateoryList;
    private List<SelectItem> categorizationDetailsList;
    private List<SelectItem> monthCategoryList;
    private List<EmployeeDetailGrid> empSearchTable;
    private List<SalaryProcessingGrid> salaryProcessingGrid;
    private List<HrSalaryProcessingTO> salaryProcessingList;
    private PayrollTransactionsManagementDelegate payRollDelegate;
    private PayrollTransactionsFacadeRemote payrollRemote;
    private PayrollOtherMgmFacadeRemote payrollOthRemote;
    private List<SelectItem> yearList;
    private String year;
    private CommonReportMethodsRemote commomRemote;
    private List<SalaryRegisterPojo> salaryListReport;
    
    

    /**
     * Creates a new instance of ArrearProcessing
     */
    public SalaryProcessing() {
        try {
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
            payrollOthRemote = (PayrollOtherMgmFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollOtherMgmFacade");
            commomRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            compCode = Integer.parseInt(getOrgnBrCode());
            getFinancialYear();
            getInitalData();
            payRollDelegate = new PayrollTransactionsManagementDelegate();
            categorizationDetailsList = new ArrayList<SelectItem>();
            categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));
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
                yearToDate   =   ele[1].toString().trim();
            }
//            PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
//            List<PayrollCalendarTO> payrollCalendarTOs = masterManagementDelegate.getFinancialYear(compCode);
//            if (!payrollCalendarTOs.isEmpty()) {
//                for (PayrollCalendarTO payrollCalendarTO : payrollCalendarTOs) {
//                    this.setYearFromDate(formatter.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateFrom()));
//                    this.setYearToDate(formatter.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateTo()));
//                }
//            }
//        } catch (WebException e) {
//            logger.error("Exception occured while executing method getFinancialYear()", e);
//            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
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
            PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
            processingCateoryList = new ArrayList<SelectItem>();
            processingCateoryList.add(new SelectItem("0", "--SELECT--"));
            processingCateoryList.add(new SelectItem("EWE", "EMPLOYEE WISE"));
            processingCateoryList.add(new SelectItem("BRN", "BRANCH WISE"));
            List<HrMstStructTO> HrMstStructTOs = masterManagementDelegate.getInitialData(compCode, "CHO%");
            if (!HrMstStructTOs.isEmpty()) {
                for (HrMstStructTO HrMstStructTO : HrMstStructTOs) {
                    processingCateoryList.add(new SelectItem(HrMstStructTO.getDescription().substring(0, 3), HrMstStructTO.getDescription()));
                }
            }
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
             
            salaryListReport = new ArrayList<SalaryRegisterPojo>();
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
            if (processingCateory.equalsIgnoreCase("EWE")) {
                employeeWise = "true";
                disableCategorizeDetails = true;
                this.setMessage("");
            } else if (processingCateory.equalsIgnoreCase("0")) {
                employeeWise = "false";
                disableCategorizeDetails = false;
                this.setMessage("Please Select Categorization !!");
            } else if (processingCateory.equalsIgnoreCase("BRN")) {
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
                String structCode = this.processingCateory + "%";
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

    /**
     * function to set the employee details on selecting the employee details
     * and print the grid of tax processing for the financial year
     *
     */
    public void setEmpRowValues() {
        categorizationDetailsList.clear();
        categorizationDetailsList.add(new SelectItem(currentItem.getEmpId(), currentItem.getEmpName()));
    }

    public void salaryPosting() {
        try {
            if (salaryProcessingList.isEmpty()) {
                this.setMessage("There is no data exists for posting");
            } else {
                if (monthCategory.equalsIgnoreCase("0")) {
                    this.setMessage("Please select the month category!!");
                    return;
                }

                if (this.year == null || this.year.equals("0")) {
                    this.setMessage("Please select the year!!");
                    return;
                }

                Date fstDt = formatter.parse("01" + "/" + com.cbs.constant.Months.getMonthValue(monthCategory) + "/" + year);
                Date lastDt = Util.calculateMonthEndDate(Integer.parseInt(com.cbs.constant.Months.getMonthValue(monthCategory)),
                Integer.parseInt(year));

                String result = payRollDelegate.salaryPosting(salaryProcessingList, processingCateory, categorizationDetails, getUserName(), fstDt, lastDt);
                refresh();
                if (result.equalsIgnoreCase("True")) {
                    this.setMessage("Salary is successfully posted for selected crietria");
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method postSalaryButton()", e);
            this.setMessage(e.getMessage());
        }
    }

    /**
     * set the employee wise data on click of find button in page
     */
    public void getEmployeeDetails() {
        try {
            PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();
            empSearchTable = new ArrayList<EmployeeDetailGrid>();
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
     * Function handles the salary processed and saved either by employee wise
     * or category wise. It executes deletion process to on DELETE mode
     *
     */
    public void salaryCalculation() {
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
            salaryProcessingList = new ArrayList<HrSalaryProcessingTO>();
            if (disableCategorizeDetails == true) {
                salaryProcessingList = payrollOthRemote.salaryCalculation(hrSalaryProcessingTO, processingCateory, currentItem.getEmpId(), this.getMonthCategory());
            } else {
                salaryProcessingList = payrollOthRemote.salaryCalculation(hrSalaryProcessingTO, processingCateory, categorizationDetails, this.getMonthCategory());
            }
            if (salaryProcessingList.isEmpty()) {
                setMessage("Problem in salary calculation. Please check attendance details and salary allocation.");
                setShowGrid("false");
            }
            salaryProcessingGrid = new ArrayList<SalaryProcessingGrid>();
            SalaryProcessingGrid salGrid;
            
           
             
            for (HrSalaryProcessingTO salaryProcessingTO : salaryProcessingList) {
                
//              salGrid = new SalaryProcessingGrid();
//              salGrid.setEmployeeId(salaryProcessingTO.getHrPersonnelDetailsTO().getEmpId());
//              salGrid.setEmployeeName(salaryProcessingTO.getHrPersonnelDetailsTO().getEmpName());
//              salGrid.setCompType("BASIC");
//              salGrid.setCompAmt(BigDecimal.valueOf(Double.valueOf(salaryProcessingList.get(0).getBasicSalary())));
//              salGrid.setMonth(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
//              salGrid.setGrossSalary(0.0);
//              salGrid.setIncomeTax(0.0);
//              salGrid.setNetSalary(0.0);
//              salaryProcessingGrid.add(salGrid);
                
                for (int i = 0; i < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); i++) {
                    if (!salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR003")) {
                        salGrid = new SalaryProcessingGrid();
                        salGrid.setEmployeeId(salaryProcessingTO.getHrPersonnelDetailsTO().getEmpId());
                        salGrid.setEmployeeName(salaryProcessingTO.getHrPersonnelDetailsTO().getEmpName());
                        salGrid.setCompType(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getComponentType());
                        salGrid.setCompAmt(new BigDecimal(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary()));
                        salGrid.setMonth(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                        salGrid.setGrossSalary(0.0);
                        salGrid.setIncomeTax(0.0);
                        salGrid.setNetSalary(0.0);
                        salaryProcessingGrid.add(salGrid);
                    }
                }
                salGrid = new SalaryProcessingGrid();
                salGrid.setEmployeeId("");
                salGrid.setEmployeeName("");
                salGrid.setCompType("");
                salGrid.setCompAmt(new BigDecimal(0));
                salGrid.setMonth("Total");
                salGrid.setGrossSalary(salaryProcessingTO.getGrossSalary());
                salGrid.setIncomeTax(salaryProcessingTO.getDeductiveTax());
                salGrid.setNetSalary(salaryProcessingTO.getSalary());
                salaryProcessingGrid.add(salGrid);
            }
            setShowGrid("true");

              List<String> compName = new ArrayList<String>();
              List<String> EmpcompNameList = new ArrayList<String>();
              int count= 0;
               
             for(int k=0;k<salaryProcessingList.size();k++){
                  for(int j=0;j<salaryProcessingList.get(k).getHrSalaryProcessingDetailTO().size();j++){
                    if(!compName.contains(salaryProcessingList.get(k).getHrSalaryProcessingDetailTO().get(j).getHrSalaryProcessingDetailPK().getComponentType().toString())){
                   compName.add(salaryProcessingList.get(k).getHrSalaryProcessingDetailTO().get(j).getHrSalaryProcessingDetailPK().getComponentType().toString());
                   count++; 
                    } 
                  }  
               }
     
            salaryListReport.clear();
            for(int i=0;i<salaryProcessingList.size();i++){ 
               
                for(int j=0;j<salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().size();j++){
                     EmpcompNameList.add(salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getHrSalaryProcessingDetailPK().getComponentType());
                 }
                
          
               
                
            for(int j=0;j<salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().size();j++){
                SalaryRegisterPojo salrp1 = new SalaryRegisterPojo(); 
                salrp1.setEmpId(salaryProcessingList.get(i).getHrPersonnelDetailsTO().getEmpId());
                salrp1.setEmpCode(String.valueOf(salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getHrSalaryProcessingDetailPK().getEmpCode()));
                salrp1.setNetpayAmt(salaryProcessingList.get(i).getSalary());
                salrp1.setEmpName(salaryProcessingList.get(i).getHrPersonnelDetailsTO().getEmpName());
                if(compName.contains(salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getHrSalaryProcessingDetailPK().getComponentType().toString())){
                        
               if(salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getPurCode().equalsIgnoreCase("PUR001")){
                 salrp1.setComponentName(salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getHrSalaryProcessingDetailPK().getComponentType());
                 salrp1.setComponentAmount(salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getSalary());
                 salrp1.setPurposeCode(salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getPurCode());
                 salrp1.setPurposeType("1.E");
                 salrp1.setCompCount(count);
                
      
             }else if(salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getPurCode().equalsIgnoreCase("PUR002") || salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getPurCode().equalsIgnoreCase("PUR003")){
                 salrp1.setComponentName(salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getHrSalaryProcessingDetailPK().getComponentType());
                 salrp1.setComponentAmount(0.00 - salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getSalary());
                 salrp1.setPurposeCode(salaryProcessingList.get(i).getHrSalaryProcessingDetailTO().get(j).getPurCode().toString());
                 salrp1.setPurposeType("2.D");
                 salrp1.setCompCount(count);
                  
             }           
        
            } 
                  salaryListReport.add(salrp1);
                  EmpcompNameList.clear();
            }             
          }
         
        } catch (Exception e) {
            
            logger.error("Exception occured while executing method saveSalaryProcessing()", e);
            this.setMessage(e.getLocalizedMessage());
            setShowGrid("false");
        }
    }

    /**
     * validations check
     *
     * @return
     */
    public String validations() {
        if (monthCategory.equalsIgnoreCase("0")) {
            return "Please select the month category!!";
        }
        if (processingCateory.equalsIgnoreCase("0")) {
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
        setShowGrid("false");
        this.setMessage("");
        this.setProcessingCateory("0");
        this.setCategorizationDetails("0");
        this.setMonthCategory("0");
        disableCategorizeDetails = true;
        categorizationDetailsList.clear();
        categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));
    }

    public void getSalRegisterRep(){
       try{ 
        salaryCalculation();
        String EmpBranch ="";
         int c= salaryListReport.get(0).getCompCount();
        System.out.println(c);
        String BankName=null;
          String ReportName="MonthlySalaryRegisterProjection";
          Map fillParams = new HashMap();
          BankName = commomRemote.getBankName(); 
          List BranchLocation= new ArrayList();
           if (disableCategorizeDetails == true) {
             EmpBranch = payrollOthRemote.getEmpBranchCode(currentItem.getEmpId());
             System.out.println(EmpBranch);
             BranchLocation= commomRemote.getAlphacodeAllAndBranch(EmpBranch);
           }else{
           BranchLocation= commomRemote.getAlphacodeAllAndBranch(categorizationDetails);           
           }
         
          Vector vec = (Vector)BranchLocation.get(0);
               fillParams.put("pBankName",BankName);
               fillParams.put("pBankAdd", vec.get(0).toString());
               fillParams.put("pMonth",monthCategory);
               fillParams.put("pYear",year);
               fillParams.put("pReportName",ReportName);
               if(c>20){
               new ReportBean().openPdf("MonthlySalaryRegisterProjection","MonthlySalaryRegisterReportBefore30Comp", new JRBeanCollectionDataSource(salaryListReport),fillParams);
               ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
               HttpSession httpSession = getHttpSession();
               httpSession.setAttribute("ReportName", "MonthlySalaryRegisterProjection");   
               }else{
               new ReportBean().openPdf("MonthlySalaryRegisterProjection","MonthlySalaryRegisterReportBefore16comp", new JRBeanCollectionDataSource(salaryListReport),fillParams);
               ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
               HttpSession httpSession = getHttpSession();
               httpSession.setAttribute("ReportName", "MonthlySalaryRegisterProjection"); 
               }
               
       }catch(Exception e){
           this.setMessage(e.getMessage());
       }
    }
    
    
    
    public String btnExitAction() {
        refresh();
        return "case1";
    }

    public SalaryProcessingGrid getCurrentGridItem() {
        return currentGridItem;
    }

    public void setCurrentGridItem(SalaryProcessingGrid currentGridItem) {
        this.currentGridItem = currentGridItem;
    }

    public List<SalaryProcessingGrid> getSalaryProcessingGrid() {
        return salaryProcessingGrid;
    }

    public void setSalaryProcessingGrid(List<SalaryProcessingGrid> salaryProcessingGrid) {
        this.salaryProcessingGrid = salaryProcessingGrid;
    }

    public boolean isDisableCategorizeDetails() {
        return disableCategorizeDetails;
    }

    public void setDisableCategorizeDetails(boolean disableCategorizeDetails) {
        this.disableCategorizeDetails = disableCategorizeDetails;
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

    public EmployeeDetailGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(EmployeeDetailGrid currentItem) {
        this.currentItem = currentItem;
    }

    public String getEmpSearchCriteria() {
        return empSearchCriteria;
    }

    public void setEmpSearchCriteria(String empSearchCriteria) {
        this.empSearchCriteria = empSearchCriteria;
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

    public String getEmployeeWise() {
        return employeeWise;
    }

    public void setEmployeeWise(String employeeWise) {
        this.employeeWise = employeeWise;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public PayrollTransactionsManagementDelegate getPayRollDelegate() {
        return payRollDelegate;
    }

    public void setPayRollDelegate(PayrollTransactionsManagementDelegate payRollDelegate) {
        this.payRollDelegate = payRollDelegate;
    }

    public String getProcessingCateory() {
        return processingCateory;
    }

    public void setProcessingCateory(String processingCateory) {
        this.processingCateory = processingCateory;
    }

    public List<SelectItem> getProcessingCateoryList() {
        return processingCateoryList;
    }

    public void setProcessingCateoryList(List<SelectItem> processingCateoryList) {
        this.processingCateoryList = processingCateoryList;
    }

    public String getShowGrid() {
        return showGrid;
    }

    public void setShowGrid(String showGrid) {
        this.showGrid = showGrid;
    }

    public int getTotalEmpRecords() {
        return totalEmpRecords;
    }

    public void setTotalEmpRecords(int totalEmpRecords) {
        this.totalEmpRecords = totalEmpRecords;
    }

    public List<SelectItem> getYearList() {
        return yearList;
    }

    public void setYearList(List<SelectItem> yearList) {
        this.yearList = yearList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
