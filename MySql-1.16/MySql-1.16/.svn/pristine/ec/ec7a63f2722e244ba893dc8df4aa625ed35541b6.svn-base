/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.hr;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.GPFRegisterPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.payroll.PayrollOtherMgmFacadeRemote;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.utils.LocalizationUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
 

/**
 *
 * @author root
 */
public class GpfRegister extends BaseBean {
   private String message;
   private List<SelectItem> monthList;
   private static final Logger logger = Logger.getLogger(FinancialSalaryProjection.class);
   private PayrollTransactionsFacadeRemote payrollRemote;
   private String yearFromDate;
   private String yearToDate;
   private String month;
   private List<SelectItem> yearList;
   private String year;
   private PayrollOtherMgmFacadeRemote payrollOtrRemote;
   List<GPFRegisterPojo> gpfRegisterList;
   private CommonReportMethodsRemote commomRemote;
   private String  todayDate;
  
   public GpfRegister(){
       try{
         commomRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");  
         payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
         payrollOtrRemote= (PayrollOtherMgmFacadeRemote)HrServiceLocator.getInstance().lookup("PayrollOtherMgmFacade");
         gpfRegisterList = new ArrayList<>();
         getInitalData();
         todayDate = getTodayDate() ;
       }catch (Exception e) {
            this.setMessage(e.getMessage());
        }
   }
   
   public void getInitalData(){
       try{
       monthList = new ArrayList<SelectItem>();
       monthList.add(new SelectItem("0","--Select--"));
       monthList.add(new SelectItem("January","January"));
       monthList.add(new SelectItem("February","February"));
       monthList.add(new SelectItem("March","March"));
       monthList.add(new SelectItem("April","April"));
       monthList.add(new SelectItem("May","May"));
       monthList.add(new SelectItem("June","June"));
       monthList.add(new SelectItem("July","July"));
       monthList.add(new SelectItem("August","August"));
       monthList.add(new SelectItem("September","September"));
       monthList.add(new SelectItem("October","October"));
       monthList.add(new SelectItem("November","November"));
       monthList.add(new SelectItem("December","December"));
       
       yearList = new ArrayList<SelectItem>();
       yearList.add(new SelectItem("0","--Select--"));
       List yrlist = new ArrayList();
        List list = payrollRemote.getYears(getOrgnBrCode());
         yrlist = payrollRemote.getYearList();
         for(int j=0;j<yrlist.size();j++){
             yearList.add(new SelectItem(yrlist.get(j).toString()));
         }
       
      //   List list = payrollRemote.getYears(getOrgnBrCode());
         
            for (int i = 0; i < list.size(); i++) {
                Object[] ele = (Object[]) list.get(0);
                yearFromDate = ele[0].toString().trim();
                yearToDate = ele[1].toString().trim();
//                if (yearFromDate.equalsIgnoreCase(yearToDate)) {
//                    yearList.add(new SelectItem(yearFromDate));
//                } else {
//                    yearList.add(new SelectItem(yearFromDate));
//                    yearList.add(new SelectItem(yearToDate));
//                }           
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
    
       public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
     
     
       
       
    public void gpfRegisterRep(){
        try{
           Pattern p = Pattern.compile("[0-9]*");
          
            if (month == null || month.equalsIgnoreCase("0")) {
                setMessage("Please select month !");
                return;
            }
            if (year == null || year.equalsIgnoreCase("")) {
                setMessage("Year can not be blank.");
                return;
            }
            Matcher m = p.matcher(year);
            if (m.matches() != true) {
                setMessage("Please fill proper Year.");
                return;
            }
          
          gpfRegisterList = payrollOtrRemote.getGpfRegisterData(month, year,todayDate);
           
          String ReportName = "GPF Register";
          Map fillParams = new HashMap();
          String BankName = commomRemote.getBankName(); 
             fillParams.put("pBankName",BankName);
             fillParams.put("pReportName",ReportName);
             fillParams.put("pYearFrom",yearFromDate);
             fillParams.put("pYearTo", yearToDate);
             fillParams.put("pMonth", month);
             fillParams.put("pYear", year);
                new ReportBean().openPdf(ReportName,"GPF_Register_Report", new JRBeanCollectionDataSource(gpfRegisterList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
               HttpSession httpSession = getHttpSession();
               httpSession.setAttribute("ReportName",ReportName);           
           
        }catch(Exception ex){          
            setMessage(ex.getMessage());
        }
    } 
    
    
    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
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
    
    
   public void refresh() {
      this.setMessage("");
      this.setMonth("0");
      this.setYear("0");
    }   
   
   public String btnExitAction() {
        refresh();
        return "case1";
    }
   
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
   
}
