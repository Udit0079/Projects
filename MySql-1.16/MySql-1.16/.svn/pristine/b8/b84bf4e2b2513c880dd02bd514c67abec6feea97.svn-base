/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.hr;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.SalaryRegisterPojo;
import com.cbs.pojo.consolidatedMsrPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.payroll.PayrollOtherMgmFacadeRemote;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.utils.LocalizationUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class consolidatedMsr extends BaseBean{
     private String message;
  
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private CommonReportMethodsRemote commomRemote;
    private PayrollTransactionsFacadeRemote payrollRemote;
    private PayrollOtherMgmFacadeRemote payrollOthrMgm;
    private List<SelectItem> monthList;
    private static final Logger logger = Logger.getLogger(FinancialSalaryProjection.class);  
    private String yearFromDate;
    private String yearToDate;
    private String month;
    private List<SelectItem> yearList;
    private String year;
    private List<consolidatedMsrPojo> salaryRegisterDataList;
    
    
    public consolidatedMsr(){
     try {
        commomRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
        payrollRemote = (PayrollTransactionsFacadeRemote)HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
        payrollOthrMgm =(PayrollOtherMgmFacadeRemote)HrServiceLocator.getInstance().lookup("PayrollOtherMgmFacade");
        getFinancialYear();
        getInitalData();
        salaryRegisterDataList = new ArrayList();
        }catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
   }
   
     public void getInitalData(){
       try{           
       monthList = new ArrayList<SelectItem>();
       monthList.add(new SelectItem("0","--Select--"));
       monthList.add(new SelectItem("JANUARY","JANUARY"));
       monthList.add(new SelectItem("FEBRUARY","FEBRUARY"));
       monthList.add(new SelectItem("MARCH","MARCH"));
       monthList.add(new SelectItem("APRIL","APRIL"));
       monthList.add(new SelectItem("MAY","MAY"));
       monthList.add(new SelectItem("JUNE","JUNE"));  
       monthList.add(new SelectItem("JULY","JULY"));
       monthList.add(new SelectItem("AUGUST","AUGUST"));
       monthList.add(new SelectItem("SEPTEMBER","SEPTEMBER"));
       monthList.add(new SelectItem("OCTOBER","OCTOBER"));
       monthList.add(new SelectItem("NOVEMBER","NOVEMBER"));
       monthList.add(new SelectItem("DECEMBER","DECEMBER"));
       
       yearList = new ArrayList<SelectItem>();
       yearList.add(new SelectItem("0","--Select--"));
        List yrlist = new ArrayList();
        List list = payrollRemote.getYears(getOrgnBrCode());
         yrlist = payrollRemote.getYearList();
         for(int j=0;j<yrlist.size();j++){
             yearList.add(new SelectItem(yrlist.get(j).toString()));
         }
         
        for (int i = 0; i < list.size(); i++) {
            Object[] ele = (Object[]) list.get(0);
            yearFromDate = ele[0].toString().trim();
            yearToDate = ele[1].toString().trim();        
        } 
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
    
   public void getSalRegisterRep(){
     try{
        setMessage("");
        if(getMonth().equalsIgnoreCase("0")){
            setMessage("Please Select Month!!");
            return;
        }
        if(getYear().equalsIgnoreCase("0")){
            setMessage("Please Select Year!!");
            return;
        }
          
        salaryRegisterDataList = payrollOthrMgm.getConsolidatedMsrData(getMonth(),getYear());
        
        if(!salaryRegisterDataList.isEmpty()){
        int c= salaryRegisterDataList.get(0).getCompCount();
        System.out.println(c);
        String BankName=null;
          String ReportName="ConsolidatedMonthlySalaryRegisterReport";
          Map fillParams = new HashMap();
          BankName = commomRemote.getBankName(); 
           List BranchLocation = new ArrayList();
         
            BranchLocation= commomRemote.getAlphacodeAllAndBranch("90");
         
          Vector vec = (Vector)BranchLocation.get(0);
               fillParams.put("pBankName",BankName);
               fillParams.put("pBankAdd", vec.get(0).toString());
               fillParams.put("pMonth",getMonth());
               fillParams.put("pYear",getYear());
               fillParams.put("pReportName",ReportName);
              
                new ReportBean().openPdf("ConsolidatedMSRReport","ConsolidatedMSRReport30Comp", new JRBeanCollectionDataSource(salaryRegisterDataList),fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
               HttpSession httpSession = getHttpSession();
               httpSession.setAttribute("ReportName", "ConsolidatedMSRReport");   
        }else{
            this.setMessage("Data doestn't exist!!");
        }     
               
       }catch(Exception e){
           this.setMessage(e.getMessage());
       }
   }
     
     
     public void refresh() {
      this.setMessage("");
      this.setMonth("0");
      this.setYear("0");
    }   
   
   public String btnExitAction() {
        refresh();
        return "case1";
    }

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
  
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
