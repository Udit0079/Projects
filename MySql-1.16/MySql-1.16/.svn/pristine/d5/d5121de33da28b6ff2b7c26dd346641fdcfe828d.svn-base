/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.hr;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.FinancialSalProjectionPojo;
import com.cbs.pojo.SalarySheetPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
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
import java.text.ParseException;
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
 * @author root
 */
public class FinancialSalaryProjection extends BaseBean{
    private String message;
    private static final Logger logger = Logger.getLogger(FinancialSalaryProjection.class);
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private String projectionCateory;
    private List<SelectItem> projectionCateoryList;
    private String categorizationDetails;
    private boolean disableCategorizeDetails;
    private List<SelectItem> categorizationDetailsList;
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
    private boolean disableMonth;
    private Date pageFromDate;
    private Date pageToDate;
    private List<FinancialSalProjectionPojo> repErnList;
    private List<FinancialSalProjectionPojo>  repDedList;
    String retirementDate ="";
    
    
    public FinancialSalaryProjection(){
        try{
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");           
            commomRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            compCode = Integer.parseInt(getOrgnBrCode());  
            payRollDelegate = new PayrollTransactionsManagementDelegate();    
            categorizationDetailsList = new ArrayList<SelectItem>();
            categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));        
            getInitalData();
            getFinancialYear();
            repErnList = new ArrayList<FinancialSalProjectionPojo>();
            repDedList = new ArrayList<FinancialSalProjectionPojo>();
        }catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
      
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
     
     
      public void financialProjectionRep() {
       try{    
        String errorResult = validations();
            if (!errorResult.equalsIgnoreCase("true")) {
                this.setMessage(errorResult);
                return;
            }
             
//        repErnList = payrollRemote.getErnFinancialPrjnData(currentItem.getEmpId(),ymd.format(dmy.parse(yearFromDate)),ymd.format(dmy.parse(yearToDate)),"1:Gross Earning");   
//        repDedList =payrollRemote.getErnFinancialPrjnData(currentItem.getEmpId(),ymd.format(dmy.parse(yearFromDate)),ymd.format(dmy.parse(yearToDate)),"2:Gross Deduction");
       
        repErnList = payrollRemote.getErnFinancialPrjnData(currentItem.getEmpId(),ymd.format(pageFromDate),ymd.format(pageToDate),"1:Gross Earning");   
        repDedList =payrollRemote.getErnFinancialPrjnData(currentItem.getEmpId(),ymd.format(pageFromDate),ymd.format(pageToDate),"2:Gross Deduction");
        
        for(int i=0;i<repDedList.size();i++){
            repErnList.add(repDedList.get(i));
        }
          
          List brNameAndAddList=null;
          Map fillParams = new HashMap();
           brNameAndAddList = commomRemote.getBranchNameandAddress(commomRemote.getBranchCode(currentItem.getEmpId())); 
             fillParams.put("pBankName", brNameAndAddList.get(0).toString());
             fillParams.put("pBankAdd", brNameAndAddList.get(1).toString());
             fillParams.put("pFromDate",dmy.format(pageFromDate));
             fillParams.put("pToDate", dmy.format(pageToDate));
             fillParams.put("pEmpId", currentItem.getEmpId().substring(3));
             fillParams.put("pEmpName",currentItem.getEmpName());
            
               new ReportBean().openPdf("financial_salary_projection", "financial_salary_projection", new JRBeanCollectionDataSource(repErnList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
               HttpSession httpSession = getHttpSession();
               httpSession.setAttribute("ReportName", "financial_salary_projection");
            
       } catch (Exception e) {
            logger.error("Exception occured while executing method saveSalaryProjection()", e);
            this.setMessage(e.getLocalizedMessage());
        }   
   }
     
      public String validations() {
        setMessage("");
       
       
//        if (projectionCateory.equalsIgnoreCase("0")) {
//            return "Please select the processing category!!";
//        }
//        if (disableCategorizeDetails == false) {
//            if (categorizationDetails.equalsIgnoreCase("0")) {
//                return "Please select the categorization details!!";
//            }
//        }
        if(pageFromDate == null){
            return "Please select Year From Date!!";
        }
        if(pageToDate == null){
            return "Please select Year To Date!!";
        }
        if(!dmy.format(pageFromDate).equalsIgnoreCase(yearFromDate)){
            return "Invalid financial from date!!";
        }
        if(!dmy.format(pageToDate).equalsIgnoreCase(yearToDate)){
            return "Invalid financial to date!!";
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
        this.setPageFromDate(null);
        this.setPageToDate(null);

        disableCategorizeDetails = true;
        categorizationDetailsList.clear();
        categorizationDetailsList.add(new SelectItem("0", "--SELECT--"));
    }

    public String btnExitAction() {
        refresh();
        return "case1";
    }
     
     public void getInitalData() {
        try {
            projectionCateoryList = new ArrayList<SelectItem>();
            projectionCateoryList.add(new SelectItem("0", "--SELECT--"));
            projectionCateoryList.add(new SelectItem("EWE", "EMPLOYEE WISE"));
            
        }catch (Exception e) {
            this.setMessage(e.getMessage());
        }
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

    public PayrollTransactionsManagementDelegate getPayRollDelegate() {
        return payRollDelegate;
    }

    public void setPayRollDelegate(PayrollTransactionsManagementDelegate payRollDelegate) {
        this.payRollDelegate = payRollDelegate;
    }

    public PayrollTransactionsFacadeRemote getPayrollRemote() {
        return payrollRemote;
    }

    public void setPayrollRemote(PayrollTransactionsFacadeRemote payrollRemote) {
        this.payrollRemote = payrollRemote;
    }

    public CommonReportMethodsRemote getCommomRemote() {
        return commomRemote;
    }

    public void setCommomRemote(CommonReportMethodsRemote commomRemote) {
        this.commomRemote = commomRemote;
    }

    public List<SalaryProcessingGrid> getSalaryProcessingGrid() {
        return salaryProcessingGrid;
    }

    public void setSalaryProcessingGrid(List<SalaryProcessingGrid> salaryProcessingGrid) {
        this.salaryProcessingGrid = salaryProcessingGrid;
    }

    public boolean isDisableMonth() {
        return disableMonth;
    }

    public void setDisableMonth(boolean disableMonth) {
        this.disableMonth = disableMonth;
    }

}
