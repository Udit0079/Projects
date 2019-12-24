/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.NpciInwardNonaadhaarPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class NonAadhaarAccountValidation extends BaseBean{
    private String message;
    private String status;
    private List<SelectItem> statusList;
    private String frdt;
    private String todt;
    private CommonReportMethodsRemote common ;
    private OtherMasterFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<NpciInwardNonaadhaarPojo> reportList = new ArrayList<NpciInwardNonaadhaarPojo>();

   
    
    
    public NonAadhaarAccountValidation() throws ServiceLocatorException {
        try{
         frdt = "";
         todt = "";
         common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
         remoteFacade = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
         
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("0", "--Select--"));
            statusList.add(new SelectItem("All" ,"All"));
            statusList.add(new SelectItem("S", "Success"));
            statusList.add(new SelectItem("U", "UnSuccess"));
        }catch(Exception e){
            this.setMessage(e.getMessage());
        }
    }
     public void btnHtmlAction(){
         try{
             String report = "Npci Ac Validation Report";
            if (frdt == null || frdt.trim().equals("")) {
                    this.setMessage("Please fill the from date.");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(frdt)) {
                    this.setMessage("Please fill proper from date.");
                    return;
                }
                if (dmy.parse(frdt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setMessage("From date can not be greater than current date.");
                    return;
                }
                if (todt == null || todt.trim().equals("")) {
                    this.setMessage("Please fill the to date .");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(todt)) {
                    this.setMessage("Please fill proper to date.");
                    return;
                }
                if (dmy.parse(todt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setMessage("To date can not be greater than current date.");
                    return;
                }
                if (dmy.parse(frdt).compareTo(dmy.parse(todt)) > 0) {
                    this.setMessage("From date can not be greater than to date.");
                    return;
                }
             
           List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", frdt + " to " + todt);
            if(status.equalsIgnoreCase("U")){
                fillParams.put("pReportType", "Unsuccess"); 
            }else if(status.equalsIgnoreCase("S")){
                 fillParams.put("pReportType", "Success");
            }else if(status.equalsIgnoreCase("All")){
                  fillParams.put("pReportType", "All");
            }
            fillParams.put("pDate",getTodayDate());
            reportList=remoteFacade.getNpciInwardNonAadharDetails(this.frdt,this.todt,this.status);
            if(reportList.isEmpty()){
                throw new ApplicationException("Data does not exits.");
            }
              new ReportBean().jasperReport("NpciAcValidationReport", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "NpciAcValidationReport");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
             
         }catch(Exception ex){
             this.setMessage(ex.getMessage());
         }
 }
     
     public void viewPdfReport(){
         try{
             String report = "Npci Ac Validation Report";
             if (frdt == null || frdt.trim().equals("")) {
                    this.setMessage("Please fill the from date.");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(frdt)) {
                    this.setMessage("Please fill proper from date.");
                    return;
                }
                if (dmy.parse(frdt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setMessage("From date can not be greater than current date.");
                    return;
                }
                if (todt == null || todt.trim().equals("")) {
                    this.setMessage("Please fill the to date .");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(todt)) {
                    this.setMessage("Please fill proper to date.");
                    return;
                }
                if (dmy.parse(todt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setMessage("To date can not be greater than current date.");
                    return;
                }
                if (dmy.parse(frdt).compareTo(dmy.parse(todt)) > 0) {
                    this.setMessage("From date can not be greater than to date.");
                    return;
                }
             
           List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", frdt + " to " + todt);
            if(status.equalsIgnoreCase("U")){
                fillParams.put("pReportType", "Unsuccess"); 
            }else if(status.equalsIgnoreCase("S")){
                 fillParams.put("pReportType", "Success");
            }else if(status.equalsIgnoreCase("All")){
                  fillParams.put("pReportType", "All");
            }
            fillParams.put("pDate",getTodayDate());
            reportList=remoteFacade.getNpciInwardNonAadharDetails(this.frdt,this.todt,this.status);
            if(reportList.isEmpty()){
                throw new ApplicationException("Data does not exits.");
            }
             new ReportBean().openPdf("Npci Ac Validation Report", "NpciAcValidationReport", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

         }catch(Exception ex){
             this.setMessage(ex.getMessage());
         }
 }
     
      public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
    
    public void btnRefreshAction() {
        setMessage("");
       this.setStatus("0");
        setFrdt("");
        setTodt("");
    }     
    
     public String btnExitAction() {
        return "case1";
    }

    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrdt() {
        return frdt;
    }

    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTodt() {
        return todt;
    }

    public void setTodt(String todt) {
        this.todt = todt;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }
    
 }
