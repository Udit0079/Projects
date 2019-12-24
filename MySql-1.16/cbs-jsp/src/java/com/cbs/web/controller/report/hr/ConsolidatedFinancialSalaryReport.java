/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.hr;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.ConsolidatedFinancialSalaryPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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
public class ConsolidatedFinancialSalaryReport  extends BaseBean{
    private String message;
    private static final Logger logger = Logger.getLogger(FinancialSalaryProjection.class);
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private String projectionCateory;
 
    private List<HrSalaryProcessingTO> salaryProcessingList;
    private PayrollTransactionsManagementDelegate payRollDelegate;
    private PayrollTransactionsFacadeRemote payrollRemote;
    private CommonReportMethodsRemote commomRemote;
    private List<SalaryProcessingGrid> salaryProcessingGrid;
   
    private Date pageFromDate;
    private Date pageToDate;
    private List<ConsolidatedFinancialSalaryPojo> repErnList;
    
    public ConsolidatedFinancialSalaryReport(){
        try{
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");           
            commomRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
       
            payRollDelegate = new PayrollTransactionsManagementDelegate();    
      
            repErnList = new ArrayList<ConsolidatedFinancialSalaryPojo>();
        }catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
      
      public void financialProjectionRep() {
       try{    
        String errorResult = validations();
            if (!errorResult.equalsIgnoreCase("true")) {
                this.setMessage(errorResult);
                return;
            }
            
         repErnList = payrollRemote.consolidatedFinancialSalaryReport(ymd.format(pageFromDate),ymd.format(pageToDate));   
         if(!repErnList.isEmpty()){
        int c= repErnList.get(0).getCompCount();
        String BankName=null;
          String ReportName="Consolidated Financial Salary Report";
          Map fillParams = new HashMap();
          BankName = commomRemote.getBankName(); 
           List BranchLocation = new ArrayList();         
              BranchLocation= commomRemote.getAlphacodeAllAndBranch("90");
               Vector vec = (Vector)BranchLocation.get(0);
               fillParams.put("pBankName",BankName);
               fillParams.put("pBankAdd", vec.get(0).toString());
               fillParams.put("pMonth",dmy.format(getPageFromDate()));
               fillParams.put("pYear",dmy.format(getPageToDate()));
               fillParams.put("pReportName",ReportName);
               if(c>20){
                new ReportBean().openPdf("Consolidated Financial Salary Report","CONSOLIDATEDFINANCIALReport30Comp", new JRBeanCollectionDataSource(repErnList),fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
               HttpSession httpSession = getHttpSession();
               httpSession.setAttribute("ReportName", "Consolidated Financial Salary Report");   
               }else{
                new ReportBean().openPdf("Consolidated Financial Salary Report","CONSOLIDATEDFINANCIALReport16comp", new JRBeanCollectionDataSource(repErnList),fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
               HttpSession httpSession = getHttpSession();
               httpSession.setAttribute("ReportName", "Consolidated Financial Salary Report"); 
               }
               
         }else{
             this.setMessage("Data doesn't exist");
         }
       }catch(Exception e){
           this.setMessage(e.getMessage());
       }
      
   }
     
      public String validations() {
        setMessage("");
        if(pageFromDate == null){
            return "Please select Year From Date!!";
        }
        if(pageToDate == null){
            return "Please select Year To Date!!";
        }
         return "true";
    }

    /**
     * Function to clear the forms values
     */
    public void refresh() {
        this.setMessage("");      
        this.setPageFromDate(null);
        this.setPageToDate(null);
    }

    public String btnExitAction() {
        refresh();
        return "case1";
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

    
}
